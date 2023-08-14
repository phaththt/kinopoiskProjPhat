package com.example.kinopoiskprojphat.repository;

import com.example.kinopoiskprojphat.model.FilmEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Repository
public interface FilmEntityCustomRepository extends CrudRepository<FilmEntity, Long> {
}
