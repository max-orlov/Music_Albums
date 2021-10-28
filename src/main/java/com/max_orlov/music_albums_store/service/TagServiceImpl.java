package com.max_orlov.music_albums_store.service;

import com.max_orlov.music_albums_store.model.Tag;
import com.max_orlov.music_albums_store.repository.AlbumRepository;
import com.max_orlov.music_albums_store.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TagServiceImpl implements TagService{

    private TagRepository tagRepository;
    private AlbumRepository albumRepository;

    @Override
    public List<String> getTagsCanAddToAlbum(Long albumId) {
        return tagRepository.findTagsThatCanBeAddedToAlbum(albumId);
    }

    @Override
    public void addNewTag(String tagName, Long albumId) {
        Tag tag = tagRepository.findTagByTagName(tagName);
        if (tag == null) {
            Tag newTag = new Tag(tagName);
            albumRepository.getById(albumId).getTagList().add(newTag);
            tagRepository.save(newTag);
        }
    }

}
