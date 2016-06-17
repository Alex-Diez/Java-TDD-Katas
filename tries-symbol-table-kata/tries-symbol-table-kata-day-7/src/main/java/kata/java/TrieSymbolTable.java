package kata.java;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;

import java.util.Optional;

public class TrieSymbolTable<V> {

    private final Node root = new Node();

    public Optional<V> get(String key) {
        Node node = root;
        for (int i = 0; i < key.length() && node != null; i++) {
            node = node.next.get(key.charAt(i));
        }
        return node != null ? Optional.ofNullable(node.value) : Optional.empty();
    }

    public void put(String key, V value) {
        Node node = root;
        for (int i = 0; i < key.length(); i++) {
            node = node.next.computeIfAbsent(key.charAt(i), k -> new Node());
        }
        node.value = value;
    }

    private class Node {
        private final Char2ObjectMap<Node> next = new Char2ObjectOpenHashMap<>();
        private V value;
    }
}
