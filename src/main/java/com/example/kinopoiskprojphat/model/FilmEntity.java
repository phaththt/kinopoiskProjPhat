package com.example.kinopoiskprojphat.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "film_entity")
public class FilmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "film_name")
    private String nameRu;
    @Column(name = "year")
    private Integer year;
    @Column(name = "film_id")
    private Long kinopoiskId;
    @Column(name = "rating")
    private Double ratingKinopoisk;

    @Column(name = "description")
    private String description;

}
