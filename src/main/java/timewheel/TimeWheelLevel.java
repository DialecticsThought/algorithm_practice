package timewheel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:55
 */
public class TimeWheelLevel {
    private int slotDuration;  // 每槽的时间粒度（秒）
    private List<Slot> slots;  // 槽列表
    private TimeWheelLevel nextLevel;  // 下一层时间轮
    private int currentSlot;  // 当前指针位置

    public TimeWheelLevel(int slotCount, int slotDuration, TimeWheelLevel nextLevel) {
        this.slotDuration = slotDuration;
        this.nextLevel = nextLevel;
        this.currentSlot = 0;

        this.slots = new ArrayList<>();
        for (int i = 0; i < slotCount; i++) {
            this.slots.add(new Slot());
        }
    }

    // 推进当前槽指针，并递归推进下一层
    public void advanceSlot() {
        currentSlot = (currentSlot + 1) % slots.size();  // 推进当前层的槽指针
        if (currentSlot == 0 && nextLevel != null) {
            nextLevel.advanceSlot();  // 当当前层轮回时，推进下一层
        }
    }

    // 获取当前槽
    public Slot getCurrentSlot() {
        return slots.get(currentSlot);
    }

    // 获取指定槽
    public Slot getSlot(int index) {
        return slots.get(index % slots.size());
    }

    // 获取当前层的总时间长度
    public int getTotalDuration() {
        return slots.size() * slotDuration;
    }

    // 获取下一层时间轮
    public TimeWheelLevel getNextLevel() {
        return nextLevel;
    }
}
