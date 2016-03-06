package kata.joins;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

public class NestedLoopJoin
        implements InnerJoin, OuterJoin {

    @Override
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> innerJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        List<Triple<K, V, V>> collect = new ArrayList<>();
        for (Pair<K, V> l : left) {
            for (Pair<K, V> r : right) {
                if (l.getKey().compareTo(r.getKey()) == 0) {
                    collect.add(new ImmutableTriple<>(l.getKey(), l.getValue(), r.getValue()));
                }
            }
        }
        return collect;
    }

    @Override
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> leftJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        List<Triple<K, V, V>> collect = new ArrayList<>();
        for (Pair<K, V> l : left) {
            boolean find = false;
            for (Pair<K, V> r : right) {
                if (l.getKey().compareTo(r.getKey()) == 0) {
                    find = true;
                    collect.add(new ImmutableTriple<>(l.getKey(), l.getValue(), r.getValue()));
                }
            }
            if (!find) {
                collect.add(new ImmutableTriple<>(l.getKey(), l.getValue(), null));
            }
        }
        return collect;
    }
}
