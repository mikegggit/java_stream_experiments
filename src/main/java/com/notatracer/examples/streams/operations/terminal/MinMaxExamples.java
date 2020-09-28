package com.notatracer.examples.streams.operations.terminal;

import com.notatracer.examples.domain.Album;
import com.notatracer.examples.utils.ExampleUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class MinMaxExamples {

    public static void main(String[] args) {
        MinMaxExamples examples = new MinMaxExamples();
        examples.simple();
        examples.customComparator();
    }

    private void simple() {
        Random random = new Random();
        int i = random.ints()
                .limit(100)
                .min()
                .orElse(-1);
        System.out.println(i);
    }

    private void customComparator() {
        Optional<Album> oldest = ExampleUtils.listOfAlbums()
                .stream()
                .min((l, r) -> l.year <= r.year ? -1 : 1);

        System.out.println(oldest.orElseThrow());

        Optional<Album> newest = ExampleUtils.listOfAlbums()
                .stream()
                .min((l, r) -> l.year > r.year ? -1 : 1);

        System.out.println(newest.orElseThrow());
    }

}
