package kata.joins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

@SuppressWarnings({"ALL"})
public class NestedJoin {

    public <K, VL, VR> List<Triple<K, VL, VR>> innerJoin(List<Pair<K, VL>> left, List<Pair<K, VR>> right) {
        List<Triple<K, VL, VR>> collect = new ArrayList<>();
        for (Pair<K, VL> leftPair : left) {
            for (Pair<K, VR> rightPair : right) {
                if (leftPair.getKey().equals(rightPair.getKey())) {
                    collect.add(new ImmutableTriple<K, VL, VR>(leftPair.getKey(), leftPair.getRight(), rightPair.getRight()));
                }
            }
        }
        return collect;
    }

    public <K, VL, VR> List<Triple<K, VL, VR>> leftJoin(List<Pair<K, VL>> left, List<Pair<K, VR>> right) {
        List<Triple<K, VL, VR>> collect = new ArrayList<>();
        for (Pair<K, VL> leftPair : left) {
            boolean find = false;
            for (Pair<K, VR> rightPair : right) {
                if (leftPair.getKey().equals(rightPair.getKey())) {
                    find = true;
                    collect.add(new ImmutableTriple<K, VL, VR>(leftPair.getKey(), leftPair.getRight(), rightPair.getRight()));
                }
            }
            if (!find) {
                collect.add(new ImmutableTriple<K, VL, VR>(leftPair.getKey(), leftPair.getRight(), null));
            }
        }
        return collect;
    }

    public <K, VL, VR> List<Triple<K, VL, VR>> rightJoin(List<Pair<K, VL>> left, List<Pair<K, VR>> right) {
        List<Triple<K, VL, VR>> collect = new ArrayList<>();
        for (Pair<K, VR> rightPair : right) {
            boolean find = false;
            for (Pair<K, VL> leftPair : left) {
                if (leftPair.getKey().equals(rightPair.getKey())) {
                    find = true;
                    collect.add(new ImmutableTriple<K, VL, VR>(leftPair.getKey(), leftPair.getRight(), rightPair.getRight()));
                }
            }
            if (!find) {
                collect.add(new ImmutableTriple<K, VL, VR>(rightPair.getKey(), null, rightPair.getRight()));
            }
        }
        return collect;
    }
}
