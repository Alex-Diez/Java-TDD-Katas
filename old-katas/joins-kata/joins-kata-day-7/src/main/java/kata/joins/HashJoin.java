package kata.joins;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HashJoin
        implements InnerJoin, OuterJoin {

    @Override
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> innerJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        Map<K, V> hash = right.stream().collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        return left.stream()
                .filter(l -> hash.containsKey(l.getKey()))
                .map(l -> new ImmutableTriple<>(l.getKey(), l.getValue(), hash.get(l.getKey())))
                .collect(Collectors.toList());
    }

    @Override
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> leftJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        Map<K, V> hash = new HashMap<>();
        for (Pair<K, V> r : right) {
            hash.put(r.getKey(), r.getValue());
        }
        List<Triple<K, V, V>> collect = new ArrayList<>();
        for (Pair<K, V> l : left) {
            if (hash.containsKey(l.getKey())) {
                collect.add(new ImmutableTriple<>(l.getKey(), l.getValue(), hash.get(l.getKey())));
            }
            else {
                collect.add(new ImmutableTriple<>(l.getKey(), l.getValue(), null));
            }
        }
        return collect;
    }
}
