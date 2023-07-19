package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.FilmSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oFilmSessionRepositoryTest {
    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    @Test
    public void whenFilmSessionsFindAll() {
        var filmSession1 = new FilmSession(1, 1, 1,
                LocalDateTime.of(2023, 7, 10, 14, 0, 0),
                LocalDateTime.of(2023, 7, 10, 15, 20, 0), 300);
        var filmSession2 = new FilmSession(2, 1, 2,
                LocalDateTime.of(2023, 7, 12, 19, 0, 0),
                LocalDateTime.of(2023, 7, 12, 20, 20, 0), 300);
        var filmSession3 = new FilmSession(3, 2, 1,
                LocalDateTime.of(2023, 7, 10, 16, 0, 0),
                LocalDateTime.of(2023, 7, 10, 17, 40, 0), 400);
        var filmSession4 = new FilmSession(4, 3, 2,
                LocalDateTime.of(2023, 7, 14, 14, 0, 0),
                LocalDateTime.of(2023, 7, 14, 15, 30, 0), 300);
        var result = sql2oFilmSessionRepository.findAll();
        assertThat(result).isEqualTo(
                List.of(filmSession1, filmSession3, filmSession2, filmSession4));
    }

    @Test
    public void whenFilmSessionFindById() {
        var filmSession = new FilmSession(1, 1, 1,
                LocalDateTime.of(2023, 7, 10, 14, 0, 0),
                LocalDateTime.of(2023, 7, 10, 15, 20, 0), 300);
        var result = sql2oFilmSessionRepository.findById(filmSession.getId()).get();
        assertThat(result).isEqualTo(filmSession);
    }
}
