package kata.joins;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MergeJoin<K extends Comparable<K>, V>
        implements InnerJoin<K, V> {

    @Override
    public List<Triple<K, V, V>> innerJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        List<Triple<K, V, V>> collect = new ArrayList<>();

        Iterator<Pair<K, V>> lIterator = left.iterator();
        Iterator<Pair<K, V>> rIterator = right.iterator();

        Pair<K, V> l = lIterator.next();
        Pair<K, V> r = rIterator.next();

        while(true) {
            int cmp = l.getKey().compareTo(r.getKey());

            if (cmp < 0) {
                if (lIterator.hasNext()) {
                    l = lIterator.next();
                } else {
                    break;
                }
            }
            else if (cmp > 0) {
                if (rIterator.hasNext()) {
                    r = rIterator.next();
                }
                else {
                    break;
                }
            }
            else {
                collect.add(new ImmutableTriple<>(l.getKey(), l.getValue(), r.getValue()));
                if (lIterator.hasNext() && rIterator.hasNext()) {
                    l = lIterator.next();
                    r = rIterator.next();
                }
                else {
                    break;
                }
            }

        }
        return collect;
    }

    public List<Triple<K, V, V>> leftJoin(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        return null;
    }
}
