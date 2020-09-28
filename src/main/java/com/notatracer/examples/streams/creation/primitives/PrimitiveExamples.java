package com.notatracer.examples.streams.creation.primitives;

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Boxing is needed in certain use-cases to handle streams of primitives.
 */
public class PrimitiveExamples {

    public static void main(String[] args) {
        PrimitiveExamples examples = new PrimitiveExamples();
        examples.intStream();
        examples.doubleStream();
    }

    private void intStream() {
        // Creating a List of Strings
        IntStream.range(0, 11)
                .forEach(System.out::println);
    }

    private void doubleStream() {
        DoubleStream.of(1.2, 3.533, 99.123)
                .forEach(
                        System.out::println
                );

        DoubleStream.builder().add(2.3344).add(444.34).build()
                .forEach(
                        System.out::println
                );

        DoubleStream.generate(
                    new Random()::nextDouble
                )
                .limit(100)
                .forEach(System.out::println);

    }
}
