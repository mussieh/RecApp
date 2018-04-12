package com.mussieh.recapp.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mussie on 2/25/2018.
 * Book data model
 */
public class Book extends ResourceListItem implements Parcelable {
    private String author;
    private double averageRating;
    private String bookType;
    private String description;
    private String edition;
    private String imageURL;
    private String isbn13;
    private long rank;
    private long ratingsCount;
    private String title;

    /**
     * Empty Book constructor
     */
    public Book() {

    }

    /**
     * Constructs the Book item
     * @param author the book's author
     * @param averageRating the book's average rating
     * @param bookType the book's type
     * @param description the book's description
     * @param edition the book's edition
     * @param imageURL the book's image URL
     * @param isbn13 the book's ISBN-13
     * @param rank the book's rank
     * @param ratingsCount the book's ratings count
     * @param title the book's title
     */
    public Book(String author, double averageRating, String bookType, String description,
                String edition, String imageURL, String isbn13, long rank, long ratingsCount,
                String title) {
        this.author = author;
        this.averageRating = averageRating;
        this.bookType = bookType;
        this.description = description;
        this.edition = edition;
        this.imageURL = imageURL;
        this.isbn13 = isbn13;
        this.rank = rank;
        this.ratingsCount = ratingsCount;
        this.title = title;
    }

    /**
     * Constructor for reading in Book Parcel data
     * @param in the Parcel to read
     */
    public Book(Parcel in) {
        this.author = in.readString();
        this.averageRating = in.readDouble();
        this.bookType = in.readString();
        this.description = in.readString();
        this.edition = in.readString();
        this.imageURL = in.readString();
        this.isbn13 = in.readString();
        this.rank = in.readLong();
        this.ratingsCount = in.readLong();
        this.title = in.readString();
    }

    /**
     * Get the book's author name
     * @return the book's author name
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Get the book's average rating
     * @return the book's average rating
     */
    public double getAverageRating() {
        return this.averageRating;
    }

    /**
     * Get the book type
     * @return the book type
     */
    public String getBookType() {
        return this.bookType;
    }

    /**
     * Get the book description
     * @return the book description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the book edition
     * @return the book edition
     */
    public String getEdition() {
        return this.edition;
    }

    /**
     * Get the image URL
     * @return the image URL
     */
    public String getImageURL() {
        return this.imageURL;
    }

    /**
     * Get the book's ISBN-13
     * @return the book's ISBN-13
     */
    public String getISBN13() {
        return this.isbn13;
    }

    /**
     * Get the book rank
     * @return the rank of the book
     */
    public long getRank() {
        return this.rank;
    }

    /**
     * Get the book ratings count
     * @return the ratings count for the book
     */
    public long getRatingsCount() {
        return this.ratingsCount;
    }

    /**
     * Get the book title
     * @return the title of the book
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set the author's name
     * @param author the author name to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Set the book average rating
     * @param averageRating the average rating to set
     */
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * Set the book's description
     * @param description the book's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the book image URL
     * @param imageURL the book image URL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Set the book title
     * @param title the book title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.author);
        parcel.writeString(this.bookType);
        parcel.writeString(this.description);
        parcel.writeString(this.edition);
        parcel.writeString(this.imageURL);
        parcel.writeString(this.isbn13);
        parcel.writeLong(this.rank);
        parcel.writeString(this.title);

        if (this.averageRating != 0.0d) {
            parcel.writeDouble(this.averageRating);
            parcel.writeLong(this.ratingsCount);
        }
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public String toString() {
        return "Book Title: " + title + " Book Author: " + author + " imageURLString: " + imageURL;
    }
}
