package kata.joins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class MergeJoin {
    public <K extends Comparable<K>, V> List<Triple<K, V, V>> join(List<Pair<K, V>> left, List<Pair<K, V>> right) {
        List<Triple<K, V, V>> collect = new ArrayList<>();
        Iterator<Pair<K, V>> leftIterator = left.iterator();
        Iterator<Pair<K, V>> rightIterator = right.iterator();

        Pair<K, V> leftPair = leftIterator.next();
        Pair<K, V> rightPair = rightIterator.next();

        while (true) {
            int cmp = leftPair.getKey().compareTo(rightPair.getKey());
            if (cmp < 0) {
                if (leftIterator.hasNext()) {
                    leftPair = leftIterator.next();
                }
                else {
                    break;
                }
            }
            else if (cmp > 0) {
                if (rightIterator.hasNext()) {
                    rightPair = rightIterator.next();
                }
                else {
                    break;
                }
            }
            else {
                collect.add(new ImmutableTriple<>(leftPair.getKey(), leftPair.getValue(), rightPair.getValue()));
                if (leftIterator.hasNext() && rightIterator.hasNext()) {
                    leftPair = leftIterator.next();
                    rightPair = rightIterator.next();
                }
                else {
                    break;
                }
            }
        }
        return collect;
    }
}
