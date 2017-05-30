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

    public static <S> PersistentList<S> empty() {
        return (PersistentList<S>)EMPTY;
    }

    @Override
    public String toString() {
        PersistentList<E> list = this;
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        if (list != EMPTY) {
            while (list.tail != EMPTY) {
                builder.append(list.head).append(", ");
                list = list.tail;
            }
            builder.append(list.head);
        }
        builder.append(']');
        return builder.toString();
    }

    public PersistentList<E> prepend(E head) {
        return new PersistentList<>(head, this);
    }

    public Optional<E> head() {
        return Optional.ofNullable(head);
    }

    public PersistentList<E> tail() {
        return this == EMPTY ? empty() : tail;
    }

    public PersistentList<E> drop(int n) {
        if (this == EMPTY) return empty();
        else if (n < 1) return this;
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
        return foldLeft((item, acc) -> acc.prepend(item), empty());
    }

    public <R> PersistentList<R> map(Function<E, R> map) {
        return reverse().foldLeft((item, acc) -> acc.prepend(map.apply(item)), empty());
    }

    public <A> A foldLeft(BiFunction<E, A, A> function, A accumulator) {
        return this == EMPTY ? accumulator : tail.foldLeft(function, function.apply(head, accumulator));
    }
}
