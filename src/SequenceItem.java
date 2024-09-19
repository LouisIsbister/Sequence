package src;

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

}