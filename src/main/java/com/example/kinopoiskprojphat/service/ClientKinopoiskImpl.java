package com.example.kinopoiskprojphat.service;

import com.example.kinopoiskprojphat.model.FilmDTO;
import com.example.kinopoiskprojphat.model.FilmEntity;
import com.example.kinopoiskprojphat.model.FilmFilterDTO;
import com.example.kinopoiskprojphat.model.KinopoiskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientKinopoiskImpl implements ClientKinopoisk {
    final String url = "https://kinopoiskapiunofficial.tech/api/v2.2/films";
    final String urlId = "https://kinopoiskapiunofficial.tech/api/v2.2/films/{id}";

    private final RestTemplate restTemplate;
    HttpHeaders httpHeaders = new HttpHeaders();




    @Override
    public List<FilmEntity> getFilm(FilmFilterDTO filterDTO) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("X-API-KEY","baeb9e0f-b851-4124-be40-58bf5825007b");

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<KinopoiskResponse> responseEntity = restTemplate.exchange(generateUrl(filterDTO),
                HttpMethod.GET,
                httpEntity, KinopoiskResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null &&
                responseEntity.getBody().getItems() != null) {
            return responseEntity.getBody().getItems();
        } else {
            return null;
        }

    }

    @Override
    public String generateUrl(FilmFilterDTO filterDTO) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);

        Optional<FilmFilterDTO> optionalFilter = Optional.ofNullable(filterDTO);

        optionalFilter.map(FilmFilterDTO::getOrder)
                .ifPresent(sort -> uriComponentsBuilder.queryParam("order", sort));
        optionalFilter.map(FilmFilterDTO::getType)
                .ifPresent(type -> uriComponentsBuilder.queryParam("type", type));
        optionalFilter.map(FilmFilterDTO::getCountries)
                .ifPresent(countries -> uriComponentsBuilder.queryParam("type", countries.toString()));
        optionalFilter.map(FilmFilterDTO::getGenres)
                .ifPresent(genres -> uriComponentsBuilder.queryParam("genres", genres.toString()));
        optionalFilter.map(FilmFilterDTO::getCountries)
                .ifPresent(countries -> uriComponentsBuilder.queryParam("countries", countries.toString()));
        optionalFilter.map(FilmFilterDTO::getRatingFrom)
                .ifPresent(ratingFrom -> uriComponentsBuilder.queryParam("ratingFrom", ratingFrom.toString()));
        optionalFilter.map(FilmFilterDTO::getRatingTo)
                .ifPresent(ratingTo -> uriComponentsBuilder.queryParam("ratingTo", ratingTo.toString()));
        optionalFilter.map(FilmFilterDTO::getYearFrom)
                .ifPresent(yearFrom -> uriComponentsBuilder.queryParam("yearFrom", yearFrom.toString()));
        optionalFilter.map(FilmFilterDTO::getYearTo)
                .ifPresent(yearTo -> uriComponentsBuilder.queryParam("yearTo", yearTo.toString()));
        optionalFilter.map(FilmFilterDTO::getPage)
                .ifPresent(page -> uriComponentsBuilder.queryParam("page", page.toString()));
        optionalFilter.map(FilmFilterDTO::getKeyword)
                .ifPresent(keyword -> uriComponentsBuilder.queryParam("keyword", keyword));

        return uriComponentsBuilder.build().toUriString();
    }
}