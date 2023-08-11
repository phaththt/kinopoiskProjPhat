package com.example.kinopoiskprojphat.service;

import com.example.kinopoiskprojphat.mapper.FilmMapper;
import com.example.kinopoiskprojphat.model.FilmEntity;
import com.example.kinopoiskprojphat.model.FilmFilterDTO;
import com.example.kinopoiskprojphat.model.FilmFilterDataBaseDTO;
import com.example.kinopoiskprojphat.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FilmService {

    private final FilmRepository filmRepository;

    private final FilmMapper filmMapper;

    private final ClientKinopoisk clientKinopoisk;


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
        return clientKinopoisk.getFilm(filterDTO)
                .stream()
                .peek(this::addFilms)
                .collect(Collectors.toList());

    }


    public Page<FilmEntity> findAll(PageRequest pageRequest){
        return filmRepository.findAll(pageRequest);

    }
}