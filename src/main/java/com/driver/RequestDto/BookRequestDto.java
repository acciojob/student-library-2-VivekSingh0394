package com.driver.RequestDto;

import com.driver.models.Genre;

public class BookRequestDto {
    private String name;
    private Genre genre;
    private int authorId;

    public BookRequestDto(String name, Genre genre, int authorId) {
        this.name = name;
        this.genre = genre;
        this.authorId = authorId;
    }

    public BookRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
