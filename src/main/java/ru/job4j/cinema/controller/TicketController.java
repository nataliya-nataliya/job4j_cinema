package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.service.UserService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final FilmSessionService filmSessionService;
    private final TicketService ticketService;
    private final HallService hallService;
    private final UserService userService;

    public TicketController(FilmSessionService filmSessionService, TicketService ticketService,
                            HallService hallService, UserService userService) {
        this.filmSessionService = filmSessionService;
        this.ticketService = ticketService;
        this.hallService = hallService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String getTicket(Model model, @PathVariable int id) {
        var ticketOptional = ticketService.findById(id);
        if (ticketOptional.isEmpty()) {
            model.addAttribute("message", "There is no such ticket id");
            return "errors/404";
        }
        model.addAttribute("ticket", ticketOptional.get());
        return "tickets/one";
    }
}
