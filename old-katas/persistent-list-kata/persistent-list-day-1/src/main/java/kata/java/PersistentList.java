package kata.java;

import java.util.Optional;
import java.util.function.Predicate;

public class PersistentList {

    private static final PersistentList EMPTY = new PersistentList(null, null);

    private final Integer item;
    private final PersistentList tail;

    public static PersistentList empty() {
        return EMPTY;
    }

    private PersistentList(Integer item, PersistentList tail) {
        this.item = item;
        this.tail = tail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        PersistentList list = this;
        while (list.item != null) {
            sb.append(list.item);
            if (list.tail != EMPTY) {
                sb.append(", ");
            }
            list = list.tail;
        }
        return sb.append(']').toString();
    }

    public PersistentList prepend(int item) {
        return new PersistentList(item, this);
    }

    public PersistentList append(int item) {
        return this != EMPTY ? new PersistentList(this.item, this.tail.prepend(item)) : new PersistentList(item, EMPTY);
    }

    public Optional<Integer> head() {
        return Optional.ofNullable(item);
    }

    public PersistentList tail() {
        return this != EMPTY ? tail : EMPTY;
    }

    public boolean forAll(Predicate<Integer> predicate) {
        return this == EMPTY || predicate.test(item) && tail.forAll(predicate);
    }
}
