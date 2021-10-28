package com.max_orlov.music_albums_store.validator;

import com.max_orlov.music_albums_store.dto.AlbumDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class AlbumDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return AlbumDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "albumName",
                "albumName.empty",
                "VALIDATOR-MESSAGE: Album name must be not empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "releaseYear",
                "releaseYear.empty",
                "VALIDATOR-MESSAGE: Year must be not empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bandName",
                "bandName.empty",
                "VALIDATOR-MESSAGE: Band name must be not empty");
    }

}
