package com.max_orlov.music_albums_store.controller;

import com.max_orlov.music_albums_store.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageRestController {

    private static final String IMAGE_REQUEST_PARAMETER = "picture";

    private ImageService imageService;

    @GetMapping(value = "/{pictureName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getImage(@PathVariable String pictureName) throws MalformedURLException {
        return new ResponseEntity<>(imageService.loadAsResource(pictureName), HttpStatus.OK);
    }

    @PostMapping(value = "/newImage")
    public ResponseEntity<String> saveImage(@RequestParam(IMAGE_REQUEST_PARAMETER)
                                                @NotNull MultipartFile multipartFile)
            throws IOException {
        Path path = imageService.getPathToSaveImage(multipartFile);
        return new ResponseEntity<>(imageService.saveImage(multipartFile, path), HttpStatus.OK);
    }

}
