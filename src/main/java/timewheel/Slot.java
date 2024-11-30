package timewheel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/27 09:55
 */
// 代表一个时间轮中的槽（父类）
interface Slot {
    void addTask(Task task); // 添加任务

    void executeTasks(); // 执行任务
}