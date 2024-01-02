package heima_data_structure.java.src.main.java.com.itheima.algorithm.exhaustion;

import java.util.LinkedList;

/**
 * <h3>0-1 背包问题</h3>
 */
public class KnapsackProblem3 {
    static class Item {
        int index;
        int value;
        int weight;

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        public Item(int index, int weight, int value) {
            this.index = index;
            this.value = value;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Item(" + index + ")";
        }

        public int getValue() {
            return value;
        }
    }

    static int level = -1;

    static int res(Item[] items, int i, int total, LinkedList<Item> stack) {
        if (i == items.length) {
            return 0;
        }
        level++;
//        for (int j = 0; j < level; j++) {
//            System.out.print("| ");
//        }
//        System.out.println("rec(" + items[i] + "[" + items[i].weight + "]" + "," + total + ")");
        try {
            stack.push(items[i]);
            int max = 0;
            if (total >= items[i].weight) {
//                System.out.println(i+" "+total);
                int x = res(items, i + 1, total - items[i].weight, stack) + items[i].value;
//                stack.pop();
                int y = res(items, i + 1, total, stack);
                max = Math.max(x, y);
                System.out.println("放=" + x + " 不放=" + y);
//                System.out.println("有解:"+stack);
            } else {
//                System.out.println("无解:"+stack);
            }
            level--;
            return max;
        } finally {
            stack.pop();
        }
    }

    public static void main(String[] args) {
        Item[] items = new Item[]{
                new Item(3, 5, 30),
                new Item(1, 4, 1600),
                new Item(0, 1, 1_000_000),
                new Item(2, 8, 2400),
        };
        int total = 10;
        int res = res(items, 0, total, new LinkedList<>());
        System.out.println(res);

    }

    /*
rec(Item(3),10)                    返回 Max(3)+1600+1_000_000+0,1_002_400)
| rec(Item(1),5)        放入Item(3) 返回 Max(1600+1_000_000+0,1_000_000)
| | rec(Item(0),1)      放入Item(1) 返回 Max(1_000_000+0,0)
| | | rec(Item(2),0)    放入Item(0) 返回 0
| | | rec(Item(2),1)    不放Item(0) 返回 0
| | rec(Item(0),5)      不放Item(1) 返回 Max(1_000_000+0,0)
| | | rec(Item(2),4)    放入Item(0) 返回 0
| | | rec(Item(2),5)    不放Item(0) 返回 0
| rec(Item(1),10)       不放Item(3) 返回 Max(1600+1_000_000,1_002_400)
| | rec(Item(0),6)      放入Item(1) 返回 Max(1_000_000+0,0)
| | | rec(Item(2),5)    放入Item(0) 返回 0
| | | rec(Item(2),6)    不放Item(0) 返回 0
| | rec(Item(0),10)     不放Item(1) 返回 Max(1_000_000+2400,0)
| | | rec(Item(2),9)    放入Item(0) 返回 2400
| | | rec(Item(2),10)   不放Item(0) 返回 0（item处理完成）
     */
}
