package com.example.kinopoiskprojphat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmFilterDTO {
    List<Integer> countries;
    List<Integer> genres;
    Order order;
    Type type;
    Integer ratingTo;
    Integer ratingFrom;
    Integer yearFrom;
    Integer yearTo;
    String imdb;
    String keyword;
    Integer page;
}
