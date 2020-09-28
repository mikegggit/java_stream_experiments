package com.notatracer.examples.streams;

import java.util.function.Function;
import java.util.stream.Stream;

public class StreamConcatExamples {

    public static void main(String[] args) {

        StreamConcatExamples examples = new StreamConcatExamples();
        examples.concat();
        examples.merge1();



    }

    private void concat() {
        Stream<Integer> numberStream1 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Stream<Integer> numberStream2 = Stream.of(10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

        Stream<Integer> joinedStream = Stream.concat(numberStream1, numberStream2);

        Stream.concat(joinedStream, Stream.of(20, 21, 22, 23, 24, 25)).forEach(
                System.out::println
        );
    }

    private void merge1() {
        Stream<Integer> integerStream = Stream.of(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9), Stream.of(10, 11, 12, 13, 14, 15, 16, 17, 18, 19), Stream.of(20, 21, 22, 23, 24, 25))
                .flatMap(Function.identity());
        integerStream.forEach(
                System.out::println
        );
    }
}
