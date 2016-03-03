package kata.joins;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashJoin {

    public <K extends Comparable<K>, V> List<Triple<K, V, V>> innerJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        Map<K, V> hash = new HashMap<>();
        for (Pair<K, V> rPair : right) {
            hash.put(rPair.getKey(), rPair.getValue());
        }
        List<Triple<K, V, V>> collect = new ArrayList<>();
        for (Pair<K, V> lPair : left) {
            if (hash.containsKey(lPair.getKey())) {
                collect.add(new ImmutableTriple<>(lPair.getKey(), lPair.getValue(), hash.get(lPair.getKey())));
            }
        }
        return collect;
    }

    public <K extends Comparable<K>, V> List<Triple<K, V, V>> leftJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        Map<K, V> hash = new HashMap<>();
        for (Pair<K, V> rPair : right) {
            hash.put(rPair.getKey(), rPair.getValue());
        }
        List<Triple<K, V, V>> collect = new ArrayList<>();
        for (Pair<K, V> lPair : left) {
            boolean find = false;
            if (hash.containsKey(lPair.getKey())) {
                find = true;
                collect.add(new ImmutableTriple<>(lPair.getKey(), lPair.getValue(), hash.get(lPair.getKey())));
            }
            if (!find) {
                collect.add(new ImmutableTriple<>(lPair.getKey(), lPair.getValue(), null));
            }
        }
        return collect;
    }
}
