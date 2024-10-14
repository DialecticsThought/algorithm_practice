package heima_data_structure.java.src.main.java.com.itheima.datastructure.btree;

public class BPTree {

    sealed static class Node permits InternalNode, LeafNode {

    }

    static final class LeafNode extends Node {
        Entry[] entries;
        int size;

        public LeafNode(int m) {
            entries = new Entry[m];
        }

        public void insert(Entry entry, int index) {
            System.arraycopy(entries, index, entries, index + 1, size - index);
            entries[index] = entry;
            size++;
        }
    }

    static final class InternalNode extends Node {
        int[] keys;
        Node[] children;
        int size;

        public InternalNode(int m) {
            keys = new int[m];
            children = new Node[m + 1];
        }

        public void insertChild(Node node, int index) {
            System.arraycopy(children, index, children, index + 1, size - index);
            children[index] = node;
        }

        public void insertKey(int key, int index) {
            System.arraycopy(keys, index, keys, index + 1, size - index);
            keys[index] = key;
            size++;
        }
    }


    int internalOrder;

    int leafOrder;

    InternalNode root;

    LeafNode first;

    static class Entry {
        int key;
        Object value;

        public Entry(int key, Object value) {
            this.key = key;
            this.value = value;
        }
    }


    private LeafNode findNode(InternalNode node, int key) {
        int i = 0;
        while (i < node.size) {
            if (node.keys[i] > key) {
                break;
            }
            i++;
        }
        Node child = node.children[i];
        if (child instanceof LeafNode leaf) {
            return leaf;
        } else {
            return findNode((InternalNode) child, key);
        }
    }

    public void put(int key, Object value) {
        Entry entry = new Entry(key, value);
        if (first == null) {
            first = new LeafNode(leafOrder);
            first.insert(entry, 0);
            return;
        }
        LeafNode leaf = root == null ? first : findNode(root, key);
        int i = 0;
        while (i < leaf.size) {
            if (leaf.entries[i].key == key) {
                leaf.entries[i].value = value;
                return;
            }
            if (leaf.entries[i].key > key) {
                break;
            }
            i++;
        }
        System.arraycopy(leaf.entries, i, leaf.entries, i + 1, leaf.size - i);
        leaf.entries[i] = entry;
        leaf.size++;
        if (leaf.size == leafOrder - 1) {
//            split(parent, leaf, index);
        }
    }

    private void split(InternalNode parent, LeafNode left, int index) {
        if (parent == null) {
            InternalNode root = new InternalNode(internalOrder);
            root.insertChild(left, 0);
            this.root = root;
            parent = root;
        }
        LeafNode right = new LeafNode(leafOrder);
        int mid = getMid(leafOrder);
        int n = left.size;
        for (int i = mid; i < n; i++) {
            right.entries[right.size++] = left.entries[mid];
            left.entries[mid] = null;
            left.size--;
        }

    }

    static int getMid(int m) {
        return (int) Math.ceil((m + 1) / 2.0) - 1;
    }
}
