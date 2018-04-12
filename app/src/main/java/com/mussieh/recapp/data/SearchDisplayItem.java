package com.mussieh.recapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class SearchDisplayItem extends ResourceListItem {
    private String searchResultTitle;

    /**
     * Empty SearchDisplayItem constructor
     */
    public SearchDisplayItem() {
    }

    /**
     * Constructs the SearchDisplayItem
     * @param title the title of the current search result
     */
    public SearchDisplayItem(String title) {
        searchResultTitle = title;
    }

    /**
     * Constructor for reading in SearchDisplayItem Parcel data
     * @param in the Parcel to read
     */
    private SearchDisplayItem(Parcel in) {
        this.searchResultTitle = in.readString();
    }

    /**
     * Gets the Search Result title
     * @return the search result title
     */
    public String getSearchResultTitle() {
        return searchResultTitle;
    }

    /**
     * Sets the Search Result title
     * @param title the search result title to set
     */
    public void setSearchResultTitle(String title) {
        this.searchResultTitle = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.searchResultTitle);
    }

    public static final Parcelable.Creator<SearchDisplayItem> CREATOR = new Parcelable.Creator<SearchDisplayItem>() {
        public SearchDisplayItem createFromParcel(Parcel parcel) {
            return new SearchDisplayItem(parcel);
        }

        public SearchDisplayItem[] newArray(int size) {
            return new SearchDisplayItem[size];
        }
    };
}
