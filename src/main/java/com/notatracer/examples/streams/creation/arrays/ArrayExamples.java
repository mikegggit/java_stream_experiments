package com.notatracer.examples.streams.creation.arrays;

import java.util.Arrays;
import java.util.stream.Stream;

public class ArrayExamples {

    public static final void main(String[] args) {
        ArrayExamples examples = new ArrayExamples();
        examples.existingArray();
        examples.factory();
    }

    private void existingArray() {
        String[] ar = new String[] {"one", "two", "three"};
        Arrays.stream(ar)
                .forEach(System.out::println);

    }

    private void factory() {
        Stream.of("one", "two", "three")
                .forEach(System.out::println);
    }

}
