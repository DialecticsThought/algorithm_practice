package timewheel;

import timewheel.task.Task;
import timewheel.task.TaskExecutor;

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
    public void executeTasks(TaskExecutor executor) {
        for (Task task : tasks) {
            executor.execute(task);  // 使用任务执行器来执行任务
        }
        tasks.clear();  // 清空已执行的任务
    }

    @Override
    public boolean hasTasks() {
        return !tasks.isEmpty();
    }
}
