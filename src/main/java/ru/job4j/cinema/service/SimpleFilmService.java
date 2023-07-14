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

    public String convertMinutesToHours(int durationInMinutes) {
        int hours = durationInMinutes / 60;
        int remainingMinutes = durationInMinutes % 60;
        return String.format("%s hour(s) %s minute(s)", hours, remainingMinutes);
    }

    @Override
    public Collection<FilmDto> findAll() {
        List<FilmDto> filmsDto = new ArrayList<>();
        for (Film film : filmRepository.findAll()) {
            filmsDto.add(new FilmDto(film.getId(), film.getName(), film.getDescription(),
                    film.getYear(),
                    film.getMinimalAge(),
                    convertMinutesToHours(film.getDurationInMinutes()),
                    genreRepository.findById(film.getId()).get().getName()));
        }
        return filmsDto;
    }
}
