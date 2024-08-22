package code_for_great_offer.class05;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProdCons_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i <= 5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i <= 5; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
    }
}

//资源类 所有资源的操作 都定义在类里面
class ShareData {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception {
        lock.lock();
        try {
            //判断
            while (number != 0) {
                //等待消费 不生产
                //阻塞
                condition.await();
            }
            //干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //通知唤醒 其他线程执行 -1 操作
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws Exception {

        lock.lock();
        try {
            //判断
            while (number == 0) {
                //等待消费 不生产
                //阻塞
                condition.await();
            }
            //干活
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //通知唤醒 其他线程执行 -1 操作
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    class MySource {
        public volatile boolean FLAG = true;
        public AtomicInteger atomicInteger;
        //定义一个 用来注入
        BlockingQueue<String> blockingQueue = null;

        //多态 传入一个接口实现类
        public MySource(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
            this.atomicInteger = new AtomicInteger(0);
            //System.out.println(blockingQueue.getClass().getName());
        }

        public void produce() {
            String data = null;
            boolean returnValve;

            while (FLAG) {
                //相当于num++
                //转成String
                data = atomicInteger.incrementAndGet() + "";
                //向阻塞队列塞入数据 如果队列满了2秒后 算超时 结束
                try {
                    returnValve = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);

                    if (returnValve) {
                        System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "成功");
                    } else {
                        System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "失败");
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t大老板叫停 表示FLAG=false,生产动作结束");
        }

        public void conumse() {
            String result = null;
            boolean returnValve;

            while (FLAG) {
                try {
                    result = blockingQueue.poll(2L, TimeUnit.SECONDS);

                    if (result == null||result.equals("")) {

                        FLAG = false;

                        System.out.println(Thread.currentThread().getName()+"\t 超过2s没有取到蛋糕 消费退出");
                        //表示 退出循环
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t大老板叫停 表示FLAG=false,生产动作结束");
        }
    }
}
