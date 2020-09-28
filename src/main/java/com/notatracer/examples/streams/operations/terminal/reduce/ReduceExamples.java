package com.notatracer.examples.streams.operations.terminal.reduce;

import com.notatracer.examples.domain.Album;
import com.notatracer.examples.utils.ExampleUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Reduction is the iterative application of some kind of function to the
 * elements of a stream to produce a single result.
 *
 * A reduction operation must be associative to be capable of supporting
 * parallel calculation.  For example, (1 + 2) + 3 = 1 + (2 + 3).  Addition,
 * multiplication, AND and OR are associative.  Associativity is important
 * to ensure that a problem can be broken down into subtasks to support
 * parallelism.
 *
 * In a multi-processor architecture, where a master node partitions a dataset
 * and distributes work as remote tasks, the reduction operator must also be
 * commutative, since the results may be returned in any order.  Addition,
 * multiplication, AND and OR are commutative.
 *
 * Association does NOT imply commutative.
 */
public class ReduceExamples {

    public static void main(String[] args) {
        ReduceExamples examples = new ReduceExamples();
        examples.simple();
        examples.longestLine();
        examples.longestWord();
        examples.min();
        examples.minCustomComparator();
    }

    private void simple() {
        IntStream numbers = IntStream.range(0, 100);

        // Identity function must return the value passed in...
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


    private void min() {
        Random random = new Random();
        int i = random.ints()
                .limit(100)
                .min()
                .orElse(-1);
        System.out.println(i);
    }

    private void minCustomComparator() {
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
