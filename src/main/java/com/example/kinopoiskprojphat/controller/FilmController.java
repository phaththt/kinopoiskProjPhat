package com.example.kinopoiskprojphat.controller;


import com.example.kinopoiskprojphat.mapper.FilmMapper;
import com.example.kinopoiskprojphat.model.FilmDTO;
import com.example.kinopoiskprojphat.model.FilmEntity;
import com.example.kinopoiskprojphat.model.FilmFilterDTO;
import com.example.kinopoiskprojphat.model.FilmFilterDataBaseDTO;
import com.example.kinopoiskprojphat.service.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class FilmController {
    private final FilmService filmService;
    private final FilmMapper filmMapper;

    public FilmController(FilmService filmService, FilmMapper filmMapper) {
        this.filmService = filmService;
        this.filmMapper = filmMapper;
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

    @GetMapping("api/films")
    public FilmFilterDataBaseDTO<FilmEntity> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        Page<FilmEntity> pageData = filmService.findAll(PageRequest.of(page,size));

        FilmFilterDataBaseDTO<FilmEntity> filterDataBaseDTO = new FilmFilterDataBaseDTO<>();
        filterDataBaseDTO.setData(pageData.getContent());
        filterDataBaseDTO.setTotal(pageData.getTotalElements());

        return filterDataBaseDTO;


    }
}
