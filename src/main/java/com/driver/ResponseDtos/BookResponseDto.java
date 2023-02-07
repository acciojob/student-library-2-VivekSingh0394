package com.driver.ResponseDtos;

import com.driver.models.Genre;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class BookResponseDto {
      private String bookName;
    @Enumerated(EnumType.STRING)
    private Genre genre;

    private String authorName;

    public BookResponseDto(String bookName, Genre genre, String authorName) {
        this.bookName = bookName;
        this.genre = genre;
        this.authorName = authorName;
    }

    public BookResponseDto() {
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
