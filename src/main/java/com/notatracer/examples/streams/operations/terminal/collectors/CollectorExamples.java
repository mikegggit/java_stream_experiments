package com.notatracer.examples.streams.operations.terminal.collectors;

import com.notatracer.examples.domain.Album;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Collectors perform some kind of reduction operation on stream elements.
 *
 * Reductions might include arranging elements into a container,
 * transforming elements to result in the desired format, performing
 * summary operations across and within stream elements, etc.
 */
public class CollectorExamples {

    public static void main(String[] args) {
        CollectorExamples examples = new CollectorExamples();
        examples.collectToMutableList();
        examples.collectToMutableMap();
        examples.joinStrings();
    }

    private void collectToMutableList() {
        // Just put stream el's into a list...
        List<Integer> ints = Stream
                .iterate(0, i -> i < 999, i -> i + 3)
                .collect(Collectors.toList());

        ints.forEach(System.out::println);

        // Notice the collector result is mutable, meaning you can
        // add elements to it.
        ints.add(100);
    }

    private void collectToMutableMap() {
        Map<String, Album> registry = Arrays.asList(
                new Album.AlbumBuilder("Wish You Were Here").playedBy("Pink Floyd").fromYear(1975).build(),
                new Album.AlbumBuilder("Back In Black").playedBy("AC/DC").fromYear(1982).build(),
                new Album.AlbumBuilder("Brothers in Arms").playedBy("Dire Straits").fromYear(1985).build(),
                new Album.AlbumBuilder("Nevermind").playedBy("Nirvana").fromYear(1991).build())
                .stream()
                .collect(Collectors.toMap(
                        a -> a.name, b -> b
                ));

        System.out.println(registry.get("Nevermind"));
        registry.put("Dead Cross", new Album.AlbumBuilder("Dead Cross").fromYear(2018).playedBy("Dead Cross").build());

    }

    private void joinStrings() {
        String joined = Stream.of("One", "Two", "Three")
                .collect(Collectors.joining(",", "Starting|", "|Done"));

        System.out.println(joined);
    }

}
