package com.mussieh.recapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mussie on 3/23/2018.
 * Holds common methods and attributes used by the data model classes (Book, Video, Website)
 */
public abstract class ResourceListItem implements Parcelable {

    private long timeAddedToList;

    @Override
    public abstract int describeContents();

    @Override
    public abstract void writeToParcel(Parcel parcel, int i);

    /**
     * Gets the time the item was added to the list (time in milliseconds)
     * @return the time in milliseconds
     */
    public long getTimeAddedToList() {
        return timeAddedToList;
    }

    /**
     * Sets the time the item was added to the list (time in milliseconds)
     * @param timeAddedToList the time in milliseconds
     */
    public void setTimeAddedToList(long timeAddedToList) {
        this.timeAddedToList = timeAddedToList;
    }
}
