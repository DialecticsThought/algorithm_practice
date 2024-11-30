package timewheel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:55
 */
public class TimeWheelLevel {
    private int slotInterval;  // 每槽的时间粒度（秒）
    private List<Slot> slots;  // 槽列表
    private TimeWheelLevel nextLevel;  // 下一层时间轮
    private int currentSlotIndex;  // 当前指针位置

    public TimeWheelLevel(int slotCount, int slotInterval, TimeWheelLevel nextLevel) {
        this.slotInterval = slotInterval;
        this.nextLevel = nextLevel;
        this.currentSlotIndex = 0;
        this.slots = new ArrayList<>();

        // 为外层时间轮添加 OutSlot，每个 OutSlot 会指向下一层时间轮
        for (int i = 0; i < slotCount; i++) {
            if (nextLevel != null) {
                this.slots.add(new OuterSlot(nextLevel));  // 外层槽通过 OutSlot 连接到下一层
            } else {
                this.slots.add(new InnerSlot());  // 最内层时间轮的槽使用 InnerSlot
            }
        }
    }

    // 推进当前槽指针，并递归推进下一层
    public void advanceSlot() {
        currentSlotIndex = (currentSlotIndex + 1) % slots.size();  // 推进当前层的槽指针
        if (currentSlotIndex == 0 && nextLevel != null) {
            nextLevel.advanceSlot();  // 当当前层轮回时，推进下一层
        }
    }

    /**
     * 判断当前层是否有任务
     *
     * @return
     */
    public boolean hasTasks() {
        for (Slot slot : slots) {
            if (slot.hasTasks()) {
                return true;  // 如果某个槽有任务，返回 true
            }
        }
        // 如果当前层没有任务，递归检查下一层
        if (nextLevel != null && nextLevel.hasTasks()) {
            return true;
        }

        return false;  // 如果没有任务，则返回false
    }

    /**
     * 获取当前槽索引
     *
     * @return
     */
    public int getCurrentSlotIndex() {
        return currentSlotIndex;
    }

    /**
     * 获取指定槽
     *
     * @param index
     * @return
     */
    public Slot getSlot(int index) {
        return slots.get(index % slots.size());
    }

    /**
     * 获取当前层的总时间长度
     *
     * @return
     */
    public int getTotalDuration() {
        return slots.size() * slotInterval;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public int getSlotInterval() {
        return slotInterval;
    }

    public TimeWheelLevel getNextLevel() {
        return nextLevel;
    }

    public void setSlotInterval(int slotInterval) {
        this.slotInterval = slotInterval;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public void setNextLevel(TimeWheelLevel nextLevel) {
        this.nextLevel = nextLevel;
    }

    public void setCurrentSlotIndex(int currentSlotIndex) {
        this.currentSlotIndex = currentSlotIndex;
    }
}
