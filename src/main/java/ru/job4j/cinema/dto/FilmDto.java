package ru.job4j.cinema.dto;

public class FilmDto {
    private int id;
    private String name;
    private String description;
    private int year;
    private int minimalAge;
    private String formatDuration;
    private String genre;
    private int fileId;

    public FilmDto(int id, String name, String description, int year, int minimalAge,
                   String formatDuration, String genre, int fileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.minimalAge = minimalAge;
        this.formatDuration = formatDuration;
        this.genre = genre;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public String getFormatDuration() {
        return formatDuration;
    }

    public void setFormatDuration(String formatDuration) {
        this.formatDuration = formatDuration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
