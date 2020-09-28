package com.notatracer.examples.streams.creation.collection;

import java.util.*;
import java.util.stream.Stream;

public class CollectionExamples {

    public static void main(String[] args) {
        CollectionExamples examples = new CollectionExamples();
        examples.list();
        examples.map();
        examples.set();
    }

    private void list() {
        List<String> l = Arrays.asList("one", "two", "three");
        l.stream()
                .forEach(System.out::println);

    }

    private void map() {
        Map<Integer, String> m =
                Map.of(
                        1, "one",
                        2, "two",
                        3, "three",
                        4, "four");
        Stream<Map.Entry<Integer, String>> stream = m.entrySet().stream();
        stream
                .sorted((l, r) -> {
                    return l.getKey().compareTo(r.getKey());
                })
                .forEach(entry -> {
                    System.out.println("key=" + entry.getKey() + ", val=" + entry.getValue());
                });
    }

    private void set() {
        Set<Integer> i = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        i.stream()
                .sorted((l, r) -> {
                    return l.compareTo(r);
                })
                .forEach(System.out::println);
    }

}
