package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/timetable")
public class FilmSessionController {
    private final FilmSessionService filmSessionService;
    private final TicketService ticketService;
    private final HallService hallService;

    public FilmSessionController(FilmSessionService filmSessionService, TicketService ticketService,
                                 HallService hallService) {
        this.filmSessionService = filmSessionService;
        this.ticketService = ticketService;
        this.hallService = hallService;
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
        model.addAttribute("tickets", ticketService.findAll());
        model.addAttribute("filmSession", filmSessionOptional.get());
        model.addAttribute("hall", hallOptional.get());
        return "timetable/one";
    }

    @PostMapping("/buy")
    public String register(Model model, @ModelAttribute Ticket ticket) {
        var savedUser = ticketService.save(ticket);
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "The ticket for this row and seat is already taken.");
            return "errors/404";
        }
        return "redirect:/index";
    }
}
