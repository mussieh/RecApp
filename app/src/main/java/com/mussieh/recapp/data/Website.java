package com.mussieh.recapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mussie on 3/6/2018.
 * Website data model
 */
public class Website extends ResourceListItem {
    private String websiteFullTitle;
    private String websiteImageName;
    private String websiteImageURL;
    private String websiteLink;
    private long websiteRank;
    private String title;
    private String websiteSnippet;

    /**
     * Empty Website Constructor
     */
    public Website() {

    }

    /**
     * Constructs the Website item
     * @param websiteFullTitle the title of the website
     * @param websiteImageName the website image name
     * @param websiteImageURL the website image URL
     * @param websiteLink the website image link
     * @param websiteRank the website rank
     * @param title the website title
     * @param websiteSnippet the website snippet
     */
    public Website(String websiteFullTitle, String websiteImageName, String websiteImageURL,
                   String websiteLink, long websiteRank, String title,
                   String websiteSnippet) {
        this.websiteFullTitle = websiteFullTitle;
        this.websiteImageName = websiteImageName;
        this.websiteImageURL = websiteImageURL;
        this.websiteLink = websiteLink;
        this.websiteRank = websiteRank;
        this.title = title;
        this.websiteSnippet = websiteSnippet;
    }

    /**
     * Constructor for reading in Website Parcel data
     * @param in the Parcel to read
     */
    public Website(Parcel in) {
        this.websiteFullTitle = in.readString();
        this.websiteImageName = in.readString();
        this.websiteImageURL = in.readString();
        this.websiteLink = in.readString();
        this.websiteRank = in.readLong();
        this.title = in.readString();
        this.websiteSnippet = in.readString();
    }

    /**
     * Get the website's full title
     * @return the website's full title
     */
    public String getWebsiteFullTitle() {
        return websiteFullTitle;
    }

    /**
     * Get the website's image name
     * @return the website image name
     */
    public String getWebsiteImageName() {
        return websiteImageName;
    }

    /**
     * Get the website's image URL
     * @return the website image URL
     */
    public String getWebsiteImageURL() {
        return websiteImageURL;
    }

    /**
     * Get the website's link
     * @return the website link
     */
    public String getWebsiteLink() {
        return websiteLink;
    }

    /**
     * Get the website rank
     * @return the website rank
     */
    public long getWebsiteRank() {
        return websiteRank;
    }

    /**
     * Get the website title
     * @return the website title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the website snippet
     * @return the website snippet
     */
    public String getWebsiteSnippet() {
        return websiteSnippet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.websiteFullTitle);
        parcel.writeString(this.websiteImageName);
        parcel.writeString(this.websiteImageURL);
        parcel.writeString(this.websiteLink);
        parcel.writeLong(this.websiteRank);
        parcel.writeString(this.title);
        parcel.writeString(this.websiteSnippet);
    }

    public static final Parcelable.Creator<Website> CREATOR = new Parcelable.Creator<Website>() {
        public Website createFromParcel(Parcel parcel) {
            return new Website(parcel);
        }

        public Website[] newArray(int size) {
            return new Website[size];
        }
    };

    @Override
    public String toString() {
        return "Website Title: " + this.title +
                " Website Snippt: " + this.websiteSnippet +
                " Website Link: " + this.websiteLink;
    }
}
