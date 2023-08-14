package com.example.kinopoiskprojphat.model;

import lombok.*;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class FilmFilterPage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "nameRu";
}
