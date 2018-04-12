package com.mussieh.recapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mussieh.recapp.R;
import com.mussieh.recapp.adapter.WebsiteListAdapter;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.SharedPreferencesHelper;

import java.util.ArrayList;

/**
 * Fragment for hosting and displaying Website data
 * Note: This Fragment is functional but not complete
 */
public class WebsiteFragment extends Fragment {

    private static final String TAG = WebsiteFragment.class.getSimpleName();
    private ProgressBar mLoadingIndicator;
    private RecyclerView mWebsiteRecylcerView;
    private WebsiteListAdapter mWebsiteListAdapter;
    private FirebaseHelper firebaseHelper;

    // Required empty public constructor
    public WebsiteFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_website, container, false);
        rootView.setTag(TAG);

        // Get a handle to the RecyclerView
        mWebsiteRecylcerView = rootView.findViewById(R.id.website_recycler_view);
        // Get a handle to the ProgressBar
        mLoadingIndicator = rootView.findViewById(R.id.pb_website_loading_indicator);
        // Create an adapter
        mWebsiteListAdapter = new WebsiteListAdapter(getContext());
        mWebsiteListAdapter.setLoadingIndicator(mLoadingIndicator);

        firebaseHelper = new FirebaseHelper(mWebsiteListAdapter);

        // Connect the adapter with the RecyclerView
        mWebsiteRecylcerView.setAdapter(mWebsiteListAdapter);
        // Give the RecyclerView a default layout manager
        mWebsiteRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final SwipeRefreshLayout mySwipeRefreshLayout = rootView.findViewById(R.id.website_swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPreferencesHelper.loadUserChoice(getContext(), firebaseHelper,
                        FirebaseHelper.KEY_WEBSITES,
                        FirebaseHelper.KEY_WEBSITE_RANK);
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        // Saves previously loaded data during screen orientation change
        if (savedInstanceState != null){
            ArrayList<ResourceListItem> websites = savedInstanceState.getParcelableArrayList("Websites");
            mWebsiteListAdapter.setData(websites);
        } else {
            String userChoice = SharedPreferencesHelper.getValue(getContext(),
                    SettingsFragment.KEY_SUMMARY,
                    "None");
            firebaseHelper.fetchAppResources(userChoice, FirebaseHelper.KEY_WEBSITES,
                    FirebaseHelper.KEY_WEBSITE_RANK);
        }

        Log.d(TAG, "Returning rootview");
        return rootView;
    }

    /**
     * Returns a new instance of WebsiteFragment
     * @return a WebsiteFragment
     */
    public static WebsiteFragment newInstance() {
        return new WebsiteFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferencesHelper.loadUserChoice(getContext(), firebaseHelper,
                FirebaseHelper.KEY_WEBSITES,
                FirebaseHelper.KEY_WEBSITE_RANK);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("Websites", mWebsiteListAdapter.getResources());
        super.onSaveInstanceState(outState);
    }

}
