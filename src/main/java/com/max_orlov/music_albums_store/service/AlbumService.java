package com.max_orlov.music_albums_store.service;

import com.max_orlov.music_albums_store.dto.AlbumDto;

import java.util.List;

public interface AlbumService {
    AlbumDto getAlbumById(Long albumId);

    List<AlbumDto> getAllAlbums(String releaseYear, String bandName, String orderBy);

    AlbumDto addNewAlbum(AlbumDto albumDto);

    void updateAlbumRating(int rating, Long albumId);

    void addTagToAlbum(Long albumId, String tagName);

}
