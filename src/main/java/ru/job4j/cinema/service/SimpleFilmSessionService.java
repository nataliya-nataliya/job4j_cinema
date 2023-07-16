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
import java.util.Optional;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;
    private final FileService fileService;
    private final HallService hallService;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository,
                                    FilmRepository filmRepository, FileService fileService,
                                    HallService hallService) {
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
        this.fileService = fileService;
        this.hallService = hallService;
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
                    hallService.findById(filmSession.getHallId()).get().getName(),
                    hallService.findById(filmSession.getHallId()).get().getId(),
                    filmRepository.findById(filmSession.getFilmId()).get().getFileId()
            ));
        }
        return filmSessionDto;
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        var filmSessionOptional = filmSessionRepository.findById(id);
        if (filmSessionOptional.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new FilmSessionDto(filmSessionOptional.get().getId(),
                filmRepository.findById(filmSessionOptional.get().getFilmId()).get().getName(),
                formatLocalDateTime(filmSessionOptional.get().getStartTime()),
                filmSessionOptional.get().getPrice(),
                hallService.findById(filmSessionOptional.get().getHallId()).get().getName(),
                hallService.findById(filmSessionOptional.get().getHallId()).get().getId(),
                filmRepository.findById(filmSessionOptional.get().getFilmId()).get().getFileId())
        );
    }
}
