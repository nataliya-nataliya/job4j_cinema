package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmSessionControllerTest {

    private FilmSessionService filmSessionService;
    private HallService hallService;
    private FilmService filmService;
    private TicketService ticketService;
    private FilmSessionController filmSessionController;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        hallService = mock(HallService.class);
        filmService = mock(FilmService.class);
        ticketService = mock(TicketService.class);
        filmSessionController = new FilmSessionController(filmSessionService, ticketService,
                hallService, filmService);
    }

    @Test
    public void whenRequestFilmSessionListPageThenGetPageWithFilmSessions() {
        var filmSession1 = new FilmSessionDto(1, "test1", 1,
                "desc1", "10.07.2023 16:00", "10.07.2023 17:40", 300, "Hall 1", 1, 1);
        var filmSession2 = new FilmSessionDto(2, "test2", 2,
                "desc2", "10.07.2023 16:00", "10.07.2023 17:40", 300, "Hall 2", 2, 2);
        var filmSessions = List.of(filmSession1, filmSession2);
        when(filmSessionService.findAll()).thenReturn(filmSessions);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var actualFilmSessions = model.getAttribute("filmsSession");

        assertThat(view).isEqualTo("timetable/list");
        assertThat(actualFilmSessions).isEqualTo(filmSessions);
    }

    @Test
    public void whenRequestFilmSessionPageThenGetFilmSessionPage() {
        var filmSession = new FilmSessionDto(1, "test1", 1,
                "desc1", "10.07.2023 16:00", "10.07.2023 17:40", 300, "Hall 1", 1, 1);
        when(filmSessionService.findById(1)).thenReturn(Optional.of(filmSession));

        var model = new ConcurrentModel();
        var view = filmSessionController.getById(model, 1);
        var actualFilmSessions = model.getAttribute("filmSession");

        assertThat(view).isEqualTo("timetable/one");
        assertThat(actualFilmSessions).isEqualTo(filmSession);
    }

    @Test
    public void whenSomeExceptionThrownUponFilmSessionIsEmptyThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("There is no such film session id");
        when(filmSessionService.findById(1)).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = filmSessionController.getById(model, new FilmSession().getId());
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }
}
