package com.mussieh.recapp.viewholder;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mussieh.recapp.R;
import com.mussieh.recapp.adapter.ItemListAdapter;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.Website;

import java.util.ArrayList;

/**
 * Created by Mussie on 3/22/2018.
 * ViewHolder with references to Website item attributes, as well as Video item methods
 */
public class WebsiteViewHolder extends ItemViewHolder {
    private static final String TAG = WebsiteViewHolder.class.getSimpleName();
    private final ImageView webImageView;
    private final TextView websiteTitleTextView;
    private final TextView websiteNameTextView;
    private final Button visitButton;
    private final ImageButton websiteMenuButton;
    private ArrayList<ResourceListItem> mWebsiteList;

    /**
     * Constructs a WebsiteViewHolder
     * @param itemView the item view
     * @param adapter the item adapter
     */
    public WebsiteViewHolder(View itemView, final ItemListAdapter adapter) {
        super(itemView, adapter);
        mWebsiteList = adapter.getResources();
        webImageView = itemView.findViewById(R.id.web_image_view);
        websiteTitleTextView = itemView.findViewById(R.id.tv_website_title);
        websiteNameTextView = itemView.findViewById(R.id.tv_website_name);
        visitButton = itemView.findViewById(R.id.web_visit_btn);
        websiteMenuButton = itemView.findViewById(R.id.website_image_button);
        websiteMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebsiteViewHolder.super.showPopupMenu(websiteMenuButton, getLayoutPosition(),
                        mWebsiteList, FirebaseHelper.KEY_WEBSITES);
            }
        });

        visitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "New Activity");
                Website websiteItem = (Website) mWebsiteList.get(getLayoutPosition());
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(websiteItem.getWebsiteLink()));
                try {
                    adapter.getContext().startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {
                    ex.getMessage();
                }

            }
        });
    }

    /**
     * Sets the website data
     * @param resources the website data to set
     */
    public void setDataSet(final ArrayList<ResourceListItem> resources) {
        mWebsiteList = resources;
    }

    /**
     * Get the Website ImageView
     * @return the Website ImageView
     */
    public ImageView getWebImageView() {
        return webImageView;
    }

    /**
     * Get the website title TextView
     * @return the website title TextView
     */
    public TextView getWebsiteTitleTextView() {
        return websiteTitleTextView;
    }

    /**
     * Get the website image name TextView
     * @return the website image name TextView
     */
    public TextView getWebsiteNameTextView() {
        return websiteNameTextView;
    }
}
