package ru.job4j.cinema.dto;

public class FilmSessionDto {
    private int id;
    private String name;
    private String startTime;
    private int price;
    private int fileId;

    public FilmSessionDto(int id, String name, String startTime, int price, int fileId) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.price = price;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
