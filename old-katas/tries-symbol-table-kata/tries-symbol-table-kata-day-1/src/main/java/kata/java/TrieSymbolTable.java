package kata.java;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;

import java.util.OptionalInt;

public class TrieSymbolTable {

    private final Node root;

    public TrieSymbolTable() {
        root = new Node(OptionalInt.empty());
    }

    public OptionalInt get(String key) {
        Node node = get(root, key, 0);
        if (node == null) return OptionalInt.empty();
        else return node.value;
    }

    private Node get(Node node, String key, int index) {
        if (node == null) return null;
        if (index == key.length()) return node;
        char c = key.charAt(index);
        return get(node.next.get(c), key, index + 1);
    }

    public void put(String key, int value) {
        put(root, key, value, 0);
    }

    private Node put(Node node, String key, int value, int index) {
        if (node == null) node = new Node(OptionalInt.empty());
        if (index == key.length()) {
            node.value = OptionalInt.of(value);
            return node;
        }
        else {
            char c = key.charAt(index);
            node.next.put(c, put(node.next.get(c), key, value, index + 1));
            return node;
        }
    }

    private static class Node {
        private OptionalInt value;
        private final Char2ObjectMap<Node> next;

        Node(OptionalInt value) {
            this.value = value;
            this.next = new Char2ObjectOpenHashMap<>();
        }

        @Override
        public String toString() {
            return "[Node of value - " + value + " ]";
        }
    }
}
