package com.example.kinopoiskprojphat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KinopoiskResponse {
    private List<FilmEntity> items;
    private Integer totalPages;
    private Integer total;
}
