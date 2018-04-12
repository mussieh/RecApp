package com.mussieh.recapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mussieh.recapp.R;
import com.mussieh.recapp.adapter.ItemListAdapter;

/**
 * Created by Mussie Habtemichael on 3/26/2018.
 * ViewHolder with SearchDisplayItem methods and attributes
 */
public class SearchDisplayViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = SearchDisplayViewHolder.class.getSimpleName();

    private TextView searchResultsTextView;
    private Button filterOptionButton;

    /**
     * Constructs the SearchDisplayViewHolder
     * @param itemView the item view
     * @param adapter the resource list adapter
     */
    public SearchDisplayViewHolder(final View itemView, ItemListAdapter adapter) {
        super(itemView);
        searchResultsTextView = itemView.findViewById(R.id.search_results_tv);
        filterOptionButton = itemView.findViewById(R.id.search_filter_button);
    }

    /**
     * Returns the search results TextView
     * @return the search results TextView
     */
    public TextView getSearchResultsTextView() {
        return searchResultsTextView;
    }

    /**
     * Returns the filter options ImageButton
     * @return the filter options ImageButton
     */
    public Button getFilterOptionImageButton() {
        return filterOptionButton;
    }

}
