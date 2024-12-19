package timewheel.task;

/**
 * @Description 任务执行器
 * @Author veritas
 * @Data 2024/11/30 12:04
 */
public interface TaskExecutor {
    /**
     * 执行任务
     * @param task
     */
    void execute(Task task);
}
