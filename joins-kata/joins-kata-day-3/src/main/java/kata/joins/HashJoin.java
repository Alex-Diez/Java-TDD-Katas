package kata.joins;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HashJoin<K extends Comparable<K>, V>
        implements InnerJoin<K, V> {

    private static final Function<JoinType, Boolean> PREDICATE = (j) -> j == JoinType.INNER;

    @Override
    public List<Triple<K, V, V>> innerJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        return join(left, right, PREDICATE, JoinType.INNER);
    }

    public List<Triple<K, V, V>> leftJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        return join(left, right, PREDICATE, JoinType.OUTER);
    }

    private Map<K, V> createHash(List<Pair<K, V>> data) {
        return data.stream().collect(Collectors.toMap((kvPair -> kvPair.getKey()), (p -> p.getValue())));
    }

    private List<Triple<K, V, V>> join(List<Pair<K, V>> left, List<Pair<K, V>> right, Function<JoinType, Boolean> mapPredicate, JoinType joinType) {
        Map<K, V> hash = createHash(right);
        return left.stream()
                .map(l -> {
                            V r = null;
                            if (mapPredicate.apply(joinType) || hash.containsKey(l.getKey())) {
                                r = hash.get(l.getKey());
                            }
                            return new ImmutableTriple<>(l.getKey(), l.getValue(), r);
                        }
                ).collect(Collectors.toList());
    }
}
