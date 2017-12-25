package kata.joins;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

public class NestedLoopJoin {
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> innerJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        List<Triple<K, V, V>> collect = new ArrayList<>();
        for (Pair<K, V> lPair : left) {
            for (Pair<K, V> rPair : right) {
                if (lPair.getKey().compareTo(rPair.getKey()) == 0) {
                    collect.add(new ImmutableTriple<>(lPair.getKey(), lPair.getValue(), rPair.getValue()));
                }
            }
        }
        return collect;
    }

    public <K extends Comparable<K>, V> List<Triple<K, V, V>> leftJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        List<Triple<K, V, V>> collect = new ArrayList<>();
        for (Pair<K, V> lPair : left) {
            boolean find = false;
            for (Pair<K, V> rPair : right) {
                if (lPair.getKey().compareTo(rPair.getKey()) == 0) {
                    find = true;
                    collect.add(new ImmutableTriple<>(lPair.getKey(), lPair.getValue(), rPair.getValue()));
                }
            }
            if (!find) {
                collect.add(new ImmutableTriple<>(lPair.getKey(), lPair.getValue(), null));
            }
        }
        return collect;
    }
}
