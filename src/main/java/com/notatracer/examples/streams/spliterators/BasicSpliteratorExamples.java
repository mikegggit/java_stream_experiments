package com.notatracer.examples.streams.spliterators;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Spliterator can be considered an iterator for handing items of a source to a stream.
 *
 * Spliterator knows how to tokenize, or split, a given source.
 */
public class BasicSpliteratorExamples {
    public static void main(String[] args) {
        BasicSpliteratorExamples examples = new BasicSpliteratorExamples();
        examples.splitByEachChar();
        examples.splitOnEveryWord();
    }

    private void splitByEachChar() {
        // no need to implement our own spliterator for streaming chars of a string...
        // ...java already provides one.
        "This is a string".chars()
                .forEach(
                        c -> System.out.println((char)c)
                );
    }

    private void splitOnEveryWord() {
        String s = " This     is  a string with  seven words.";
        Spliterator<String> spliterator = Stream.of(s).spliterator();
        Spliterator<String> wordSpliterator = new WordSpliterator(spliterator);

        Stream<String> wordStream = StreamSupport.stream(wordSpliterator, false);

        wordStream.forEach(l -> {
            System.out.println(l);
                }
        );
    }

    class WordSpliterator implements Spliterator<String>  {

        public static final String REGEX_WORD_BOUNDARY = "\\b[\\W]+\\b"; // NOTE: Doesn't trim start of line spaces...
        public final Pattern WORD_BOUNDARY_PATTERN = Pattern.compile(REGEX_WORD_BOUNDARY);
        private Spliterator<String> lineSpliterator;

        public WordSpliterator(Spliterator<String> lineSpliterator) {
            this.lineSpliterator = lineSpliterator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super String> action) {
            final List<String> words = new ArrayList();
            this.lineSpliterator.tryAdvance(line -> words.addAll(Arrays.asList(
                    WORD_BOUNDARY_PATTERN.split(line))
            ));

            words.forEach( w ->
                    action.accept(w));

            return true;
        }

        @Override
        public Spliterator<String> trySplit() {
            // only needed when attempting parallel...
            return null;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public int characteristics() {
            return 0;
        }
    }

}
