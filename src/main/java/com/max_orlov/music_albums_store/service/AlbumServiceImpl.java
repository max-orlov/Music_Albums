package com.max_orlov.music_albums_store.service;

import com.max_orlov.music_albums_store.repository.dao.AlbumDao;
import com.max_orlov.music_albums_store.dto.AlbumDto;
import com.max_orlov.music_albums_store.model.Album;
import com.max_orlov.music_albums_store.model.Band;
import com.max_orlov.music_albums_store.model.Tag;
import com.max_orlov.music_albums_store.repository.AlbumRepository;
import com.max_orlov.music_albums_store.repository.BandRepository;
import com.max_orlov.music_albums_store.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private static final String EMPTY_STRING = "";
    private static final String PERCENT_SYMBOL = "%";

    private AlbumRepository albumRepository;
    private BandRepository bandRepository;
    private TagRepository tagRepository;
    private AlbumDao albumDao;

    @Override
    public AlbumDto getAlbumById(Long albumId) {
        Album album = albumRepository.getById(albumId);
        List<String> tags = album.getTagList()
                .stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());
        return AlbumDto
                .builder()
                .albumId(albumId)
                .albumName(album.getAlbumName())
                .releaseYear(album.getReleaseYear())
                .bandName(album.getBand().getBandName())
                .rating(album.getCurrentRating())
                .pathToPicture(album.getPathToPicture())
                .tagList(tags)
                .build();
    }

    private List<AlbumDto> mapAlbumsToAlbumDtos(List<Album> albumList) {
        List<AlbumDto> albumDtoList = new ArrayList<>();
        for (Album album : albumList) {
            AlbumDto albumDto = AlbumDto
                    .builder()
                    .albumId(album.getAlbumId())
                    .albumName(album.getAlbumName())
                    .releaseYear(album.getReleaseYear())
                    .bandName(album.getBand().getBandName())
                    .rating(album.getCurrentRating())
                    .pathToPicture(album.getPathToPicture())
                    .tagList(album.getTagList()
                            .stream()
                            .map(Tag::getTagName)
                            .collect(Collectors.toList()))
                    .build();
            albumDtoList.add(albumDto);
        }
        return albumDtoList;
    }

    @Override
    public List<AlbumDto> getAllAlbums(String releaseYear, String bandName, String orderBy) {
        String releaseYearParameter= new StringBuilder(releaseYear)
                .append(PERCENT_SYMBOL).toString();
        String bandNameParameter = new StringBuilder(firstCharToUpperCase(bandName))
                .append(PERCENT_SYMBOL).toString();
        List<Album> albumList =
                albumDao.findAlbumByYearAndBand(releaseYearParameter, bandNameParameter, orderBy);
        return mapAlbumsToAlbumDtos(albumList);
    }

    private String firstCharToUpperCase(String query) {
        if (query == null || query.isEmpty()) {
            return EMPTY_STRING;
        }
        return query.substring(0, 1).toUpperCase() + query.substring(1).toLowerCase();
    }

    @Override
    public AlbumDto addNewAlbum(AlbumDto albumDto) {
        Band band = bandRepository.findBandByBandName(albumDto.getBandName());
        if (band == null) {
            band = bandRepository.save(new Band(albumDto.getBandName()));
        }
        Album album = new Album();
        album.setAlbumName(albumDto.getAlbumName());
        album.setReleaseYear(albumDto.getReleaseYear());
        album.setBand(band);
        album.setPathToPicture(albumDto.getPathToPicture());
        albumRepository.save(album);
        return albumDto;
    }

    @Override
    public void updateAlbumRating(int rating, Long albumId) {
        Album album = albumRepository.getById(albumId);
        int totalRating = album.getTotalRating() + rating;
        int ratingUpdateCounter = album.getRatingUpdateCounter();
        int currentRating = totalRating / ++ratingUpdateCounter;
        albumRepository.updateAlbumRating(currentRating, totalRating, ratingUpdateCounter, albumId);
    }

    @Override
    public void addTagToAlbum(Long albumId, String tagName) {
        Tag tag = tagRepository.findTagByTagName(tagName);
        Album album = albumRepository.getById(albumId);
        album.getTagList().add(tag);
        albumRepository.save(album);
    }

}
