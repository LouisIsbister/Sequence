package src;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public interface SequenceCollection<T> {

    public abstract <R> SequenceItem<R> reduce(R identity, BiFunction<R, ? super T, R> f);

    static <K, V> SequenceMap<K, V> of(Map<K, V> m) {
        return new SequenceMap<>(m);
    }
    static <E> SequenceList<E> of(List<E> l) {
        return new SequenceList<>(l);
    }

}
