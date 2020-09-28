package com.notatracer.examples.streams.operations.terminal;

import com.notatracer.examples.utils.ExampleUtils;

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Certain operations will stop processing the pipeline as soon as some
 * condition is or isn't matched.
 */
public class ShortCircuitingExamples {
    public static void main(String[] args) {
        ShortCircuitingExamples examples = new ShortCircuitingExamples();
        examples.matchesAll();
        examples.matchesAny();
        examples.matchesNone();
    }

    private void matchesAll() {
        boolean allNewish = ExampleUtils.listOfAlbums()
                .stream()
                .allMatch(a -> a.year > 1950);
        System.out.println(allNewish);
    }

    private void matchesAny() {
        boolean anyACDCAlbums = ExampleUtils.listOfAlbums()
                .stream()
                .anyMatch(a -> a.bandName.equals("AC/DC"));

        System.out.println(anyACDCAlbums);
    }

    private void matchesNone() {
        boolean noSeventiesAlbums = ExampleUtils.listOfAlbums()
                .stream()
                .peek(System.out::println) // This doesn't print all source items.
                .noneMatch(a -> a.year >= 1970 &&  a.year < 1980);

        System.out.println(noSeventiesAlbums);
    }
}
