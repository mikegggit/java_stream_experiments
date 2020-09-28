package com.notatracer.examples.streams.operations.intermediate.map;

import java.util.Arrays;
import java.util.List;

/**
 * Flattening something means restructuring it to remove nested structure.
 *
 * It is the combination of a map and a flattener.
 *
 *
 */
public class MapExamples {

    public static void main(String[] args) {
        MapExamples examples = new MapExamples();
        examples.changeType();
    }

    private void changeType() {
        // Creating a List of Strings
        List<String> list = Arrays.asList("ABC", "DEF", "GHI",
                "JKL", "MNO", "PQR", "STU", "VWX", "YZ");

        // Using Stream flatMap(Function mapper)
        list
                .stream()
                .map(l -> l.length())
                .forEach(
                        System.out::println
                );
    }
}
