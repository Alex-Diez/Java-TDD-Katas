package kata.org;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public interface OuterJoin {

    <K extends Comparable<K>, V> List<Triple<K, V, V>> leftJoin(List<Pair<K, V>> left, List<Pair<K, V>> right);

    <K extends Comparable<K>, V> List<Triple<K, V, V>> rightJoin(List<Pair<K, V>> left, List<Pair<K, V>> right);
}
