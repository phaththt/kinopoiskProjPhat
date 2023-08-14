package com.example.kinopoiskprojphat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO {

    private String nameRu;

    private Long year;

    private Long kinopoiskId;

    private Double ratingKinopoisk;

    private String description;
}
