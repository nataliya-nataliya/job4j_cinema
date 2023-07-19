package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {
    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Collection<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return Optional.of(ticketRepository.findById(id)).get();
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public boolean deleteAll() {
        return ticketRepository.deleteAll();
    }

}
