package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketControllerTest {

    private FilmSessionService filmSessionService;
    private TicketService ticketService;
    private TicketController ticketController;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(filmSessionService, ticketService);
    }

    @Test
    public void whenRequestTicketPageThenGetTicketPage() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketService.findById(1)).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = ticketController.getById(model, 1);
        var actualFilmSessions = model.getAttribute("ticket");

        assertThat(view).isEqualTo("tickets/one");
        assertThat(actualFilmSessions).isEqualTo(ticket);
    }

    @Test
    public void whenSomeExceptionThrownUponTicketIsEmptyThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("There is no such ticket id");
        when(ticketService.findById(1)).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = ticketController.getById(model, new Ticket().getId());
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }
}
