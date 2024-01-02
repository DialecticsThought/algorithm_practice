package heima_data_structure.test.other;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) throws IOException {

//        String collect = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(50))
//                .mapToObj(String::valueOf).limit(20).collect(Collectors.joining("\n"));
//        Files.write(Path.of("input.txt"), collect.getBytes(StandardCharsets.UTF_8));

        printTable("%2s", 0, 128);

    }

    public static void printTable(String format, int begin, int end) {
        StringBuilder sb = new StringBuilder(1024);
        // 拼接表头，低位数字
        StringJoiner sj = new StringJoiner("|", "|", "|");
        for (int i = -1; i < 16; i++) {
            if (i == -1) {
                sj.add(" \\ ");
            } else {
                sj.add(String.format("%2s", Integer.toHexString(i)).replaceAll("\\s", "0"));
            }
        }
        System.out.println(sj);
        // 表头分隔线
        sj = new StringJoiner("|", "|", "|");
        for (int i = -1; i < 16; i++) {
            sj.add("---");
        }
        System.out.println(sj);
        // 遍历
        for (int i = begin; i < end; i++) {
            String x;
            if (i < 32 || 127 <= i && i < 161) {
                x = String.format(format, Integer.toHexString(i)).replaceAll("\\s", "0");
            } else if (i == 0x7c) {
                x = " \\| ";
            } else {
                x = " " + Character.toString((char) i) + " ";
            }
            if (i % 16 == 0) {
                // 处理第一列
                sj = new StringJoiner("|", "|", "|");
                sj.add(String.format("%4s", Integer.toHexString(i)).replaceAll("\\s", "0"));
            } else if ((i + 1) % 16 == 0) {
                // 处理最后一列
                sj.add(x);
                sb.append(sj.toString()).append("\n");
            }
            sj.add(x);
        }
        System.out.println(sb);
    }
}
