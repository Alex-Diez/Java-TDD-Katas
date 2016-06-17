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

    private <K extends Comparable<K>, V> List<Triple<K, V, V>> join(List<Pair<K, V>> left, List<Pair<K, V>> right, Join join) {
        Map<K, V> hash = right.stream().collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        List<Triple<K, V, V>> collect = new ArrayList<>();
        for (Pair<K, V> l : left) {
            if (hash.containsKey(l.getKey())) {
                join.keyMatching();
                collect.add(new ImmutableTriple<>(l.getKey(), l.getValue(), hash.get(l.getKey())));
            }
            if (!join.checkMatchingAndReset()) {
                collect.add(new ImmutableTriple<>(l.getKey(), l.getValue(), null));
            }
        }
        return collect;
    }

    @Override
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> innerJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        return join(left, right, Join.INNER);
    }

    @Override
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> leftJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        return join(left, right, Join.OUTER);
    }

    @Override
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> rightJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        return join(right, left, Join.OUTER);
    }
}
