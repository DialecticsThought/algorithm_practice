package timewheel;

import org.springframework.core.task.TaskExecutor;

import java.util.List;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 16:45
 */
public class MultiLevelTimeWheel {
    private final TimeWheelLevel rootLevel;  // 最外层时间轮

    public MultiLevelTimeWheel(List<Integer> layerSlots, int innerSlotDuration) {
        TimeWheelLevel current = null;
        for (int i = layerSlots.size() - 1; i >= 0; i--) {
            int slotCount = layerSlots.get(i);
            int slotDuration = (i == layerSlots.size() - 1) ? innerSlotDuration : current.getTotalDuration();
            current = new TimeWheelLevel(slotCount, slotDuration, current);
        }
        rootLevel = current;
    }

    public void addTask(Task task, int delay) {
        TimeWheelLevel currentLevel = rootLevel;
        while (currentLevel != null) {
            int slotDuration = currentLevel.getSlotDuration();
            int slotCount = currentLevel.getSlotCount();

            if (delay < slotCount * slotDuration) {
                int targetSlot = (currentLevel.getCurrentSlot() + delay / slotDuration) % slotCount;
                currentLevel.getSlot(targetSlot).addTask(task);
                return;
            }

            delay -= slotCount * slotDuration;
            currentLevel = currentLevel.getNextLevel();
        }
        throw new IllegalArgumentException("Delay exceeds the time wheel capacity.");
    }

    public void tick(TaskExecutor taskExecutor) {
        TimeWheelLevel currentLevel = rootLevel;
        while (currentLevel != null) {
            Slot currentSlot = currentLevel.getCurrentSlot();
            if (currentSlot.hasTasks()) {
                currentSlot.executeTasks(taskExecutor);
            }
            currentLevel.advanceSlot();
            if (currentLevel.getCurrentSlot() != 0) {
                break;
            }
            currentLevel = currentLevel.getNextLevel();
        }
    }
}
