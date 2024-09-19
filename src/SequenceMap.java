package src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class SequenceMap<K, V> implements SequenceCollection<V> {

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
                return new HashMap<>();
            }
        };

        Map<K, R> ret = s.get();
        for (Map.Entry<K, V> entry : input.entrySet()) {
            ret.put(entry.getKey(), f.apply(entry.getValue()));
        }

        return new SequenceMap<>(ret);
    }

    @Override
    public <R> SequenceItem<R> reduce(R identity, BiFunction<R, ? super V, R> f) {
        R result = identity;
        for (V item : input.values()) {
            result = f.apply(result, item);
        }
        return new SequenceItem<R>(result);
    }

    
}