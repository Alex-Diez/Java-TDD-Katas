package kata.java;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class PersistentList<E> {
    private static final PersistentList<?> EMPTY = new PersistentList<>(null, null);

    private final E head;
    private final PersistentList<E> tail;

    private PersistentList(E head, PersistentList<E> tail) {
        this.head = head;
        this.tail = tail;
    }

    public static <E> PersistentList<E> empty() {
        return (PersistentList<E>)EMPTY;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        PersistentList list = this;
        if (list != empty()) {
            while (list.tail != empty()) {
                builder.append(list.head);
                builder.append(", ");
                list = list.tail;
            }
            builder.append(list.head);
        }
        builder.append(']');
        return builder.toString();
    }

    public PersistentList<E> prepend(E item) {
        return new PersistentList<>(item, this);
    }

    public PersistentList<E> append(E item) {
        return this == EMPTY ? new PersistentList<>(item, empty()) : new PersistentList<>(head, tail.append(item));
    }

    public Optional<E> head() {
        return Optional.ofNullable(head);
    }

    public PersistentList tail() {
        return this == EMPTY ? EMPTY : tail;
    }

    public boolean forAll(Predicate<E> predicate) {
        return this == EMPTY || predicate.test(head) && tail.forAll(predicate);
    }

    public PersistentList<E> concat(PersistentList<E> other) {
        return this == EMPTY ? other : new PersistentList<>(head, tail.concat(other));
    }

    public PersistentList<E> drop(int n) {
        if (this == EMPTY) return empty();
        else if (n == 0) return this;
        else return tail.drop(n - 1);
    }

    public PersistentList<E> dropWhile(Predicate<E> predicate) {
        if (this == EMPTY) return empty();
        else if (!predicate.test(head)) return this;
        else return tail.dropWhile(predicate);
    }

    public PersistentList<E> filter(Predicate<E> predicate) {
        if (this == EMPTY) return empty();
        else if (!predicate.test(head)) return tail.filter(predicate);
        else return new PersistentList<>(head, tail.filter(predicate));
    }

    public PersistentList<E> reverse() {
        return foldLeft(empty(), PersistentList::prepend);
    }

    public <M> PersistentList<M> map(Function<E, M> map) {
        return reverse().foldLeft(empty(), (acc, item) -> acc.prepend(map.apply(item)));
    }

    public <A> A foldLeft(A accumulator, BiFunction<A, E, A> function) {
        return this == EMPTY ? accumulator : tail.foldLeft(function.apply(accumulator, head), function);
    }
}
