package com.notatracer.examples.streams.operations.intermediate.flatmap;

import java.util.Arrays;
import java.util.List;

/**
 * Flattening something means restructuring it to remove nested structure.
 *
 * It is the combination of a map and a flattener.
 *
 *
 */
public class FlatmapExamples {

    public static void main(String[] args) {
        FlatmapExamples examples = new FlatmapExamples();
        examples.simple();
    }

    private void simple() {
        // Creating a List of Strings
        List<String> list = Arrays.asList("ABC", "DEF", "GHI",
                "JKL", "MNO", "PQR", "STU", "VWX", "YZ");

        // Using Stream flatMap(Function mapper)
        list
                .stream()
                .flatMap(s -> s.chars().boxed())
//                .map(c -> (char)(c.intValue()))
                .forEach(
                        i -> System.out.println((char)i.intValue())
                );
    }
}
