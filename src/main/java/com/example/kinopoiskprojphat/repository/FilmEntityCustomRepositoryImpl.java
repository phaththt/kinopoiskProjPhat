package com.example.kinopoiskprojphat.repository;

import com.example.kinopoiskprojphat.model.FilmDTO;
import com.example.kinopoiskprojphat.model.FilmEntity;
import com.example.kinopoiskprojphat.model.FilmEntity_;
import com.example.kinopoiskprojphat.model.FilmFilterPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class FilmEntityCustomRepositoryImpl {


    private final EntityManager entityManager;

    private final CriteriaBuilder criteriaBuilder;


    public FilmEntityCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<FilmEntity> findAllFilters(FilmFilterPage filmFilterPage, FilmDTO filmDTO){
        CriteriaQuery<FilmEntity> criteriaQuery = criteriaBuilder.createQuery(FilmEntity.class);
        Root<FilmEntity> filmEntityroot = criteriaQuery.from(FilmEntity.class);
        Predicate predicate = getPridicate(filmDTO, filmEntityroot);
        criteriaQuery.where(predicate);
        setOrder(filmFilterPage, criteriaQuery,filmEntityroot);
        TypedQuery<FilmEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(filmFilterPage.getPageNumber() * filmFilterPage.getPageSize());
        typedQuery.setMaxResults(filmFilterPage.getPageSize());

        Pageable pageable = getPageable(filmFilterPage);

        Long filmCount = getFilmCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, filmCount);
    }



    private Predicate getPridicate(FilmDTO filmDTO,
                                   Root<FilmEntity> filmEntityroot) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(filmDTO.getNameRu())){
            predicates.add(criteriaBuilder.like(filmEntityroot.get("nameRu"),
                    "%" + filmDTO.getNameRu() + "%"));
        }

        if(Objects.nonNull(filmDTO.getYear())){
            predicates.add(criteriaBuilder.equal(filmEntityroot.get("year"),
                    filmDTO.getYear()));
        }

        if(Objects.nonNull(filmDTO.getKinopoiskId())){
            predicates.add(criteriaBuilder.equal(filmEntityroot.get("kinopoiskId"),
                    filmDTO.getKinopoiskId()));
        }

        if(Objects.nonNull(filmDTO.getRatingKinopoisk())){
            predicates.add(criteriaBuilder.equal(filmEntityroot.get("ratingKinopoisk"),
                    filmDTO.getRatingKinopoisk()));
        }

        if(Objects.nonNull(filmDTO.getDescription())){
            predicates.add(criteriaBuilder.like(filmEntityroot.get("description"),
                    "%" + filmDTO.getDescription() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(FilmFilterPage filmFilterPage,
                          CriteriaQuery<FilmEntity> criteriaQuery,
                          Root<FilmEntity> filmEntityroot) {
        if (filmFilterPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(filmEntityroot.get(filmFilterPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(filmEntityroot.get(filmFilterPage.getSortBy())));
        }
    }

    private Pageable getPageable(FilmFilterPage filmFilterPage) {
        Sort sort = Sort.by(filmFilterPage.getSortDirection(), filmFilterPage.getSortBy());
        return PageRequest.of(filmFilterPage.getPageNumber(),filmFilterPage.getPageSize(), sort);
    }

    private Long getFilmCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<FilmEntity> countRoot = countQuery.from(FilmEntity.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
