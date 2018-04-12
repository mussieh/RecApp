package com.mussieh.recapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mussieh.recapp.R;

/**
 * Created by Mussie Habtemichael on 4/05/2018.
 * ViewHolder with SearchSuggestion item view methods and attributes
 */
public class SearchSuggestionViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = SearchSuggestionViewHolder.class.getSimpleName();
    private ImageView searchSuggestionImageView;
    private TextView searchSuggestionTitleTextView;
    private TextView searchSuggestionDetailTextView;

    public SearchSuggestionViewHolder(View itemView) {
        super(itemView);
        this.searchSuggestionTitleTextView = itemView.findViewById(
                R.id.search_suggestion_title_textview);
        this.searchSuggestionDetailTextView = itemView.findViewById(
                R.id.search_suggestion_description_textview);
    }

    /**
     * Returns the search suggestion ImageView
     * @return the search suggestion ImageView
     */
    public ImageView getSearchImageView() {
        return searchSuggestionImageView;
    }

    /**
     * Returns the search suggestion title TextView
     * @return the search suggestion title TextView
     */
    public TextView getTitleTextView() {
        return searchSuggestionTitleTextView;
    }

    /**
     * Returns the search suggestion description TextView
     * @return the search suggestion description TextView
     */
    public TextView getDescriptionTextView() {
        return searchSuggestionDetailTextView;
    }
}
