package src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class SequenceMap<K, V> extends SequenceItem<K> {

    Map<K, V> input;

    public SequenceMap(Map<K, V> input) {
        this.input = input;
    }

    // TODO
    // public SequenceSet<K> keys() {
    //     return input.keySet();
    // }

    public Collection<V> values() {
        return input.values();
    }

    @SuppressWarnings("unchecked")
    public <R> SequenceMap<K, R> mapValues(Function<V, R> f) {
        Supplier<Map<K, R>> s = () -> {
            try {
                return input.getClass().getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                // default map type if given an immutable map
                return new HashMap<>();
            }
        };

        Map<K, R> ret = s.get();
        for (Map.Entry<K, V> entry : input.entrySet()) {
            ret.put(entry.getKey(), f.apply(entry.getValue()));
        }

        return new SequenceMap<>(ret);
    }

    /**
     * Return the reduced version of the maps keyset
     * 
     * @param <R> return type
     * @param identity initial value of the accumlator
     * @param f bifunction to perform reduction 
     * @return the reduced keyset
     */
    public <R> SequenceItem<R> reduceKeys(R identity, BiFunction<R, ? super K, R> f) {
        return SequenceItem.<R, K>reducer(identity, f, input.keySet());
    }

    /**
     * Return the reduced version of the maps values
     * 
     * @param <R> return type
     * @param identity initial value of the accumlator
     * @param f bifunction to perform reduction 
     * @return the reduced collection of values
     */
    public <R> SequenceItem<R> reduceValues(R identity, BiFunction<R, ? super V, R> f) {
        return SequenceItem.<R, V>reducer(identity, f, input.values());
    }

}
