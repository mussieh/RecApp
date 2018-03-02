package com.mussieh.recapp.data;

/**
 * Created by Mussie on 2/25/2018.
 *
 */

public class BookItem {
    private String isbn;
    private String title;
    private String author;
    private String bookType;
    private int rank;
    private String description;
    private String pictureLocation;
    private String subjectName;

    public class WordItem {

    }

    public String getIsbn() {return isbn;}

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookType() {
        return bookType;
    }

    public int getRank() {
        return rank;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureLocation() {
        return pictureLocation;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictureLocation(String pictureLocation) {
        this.pictureLocation = pictureLocation;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
