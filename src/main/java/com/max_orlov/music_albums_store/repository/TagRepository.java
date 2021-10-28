package com.max_orlov.music_albums_store.repository;

import com.max_orlov.music_albums_store.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "select distinct t.tag_name from tags t " +
            "join albums_tags at on at.tag_id = t.tag_id " +
            "where t.tag_id not in " +
            "(select t.tag_id from tags t left join albums_tags at on t.tag_id = at.tag_id " +
            "where at.album_id = ?1)", nativeQuery = true)
    List<String> findTagsThatCanBeAddedToAlbum(Long albumId);

    Tag findTagByTagName(String tagName);

}
