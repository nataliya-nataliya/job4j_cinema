package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oTicketRepositoryTest {
    private static Sql2oTicketRepository sql2oTicketRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    public void clearTickets() {
        sql2oTicketRepository.deleteAll();
    }

    @Test
    public void whenTicketSaveAndFindById() {
        var ticket = sql2oTicketRepository.save(new Ticket(0,
                1, 5, 7, 1));
        var result = sql2oTicketRepository.findById(ticket.get().getId());
        assertThat(result).isEqualTo(ticket);
    }

    @Test
    public void whenSomeTicketsSaveAndFindAll() {
        var ticket1 = sql2oTicketRepository.save(new Ticket(0,
                1, 5, 7, 1)).get();
        var ticket2 = sql2oTicketRepository.save(new Ticket(1,
                2, 6, 7, 1)).get();
        var result = sql2oTicketRepository.findAll();
        assertThat(result).isEqualTo(List.of(ticket1, ticket2));
    }
}
