package com.example.kinopoiskprojphat.mapper;

import com.example.kinopoiskprojphat.model.FilmDTO;
import com.example.kinopoiskprojphat.model.FilmEntity;
import com.example.kinopoiskprojphat.model.FilmFilterDataBaseDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
@Mapper(componentModel = "spring")
@Component
public interface FilmMapper {
    FilmEntity toFilm(FilmDTO filmDTO);
    FilmDTO toFilmDTO(FilmEntity filmEntity);

}
