package kata.java;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class PersistentList<E> {
    private static final PersistentList<?> EMPTY = new PersistentList<>(null, null);

    private final E head;
    private final PersistentList<E> tail;

    public static <E> PersistentList<E> empty() {
        return (PersistentList<E>)EMPTY;
    }

    public PersistentList(E head, PersistentList<E> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public String toString() {
        PersistentList<E> list = this;
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        if (list != EMPTY) {
            while (list.tail != EMPTY) {
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
        return foldLeft(empty(), (item, list) -> list.prepend(item));
    }


    public <R> PersistentList<R> map(Function<E, R> map) {
        return reverse().foldLeft(empty(), (item, acc) -> acc.prepend(map.apply(item)));
    }

    public <A> A foldLeft(A accumulator, BiFunction<E, A, A> function) {
        return this == EMPTY ? accumulator : tail.foldLeft(function.apply(head, accumulator), function);
    }
}
