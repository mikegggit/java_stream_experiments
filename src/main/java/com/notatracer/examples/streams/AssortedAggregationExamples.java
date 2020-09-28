package com.notatracer.examples.streams;

import com.notatracer.examples.domain.Album;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AssortedAggregationExamples {

    public static void main(String[] args) {
        AssortedAggregationExamples example = new AssortedAggregationExamples();
        example.streamFileContents();
        example.numericStreams();
        example.fromArrayOfObjects();
        example.combineFileContents();
    }

    public AssortedAggregationExamples() {
    }

    public void streamFileContents() {
        Path text = Paths.get("src/main/resources/tomsawyer.txt");

        try {
            Stream<String> lines = Files.lines(text);
            System.out.println(lines.count());
        } catch (IOException ioe) {
            // yum
            System.out.println(ioe);
        }
    }

    private void numericStreams() {
        long count = IntStream.range(0, 1000).filter(
                n -> n % 2 == 0
        ).count();
        System.out.println(count);
    }

    private void fromArrayOfObjects() {
        Stream.of(new Album("Fly By Night", "Rush", 1978), new Album("Mr. Bungle", "Mr. Bungle", 1991))
                .sorted((x, y) -> Integer.compare(x.year, y.year))
                .forEach(a -> System.out.println(a.year));
    }


    private void combineFileContents() {
        Path p1 = Paths.get("src/main/resources/tomsawyer_part1.txt");
        Path p2 = Paths.get("src/main/resources/tomsawyer_part2.txt");
        Path p3 = Paths.get("src/main/resources/tomsawyer_part3.txt");
        Path p4 = Paths.get("src/main/resources/tomsawyer_part4.txt");

        try {
            Stream<String> streamingLines1 = Files.lines(p1);
            Stream<String> streamingLines2 = Files.lines(p2);
            Stream<String> streamingLines3 = Files.lines(p3);
            Stream<String> streamingLines4 = Files.lines(p4);

            System.out.println(streamingLines1.count());
            System.out.println(streamingLines2.count());
            System.out.println(streamingLines3.count());
            System.out.println(streamingLines4.count());

            long streamCount = Stream.of(streamingLines1, streamingLines2, streamingLines3, streamingLines4)
                    .count();
            assert streamCount == 4;

            // This was a stream of streams, so the count is only four!

            streamingLines1 = Files.lines(p1);
            streamingLines2 = Files.lines(p2);
            streamingLines3 = Files.lines(p3);
            streamingLines4 = Files.lines(p4);

            long lineCount = Stream.of(streamingLines1, streamingLines2, streamingLines3, streamingLines4)
                    .flatMap(Function.identity())
                    .count();

            System.out.println(lineCount);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
