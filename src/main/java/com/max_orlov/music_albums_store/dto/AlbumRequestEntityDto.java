package com.max_orlov.music_albums_store.dto;

import lombok.Data;

@Data
public class AlbumRequestEntityDto {
    private String band;
    private String year;
    private String order;
}
