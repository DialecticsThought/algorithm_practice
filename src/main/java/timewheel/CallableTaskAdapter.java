package timewheel;

import java.util.concurrent.Callable;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:27
 */
public class CallableTaskAdapter<V> implements Task {
    private final Callable<V> callable;

    public CallableTaskAdapter(Callable<V> callable) {
        this.callable = callable;
    }

    @Override
    public void execute() {
        try {
            V result = callable.call();  // 执行 Callable 任务
            System.out.println("Callable executed, result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        System.out.println("Callable task canceled.");
    }
}