package fst;
import java.util.List;


/**
 * @Description
 * @Author veritas
 * @Data 2025/1/6 19:12
 */


class PostingList {
    private List<String> documents; // 倒排列表（文档 ID）
    private int frequency;          // 出现频率

    public PostingList(List<String> documents, int frequency) {
        this.documents = documents;
        this.frequency = frequency;
    }

    // Getter 和 Setter 方法
    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "PostingList{" +
                "documents=" + documents +
                ", frequency=" + frequency +
                '}';
    }
}
