package com.notatracer.examples.utils;

import com.notatracer.examples.domain.Album;

import java.util.Arrays;
import java.util.List;

public class ExampleUtils {
    public static List<Album> listOfAlbums() {
        return Arrays.asList(
                new Album.AlbumBuilder("Wish You Were Here").playedBy("Pink Floyd").fromYear(1975).build(),
                new Album.AlbumBuilder("Back In Black").playedBy("AC/DC").fromYear(1982).build(),
                new Album.AlbumBuilder("Brothers in Arms").playedBy("Dire Straits").fromYear(1985).build(),
                new Album.AlbumBuilder("Nevermind").playedBy("Nirvana").fromYear(1991).build());
    }
}
