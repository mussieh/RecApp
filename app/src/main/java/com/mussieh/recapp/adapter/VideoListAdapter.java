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
import com.mussieh.recapp.data.Video;
import com.mussieh.recapp.viewholder.VideoViewHolder;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Mussie on 3/10/2018.
 * An adapter that binds Video data through a RecyclerView
 */
public class VideoListAdapter extends ItemListAdapter {
    private static final String TAG = VideoListAdapter.class.getSimpleName();

    /**
     * Constructs the VideoListAdapter
     * @param context the screen context
     */
    public VideoListAdapter(Context context) {
        super(context);
    }


    @Override
    @NonNull
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = super.getLayoutInflater();
        View mItemView = inflater.inflate(R.layout.videolist_item, parent, false);
        return new VideoViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArrayList<ResourceListItem> resources = super.getResources();
        VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
        videoViewHolder.setDataSet(resources);
        Video current = (Video) resources.get(position);
        GlideApp.with(videoViewHolder.itemView).load(current.getVideoImageURL()).
                placeholder(new ColorDrawable(Color.rgb(238,238,238))).
                transition(withCrossFade()).
                into(videoViewHolder.getVideoImageView());
        videoViewHolder.getVideoChannelTextView().setText(current.getChannelName());
        videoViewHolder.getVideoTitleTextView().setText(current.getTitle());
        videoViewHolder.getRatingBarView().setRating( (float) current.getVideoRating() );
    }

    @Override
    public String getAdapterName() {
        return TAG;
    }
}
