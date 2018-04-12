package com.mussieh.recapp;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.mussieh.recapp.adapter.ResourceListAdapter;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.SearchHelper;

/**
 * Author: Mussie Habtemichael
 * The Activity responsible for displaying search results
 */
public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = SearchResultsActivity.class.getSimpleName();
    public static final String SEARCH_LIST_ADAPTER = "search_list_adapter";
    private ProgressBar mLoadingIndicator;
    private RecyclerView mSearchRecylcerView;
    private ResourceListAdapter msearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Get a handle to the RecyclerView
        mSearchRecylcerView = findViewById(R.id.resourcelist_recycler_view);
        // Get a handle to the ProgressBar
        mLoadingIndicator = findViewById(R.id.rlist_loading_indicator);

        // Create and set the adapter
        msearchAdapter = new ResourceListAdapter(getApplicationContext(), SEARCH_LIST_ADAPTER);
        msearchAdapter.setLoadingIndicator(mLoadingIndicator);


        // Connect the adapter with the RecyclerView
        mSearchRecylcerView.setAdapter(msearchAdapter);
        // Give the RecyclerView a default layout manager
        mSearchRecylcerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        final SwipeRefreshLayout mySwipeRefreshLayout = findViewById(R.id.resourcelist_swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        handleIntent(getIntent());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handle the search intent
     * @param intent the search intent
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String queryString = intent.getStringExtra(SearchManager.QUERY);
            setTitle(queryString);
            SearchHelper.searchResources(queryString, msearchAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RecappActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
