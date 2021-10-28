package com.max_orlov.music_albums_store.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bands")
@Data
@NoArgsConstructor
public class Band implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "band_id")
    private Long bandId;

    @Column(name = "band_name")
    private String bandName;

    public Band(String bandName) {
        this.bandName = bandName;
    }

}
