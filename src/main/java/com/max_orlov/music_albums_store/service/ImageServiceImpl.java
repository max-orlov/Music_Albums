package com.max_orlov.music_albums_store.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String saveImage(MultipartFile multipartFile, Path path) throws IOException {
        Files.copy(multipartFile.getInputStream(), path,
                StandardCopyOption.REPLACE_EXISTING);
        return path.getFileName().toString();
    }

    @Override
    public Resource loadAsResource(String fileName) throws MalformedURLException {
        Path path = Paths.get(uploadPath + fileName);
        return new UrlResource(path.toUri());
    }

    @Override
    public Path getPathToSaveImage(MultipartFile multipartFile) {
        String pictureName = UUID.randomUUID().toString() + multipartFile.getOriginalFilename();
        return Paths.get(uploadPath + pictureName);
    }

}
