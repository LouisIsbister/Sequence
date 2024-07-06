
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
        Sequence.of(2024)
                .map(e -> e + 25)
                .ifPresent(System.out::println);

        // expect 0x1.f412p21
        String res = Sequence.of(2024)
                .map(e -> e * e)
                .map(e -> Double.toHexString(e))
                .obtain();
        System.out.println(res);        

        // expect 15.0
        SequenceCollection.of(List.of(1, 2, 3, 4, 5))
                .reduce(0.0, (a, b) -> a + b)
                .ifPresent(System.out::println);

        // expect 55
        SequenceCollection.of(List.of("1", "2", "3", "4", "5"))
                .map(Integer::parseInt)
                .map(e -> e * e)
                .reduce(0, (a, b) -> a + b)
                .ifPresent(System.out::println);
    }
}