package src;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class SequenceList<T> implements SequenceCollection<T> {
    
    List<T> input;

    public SequenceList(List<T> input) {
        this.input = input;
    }

    @SuppressWarnings("unchecked")
    public <R> SequenceList<R> map(Function<T, R> f) {
        Supplier<List<R>> s = () -> {
            try {
                return input.getClass().getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        };

        List<R> ret = s.get();
        for (T elem : input) {
            ret.add(f.apply(elem));
        }

        return new SequenceList<>(ret);
    }

    @Override
    public <R> SequenceItem<R> reduce(R identity, BiFunction<R, ? super T, R> f) {
        R result = identity;
        for (T item : input) {
            result = f.apply(result, item);
        }
        return new SequenceItem<R>(result);
    }
    
}

