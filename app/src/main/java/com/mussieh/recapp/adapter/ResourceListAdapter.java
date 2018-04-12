package com.mussieh.recapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mussieh.recapp.R;
import com.mussieh.recapp.data.Book;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.GlideApp;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.SearchDisplayItem;
import com.mussieh.recapp.data.SearchHelper;
import com.mussieh.recapp.data.SharedPreferencesHelper;
import com.mussieh.recapp.data.Video;
import com.mussieh.recapp.data.Website;
import com.mussieh.recapp.viewholder.BookViewHolder;
import com.mussieh.recapp.viewholder.SearchDisplayViewHolder;
import com.mussieh.recapp.viewholder.VideoViewHolder;
import com.mussieh.recapp.viewholder.WebsiteViewHolder;

import java.util.ArrayList;
import java.util.Collections;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Mussie on 3/22/2018.
 * An adapter for handling list data of resources with various types (ResourceListItem superclass)
 */
public class ResourceListAdapter extends ItemListAdapter implements ItemTouchHelperAdapter {
    private static final String TAG = ResourceListAdapter.class.getSimpleName();
    private FirebaseHelper firebaseHelper;

    /**
     * Constructs the resource list adapter
     * @param context the screen context
     * @param adapterName the classifying name of the ResourceListAdapter
     */
    public ResourceListAdapter(Context context, String adapterName) {
        super(context, adapterName);
        firebaseHelper = new FirebaseHelper(this);
    }

    public FirebaseHelper getFirebaseHelper() {
        return firebaseHelper;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case SEARCH_DISPLAY_ITEM_VIEW:
                View searchDisplayItemView = inflater.inflate(R.layout.search_display_item, parent,
                        false);
                viewHolder = new SearchDisplayViewHolder(searchDisplayItemView, this);
                break;
            case BOOK_ITEM_VIEW:
                View bookItemView = inflater.inflate(R.layout.booklist_item, parent,
                        false);
                viewHolder = new BookViewHolder(bookItemView, this);
                break;
            case VIDEO_ITEM_VIEW:
                View videoItemView = inflater.inflate(R.layout.videolist_item, parent,
                        false);
                viewHolder = new VideoViewHolder(videoItemView, this);
                break;
            case WEBSITE_ITEM_VIEW:
                View websiteItemView = inflater.inflate(R.layout.weblist_item, parent,
                        false);
                viewHolder = new WebsiteViewHolder(websiteItemView, this);
                break;
            default:
                View defaultItemView = inflater.inflate(R.layout.booklist_item, parent,
                        false);
                viewHolder = new BookViewHolder(defaultItemView, this);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArrayList<ResourceListItem> resources = super.getResources();

        switch (holder.getItemViewType()) {
            case SEARCH_DISPLAY_ITEM_VIEW:
                SearchDisplayViewHolder searchDisplayHolder = (SearchDisplayViewHolder) holder;
                SearchDisplayItem searchDisplayItem = (SearchDisplayItem)
                        resources.get(position);
                String searchResultMessage = getContext().getResources().
                        getString(R.string.search_result_message);
                Spanned formattedSearchResultMessage =
                        SearchHelper.fromHtml(searchResultMessage + " <b>"
                                + searchDisplayItem.getSearchResultTitle()
                                + "</b>");
                searchDisplayHolder.getSearchResultsTextView().setText(formattedSearchResultMessage);
                break;
            case BOOK_ITEM_VIEW:
                BookViewHolder bookViewHolder = (BookViewHolder) holder;
                bookViewHolder.setDataSet(resources);
                Book bookItem = (Book) resources.get(position);
                GlideApp.with(bookViewHolder.itemView).load(bookItem.getImageURL()).
                        placeholder(new ColorDrawable(Color.rgb(238,238,238))).
                        transition(withCrossFade()).
                        into(bookViewHolder.getBookImageView());
                bookViewHolder.getTitleTextView().setText(bookItem.getTitle());
                bookViewHolder.getRankTextView().setText(String.valueOf(bookItem.getRank()));
                bookViewHolder.getAuthorTextView().setText(bookItem.getAuthor());
                bookViewHolder.getBookTypeTextView().setText(bookItem.getBookType());
                bookViewHolder.getAmazonRatingsView().setText(String.valueOf(bookItem.getAverageRating()));
                break;
            case VIDEO_ITEM_VIEW:
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                videoViewHolder.setDataSet(resources);
                Video videoItem = (Video) resources.get(position);
                GlideApp.with(videoViewHolder.itemView).load(videoItem.getVideoImageURL()).
                        placeholder(new ColorDrawable(Color.rgb(238,238,238))).
                        transition(withCrossFade()).
                        into(videoViewHolder.getVideoImageView());
                videoViewHolder.getVideoChannelTextView().setText(videoItem.getChannelName());
                videoViewHolder.getVideoTitleTextView().setText(videoItem.getTitle());
                videoViewHolder.getRatingBarView().setRating( (float) videoItem.getVideoRating() );
                break;
            case WEBSITE_ITEM_VIEW:
                WebsiteViewHolder websiteViewHolder = (WebsiteViewHolder) holder;
                websiteViewHolder.setDataSet(resources);
                Website current = (Website) resources.get(position);
                GlideApp.with(websiteViewHolder.itemView).load(current.getWebsiteImageURL()).
                        placeholder(new ColorDrawable(Color.rgb(238,238,238))).
                        transition(withCrossFade()).
                        into(websiteViewHolder.getWebImageView());
                websiteViewHolder.getWebsiteNameTextView().setText(current.getTitle());
                websiteViewHolder.getWebsiteTitleTextView().setText(current.getWebsiteSnippet());
            default:
                break;
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        ArrayList<ResourceListItem> resources = super.getResources();

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(resources, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(resources, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        ArrayList<ResourceListItem> resources = super.getResources();
        Context context = super.getContext();
        ResourceListItem resourceItem = resources.get(position);
        String[] resourceDetail = firebaseHelper.getResourceDetail(resourceItem);
        String resourceType = resourceDetail[0];
        String resourceKey = resourceDetail[1];

        firebaseHelper.removeUserResource(resourceItem, resourceType, position);
        SharedPreferencesHelper.removeValue(super.getContext(), resourceKey);
        Toast.makeText(context, context.getString(R.string.item_removed_toast_message)
                , Toast.LENGTH_SHORT).show();
    }
}
