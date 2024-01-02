import java.util.LinkedList;
import java.util.concurrent.*;

public class LayeredTimingWheel {
    // 三个时间轮，分别代表小时、分钟和秒
    // 小时轮：包含24个槽位，每个槽位代表一个小时
    private Wheel hourWheel;
    // 分钟轮：包含60个槽位，每个槽位代表一分钟
    private Wheel minuteWheel;
    // 秒轮：包含60个槽位，每个槽位代表一秒
    private Wheel secondWheel;
    // 任务执行线程池，用于执行到期的任务
    private ExecutorService taskExecutor;

    // 初始化时间轮和线程池
    public LayeredTimingWheel() {
        hourWheel = new Wheel(24);
        minuteWheel = new Wheel(60);
        secondWheel = new Wheel(60);
        int availableProcessors = Runtime.getRuntime().availableProcessors(); // 获取CPU核心数
        long keepAliveTime = 60; // 线程保活时间（秒）
        TimeUnit unit = TimeUnit.SECONDS;
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        // 核心线程数和最大线程数设置为CPU核心数
        taskExecutor = new ThreadPoolExecutor(availableProcessors, availableProcessors, keepAliveTime, unit, workQueue);
    }

    // 向时间轮添加任务
    public void addTask(Runnable task, int hour, int minute, int second) {
        // 首先将任务添加到小时轮的相应槽位
        hourWheel.addTask(() -> {
            // 当小时轮到达指定时间，任务被移动到分钟轮
            minuteWheel.addTask(() -> {
                // 当分钟轮到达指定时间，任务被移动到秒轮
                secondWheel.addTask(task, second);
            }, minute);
        }, hour);
    }

    // 时间轮类，管理一系列的槽位
    private class Wheel {
        private Slot[] slots; // 槽位数组，每个槽位可以包含多个任务
        private int currentSlot = 0; // 当前指针指向的槽位
        private int size; // 时间轮的大小（槽位数）

        public Wheel(int size) {
            this.size = size;
            this.slots = new Slot[size];
            for (int i = 0; i < size; i++) {
                slots[i] = new Slot(); // 初始化每个槽位
            }
        }

        // 向指定延迟后的槽位添加任务
        public void addTask(Runnable task, int delay) {
            int slotIndex = (currentSlot + delay) % size; // 计算任务应放置的槽位
            slots[slotIndex].addTask(task); // 添加任务到指定槽位
        }

        // 推进时间轮，执行当前槽位的任务
        public void advance() {
            slots[currentSlot].executeTasks(); // 执行当前槽位的所有任务
            currentSlot = (currentSlot + 1) % size; // 移动到下一个槽位
        }
    }

    // 槽位类，代表时间轮中的一个槽位
    private class Slot {
        private LinkedList<Runnable> tasks = new LinkedList<>(); // 存放在该槽位的任务队列

        // 向槽位添加任务
        public void addTask(Runnable task) {
            tasks.add(task);
        }

        // 执行槽位中的所有任务
        public void executeTasks() {
            for (Runnable task : tasks) {
                taskExecutor.submit(task); // 通过线程池执行任务
            }
            tasks.clear(); // 清空槽位中的任务队列
        }
    }

    // 模拟时间推进的方法
    public void tick() {
        secondWheel.advance(); // 推进秒轮
        if (secondWheel.currentSlot == 0) {
            minuteWheel.advance(); // 当秒轮完成一圈时，推进分钟轮
            if (minuteWheel.currentSlot == 0) {
                hourWheel.advance(); // 当分钟轮完成一圈时，推进小时轮
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LayeredTimingWheel timingWheel = new LayeredTimingWheel();

        // 添加几个测试任务
        timingWheel.addTask(() -> System.out.println("任务1: 执行于1小时0分钟10秒"), 1, 0, 10);
        timingWheel.addTask(() -> System.out.println("任务2: 执行于0小时30分钟5秒"), 0, 30, 5);
        timingWheel.addTask(() -> System.out.println("任务3: 执行于0小时10分钟20秒"), 0, 10, 20);

        // 模拟时间流逝
        int hour = 0, minute = 0, second = 0;
        while (hour < 2) { // 模拟两小时内的时间流逝
            timingWheel.tick();
            second++;
            if (second == 60) {
                minute++;
                second = 0;
            }
            if (minute == 60) {
                hour++;
                minute = 0;
            }
            System.out.println("当前时间: " + hour + "小时" + minute + "分钟" + second + "秒");
            Thread.sleep(1000); // 1秒钟代表时间流逝
        }
    }
}
