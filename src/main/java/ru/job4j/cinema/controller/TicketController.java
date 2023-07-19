package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final FilmSessionService filmSessionService;
    private final TicketService ticketService;

    public TicketController(FilmSessionService filmSessionService,
                            TicketService ticketService, FilmService filmService) {
        this.filmSessionService = filmSessionService;
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var ticketOptional = ticketService.findById(id);
        if (ticketOptional.isEmpty()) {
            model.addAttribute("message", "There is no such ticket id");
            return "errors/404";
        }
        var filmSessionOptional = filmSessionService.findById(ticketOptional.get().getSessionId());
        model.addAttribute("filmSession", filmSessionOptional.get());
        model.addAttribute("ticket", ticketOptional.get());
        return "tickets/one";
    }
}
