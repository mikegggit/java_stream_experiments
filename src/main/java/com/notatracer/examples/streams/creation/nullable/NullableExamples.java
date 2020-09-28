package com.notatracer.examples.streams.creation.nullable;

import java.util.stream.Stream;

public class NullableExamples {

    public static void main(String[] args) {
        NullableExamples examples = new NullableExamples();
        examples.simple();
    }

    private void simple() {
        Integer i = null;

        // Generates an empty stream since i is null...
        Stream.ofNullable(i)
                .forEach(
                        System.out::println
                );

        Stream.ofNullable(3)
                .map(
                        j -> j * 300
                )
                .forEach(System.out::println);
    }
}
