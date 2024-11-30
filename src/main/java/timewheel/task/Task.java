package timewheel.task;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:21
 */
public interface Task<E> {
    /**
     * 执行任务
     */
    E execute();

    /**
     * 取消任务
     */
    void cancel();
}
