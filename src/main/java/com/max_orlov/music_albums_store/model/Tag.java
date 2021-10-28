package com.max_orlov.music_albums_store.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "tagList")
    @Transient
    private final List<Album> albumList = new ArrayList<>();

    public Tag(String tagName) {
        this.tagName = tagName;
    }

}
