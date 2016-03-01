package kata.joins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class HashJoin {

    public <K, V> List<Triple<K, V, V>> join(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        Map<K, V> hash = new HashMap<>();
        for (Pair<K, V> pair : left) {
            hash.put(pair.getKey(), pair.getValue());
        }
        List<Triple<K, V, V>> collect = new ArrayList<>();
        for (Pair<K, V> pair : right) {
            if (hash.containsKey(pair.getKey())) {
                collect.add(new ImmutableTriple<>(pair.getKey(), hash.get(pair.getKey()), pair.getValue()));
            }
        }
        return collect;
    }
}
