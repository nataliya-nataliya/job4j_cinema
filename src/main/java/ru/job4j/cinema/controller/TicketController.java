package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final FilmSessionService filmSessionService;
    private final TicketService ticketService;

    public TicketController(FilmSessionService filmSessionService,
                            TicketService ticketService) {
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
        model.addAttribute("ticket", ticketOptional.get());
        filmSessionOptional.ifPresent(filmSession -> model.addAttribute(
                "filmSession", filmSession));
        return "tickets/one";
    }

    @PostMapping("/buy")
    public String buy(Model model, @ModelAttribute Ticket ticket) {
        var savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            model.addAttribute("message", String.format(
                    "The ticket for %d row and %d seat is already taken. Select other places",
                    ticket.getRowNumber(), ticket.getPlaceNumber()));
            return "errors/409";
        }
        return String.format("redirect:/tickets/%d", savedTicket.get().getId());
    }
}
