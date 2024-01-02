package heima_data_structure.test.other;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ExternalSort {

    public static void sort(String inputFilename, String outputFilename, int chunkSize) throws IOException {
        List<File> tmpFiles = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename))) {
            int counter = 0;
            while (true) {
                List<Integer> lines = new ArrayList<>();
                String line;
                int totalSize = 0;
                while (totalSize < chunkSize && (line = reader.readLine()) != null) {
                    lines.add(Integer.parseInt(line));
                    totalSize++;
                }
                if (lines.isEmpty()) {
                    break;
                }
                Collections.sort(lines);
                File tempFile = new File("sorted_" + counter + ".txt");
                try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                    for (Integer l : lines) {
                        writer.println(l);
                    }
                }
                tmpFiles.add(tempFile);
                counter++;
            }
        }
        mergeFiles(tmpFiles, outputFilename);

    }

    private static void mergeFiles(List<File> tmpFiles, String outputFilename) throws IOException {
        MinHeap queue = new MinHeap(tmpFiles.size());
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFilename), StandardCharsets.UTF_8));
        List<BufferedIntReader> list = tmpFiles.stream().map(BufferedIntReader::new).toList();
        for (BufferedIntReader reader : list) {
            if (reader.hasNext()) {
                queue.offer(reader);
            }
        }
        while (!queue.isEmpty()) {
            BufferedIntReader min = queue.poll();
            writer.println(min.next());
            if (min.hasNext()) {
                queue.offer(min);
            }
        }
        writer.flush();
        writer.close();
        for (BufferedIntReader reader : list) {
            reader.close();
        }
    }


    public static void main(String[] args) throws IOException {
        ExternalSort.sort("input.txt", "output.txt", 5);
//        try (BufferedIntReader reader = new BufferedIntReader(new File("sorted_1.txt"))) {
//            while (reader.hasNext()) {
//                System.out.println(reader.next());
//            }
//        }

    }
}
