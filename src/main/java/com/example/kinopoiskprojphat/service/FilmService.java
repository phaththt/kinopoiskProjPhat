package com.example.kinopoiskprojphat.service;

import com.example.kinopoiskprojphat.mapper.FilmMapper;
import com.example.kinopoiskprojphat.model.FilmDTO;
import com.example.kinopoiskprojphat.model.FilmEntity;
import com.example.kinopoiskprojphat.model.FilmFilterDTO;
import com.example.kinopoiskprojphat.model.FilmFilterPage;
import com.example.kinopoiskprojphat.repository.FilmEntityCustomRepository;
import com.example.kinopoiskprojphat.repository.FilmEntityCustomRepositoryImpl;
import com.example.kinopoiskprojphat.repository.FilmRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FilmService {

    private final FilmRepository filmRepository;

    private final ClientKinopoisk clientKinopoisk;

    private final FilmEntityCustomRepositoryImpl filmEntityCustomRepositoryImpl;
    private ObjectMapper objectMapper;




    @PersistenceContext
    private final EntityManager entityManager;

    public List<FilmEntity> findAll(FilmFilterDTO filterDTO) {
        return clientKinopoisk.getFilm(filterDTO);
    }


    public void addFilms(FilmEntity filmEntity) {
        if (!filmRepository.existsByKinopoiskId(filmEntity.getKinopoiskId())) {
            filmRepository.save(filmEntity);
        }
    }


    public List<FilmEntity> saveFilms(FilmFilterDTO filterDTO) {
        if (filterDTO == null) {
            return null;
        } else {
            return clientKinopoisk.getFilm(filterDTO)
                    .stream()
                    .peek(this::addFilms)
                    .collect(Collectors.toList());

        }
    }

    public Page<FilmEntity> getFilmEntityPage(FilmFilterPage filmFilterPage, FilmDTO filmDTO){

        return filmEntityCustomRepositoryImpl.findAllFilters(filmFilterPage, filmDTO);
    }

}
