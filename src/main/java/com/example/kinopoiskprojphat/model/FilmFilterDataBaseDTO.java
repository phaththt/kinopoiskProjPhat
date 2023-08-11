package com.example.kinopoiskprojphat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmFilterDataBaseDTO<T> {
//    private List<T> kinopoiskId;
//    private List<T> nameRu;
//    private List<T> year;
//    private List<T> ratingKinopoisk;
//    private List<T> description;
    private Long total;
    private List<T> data;
}
