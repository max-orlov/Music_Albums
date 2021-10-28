package com.max_orlov.music_albums_store.service;

import java.util.List;

public interface TagService {
    List<String> getTagsCanAddToAlbum(Long albumId);

    void addNewTag(String tagName, Long albumId);

}
