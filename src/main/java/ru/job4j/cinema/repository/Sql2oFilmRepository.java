package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmRepository implements FilmRepository {
    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<Film> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "SELECT * FROM films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }

    @Override
    public Optional<Film> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "SELECT films.id, films.name, films.description, films.year, "
                            + "films.minimal_age AS minimalAge, "
                            + "films.duration_in_minutes AS durationInMinutes,"
                            + " genres.name FROM films JOIN genres ON genres.id = films.genre_id"
                            + " WHERE films.id = :id");
            var film = query.addParameter("id", id).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }
}
