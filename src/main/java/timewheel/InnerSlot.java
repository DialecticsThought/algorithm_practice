package timewheel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 16:47
 */
public class InnerSlot implements Slot {
    private final List<Task> tasks;  // 存储任务的队列

    // 构造方法：初始化任务队列
    public InnerSlot() {
        this.tasks = new ArrayList<>();  // 使用ArrayList来存储任务
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);  // 将任务添加到队列中
    }

    @Override
    public void executeTasks() {
        List<Task> toExecute = new ArrayList<>(tasks);  // 复制任务列表以避免在执行过程中修改原列表
        tasks.clear();  // 清空已执行的任务
        for (Task task : toExecute) {
            task.execute();  // 执行每个任务
        }
    }
}
