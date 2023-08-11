package com.example.kinopoiskprojphat.service;

import com.example.kinopoiskprojphat.model.FilmDTO;
import com.example.kinopoiskprojphat.model.FilmEntity;
import com.example.kinopoiskprojphat.model.FilmFilterDTO;

import java.util.List;

public interface ClientKinopoisk {
    List<FilmEntity> getFilm(FilmFilterDTO filterDTO);

   // FilmEntity getFilmById(FilmFilterDTO filterDTO);

    String generateUrl(FilmFilterDTO filterDTO);
}
