package com.notatracer.examples.streams.creation.builder;

import java.util.stream.Stream;

public class BuilderExamples {

    public static void main(String[] args) {
        BuilderExamples examples = new BuilderExamples();
        examples.simple();
    }

    private void simple() {
        Stream.Builder<String> builder = Stream.builder();
        builder.accept("one");
        builder.accept("two");
        builder.accept("three");

        Stream<String> build = builder.build();
    }
}
