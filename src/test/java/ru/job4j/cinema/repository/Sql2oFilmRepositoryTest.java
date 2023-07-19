package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oFilmRepositoryTest {
    private static Sql2oFilmRepository sql2oFilmRepository;

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
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    public void whenFilmsFindAll() {
        var film1 = new Film(1, "Movie 1", "Description 1",
                2016, 1, 16, 80, 1);
        var film2 = new Film(2, "Movie 2", "Description 2",
                1990, 2, 18, 100, 2);
        var film3 = new Film(3, "Movie 3", "Description 3",
                2019, 3, 6, 90, 3);
        var result = sql2oFilmRepository.findAll();
        assertThat(result).isEqualTo(List.of(film1, film2, film3));
    }

    @Test
    public void whenFilmGetById() {
        var film = new Film(1, "Movie 1", "Description 1",
                2016, 1, 16, 80, 1);
        var result = sql2oFilmRepository.findById(film.getId()).get();
        assertThat(result).isEqualTo(film);
    }
}
