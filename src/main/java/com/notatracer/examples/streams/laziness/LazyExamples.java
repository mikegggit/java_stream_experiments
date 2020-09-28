package com.notatracer.examples.streams.laziness;

import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Intermediate operations on a stream pipeline are only invoked
 * when a terminal operation is invoked, and then, subject to only
 * being invoked subject to any limiting operations, like findFirst,
 * limit, ...
 */
public class LazyExamples {
    public static void main(String[] args) {
        LazyExamples examples = new LazyExamples();
        examples.simple();
        examples.sortedIsNotLimited();
    }

    private void simple() {
        OptionalInt first = IntStream
                .range(0, 10)
                .map(i -> {
                    // this only prints once, demonstrating that the pipeline only executes
                    // as a byproduct of the terminal operation being executed.
                    System.out.println(i);
                    return i + 10;
                })
                .findFirst();

        first.ifPresent(
                i -> System.out.println(i)
        );
    }

    private void sortedIsNotLimited() {
        // Sorted is not subject to limiting since application of a limiting function
        // must come after sorting of the entire input dataset...
        IntStream.of(6,3,8,6,2,6,8,11,24553,6,4345,262,7,1,457,48,4,754,36)
                .sorted()
                .limit(1)
                .forEach(System.out::println);
    }



}
