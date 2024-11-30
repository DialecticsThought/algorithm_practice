package timewheel;

import timewheel.task.SimpleTask;
import timewheel.task.Task;
import timewheel.task.TaskExecutor;

import java.util.List;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 16:45
 */
public class MultiLevelTimeWheel {
    private final TimeWheelLevel rootLevel;  // 最外层时间轮

    public MultiLevelTimeWheel(List<Integer> layerSlots, int innerSlotInterval) {
        TimeWheelLevel currentLevel = null;
        for (int i = layerSlots.size() - 1; i >= 0; i--) {
            int slotCount = layerSlots.get(i);
            // 最内层的时间槽间隔由参数提供，外层的时间槽间隔依赖于下一层的时间轮
            int slotInterval = (i == layerSlots.size() - 1) ? innerSlotInterval : currentLevel.getTotalDuration();
            // 构建每一层时间轮
            currentLevel = new TimeWheelLevel(slotCount, slotInterval, currentLevel);
        }
        rootLevel = currentLevel;  // 根时间轮
    }

    /**
     * 将任务添加到时间轮中，任务根据延迟时间被放入正确的槽
     *
     * @param task
     * @param delayInSeconds
     */
    public void addTask(SimpleTask task, int delayInSeconds) {
        TimeWheelLevel currentLevel = rootLevel;
        // 遍历每一层时间轮，直到任务被添加到合适的槽
        while (currentLevel != null) {
            int slotInterval = currentLevel.getSlotInterval();
            int slotCount = currentLevel.getSlots().size();

            // 如果任务延迟时间在当前层的时间范围内
            if (delayInSeconds < slotCount * slotInterval) {
                // 计算任务应放入的目标槽
                int targetSlotIndex = (currentLevel.getCurrentSlotIndex() + delayInSeconds / slotInterval) % slotCount;
                // 将任务添加到目标槽
                currentLevel.getSlot(targetSlotIndex).addTask(task);
                return;
            }

            // 如果任务延迟时间超出了当前层的时间范围，递减延迟时间，并进入下一层
            delayInSeconds -= slotCount * slotInterval;
            currentLevel = currentLevel.getNextLevel();
        }
        // 如果延迟时间超过了所有层的时间容量，抛出异常
        throw new IllegalArgumentException("Delay exceeds the time wheel capacity.");
    }
    /**
     * 每次时钟“滴答”，推进每一层的时间，并执行当前槽的任务
     *
     * @param taskExecutor
     */
    public void tick(TaskExecutor taskExecutor) {
        TimeWheelLevel currentLevel = rootLevel;
        // 遍历每一层时间轮
        while (currentLevel != null) {
            // 获取当前槽
            Slot currentSlot = currentLevel.getSlot(currentLevel.getCurrentSlotIndex());
            // 如果当前槽有任务，执行任务
            if (currentSlot.hasTasks()) {
                currentSlot.executeTasks(taskExecutor);
            }
            // 推进当前层的槽指针
            currentLevel.advanceSlot();
            // 如果当前层已完成一圈，推进到下一层
            if (currentLevel.getCurrentSlotIndex() == 0 && currentLevel.getNextLevel() != null) {
                currentLevel = currentLevel.getNextLevel();
            } else {
                break;  // 如果当前层没有完成一圈，则退出
            }
        }
    }
}
