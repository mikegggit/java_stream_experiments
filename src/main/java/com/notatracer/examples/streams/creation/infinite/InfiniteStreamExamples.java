package com.notatracer.examples.streams.creation.infinite;

import java.util.Random;
import java.util.UUID;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Infinite streams have no known end.
 *
 * Streams originating from an i/o channel or other unbounded source can be considered infinite.
 *
 * an be created using supplier functions.
 *
 */
public class InfiniteStreamExamples {

    public static void main(String[] args) {
        InfiniteStreamExamples examples = new InfiniteStreamExamples();
        examples.neverEnds();
        examples.simple();
        examples.skip();
//        examples.limit();
    }

    /**
     * Unless a limit intermediate function is included in the pipeline, a pipeline involving
     * an infinite stream will never return.
     */
    private void neverEnds() {
        try {
            Stream.iterate(1, i -> i + 1)
                    .forEach(
                            n -> {
                                if (n > 200000) {
                                    throw new IllegalStateException("This would run forever if allowed");
                                }
                                System.out.println(n);
                            }
            );

        } catch (IllegalStateException ise) {
            System.out.println("Aborted infinite stream pipeline");
        }

    }

    private void simple() {
        DoubleStream.generate(
                    new Random()::nextDouble
                )
                .limit(100) // limit simply for testing purposes, otherwise this would run forever...
                .forEach(System.out::println);


        Stream.generate(
                () ->
                        UUID.randomUUID())
                .limit(200)
                .forEach(System.out::println);
    }

    private void skip() {
        IntStream.Builder builder = IntStream.builder();
        builder.accept(1);
        builder.accept(2);
        builder.accept(3);
        IntStream s = builder.build();
        s
                .skip(1)
                .forEach(
                        System.out::println
                );

    }


}
