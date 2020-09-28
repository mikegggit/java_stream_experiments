package com.notatracer.examples.streams.creation.random;

import java.util.Random;

public class RandomExamples {
    public static void main(String[] args) {
        RandomExamples examples = new RandomExamples();
        examples.infinite();
        examples.sized();
        examples.rangeBound();
        examples.sizedRangeBound();
    }

    private void infinite() {
        new Random()
                .ints()
                .limit(100) // only to avoid a neverending execution...
                .forEach(
                        System.out::println
                );
    }

    private void sized() {
        new Random()
                .ints(10)
                .forEach(System.out::println);
    }

    private void rangeBound() {
        new Random()
                .ints(0, 11)
                .limit(400) // only to avoid a neverending execution...
                .forEach(System.out::println);
    }

    private void sizedRangeBound() {
        new Random()
                .ints(15, 10, 21)
                .forEach(System.out::println);
    }
}
