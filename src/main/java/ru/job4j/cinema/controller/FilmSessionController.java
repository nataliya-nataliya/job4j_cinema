package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/timetable")
public class FilmSessionController {
    private final FilmSessionService filmSessionService;
    private final TicketService ticketService;
    private final HallService hallService;
    private final FilmService filmService;

    public FilmSessionController(FilmSessionService filmSessionService, TicketService ticketService,
                                 HallService hallService, FilmService filmService) {
        this.filmSessionService = filmSessionService;
        this.ticketService = ticketService;
        this.hallService = hallService;
        this.filmService = filmService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("filmsSession", filmSessionService.findAll());
        return "timetable/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var filmSessionOptional = filmSessionService.findById(id);
        if (filmSessionOptional.isEmpty()) {
            model.addAttribute("message", "There is no such film session id");
            return "errors/404";
        }
        var hallOptional = hallService.findById(filmSessionOptional.get().getHallId());
        var filmOptional = filmService.findById(filmSessionOptional.get().getFilmId());
        model.addAttribute("filmSession", filmSessionOptional.get());
        model.addAttribute("ticket", new Ticket());
        hallOptional.ifPresent(hall -> model.addAttribute("hall", hall));
        filmOptional.ifPresent(film -> model.addAttribute("film", film));
        return "timetable/one";
    }

    @PostMapping("/buy")
    public String buy(Model model, @ModelAttribute Ticket ticket) {
        var savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            model.addAttribute("message", String.format(
                    "The ticket for %d row and %d seat is already taken. Select other places",
                    ticket.getRowNumber(), ticket.getPlaceNumber()));
            return "errors/404";
        }
        return String.format("redirect:/tickets/%d", savedTicket.get().getId());
    }
}
