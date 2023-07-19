package ru.job4j.cinema.dto;

public class FilmSessionDto {
    private int id;
    private String name;
    private int filmId;
    private String description;
    private String startTime;
    private String endTime;
    private int price;
    private String nameHall;
    private int hallId;
    private int fileId;

    public FilmSessionDto(int id, String name, int filmId, String description, String startTime,
                          String endTime, int price,
                          String nameHall, int hallId, int fileId) {
        this.id = id;
        this.name = name;
        this.filmId = filmId;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.nameHall = nameHall;
        this.hallId = hallId;
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNameHall() {
        return nameHall;
    }

    public void setNameHall(String nameHall) {
        this.nameHall = nameHall;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
