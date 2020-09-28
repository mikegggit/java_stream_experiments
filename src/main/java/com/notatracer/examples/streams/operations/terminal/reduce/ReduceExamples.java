package com.notatracer.examples.streams.operations.terminal.reduce;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReduceExamples {

    public static void main(String[] args) {
        ReduceExamples examples = new ReduceExamples();
        examples.simple();
        examples.longestLine();
        examples.longestWord();
    }

    private void simple() {
        IntStream numbers = IntStream.range(0, 100);
        int countDivisibleByFive = numbers
                .reduce(0,
                        (l, r) ->
                            r % 5 == 0 ? l + 1 : l
                        );
        System.out.println(countDivisibleByFive);
    }

    private void longestLine() {
        Path p = Paths.get("src/main/resources/tomsawyer.txt");
        try (Stream<String> lines = Files.lines(p)) {
            String longestLine = lines
                    .reduce(null, (prev, curr) -> {
                        if (prev == null) {
                            System.out.println(curr);
                            return curr;
                        } else {
                            if (curr.length() > prev.length()) {
                                System.out.println(curr);
                                return curr;
                            } else {
                                return prev;
                            }
                        }
                    });
        } catch (IOException ioe) {
            // swallow...
            ioe.printStackTrace();
        }
    }

    private void longestWord() {
        Path p = Paths.get("src/main/resources/tomsawyer.txt");
        try (Stream<String> lines = Files.lines(p)) {
            String longestWord = lines.flatMap(l -> Arrays.stream(l.split("\\s+")))
                    .reduce(null, (l, r) -> {
                        if (l == null) {
                            return r;
                        } else if (r.length() > l.length()) {
                            return r;
                        } else {
                            return l;
                        }
                    });
            System.out.println(longestWord);

        } catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

}
