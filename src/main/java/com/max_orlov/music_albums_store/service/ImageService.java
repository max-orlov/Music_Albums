package com.max_orlov.music_albums_store.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface ImageService {
    String saveImage(MultipartFile multipartFile, Path path) throws IOException;

    Resource loadAsResource(String fileName) throws MalformedURLException;

    Path getPathToSaveImage(MultipartFile multipartFile);

}
