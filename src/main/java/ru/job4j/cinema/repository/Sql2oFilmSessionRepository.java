package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {
    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<FilmSession> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "SELECT * FROM film_sessions ORDER BY start_time");
            return query.setColumnMappings(FilmSession.COLUMN_MAPPING)
                    .executeAndFetch(FilmSession.class);
        }
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id")
                    .setColumnMappings(FilmSession.COLUMN_MAPPING);
            var filmSession = query.addParameter("id", id).executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(filmSession);
        }
    }
}
