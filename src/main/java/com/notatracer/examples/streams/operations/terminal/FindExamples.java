package com.notatracer.examples.streams.operations.terminal;

import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class FindExamples {
    public static void main(String[] args) {
        FindExamples examples = new FindExamples();
        examples.findFirst();
    }

    /**
     * FindFirst short-circuits traversal of the stream if a match is found.
     */
    private void findFirst() {
        int i1 = IntStream.range(0, 100)
                .filter(i -> i > 50)
                .findFirst()
                .orElse(300);

        System.out.println(i1);

    }


}
