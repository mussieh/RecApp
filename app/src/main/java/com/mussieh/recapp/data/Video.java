package com.mussieh.recapp.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mussie on 2/25/2018.
 * Video data model
 */
public class Video extends ResourceListItem {
    private String channelName;
    private String videoId;
    private String videoImageURL;
    private long videoRank;
    private double videoRating;
    private String title;

    /**
     * Empty Video constructor
     */
    public Video() {

    }

    /**
     * Constructs the Video item
     * @param channelName the video channel name
     * @param videoId the video id
     * @param videoImageURL the video image URL
     * @param videoRank the video rank
     * @param videoRating the video rating
     * @param title the video title
     */
    public Video(String channelName, String videoId, String videoImageURL, long videoRank,
                 double videoRating, String title) {
        this.channelName = channelName;
        this.videoId = videoId;
        this.videoImageURL = videoImageURL;
        this.videoRank = videoRank;
        this.videoRating = videoRating;
        this.title = title;
    }

    /**
     * Constructor for reading in Video Parcel data
     * @param in the Parcel to read
     */
    public Video(Parcel in) {
        this.channelName = in.readString();
        this.videoId = in.readString();
        this.videoImageURL = in.readString();
        this.videoRank = in.readLong();
        this.videoRating = in.readDouble();
        this.title = in.readString();
    }

    /**
     * Get the Video channel name
     * @return the video channel name
     */
    public String getChannelName() {
        return this.channelName;
    }

    /**
     * Get the Video Id
     * @return the video Id
     */
    public String getVideoId() {
        return this.videoId;
    }

    /**
     * Get the Video image URL
     * @return the video image URL
     */
    public String getVideoImageURL() {
        return this.videoImageURL;
    }

    /**
     * Get the Video rank
     * @return the Video rank
     */
    public long getVideoRank() {
        return this.videoRank;
    }

    /**
     * Get the Video Rating
     * @return the Video Rating
     */
    public double getVideoRating() {
        return this.videoRating;
    }

    /**
     * Get the Video title
     * @return the Video title
     */
    public String getTitle() {
        return this.title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.channelName);
        parcel.writeString(this.videoId);
        parcel.writeString(this.videoImageURL);
        parcel.writeLong(this.videoRank);
        parcel.writeDouble(this.videoRating);
        parcel.writeString(this.title);
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        public Video createFromParcel(Parcel parcel) {
            return new Video(parcel);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public String toString() {
        return "Channel Name: " + this.channelName +
                " Video Title: " + this.title + " Video Rating: " + this.videoRating;
    }
}
