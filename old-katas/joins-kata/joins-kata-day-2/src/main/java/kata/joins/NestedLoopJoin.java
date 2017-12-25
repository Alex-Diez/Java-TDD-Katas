package kata.joins;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class NestedLoopJoin {
    public <K, V> List<Triple<K, V, V>> join(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        List<Triple<K, V, V>> collect = new ArrayList<>();

        for (Pair<K, V> l : left) {
            collect.addAll(
                    right.stream().filter(r -> l.getKey().equals(r.getKey()))
                            .map(r -> new ImmutableTriple<>(l.getKey(), l.getValue(), r.getValue()))
                            .collect(Collectors.toList()));
        }
        return collect;
    }
}
