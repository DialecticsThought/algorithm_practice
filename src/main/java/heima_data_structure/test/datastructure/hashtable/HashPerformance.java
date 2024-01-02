package heima_data_structure.test.datastructure.hashtable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HashPerformance {

    private static final int NUM_KEYS = 1000000;
    private static final int MAX_VALUE = 1000;

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        Random rand = new Random();

        // Add random keys to the map
        for (int i = 0; i < NUM_KEYS; i++) {
            int key = rand.nextInt(MAX_VALUE);
            map.put(key, i);
        }

        // Calculate the number of collisions
        int collisions = 0;
        int[] histogram = new int[10];
        for (int i = 0; i < MAX_VALUE; i++) {
            int bucket = map.getOrDefault(i, -1) % 10;
            histogram[bucket]++;
            if (map.get(i) != null && map.get(i) != i) {
                collisions++;
            }
        }

        // Print the results
        double collisionRate = (double) collisions / MAX_VALUE;
        System.out.println("Collision rate: " + collisionRate * 100 + "%");
        System.out.println("Histogram: " + Arrays.toString(histogram));
    }
}
