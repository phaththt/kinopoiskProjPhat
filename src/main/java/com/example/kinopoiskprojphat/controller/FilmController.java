package com.example.kinopoiskprojphat.controller;


import com.example.kinopoiskprojphat.mapper.FilmMapper;
import com.example.kinopoiskprojphat.model.FilmDTO;
import com.example.kinopoiskprojphat.model.FilmEntity;
import com.example.kinopoiskprojphat.model.FilmFilterDTO;
import com.example.kinopoiskprojphat.model.FilmFilterPage;
import com.example.kinopoiskprojphat.repository.FilmRepository;
import com.example.kinopoiskprojphat.service.FilmService;

import static java.util.Arrays.stream;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class FilmController {
    private final FilmService filmService;
    private final FilmMapper filmMapper;
    private final FilmRepository filmRepository;

    public FilmController(FilmService filmService, FilmMapper filmMapper, FilmRepository filmRepository) {
        this.filmService = filmService;
        this.filmMapper = filmMapper;
        this.filmRepository = filmRepository;
    }

    @GetMapping("/")
    public List<FilmDTO> getAllFilms(FilmFilterDTO filterDTO) {
        return filmService.findAll(filterDTO).stream()
                .map(filmMapper::toFilmDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public List<FilmDTO> addFilms(FilmFilterDTO filterDTO) {
        return filmService.saveFilms(filterDTO).stream()
                .map(filmMapper::toFilmDTO)
                .collect(Collectors.toList());
    }

//    @GetMapping("api/films/{year}")
//    public List<FilmEntity> findAllByRsql(@RequestParam(required = false)Long kinopoiskId,
//                                          @RequestParam(required = false)String description,
//                                          @PathVariable(required = false)Integer year,
//                                          @RequestParam(required = false)Double ratingKinopoisk,
//                                          @RequestParam(required = false)String nameRu
//    ) {
//        Specification<FilmEntity> specification = where(FilmEntitySpecification.hasKinopoiskId(kinopoiskId))
//                .and(FilmEntitySpecification.hasDescription(description))
//                .and(FilmEntitySpecification.hasYear(year))
//                .and(FilmEntitySpecification.hasRating(ratingKinopoisk))
//                .and(FilmEntitySpecification.hasNameRu(nameRu));
//
//        return filmRepository.findAll(specification);

    @GetMapping("/db")
    public ResponseEntity<Page<FilmEntity>> search(FilmFilterPage filmFilterPage,
                                                   FilmDTO filmDTO) {


        return new ResponseEntity<>(filmService.getFilmEntityPage(filmFilterPage,filmDTO), HttpStatus.OK);
    }
}


