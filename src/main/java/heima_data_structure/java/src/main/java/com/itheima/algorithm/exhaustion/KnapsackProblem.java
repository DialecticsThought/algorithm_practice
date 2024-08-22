package heima_data_structure.java.src.main.java.com.itheima.algorithm.exhaustion;

import java.util.LinkedList;

/**
 * <h3>0-1 背包问题</h3>
 */
public class KnapsackProblem {
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

    public static void allSolutions(Item[] items, int totalWeight) {
        LinkedList<Item> stack = new LinkedList<>();
        for (int i = 0; i < items.length; i++) {
            rec(i, items, totalWeight - items[i].weight, stack);
        }
    }
    static int level = -1;
    static void rec(int index, Item[] items, int remainderWeight, LinkedList<Item> stack) {
        level++;
        for (int j = 0; j < level; j++) {
            System.out.print("| ");
        }
        System.out.println("rec(" + items[index] + "," + remainderWeight+")");
        stack.push(items[index]);
        try {
            if (remainderWeight <= 0) {
                return;
            }
            for (int i = index + 1; i < items.length; i++) {
                rec(i, items, remainderWeight - items[i].weight, stack);
            }
        } finally {
            level--;
            if (remainderWeight >= 0) {
                int value = stack.stream().mapToInt(Item::getValue).sum();
                System.out.println(">=0 有解：" + stack + " 最大价值：" + value);
            }
            stack.pop();
        }

    }

    public static void main(String[] args) {
//        Item[] items = {
//                new Item(0, 60, 10),
//                new Item(1, 100, 20),
//                new Item(2, 120, 30),
//                new Item(3, 30, 10),
//                new Item(4, 40, 20)
//        };
//        int totalWeight = 50;
        Item[] items = new Item[]{
                new Item(3, 5, 30),
                new Item(1, 4, 1600),
                new Item(0, 1, 1_000_000),
                new Item(2, 8, 2400),
        };
        int totalWeight = 10;
        allSolutions(items, totalWeight);

    }

}
