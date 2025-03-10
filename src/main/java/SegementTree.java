/**
 * @Description
 * @Author veritas
 * @Data 2025/3/3 21:20
 */
public class SegementTree {
    /**
     * <pre>
     * ⓐ ⓑ ⓒ ⓓ ⓔ ⓕ ⓖ ⓗ ⓘ ⓙ ⓚ ⓛ ⓜ ⓝ ⓞ ⓟ ⓠ ⓡ ⓢ ⓣ ⓤ ⓥ ⓦ ⓧ ⓨ ⓩ
     * Ⓐ Ⓑ Ⓒ Ⓓ Ⓔ Ⓕ Ⓖ Ⓗ Ⓘ Ⓙ Ⓚ Ⓛ Ⓜ Ⓝ Ⓞ Ⓟ Ⓠ Ⓡ Ⓢ Ⓣ Ⓤ Ⓥ Ⓦ Ⓧ Ⓨ Ⓩ
     * 🄰 🄱 🄲 🄳 🄴 🄵 🄶 🄷 🄸 🄹 🄺 🄻 🄼 🄽 🄾 🄿 🅀 🅁 🅂 🅃 🅄 🅅 🅆 🅇 🅈 🅉
     * https://www.bilibili.com/video/BV1vhPoeXEFf
     * 利用线段树 解决 区间最大值
     * 有一个数组 arr = [ 3 , 20 , 11 , 19 , 6 , 2 ]
     *                   1    2    3    4   5   6
     * 数组arr的区间是从1开始的
     * 下面这棵树 表示区间
     *                   [1,6]
     *                ↙        ↘
     *          [1,3]           [4,6]
     *         ↙    ↘             ↙    ↘
     *     [1,2]    [3,3]     [4,5]    [6,6]
     *    ↙   ↘                ↙   ↘
     * [1,1] [2,2]         [4,4] [5,5]
     * 把上面这棵树 变成 完全二叉树
     *                   [1,6]
     *                ↙        ↘
     *          [1,3]            [4,6]
     *         ↙    ↘             ↙    ↘
     *     [1,2]    [3,3]       [4,5]    [6,6]
     *    ↙   ↘     ↙   ↘       ↙   ↘
     * [1,1] [2,2] nill nill [4,4] [5,5]
     * 从上到下 从左到右 给每一个节点编号
     *                       1.[1,6]
     *                ↙                 ↘
     *          2.[1,3]                 3.[4,6]
     *         ↙        ↘                   ↙      ↘
     *    4.[1,2]       5.[3,3]         6.[4,5]     7.[6,6]
     *    ↙     ↘           ↙   ↘          ↙   ↘
     * 8.[1,1] 9.[2,2] 10.nill 11.nill 12.[4,4] 13.[5,5]
     * 根据完全二叉树的特性
     * 对任意节点i
     *  parent = i/2 向下取整
     *  left = 2 * i
     *  right = 2 * i + 1
     *
     *  因为每一个节点 代表了 每个对应区间的最大值 得到一棵树
     *  先得到这棵树的每一个叶子结点对应的区间的最大值
     *  然后逐层求出每一个非叶子结点的对应的区间的最大值 (由下往上)
     *                       1.[1,6]
     *                          20
     *                ↙                    ↘
     *          2.[1,3]                    3.[4,6]
     *             20                          19
     *         ↙        ↘                    ↙      ↘
     *    4.[1,2]       5.[3,3]           6.[4,5]     7.[6,6]
     *       20             11               19            2
     *    ↙     ↘          ↙   ↘            ↙     ↘
     * 8.[1,1] 9.[2,2] 10.nill 11.nill 12.[4,4] 13.[5,5]
     *    3      20                       19       6
     * 这棵树最终变成
     *                       1.[1,6]
     *                          20
     *                ↙                    ↘
     *          2.[1,3]                    3.[4,6]
     *             20                          19
     *         ↙        ↘                    ↙      ↘
     *    4.[1,2]       5.[3,3]           6.[4,5]     7.[6,6]
     *       20             11               19            2
     *    ↙     ↘                         ↙     ↘
     * 8.[1,1] 9.[2,2]               12.[4,4]  13.[5,5]
     *    3      20                       19       6
     * eg: 现在区间查询[2,3]区间上的最大值
     * 从树根开始遍历
     * 1.1 区间[2,3] 与 根节点 1.[1,6] 不完全匹配，向下遍历
     *      区间[2,3] 与 根节点 1.[1,6] 的左孩子 2.[1,3] 有交集 只往左子节点走
     *
     * 2.1 区间[2,3] 与 节点 2.[1,3] 不完全匹配，向下遍历
     *      区间[2,3] 与 节点 2.[1,3] 的左孩子 4.[1,2] 右孩子 5.[3,3] 有交集，左右2个节点都要遍历到
     *      分别 去节点4 查询区间[2,2] 和 去节点5 查询区间[3,3] 最大值
     *
     * 3.1 区间[2,2]与 节点4[1,2] 不完全匹配，向下遍历
     *      区间[2,2] 与 节点4[1,2] 的右孩子 9.[2,2] 有交集 只往右子节点走
     *
     * 4.1 区间[2,2] 与 节点9.[2,2] 完全匹配 => 得到区间[2,2]的最大值=20
     *
     * 3.2 区间[3,3]与 节点5.[3,3] 完全匹配 => 得到区间[3,3]的最大值=11
     *
     * 2.2 区间[2,3] 的最大值 是 [2,2]和[3,3]的最大值 得到 20
     *
     * 1.2 区间[2,3] 的最大值 是 20
     *
     * eg: 现在区间查询[3,5]区间上的最大值
     * 1.1 区间[3,5] 与 根节点 1.[1,6] 不完全匹配，向下遍历
     *     区间[3,5] 与 根节点 1.[1,6] 的左孩子 2.[1,3] 右孩子 3.[4,6] 有交集，左右2个节点都要遍历到
     *     分别 去节点2 查询区间[3,3] 和 去节点3 查询区间[4,5] 最大值
     *
     * 2.1 区间[3,3]与 节点2[1,3] 不完全匹配，向下遍历
     *     区间[3,3] 与 节点2[1,3] 的右孩子 5.[3,3] 有交集 只往右子节点走
     *
     * 3.1 区间[3,3]与 节点5[3,3] 完全匹配 => 得到区间[3,3]的最大值 = 11
     *
     * 2.2 区间[4,5] 与 节点2[4,6] 不完全匹配，向下遍历
     *      区间[4,5] 与 节点2[4,6] 的左孩子 6.[4,5] 有交集 只往左子节点走
     *
     * 3.2 区间[4,5]与 节点5[4,5] 完全匹配 => 得到区间[4,5]的最大值 = 19
     *
     * 1.2 区间[3,3] 和 区间[4,5] 最大值 => 19
     *
     * eg:单点更新 某一个元素
     * 更新arr[2] = 6 原始值 = 20
     * 从根节点开始
     * 位置2 与 根节点1.[1,6] 的 左孩子2.[1,3] 有交集
     * 来到其左孩子 2.[1,3]
     * 位置2 与 节点2.[1,3]的 左孩子4.[1,2] 有交集
     * 来到其左孩子 4.[1,2]
     * 位置2 与 节点4.[1,2]的 右孩子9.[2,2] 有交集
     * 来到其右孩子 9.[2,2]
     * 位置2 与 节点9.[2,2]  完全匹配，因此 修改节点.[2,2]的值 = 6
     * 接下来从底向上 更新其父节点的值
     * 更新节点4.[1,2] = Max(8.[1,1] , 9.[2,2]) = 6
     * 更新节点2.[1,3] = Max(4.[1,2] , 5.[3,3]) = 11
     * 更新节点1.[1,6] = Max(2.[1,3] , 3.[4,6]) = 19
     *
     * 区间更新: 为一个区间内的所有值 加上一个常数
     * 懒标记: 所有节点代表的区间默认没有更新，所以所有区间的节点的懒标记都是0
     * eg1:现在对区间[4,6]统一 + 1
     * 从根节点开始
     * 检查区间[4,6] 是否与 节点1对应的区间[1,6]完全匹配
     *      不匹配，继续向下遍历
     *      区间[4,6] 仅与节点1的右孩子节点3的区间[4,6]有交集，来到节点3
     * 检查区间[4,6] 是否与节点3对应的区间[4,6]完全匹配
     *      完全匹配，那么更新节点3的最大值是 19 + 1 = 20
     *      将当前的区间更新请求 暂存在节点3的懒标记上 ，懒标记的值从 0 更新成 1
     *          说明 不用 给 arr的4,5,6位置的数据都 + 1
     * 节点3的最大值出现变化，自底向上更新父节点的值 直到 根节点
     * 节点3的父节点 节点1的值 Max(2.[1,3] , 3.[4,6]) = 20
     * 此时完成了更新操作
     * eg2:现在对区间[4,5]统一 + 1
     * 从根节点开始
     * 检查区间[4,5] 是否与 节点1对应的区间[1,6]完全匹配
     *      不匹配，继续向下遍历
     *      区间[4,5] 仅与节点1的右孩子节点3的区间[4,6]有交集，来到节点 3
     * 检查区间[4,5] 是否与节点3对应的区间[4,6]完全匹配
     *       因为此时[4,6]的懒标记 = 1 !=0
     *       先下发懒标记给自己的直属左右孩子，
     *          即:
     *              左孩子 节点6的值19 + 1 = 20 ,并且 其懒标记变成 1
     *              右孩子 节点7的值2 + 1 = 3 ,并且 其懒标记变成 1
     *              父节点3的懒标记清空 变成0
     *      不匹配，继续向下遍历
     *      区间[4,5] 仅与节点3的左孩子节点6的区间[4,5]有交集，来到节点6
     * 检查区间[4,5] 是否与 节点6对应的区间[4,5]完全匹配
     *      完全匹配，那么更新节点6的最大值是 20 + 1 = 21
     *      将当前的区间更新请求 暂存在节点6的懒标记上 ，懒标记的值从 1 更新成 2
     * 节点6的最大值出现变化，自底向上更新父节点的值 直到 根节点
     * 节点6的父节点 节点3的值 Max(6.[4,5] , 7.[6,6]) = Max(21,3) = 21
     * 节点3的父节点 节点1的值 Max(2.[1,3] , 3.[4,6]) = Max(11,21) = 21
     *
     * 引入了懒标记之后 执行区间查询时，
     * 遇到有懒标记的节点，必须先让该节点下发懒标记到该节点的直属子节点 再查询该节点
     * eg:区间查询[4,4]的最大值
     * 从根节点开始
     * 检查区间[4,4] 是否与 节点1对应的区间[1,6]完全匹配
     *      不匹配，继续向下遍历
     *      区间[4,4] 仅与节点1的右孩子节点3的区间[4,6]有交集，因为节点1的懒标记 = 0 直接来到节点 3
     * 检查区间[4,4] 是否与节点3对应的区间[4,6]完全匹配
     *      不匹配，继续向下遍历
     *      区间[4,4] 仅与节点3的左孩子节点6的区间[4,5]有交集，因为节点3的懒标记 = 0 直接来到节点 6
     * 检查区间[4,4] 是否与节点6对应的区间[4,5]完全匹配
     *      不匹配，继续向下遍历
     *      区间[4,4] 仅与节点16的左孩子节点12的区间[4,4]有交集，
     *          因为节点6的懒标记 = 2
     *              将节点6的懒标记 下发到直属的左右孩子节点12和13
     *              左孩子 节点12的值:19 + 2 = 21,并且 懒标记从0更新到2
     *              右孩子 节点13的值:6 + 2 = 8,并且 懒标记从0更新到2
     *              父节点6的懒标记清空 变成0
     *          继续向下遍历,来到节点12
     * 检查区间[4,4] 是否与节点12对应的区间[4,4]完全匹配
     *      完全匹配，那么查询到区间[4,4]的最大值是 21
     * </pre>
     */
}
