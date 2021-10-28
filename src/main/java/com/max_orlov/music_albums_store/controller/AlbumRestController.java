package com.max_orlov.music_albums_store.controller;

import com.max_orlov.music_albums_store.dto.AlbumDto;
import com.max_orlov.music_albums_store.dto.AlbumRequestEntityDto;
import com.max_orlov.music_albums_store.model.QueryOrderParameters;
import com.max_orlov.music_albums_store.service.AlbumService;
import com.max_orlov.music_albums_store.service.ImageService;
import com.max_orlov.music_albums_store.validator.AlbumDtoValidator;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/albums")
@Validated
@AllArgsConstructor
public class AlbumRestController {

    private static final String ADDING_ALBUM_ERROR_MESSAGE = "Error. Album can't be added";
    private static final String ALBUM_NOT_FOUND_ERROR_MESSAGE = "Album not found...";
    private static final String PICTURE_REQUEST_PARAMETER = "picture";
    private static final String ALBUM_ID_REQUEST_PARAMETER = "albumId";
    private static final String TAG_NAME_REQUEST_PARAMETER = "tagName";
    private static final String RATING_REQUEST_PARAMETER = "rating";

    private static final Logger logger =
            LogManager.getLogger(AlbumRestController.class);

    private AlbumService albumService;
    private ImageService imageService;
    private AlbumDtoValidator albumDtoValidator;

    @GetMapping("/allAlbums")
    public List<AlbumDto> getAllAlbums(AlbumRequestEntityDto albumRequestEntityDto) {
        String year = albumRequestEntityDto.getYear();
        String band = albumRequestEntityDto.getBand();
        String order = albumRequestEntityDto.getOrder();
        if (!QueryOrderParameters.contains(order)) {
            order = QueryOrderParameters.RATING.getName();
        }
        return albumService.getAllAlbums(year,band,order);
    }

    @GetMapping(value = "/{id}")
    public AlbumDto getById(@PathVariable Long id) {
        return albumService.getAlbumById(id);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> handleNoSuchElementException() {
        logger.error(ALBUM_NOT_FOUND_ERROR_MESSAGE);
        return new ResponseEntity<>(ALBUM_NOT_FOUND_ERROR_MESSAGE, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/newAlbum")
    @PreAuthorize("hasAuthority('admin:addAlbum')")
    public ResponseEntity<?> addNewAlbum(@Valid AlbumDto albumDto, BindingResult bindingResult,
            @RequestParam(PICTURE_REQUEST_PARAMETER) MultipartFile multipartFile) throws IOException {
        Path path = imageService.getPathToSaveImage(multipartFile);
        String pathToPicture = imageService.saveImage(multipartFile, path);
        albumDto.setPathToPicture(pathToPicture);

        albumDtoValidator.validate(albumDto, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            logger.error(errorMessages);
            return new ResponseEntity<>(ADDING_ALBUM_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        albumService.addNewAlbum(albumDto);
        logger.info("User add new album {}", albumDto);
        return new ResponseEntity<>(albumDto, HttpStatus.OK);
    }

    @PostMapping(value = "/addTagToAlbum")
    @PreAuthorize("hasAnyAuthority('admin:addAlbum', 'user:write')")
    public void addTagToAlbum(@RequestParam(ALBUM_ID_REQUEST_PARAMETER) Long albumId,
                              @RequestParam(TAG_NAME_REQUEST_PARAMETER) String tagName) {
        albumService.addTagToAlbum(albumId, tagName);
        logger.info("User add tag {} to album with id {}", tagName, albumId);
    }

    @PostMapping(value = "/updateRating")
    @PreAuthorize("hasAnyAuthority('admin:addAlbum', 'user:write')")
    public void updateRating(@RequestParam(RATING_REQUEST_PARAMETER) int rating,
                             @RequestParam(ALBUM_ID_REQUEST_PARAMETER) Long albumId) {
        albumService.updateAlbumRating(rating, albumId);
        logger.info("User updates rating of album with id {}", albumId);
    }

}
