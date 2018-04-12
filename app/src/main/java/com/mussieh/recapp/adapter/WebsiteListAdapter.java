package com.mussieh.recapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mussieh.recapp.R;
import com.mussieh.recapp.data.GlideApp;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.Website;
import com.mussieh.recapp.viewholder.WebsiteViewHolder;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Mussie on 3/13/2018.
 * An adapter that binds Video data through a RecyclerView
 */
public class WebsiteListAdapter extends ItemListAdapter {
    private static final String TAG = WebsiteListAdapter.class.getSimpleName();

    /**
     * Construct the WebsiteListAdapter
     * @param context the screen context
     */
    public WebsiteListAdapter(Context context) {
        super(context);
    }

    @Override
    @NonNull
    public WebsiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = super.getLayoutInflater();
        View mItemView = inflater.inflate(R.layout.weblist_item, parent, false);
        return new WebsiteViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArrayList<ResourceListItem> resources = super.getResources();
        WebsiteViewHolder websiteViewHolder = (WebsiteViewHolder) holder;
        websiteViewHolder.setDataSet(resources);
        Website current = (Website) resources.get(position);
        GlideApp.with(websiteViewHolder.itemView).load(current.getWebsiteImageURL()).
                placeholder(new ColorDrawable(Color.rgb(238,238,238))).
                transition(withCrossFade()).
                into(websiteViewHolder.getWebImageView());
        websiteViewHolder.getWebsiteNameTextView().setText(current.getTitle());
        websiteViewHolder.getWebsiteTitleTextView().setText(current.getWebsiteSnippet());
    }


    @Override
    public String getAdapterName() {
        return TAG;
    }

}
