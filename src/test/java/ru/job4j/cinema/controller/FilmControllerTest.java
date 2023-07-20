package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.GenreService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmControllerTest {
    private FilmService filmService;

    private GenreService genreService;

    private FilmController filmController;

    private MultipartFile testFile;

    @BeforeEach
    public void initServices() {
        filmService = mock(FilmService.class);
        genreService = mock(GenreService.class);
        filmController = new FilmController(filmService);
        testFile = new MockMultipartFile("testFile.img", new byte[]{1, 2, 3});
    }

    @Test
    public void whenRequestFilmListPageThenGetPageWithFilms() {
        var film1 = new FilmDto(1, "test1", "desc1", 2010, 6,
                "1 hour(s) 30 minute(s)", "drama", 1);
        var film2 = new FilmDto(1, "test2", "desc2", 1990, 12,
                "1 hour(s) 50 minute(s)", "drama", 2);

        var expectedFilms = List.of(film1, film2);
        when(filmService.findAll()).thenReturn(expectedFilms);

        var model = new ConcurrentModel();
        var view = filmController.getAll(model);
        var actualFilms = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualFilms).isEqualTo(expectedFilms);
    }
}
