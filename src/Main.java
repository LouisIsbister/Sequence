package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    
    public static void main(String[] args) {
        // expect 2049
        assert SequenceItem.of(2024)
                .map(e -> e + 25)
                .obtain()
                == 2049;

        // expect 0x1.f412p21
        assert SequenceItem.of(2024)
                .map(e -> e * e)
                .map(e -> Double.toHexString(e))
                .obtain()
                .equals("0x1.f412p21");

        // expect 15.0
        assert SequenceItem.of(List.of(1, 2, 3, 4, 5))
                .reduce(0.0, (a, b) -> a + b)
                .obtain() 
                == 15.0;

        // expect 55
        List<String> list = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
        assert SequenceItem.of(list)
                .map(Integer::parseInt)
                .map(e -> e * e)
                .reduce(0, (a, b) -> a + b)
                .orElse(0) 
                == 55;

        // expect 20
        Map<String, Integer> map = new HashMap<>(Map.of("a", 1, "b", 2, "c", 3, "d", 4));
        assert SequenceItem.of(map)
                .mapValues(e -> e * 2)
                .reduceValues(0, (a, b) -> a + b)
                .orElse(0) 
                == 20;

        // expect "abcd"
        assert SequenceItem.of(map)
                .reduceKeys("", (a, b) -> a + b)
                .orElse("FAILURE") 
                .equals("abcd");

        assert SequenceItem.of(Map.of())
                .map(e -> null)
                .orElse("?????") 
                .equals("?????");
    }
}
