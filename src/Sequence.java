package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Sequence<T> {
    private T input;

    public Sequence() {}

    public Sequence(T input) {
        this.input = input;
    }

    public static <R> Sequence<R> of(R t) {
        return new Sequence<R>(t);
    }

    public <R> Sequence<R> map(Function<T, R> f) {
        return new Sequence<R>(f.apply(input));
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

    public static void main(String[] args) {
        // expect 2049
        assert Sequence.of(2024)
                .map(e -> e + 25)
                .obtain() 
                == 2049;

        // expect 0x1.f412p21
        assert Sequence.of(2024)
                .map(e -> e * e)
                .map(e -> Double.toHexString(e))
                .obtain()
                .equals("0x1.f412p21");

        // expect 15.0
        assert SequenceCollection.of(List.of(1, 2, 3, 4, 5))
                .reduce(0.0, (a, b) -> a + b)
                .obtain() 
                == 15;

        // expect 55
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList("1", "2", "3", "4", "5"));
        assert SequenceCollection.of(list)
                .map(Integer::parseInt)
                .map(e -> e * e)
                .reduce(0, (a, b) -> a + b)
                .orElse(0) 
                == 55;
    }
}