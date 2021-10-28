package com.max_orlov.music_albums_store.repository;

import com.max_orlov.music_albums_store.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, Long> {

    Band findBandByBandName(String bandName);

}
