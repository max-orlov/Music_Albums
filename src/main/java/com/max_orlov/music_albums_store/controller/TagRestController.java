package com.max_orlov.music_albums_store.controller;

import com.max_orlov.music_albums_store.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagRestController {

    private static final String ALBUM_ID_REQUEST_PARAMETER = "albumId";
    private static final String TAG_NAME_REQUEST_PARAMETER = "tagName";

    private TagService tagService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:addAlbum', 'user:write')")
    public List<String> findTagsThatCanBeAddedToAlbum(@RequestParam String albumId) {
        return tagService.getTagsCanAddToAlbum(Long.valueOf(albumId));
    }

    @PostMapping(value = "/newTag")
    @PreAuthorize("hasAnyAuthority('admin:addAlbum', 'user:write')")
    public void addNewTag(@RequestParam(TAG_NAME_REQUEST_PARAMETER) @NotNull String tagName,
                          @RequestParam(ALBUM_ID_REQUEST_PARAMETER) @NotNull Long albumId) {
        tagService.addNewTag(tagName, albumId);
    }

}
