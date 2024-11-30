package timewheel.task;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:26
 */
public class RunnableTaskAdapter extends SimpleTask<Void> {
    private Runnable runnable;

    public RunnableTaskAdapter(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public Void execute() {
        // 执行 Runnable 任务
        runnable.run();
        return null;
    }

    @Override
    public void cancel() {
        System.out.println("Runnable task canceled.");
    }
}


