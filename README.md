# Sequence
Collection of classes to allow chains of sequential operations

# Example usages (so far):
```java
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
```
