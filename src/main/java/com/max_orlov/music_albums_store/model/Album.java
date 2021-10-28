package com.max_orlov.music_albums_store.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albums")
@Data
public class Album implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "release_year")
    private String releaseYear;

    @Column(name="current_rating")
    private int currentRating;

    @Column(name = "total_rating")
    private int totalRating;

    @Column(name = "rating_update_counter")
    private int ratingUpdateCounter;

    @Column(name = "album_picture_name")
    private String pathToPicture;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "band_id")
    private Band band;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "albums_tags",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private final List<Tag> tagList = new ArrayList<>();

}
