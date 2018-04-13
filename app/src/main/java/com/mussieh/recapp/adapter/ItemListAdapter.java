package com.mussieh.recapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.mussieh.recapp.data.Book;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.Video;
import com.mussieh.recapp.data.Website;

import java.util.ArrayList;

/**
 * Created by Mussie on 3/23/2018.
 * Defines common methods and attributes used by the resource
 * adapters (BookList, VideoList, WebsiteList)
 */
public abstract class ItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Constants used in item view type conditions
    static final int SEARCH_DISPLAY_ITEM_VIEW = 0;
    static final int BOOK_ITEM_VIEW = 1;
    static final int VIDEO_ITEM_VIEW = 2;
    static final int WEBSITE_ITEM_VIEW = 3;

    private Context mContext;
    private LayoutInflater mInflater;
    private String mAdapterName;
    private ArrayList<ResourceListItem> mResources;
    private ProgressBar mLoadingIndicator;

    /**
     * Constructs an item list adapter from the given screen context
     * @param context the screen context
     */
    public ItemListAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mAdapterName = "";
        this.mResources = new ArrayList<>();
    }

    /**
     * Constructs an item list adapter from the given screen context with an adapter name
     * @param context the screen context
     * @param adapterName the adapter name
     */
    public ItemListAdapter(Context context, String adapterName) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mAdapterName = adapterName;
        this.mResources = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return this.mResources.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mResources.get(position) instanceof Book) {
            return BOOK_ITEM_VIEW;
        } else if (mResources.get(position) instanceof Video) {
            return VIDEO_ITEM_VIEW;
        } else if (mResources.get(position) instanceof Website) {
            return WEBSITE_ITEM_VIEW;
        } else {
            return SEARCH_DISPLAY_ITEM_VIEW;
        }
    }

    /**
     * Returns the current adapter screen context
     * @return the screen context
     */
    public Context getContext() {
        return this.mContext;
    }

    /**
     * Gets the layout inflater
     * @return the layout inflater
     */
    LayoutInflater getLayoutInflater() {
        return mInflater;
    }

    /**
     * Returns the adapter's name
     * @return the name of the adapter
     */
    public String getAdapterName() {
        return this.mAdapterName;
    }

    /**
     * Returns the resources currently loaded in the adapter
     * @return the loaded resource list items
     */
    public ArrayList<ResourceListItem> getResources() {
        return this.mResources;
    }

    /**
     * Sets data to the adapter
     * @param data the resource list data to set
     */
    public void setData(ArrayList<ResourceListItem> data) {
        if (mLoadingIndicator != null) {
            this.mLoadingIndicator.setVisibility(View.GONE);
        }
        this.mResources = data;
        this.notifyDataSetChanged();
    }

    /**
     * Method for setting the progress indicator for this adapter's context
     * @param progressIndicator the Context's progress indicator
     */
    public void setLoadingIndicator(ProgressBar progressIndicator) {
        mLoadingIndicator = progressIndicator;
    }

}
