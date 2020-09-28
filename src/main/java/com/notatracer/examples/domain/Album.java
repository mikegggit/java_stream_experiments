package com.notatracer.examples.domain;


import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Album {
    public final String name;
    public final String bandName;
    public final int year;
    public int mutableFoo;

    public Album(String name, String bandName, int year) {
        this.name = name;
        this.bandName = bandName;
        this.year = year;
    }

    public static class AlbumBuilder {
        private String name;
        private String bandName;
        private int year;

        public AlbumBuilder(String name) {
            this.name = name;
        }

        public AlbumBuilder playedBy(String bandName) {
            this.bandName = bandName;
            return this;
        }

        public AlbumBuilder fromYear(int year) {
            this.year = year;
            return this;
        }

        public Album build() {
            return new Album(name, bandName, year);
        }
    }
}
