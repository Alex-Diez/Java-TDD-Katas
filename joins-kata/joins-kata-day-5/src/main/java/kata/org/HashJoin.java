package kata.org;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HashJoin
        implements InnerJoin,
                   OuterJoin {

    private <K extends Comparable<K>, V> List<Triple<K, V, V>> join(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        Map<K, V> hash = right.stream().collect(Collectors.toMap((r -> r.getKey()), (l -> l.getValue())));
        return left.stream().map(
                l -> {
                    Triple<K, V, V> result;
                    if (hash.containsKey(l.getKey())) {
                        result = new ImmutableTriple<>(l.getKey(), l.getValue(), hash.get(l.getKey()));
                    }
                    else {
                        result = new ImmutableTriple<>(l.getKey(), l.getValue(), null);
                    }
                    return result;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> innerJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        return join(left, right);
    }

    @Override
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> leftJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        return join(left, right);
    }

    @Override
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> rightJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        return join(right, left);
    }
}
