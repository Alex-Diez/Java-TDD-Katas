package kata.java;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BTreeSet {
    private static final int MAX_CHILDREN_PER_NODE = 4;

    private Node root = new Node(0);
    private int height;

    public void add(int key) {
        Node node = insert(root, key, height);
        if (node != null) {
            Node local = new Node(2);
            local.children[0] = new Entry(root.children[0].key, root);
            local.children[1] = new Entry(node.children[0].key, node);
            root = local;
            height++;
        }
    }

    private Node insert(Node node, int key, int height) {
        int j;
        Entry e = new Entry(key, null);

        if (height == 0) {
            for (j = 0; j < node.size; j++) {
                if (key < node.children[j].key) break;
            }
        }
        else {
            for (j = 0; j < node.size; j++) {
                if ((j+1 == node.size) || key < node.children[j+1].key) {
                    Node local = insert(node.children[j++].next, key, height-1);
                    if (local != null) {
                        e.key = local.children[0].key;
                        e.next = local;
                        break;
                    }
                }
            }
        }

        System.arraycopy(node.children, j, node.children, j + 1, node.size - j);
        node.children[j] = e;
        node.size++;
        if (node.size < MAX_CHILDREN_PER_NODE) return null;
        else return split(node);
    }

    private Node split(Node node) {
        Node newNode = new Node(MAX_CHILDREN_PER_NODE / 2);
        node.size = MAX_CHILDREN_PER_NODE / 2;
        System.arraycopy(node.children, 2, newNode.children, 0, MAX_CHILDREN_PER_NODE / 2);
        return newNode;
    }

    public boolean contains(int key) {
        return search(root, key, height) != null;
    }

    private Node search(Node node, int key, int height) {
        Entry[] children = node.children;

        if (height == 0) {
            for (int j = 0; j < node.size; j++) {
                if (key == children[j].key) {
                    return node;
                }
            }
        }
        else {
            for (int j = 0; j < node.size; j++) {
                if (j+1 == node.size || key < children[j+1].key) {
                    return search(children[j].next, key, height - 1);
                }
            }
        }
        return null;
    }

    private class Node {
        private int size;
        private BTreeSet.Entry[] children = new BTreeSet.Entry[MAX_CHILDREN_PER_NODE];

        public Node(int size) {
            this.size = size;
        }
    }

    private class Entry {
        private int key;
        private BTreeSet.Node next;

        public Entry(int key, Node next) {
            this.key = key;
            this.next = next;
        }
    }
}
