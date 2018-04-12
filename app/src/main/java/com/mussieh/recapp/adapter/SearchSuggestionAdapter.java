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
import com.mussieh.recapp.data.Book;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.GlideApp;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.Video;
import com.mussieh.recapp.data.Website;
import com.mussieh.recapp.viewholder.BookViewHolder;
import com.mussieh.recapp.viewholder.SearchSuggestionViewHolder;
import com.mussieh.recapp.viewholder.VideoViewHolder;
import com.mussieh.recapp.viewholder.WebsiteViewHolder;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * An adapter that binds search suggestion data through a RecyclerView
 * Note: this class is not complete so please have that in mind when using it
 */
public class SearchSuggestionAdapter extends ItemListAdapter {
    private static final String TAG = ItemListAdapter.class.getSimpleName();

    /**
     * Construct the SearchSuggestionAdapter
     * @param context the screen context
     */
    public SearchSuggestionAdapter(Context context) {
        super(context);
    }

    @Override
    public String getAdapterName() {
        return TAG;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View searchItemView = inflater.inflate(R.layout.search_suggestion_item, parent,
                        false);
        viewHolder = new SearchSuggestionViewHolder(searchItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchSuggestionViewHolder searchViewHolder = (SearchSuggestionViewHolder) holder;
        ArrayList<ResourceListItem> resources = super.getResources();

        switch (holder.getItemViewType()) {
            case BOOK_ITEM_VIEW:
                Book bookItem = (Book) resources.get(position);
                searchViewHolder.getTitleTextView().setText(bookItem.getTitle());
                break;
            case VIDEO_ITEM_VIEW:
                Video videoItem = (Video) resources.get(position);
                searchViewHolder.getTitleTextView().setText(videoItem.getChannelName());
                break;
            case WEBSITE_ITEM_VIEW:
                Website websiteItem = (Website) resources.get(position);
                searchViewHolder.getTitleTextView().setText(websiteItem.getTitle());
            default:
                break;
        }
    }
}
