package com.notatracer.examples.streams.operations.intermediate.parallelism;

import com.notatracer.examples.domain.Album;
import com.notatracer.examples.utils.ExampleUtils;

import java.util.stream.IntStream;

/**
 * Parallelism is different from multithreading.
 *
 * In the streams frame, parallelism involves splitting up a problem
 * into subproblems, solving each in a separate thread.
 *
 * Multithreading => One thread per process => many processes
 * Parallelism => One process => many threads.
 *
 * Fork / Join is a very low-level frame used to support distributing
 * work across available cores.
 *
 * Parallel streams is built on the Fork / Join framework.
 *
 *
 *
 */
public class ParallelismExamples {
    public static void main(String[] args) {
        ParallelismExamples examples = new ParallelismExamples();
        examples.parallelStream();
        examples.parallel();
        examples.ordering();
    }

    private void parallelStream() {
        // parallelStream is one of two ways of creating a parallelStream...
        Album newest = ExampleUtils.listOfAlbums()
                .parallelStream()
                .max(
                        (l, r) -> l.year <= r.year ? -1 : 1
                ).orElseThrow();
        System.out.println(newest);
    }

    private void parallel() {
        Album newest = ExampleUtils.listOfAlbums()
                .stream()
                .parallel()
                .max(
                        (l, r) -> l.year <= r.year ? -1 : 1
                ).orElseThrow();
        System.out.println(newest);
    }

    private void ordering() {
        // The order in which stream elements are delivered to a collector
        // is not guaranteed.
        IntStream.range(1, 200)
                .parallel()
                .forEach(System.out::println);

        System.out.println("-----");
        // To guarantee sort order of the results, you need to use  a
        // sorted operator, which will introduce a sync-point across all
        // stream elements before any output is generated...
        IntStream.range(1, 200)
                .parallel()
                .forEachOrdered(System.out::println);
    }

}
