package src;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class SequenceItem<T> {
    private T input;

    public SequenceItem() {}

    public SequenceItem(T input) {
        this.input = input;
    }

    public static <R> SequenceItem<R> of(R t) {
        return new SequenceItem<R>(t);
    }

    public <R> SequenceItem<R> map(Function<T, R> f) {
        return new SequenceItem<R>(f.apply(input));
    }

    public void ifPresent(Consumer<T> c) {
        if (input != null) {
            c.accept(input);
        }
    }

    public T orElse(T other) {
        return input != null ? input : other;
    }

    public T obtain() {
        return input;
    }

    static <K, V> SequenceMap<K, V> of(Map<K, V> m) {
        return new SequenceMap<>(m);
    }
    static <E> SequenceList<E> of(List<E> l) {
        return new SequenceList<>(l);
    }

    /**
     * method to perform a reduction on any collection type
     * 
     * @param <R> the resulting type of the reduction
     * @param <S> the type of the values in the reducee collection
     * @param identity initial value of the accumulator (acc)
     * @param f bifunction for reduction
     * @param reducee collection that is to be reduced
     * @return a new sequence item with the reduced value as the input
     */
    public static <R, S> SequenceItem<R> reducer(R identity, BiFunction<R, ? super S, R> f, Collection<S> reducee) {
        R acc = identity;
        for (S item : reducee) {
            acc = f.apply(acc, item);
        }
        return new SequenceItem<R>(acc);
    }
}