package timewheel;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:21
 */
public interface Task {
    void execute();  // 执行任务
    void cancel();   // 取消任务
}
