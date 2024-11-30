package timewheel;

import timewheel.task.SimpleTask;
import timewheel.task.Task;
import timewheel.task.TaskExecutor;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 16:47
 */
public class OuterSlot implements Slot {
    private TimeWheelLevel nextLevel;  // 存储指向下一层时间轮的引用

    public OuterSlot(TimeWheelLevel nextLevel) {
        this.nextLevel = nextLevel;
    }

    @Override
    public void addTask(SimpleTask task) {
        // 计算任务应放入下一层时间轮的哪个槽
        if (nextLevel != null) {
            int slotInterval = nextLevel.getSlotInterval();
            int slotCount = nextLevel.getSlots().size();

            // 根据任务的延迟时间，计算目标槽
            int targetSlotIndex = (nextLevel.getCurrentSlotIndex() + task.getDelayInSeconds() / slotInterval) % slotCount;
            nextLevel.getSlot(targetSlotIndex).addTask(task);  // 将任务递归地添加到下一层
        }
    }

    @Override
    public void executeTasks(TaskExecutor executor) {
        // 外层的槽不执行任务
        throw new UnsupportedOperationException("OuterSlot cannot execute tasks.");
    }

    @Override
    public boolean hasTasks() {
        return nextLevel != null && nextLevel.hasTasks();  // 如果下一层有任务，则返回true
    }

    public TimeWheelLevel getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(TimeWheelLevel nextLevel) {
        this.nextLevel = nextLevel;
    }
}
