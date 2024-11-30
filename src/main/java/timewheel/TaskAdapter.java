package timewheel;

import java.util.concurrent.Callable;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:27
 */
public class TaskAdapter {
    public static Task adapt(Runnable runnable) {
        return new RunnableTaskAdapter(runnable);
    }

    public static <T> Task adapt(Callable<T> callable) {
        return new CallableTaskAdapter<>(callable);
    }
}
