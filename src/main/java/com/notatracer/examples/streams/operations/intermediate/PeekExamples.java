package com.notatracer.examples.streams.operations.intermediate;

import com.notatracer.examples.utils.ExampleUtils;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * Peek cannot change the type of the stream.
 *
 * It does return a new stream.
 *
 * If the stream elements ae mutable, they can be changed inside the operation.
 */
public class PeekExamples {
    public static void main(String[] args) {
        PeekExamples examples = new PeekExamples();
        examples.log();
        examples.alter();
    }

    private void log() {
        System.out.println(
                Stream.of("one", "two", "three")
                .peek(System.out::println)
                .count());
    }

    private void alter() {
        ExampleUtils.listOfAlbums()
                .stream()
                .peek(a -> a.mutableFoo = 999) // we've altered each element of the stream...
                .forEach(System.out::println);
    }
}
