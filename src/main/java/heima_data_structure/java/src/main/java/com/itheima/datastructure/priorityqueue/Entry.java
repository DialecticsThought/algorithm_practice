package heima_data_structure.java.src.main.java.com.itheima.datastructure.priorityqueue;

public class Entry implements Priority {

    public String value;
    int priority;

    public Entry(int priority) {
        this.priority = priority;
    }

    public Entry(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public String toString() {
        return "(" + value + " priority=" + priority + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        return priority == entry.priority;
    }

    @Override
    public int hashCode() {
        return priority;
    }
}
