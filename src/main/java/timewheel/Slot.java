package timewheel;

import timewheel.task.SimpleTask;
import timewheel.task.Task;
import timewheel.task.TaskExecutor;

/**
 * 代表一个时间轮中的槽（父类）
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:55
 */
interface Slot {
    /**
     * 添加任务
     *
     * @param task
     */
    void addTask(SimpleTask task);
    /**
     * 执行任务
     */
    void executeTasks(TaskExecutor executor);
    /**
     * 判断该槽是否有任务
     *
     * @return
     */
    boolean hasTasks();

}
