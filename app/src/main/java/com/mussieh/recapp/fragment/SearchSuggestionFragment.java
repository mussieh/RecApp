package com.mussieh.recapp.fragment;


import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mussieh.recapp.R;
import com.mussieh.recapp.SearchResultsActivity;
import com.mussieh.recapp.adapter.SearchSuggestionAdapter;
import com.mussieh.recapp.data.SearchHelper;

/**
 * Fragment for hosting and displaying search suggestion data
 * Note: This Fragment is functional but not complete
 */
public class SearchSuggestionFragment extends Fragment {
    private static final String TAG = SearchSuggestionFragment.class.getSimpleName();
    private ProgressBar mLoadingIndicator;
    private RecyclerView mSearchRecylcerView;
    private SearchSuggestionAdapter mSearchAdapter;

    // Required empty public constructor
    public SearchSuggestionFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //todo: change fragment_search layout and add ImageView
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        rootView.setTag(TAG);

        setHasOptionsMenu(true);

        Log.d(TAG, "CreateView");

        // Get a handle to the RecyclerView
        mSearchRecylcerView = rootView.findViewById(R.id.search_suggestion_recycler_view);

        mSearchAdapter = new SearchSuggestionAdapter(getContext());

        // Connect the adapter with the RecyclerView
        mSearchRecylcerView.setAdapter(mSearchAdapter);
        // Give the RecyclerView a default layout manager
        mSearchRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Not used for now until class is complete

//        if (savedInstanceState != null){
//            ArrayList<ResourceListItem> resources = savedInstanceState.
//                    getParcelableArrayList("SearchData");
//            mSearchAdapter.setData(resources);
//        } else {
//            FirebaseHelper.setResourceListAdapter(mAdapter);
//            FirebaseHelper.fetchUserResources();
//        }

        return rootView;
    }

    /**
     * Returns an instance of SearchSuggestionFragment
     * @return the SearchSuggestionFragment
     */
    public static SearchSuggestionFragment newInstance() {
        return new SearchSuggestionFragment();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        Log.d(TAG, "onCreateOptions");

        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchMenuItem.expandActionView();

        if (getActivity() != null && getActivity().getComponentName() != null ) {
            // Associate searchable configuration with the SearchView
            SearchManager searchManager = (SearchManager) getActivity().
                    getSystemService(Context.SEARCH_SERVICE);
            SearchableInfo searchableInfo = searchManager.
                    getSearchableInfo(getActivity().getComponentName());

            if (searchableInfo != null) {
                searchView.setSearchableInfo(searchableInfo);
                searchView.setIconified(false);
                searchView.setQueryHint(getResources().getString(R.string.search_hint));

                final Intent searchIntent = new Intent(getActivity(), SearchResultsActivity.class).
                        setAction(Intent.ACTION_SEARCH);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String queryString) {
                        searchIntent.putExtra(SearchManager.QUERY, queryString);
                        startActivity(searchIntent);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Log.d(TAG, newText);
                        SearchHelper.searchResources(newText, mSearchAdapter);
                        return true;
                    }
                });
            }
        }
    }
}
