package com.max_orlov.music_albums_store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QueryOrderParameters {
    YEAR("a.releaseYear"),
    BAND_NAME("a.band.bandName"),
    ALBUM_NAME("a.albumName"),
    RATING("a.currentRating");

    private String name;

    public static boolean contains(String order) {
        for (QueryOrderParameters orderParameter : QueryOrderParameters.values()) {
            if (orderParameter.getName().equals(order)) {
                return true;
            }
        }
        return false;
    }

}
