package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;
    private final FileService fileService;
    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository,
                                    FilmRepository filmRepository, FileService fileService,
                                    HallRepository hallRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
        this.fileService = fileService;
        this.hallRepository = hallRepository;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        List<FilmSessionDto> filmSessionDto = new ArrayList<>();
        for (FilmSession filmSession : filmSessionRepository.findAll()) {
            filmSessionDto.add(new FilmSessionDto(filmSession.getId(),
                    filmRepository.findById(filmSession.getFilmId()).get().getName(),
                    fileService.getFileById(filmSession.getFilmId()).get(),
                    filmSession.getHallId(), filmSession.getStartTime(), filmSession.getEndTime(),
                    filmSession.getPrice()));
        }
        return filmSessionDto;
    }
}
