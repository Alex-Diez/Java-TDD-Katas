package kata.org;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public interface InnerJoin {

    <K extends Comparable<K>, V> List<Triple<K, V, V>> innerJoin(List<Pair<K, V>> left, List<Pair<K, V>> right);
}
