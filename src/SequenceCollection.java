package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    @Override
    public <R> SequenceCollection<R> map(Function<T, R> f) {
        Collection<R> mapped = getNewCollection(input);

        @SuppressWarnings("unchecked")
        T[] arr = (T[]) input.toArray();
        
        for (T item : arr) {
            mapped.add(f.apply(item));
        }

        return new SequenceCollection<R>(mapped);
    }

    @SuppressWarnings("unchecked")
    private <R> Collection<R> getNewCollection(Collection<T> input) {
        if (input instanceof List) return new ArrayList<>();
        if (input instanceof Set) return new HashSet<>();
        try {
            return (Collection<R>) input.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unable to create a new instance of the collection: " + input.getClass(), e);
        }
    }
    
}
