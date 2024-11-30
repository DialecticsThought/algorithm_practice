package timewheel.task;

import java.util.concurrent.Callable;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:27
 */
public class CallableTaskAdapter<INPUT> extends SimpleTask {
    private Callable<INPUT> callable;

    public CallableTaskAdapter(Callable<INPUT> callable) {
        this.callable = (Callable<INPUT>) callable;
    }

    @Override
    public INPUT execute() {
        try {
            // 执行 Callable 任务
            INPUT result = callable.call();
            System.out.println("Callable executed, result: " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cancel() {
        System.out.println("Callable task canceled.");
    }
}
