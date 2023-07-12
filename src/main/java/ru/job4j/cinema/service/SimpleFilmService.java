package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SimpleFilmService implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository filmRepository, GenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Collection<FilmDto> findAll() {
        List<FilmDto> filmDtos = new ArrayList<>();
        for (Film film : filmRepository.findAll()) {
            filmDtos.add(new FilmDto(film.getId(), film.getName(), film.getDescription(),
                    film.getYear(),
                    film.getMinimalAge(), film.getDurationInMinutes(),
                    genreRepository.findById(film.getId()).get().getName()));
        }
        return filmDtos;
    }
}
