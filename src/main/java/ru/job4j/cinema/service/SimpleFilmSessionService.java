package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;
    private final FileService fileService;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository,
                                    FilmRepository filmRepository, FileService fileService) {
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
        this.fileService = fileService;
    }

    public String formatLocalDateTime(LocalDateTime startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return startTime.format(formatter);
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        List<FilmSessionDto> filmSessionDto = new ArrayList<>();
        for (FilmSession filmSession : filmSessionRepository.findAll()) {
            filmSessionDto.add(new FilmSessionDto(filmSession.getId(),
                    filmRepository.findById(filmSession.getFilmId()).get().getName(),
                    formatLocalDateTime(filmSession.getStartTime()),
                    filmSession.getPrice(),
                    filmSession.getFilmId()
            ));
        }
        return filmSessionDto;
    }
}
