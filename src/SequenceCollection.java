package src;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SequenceCollection<T> extends Sequence<T> {

    Collection<T> input;

    public SequenceCollection(Collection<T> input) {
        super();
        this.input = input;
    }

    static <R> SequenceCollection<R> of(Collection<R> col) {
        return new SequenceCollection<>(col);
    }

    public <R> Sequence<R> reduce(R identity, BiFunction<R, ? super T, R> f) {
        R result = identity;
        for (T item : input) {
            result = f.apply(result, item);
        }
        return new Sequence<>(result);
    }

    @SuppressWarnings("unchecked")
    public <R> SequenceCollection<R> map(Function<T, R> f) {
        Supplier<Collection<R>> s = () -> {
            try {
                return input.getClass().getConstructor().newInstance();
            } catch (Exception e) {
                if (input instanceof List) return new ArrayList<>();
                if (input instanceof Set) return new HashSet<>();
                // TODO: add mapping of maps functionality
                throw new UnsupportedOperationException("Unable to create a new instance of the collection: " + input.getClass(), e);
            }
        };

        Collection<R> mapped = input.stream()
                .map(e -> f.apply(e))
                .collect(Collectors.toCollection(s));

        return new SequenceCollection<R>(mapped);
    }
    
}
