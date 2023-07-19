package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {
    static final Logger SQL_2_O_TICKET_REPOSITORY_LOGGER =
            LoggerFactory.getLogger(Sql2oTicketRepository.class);
    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<Ticket> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "SELECT * FROM tickets");
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets WHERE id = :id")
                    .setColumnMappings(Ticket.COLUMN_MAPPING);
            var ticket = query.addParameter("id", id).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        Optional<Ticket> rsl = Optional.empty();
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO tickets(
                    session_id, row_number, place_number, user_id)
                    VALUES (:sessionId, :rowNumber, :placeNumber, :userId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            rsl = Optional.of(ticket);
        } catch (Sql2oException exception) {
            SQL_2_O_TICKET_REPOSITORY_LOGGER.info(
                    String.format("The ticket for the row %s and seat %s is already taken."
                                    + " Choose a different seat in the hall.",
                            ticket.getRowNumber(), ticket.getPlaceNumber()));
        }
        return rsl;
    }

    @Override
    public boolean deleteAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM tickets");
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }
}
