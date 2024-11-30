package timewheel;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:26
 */
public class RunnableTaskAdapter implements Task {
    private final Runnable runnable;

    public RunnableTaskAdapter(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void execute() {
        runnable.run();  // 执行 Runnable 任务
    }

    @Override
    public void cancel() {
        System.out.println("Runnable task canceled.");
    }
}


