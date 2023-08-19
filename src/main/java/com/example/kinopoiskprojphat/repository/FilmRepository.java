package com.example.kinopoiskprojphat.repository;

import com.example.kinopoiskprojphat.model.FilmEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface FilmRepository extends JpaRepository<FilmEntity, Long>{

    boolean existsByKinopoiskId(Long kinopoiskId);

    List<FilmEntity> findAll(Specification<FilmEntity> spec, Pageable pageable);

}