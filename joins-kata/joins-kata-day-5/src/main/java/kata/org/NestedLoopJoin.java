package kata.org;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

public class NestedLoopJoin
        implements InnerJoin,
                   OuterJoin {

    private <K extends Comparable<K>, V> List<Triple<K, V, V>> join(
            List<Pair<K, V>> left,
            List<Pair<K, V>> right,
            Join join) {
        List<Triple<K, V, V>> collect = new ArrayList<>();
        for (Pair<K, V> l : left) {
            for (Pair<K, V> r : right) {
                if (l.getKey().compareTo(r.getKey()) == 0) {
                    join.keyMatched();
                    collect.add(new ImmutableTriple<>(l.getKey(), l.getValue(), r.getValue()));
                }
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
