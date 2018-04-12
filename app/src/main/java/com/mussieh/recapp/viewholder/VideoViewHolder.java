package com.mussieh.recapp.viewholder;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mussieh.recapp.R;
import com.mussieh.recapp.adapter.ItemListAdapter;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.Video;

import java.util.ArrayList;

/**
 * Created by Mussie on 3/22/2018.
 * ViewHolder with references to Video item attributes, as well as Video item methods
 */
public class VideoViewHolder extends ItemViewHolder {
    private static final String TAG = VideoViewHolder.class.getSimpleName();
    private ImageView videoImageView;
    private TextView videoTitleTextView;
    private TextView videoChannelTextView;
    private RatingBar ratingBarView;
    private Button watchButton;
    private ImageButton videoMenuButton;
    private ArrayList<ResourceListItem> mVideoList;

    /**
     * Constructs a VideoViewHolder
     * @param itemView the item view
     * @param adapter the item adapter
     */
    public VideoViewHolder(View itemView, final ItemListAdapter adapter) {
        super(itemView, adapter);
        mVideoList = adapter.getResources();
        videoImageView = itemView.findViewById(R.id.video_image_view);
        videoTitleTextView = itemView.findViewById(R.id.tv_page_title);
        videoChannelTextView = itemView.findViewById(R.id.tv_channel_name);
        ratingBarView = itemView.findViewById(R.id.video_ratings_bar);
        videoMenuButton = itemView.findViewById(R.id.video_image_button);
        videoMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoViewHolder.super.showPopupMenu(videoMenuButton, getLayoutPosition(), mVideoList,
                        FirebaseHelper.KEY_VIDEOS);
            }
        });

        watchButton = itemView.findViewById(R.id.watch_button);
        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "New Activity");
                Video videoItem = (Video) mVideoList.get(getLayoutPosition());
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" +
                        videoItem.getVideoId()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" +
                                videoItem.getVideoId()));
                try {
                    adapter.getContext().startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    adapter.getContext().startActivity(webIntent);
                }

            }
        });
    }

    /**
     * Sets the video data
     * @param resources the video data to set
     */
    public void setDataSet(final ArrayList<ResourceListItem> resources) {
        mVideoList = resources;
    }

    /**
     * Get the Video ImageView
     * @return the Video ImageView
     */
    public ImageView getVideoImageView() {
        return videoImageView;
    }

    /**
     * Get the video title TextView
     * @return the video title TextView
     */
    public TextView getVideoTitleTextView() {
        return videoTitleTextView;
    }

    /**
     * Get the video channel TextView
     * @return the video channel TextView
     */
    public TextView getVideoChannelTextView() {
        return videoChannelTextView;
    }

    /**
     * Get the video ratings bar
     * @return the video ratings bar
     */
    public RatingBar getRatingBarView() {
        return ratingBarView;
    }
}
