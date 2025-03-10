import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author veritas
 * @Data 2024/10/8 12:09
 */
public class Test {


    public static void radixSort(String[] list, int length) {
        ArrayList<String>[] buckets = new ArrayList[256];

        for (int i = 0; i < 256; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (int i = length - 1; i >= 0; i--) {

            for (String string : list) {

                char c = string.charAt(i);

                buckets[c].add(string);
            }

            int k = 0;

            for (ArrayList<String> bucket : buckets) {

                for (String str : bucket) {
                    list[k] = str;
                    k++;
                }

                bucket.clear();
            }
        }
    }


    public static void main(String[] args) {
        // 文件夹路径
        String folderPath = "K:\\iCloud 照片1"; // 替换为实际的文件夹路径
        File folder = new File(folderPath);

        // 检查文件夹是否存在
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("文件夹不存在或不是文件夹");
            return;
        }

        // 获取所有 HEIC 文件
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".heic"));

        System.out.println(files.length);

        if (files == null || files.length == 0) {
            System.out.println("文件夹中没有 HEIC 文件");
            return;
        }

        // 提取文件名中的数字，并进行排序
        List<ImageFile> imageFiles = new ArrayList<>();
        Pattern pattern = Pattern.compile("IMG_(\\d+)\\.HEIC", Pattern.CASE_INSENSITIVE);
        for (File file : files) {
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.matches()) {
                int number = Integer.parseInt(matcher.group(1));
                imageFiles.add(new ImageFile(file.getName(), number));
            }
        }

        // 按数字升序排序
        imageFiles.sort(Comparator.comparingInt(img -> img.number));

        // 遍历文件列表，检查数字是否连续
        for (int i = 0; i < imageFiles.size() - 1; i++) {
            ImageFile current = imageFiles.get(i);
            ImageFile next = imageFiles.get(i + 1);

            if (next.number != current.number + 1) {

                System.out.println("发现不连续的文件： " + current.name + " 和 " + next.name + ", 相差:" + (next.number - current.number));
            }
        }

        System.out.println("检查完成");
    }

    // 辅助类：存储文件名和数字
    static class ImageFile {
        String name;
        int number;

        public ImageFile(String name, int number) {
            this.name = name;
            this.number = number;
        }
    }

}
