package timewheel;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 16:47
 */
public class OuterSlot implements Slot {
    private  TimeWheelLevel  nextLevel;  // 存储指向下一层时间轮的引用

    @Override
    public void addTask(Task task) {
        if (nextLevel != null) {
            // 外层槽将任务转发到下一层
            nextLevel.getSlot(0).addTask(task); // 暂时假设所有任务都进入下一层的第0个槽
        }
    }

    @Override
    public void executeTasks() {
        if (nextLevel != null) {
            nextLevel.getSlot(0).executeTasks();  // 执行下一层的任务
        }
    }

    public void setNextLevel(TimeWheelLevel nextLevel) {
        this.nextLevel = nextLevel;
    }
}
