insert into files(name, path) values('movie_poster_1.png', 'files/movie_poster_1.png');
insert into files(name, path) values('movie_poster_2.png', 'files/movie_poster_2.png');
insert into files(name, path) values('movie_poster_3.png', 'files/movie_poster_3.png');

insert into genres(name) values('drama');
insert into genres(name) values('horror');
insert into genres(name) values('comedy');

insert into films(name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) values('Movie 1','Description 1', 2016, 1, 16, 80, 1);
insert into films(name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) values('Movie 2','Description 2', 1990, 2, 18, 100, 2);
insert into films(name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) values('Movie 3','Description 3', 2019, 3, 6, 90, 3);

insert into halls(name, row_count, place_count, description) values('Hall 1', 5, 7, 'Description 1');
insert into halls(name, row_count, place_count, description) values('Hall 2', 10, 10, 'Description 2');

insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(1, 1, '2023-07-10 14:00:00', '2023-07-10 15:20:00', 300);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(1, 2, '2023-07-12 19:00:00', '2023-07-12 20:20:00', 300);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(2, 1, '2023-07-10 16:00:00', '2023-07-10 17:40:00', 400);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(3, 2, '2023-07-14 14:00:00', '2023-07-14 15:30:00', 300);
