package com.notatracer.examples.streams.operations.intermediate;

import com.notatracer.examples.domain.Album;
import com.notatracer.examples.utils.ExampleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * A stateful operation is one in which some state outside the current scope is maintained
 * and used as part of performing the operation.
 * 
 * In other words, as opposed to an operation like map, which is purely functional,
 * an operation like limit must keep track of how many stream items have been seen 
 * so far to know when to stop processing.
 *
 * Stateful operations require information outside the scope of the operation's input.
 *
 * A stateless operation only operates on a single element of the source at a time.
 *
 * Stateful operations may require all the stream elements to be calculated, such as skip,
 * limit, etc.  This obviates the benefits of going parallel, as all data needs to
 * be brought into a single place in order to perform these kinds of operations.
 */
public class StatefulIntermediateOpsExamples {
    public static void main(String[] args) {
        StatefulIntermediateOpsExamples examples = new StatefulIntermediateOpsExamples();
        examples.distinct();
        examples.sorted();
        examples.limit();
        examples.skip();
    }

    private void distinct() {
        // Need to create a resizable list implementation since Arrays.asList returns an
        // immutable fixed-size list...
        List<Album> albums = new ArrayList<>(ExampleUtils.listOfAlbums());

        albums.add(new Album.AlbumBuilder("Wish You Were Here").playedBy("Pink Floyd").fromYear(1975).build());
        albums.add(new Album.AlbumBuilder("Wish You Were Here").playedBy("Pink Floyd").fromYear(1975).build());

        System.out.println(albums.size());

        long count = albums.stream()
                .distinct()
                .count();

        System.out.println(count);
    }

    /**
     * Sorting w/ primitives is limited to natural order since java doesn't support
     * comparators w/ primitive types.
     *
     * To change the order an int stream is sorted, we can first box the ints into
     * a Stream<Integer>.
      */
    private void sorted() {
        new Random()
                .longs(20, 0, 100)
                .sorted()
                .forEach(System.out::println);

        new Random()
                .longs(20, 0, 100)
                .boxed()
                .sorted(
                        (l, r) -> l <= r ? 1 : -1
                )
                .forEach(System.out::println);

    }

    private void limit() {
        Stream.Builder<Object> builder = Stream.builder();
        builder.accept(1);
        builder.accept(10);
        builder.accept(4);
        builder.accept(99);
        builder.accept(55);
        builder.accept(5);
        builder.accept(8);
        builder.accept(2);
        builder.build()
                .limit(4)
                .forEach(System.out::println);
    }

    private void skip() {
        Stream.of(1, 2, 3, 4, 5, 6, 88, 9, 8)
                .skip(2)
                .forEach(System.out::println);
    }
}
