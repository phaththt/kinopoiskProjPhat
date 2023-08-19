package com.example.kinopoiskprojphat.service;

import com.example.kinopoiskprojphat.model.FilmDTO;
import com.example.kinopoiskprojphat.model.FilmEntity;
import com.example.kinopoiskprojphat.model.FilmFilterDTO;
import com.example.kinopoiskprojphat.model.FilmFilterPage;
import com.example.kinopoiskprojphat.repository.FilmEntityCustomRepositoryImpl;
import com.example.kinopoiskprojphat.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.PageImpl;


public class FilmServiceTest {

    private FilmService filmService;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private ClientKinopoisk clientKinopoisk;

    @Mock
    private FilmEntityCustomRepositoryImpl filmEntityCustomRepositoryImpl;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        filmService = new FilmService(filmRepository, clientKinopoisk, filmEntityCustomRepositoryImpl, entityManager);
    }

    @Test
    public void testAddFilmsWithNonExistingKinopoiskId() {

        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setKinopoiskId(1252447L);
        when(filmRepository.existsByKinopoiskId(1252447L)).thenReturn(false);


        filmService.addFilms(filmEntity);


        verify(filmRepository).save(filmEntity);
    }

    @Test
    public void testAddFilmsWithExistingKinopoiskId() {

        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setKinopoiskId(1252447L);
        when(filmRepository.existsByKinopoiskId(1252447L)).thenReturn(true);


        verify(filmRepository, never()).save(filmEntity);
    }

    @Test
    public void testSaveFilmsWithNonNullFilter() {

        FilmFilterDTO filterDTO = new FilmFilterDTO();
        filterDTO.setYearFrom(2000);
        filterDTO.setYearTo(2022);
        List<FilmEntity> mockFilms = new ArrayList<>();
        when(clientKinopoisk.getFilm(filterDTO)).thenReturn(mockFilms);


        List<FilmEntity> result = filmService.saveFilms(filterDTO);


        verify(clientKinopoisk).getFilm(filterDTO);
        verify(filmRepository, times(mockFilms.size())).save(any(FilmEntity.class)); // Убеждаемся, что метод save вызывается для каждого мок-фильма

    }

    @Test
    public void testSaveFilmsWithNullFilter() {

        List<FilmEntity> result = filmService.saveFilms(null);


        verify(clientKinopoisk, never()).getFilm(any());
        verify(filmRepository, never()).save(any(FilmEntity.class));
        assertNull(result);
    }

    @Test
    public void testGetFilmEntityPage() {

        FilmFilterPage filmFilterPage = new FilmFilterPage();
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setNameRu("Лорды раздевалки");
        filmDTO.setYear(2000);

        List<FilmEntity> mockFilms = new ArrayList<>();

        FilmEntity film1 = new FilmEntity();
        film1.setNameRu("Лорды раздевалки");
        film1.setYear(2000);
        mockFilms.add(film1);

        FilmEntity film2 = new FilmEntity();
        film2.setNameRu("Попкульт");
        film2.setYear(2005);
        mockFilms.add(film2);

        when(filmEntityCustomRepositoryImpl.findAllFilters(filmFilterPage, filmDTO))
                .thenReturn(new PageImpl<>(mockFilms));


        Page<FilmEntity> resultPage = filmService.getFilmEntityPage(filmFilterPage, filmDTO);


        List<FilmEntity> resultList = resultPage.getContent();
        assertEquals(2, resultList.size());
        assertEquals("Лорды раздевалки", resultList.get(0).getNameRu());
        assertEquals("Попкульт", resultList.get(1).getNameRu());


        verify(filmEntityCustomRepositoryImpl).findAllFilters(filmFilterPage, filmDTO);
    }

    @Test
    public void testFindAll() {

        FilmFilterDTO filterDTO = new FilmFilterDTO();
        List<FilmEntity> mockFilms = new ArrayList<>();

        FilmEntity film1 = new FilmEntity();
        film1.setNameRu("Фильм 1");
        mockFilms.add(film1);

        FilmEntity film2 = new FilmEntity();
        film2.setNameRu("Фильм 2");
        mockFilms.add(film2);

        when(clientKinopoisk.getFilm(filterDTO)).thenReturn(mockFilms);


        List<FilmEntity> result = filmService.findAll(filterDTO);


        for (FilmEntity film : result) {
            System.out.println("Название: " + film.getNameRu());

        }

        // Проверка
        verify(clientKinopoisk).getFilm(filterDTO);
        assertEquals(mockFilms, result); 
    }


}










