package com.mussieh.recapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mussieh.recapp.R;
import com.mussieh.recapp.RecappActivity;
import com.mussieh.recapp.adapter.ResourceListAdapter;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.ResourceListItem;

import java.util.ArrayList;

/**
 * Fragment for hosting and displaying Profile data
 * Note: This Fragment is not complete and only mirrors the structure of other Fragments
 * This class does not represent its final content and is only used for testing purposes
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();
    public static final String PROFILE_ADAPTER = "profile_adapter";
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecentInteractionRecylcerView;
    private ResourceListAdapter mAdapter;
    private FirebaseHelper firebaseHelper;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //todo: change layout name
        View rootView = inflater.inflate(R.layout.resource_list_items, container, false);
        rootView.setTag(TAG);

        hideBackButton();

        // Get a handle to the RecyclerView
        mRecentInteractionRecylcerView = rootView.findViewById(R.id.resourcelist_recycler_view);

        // Get a handle to the ProgressBar
        mLoadingIndicator = rootView.findViewById(R.id.rlist_loading_indicator);
        // Create an adapter
        mAdapter = new ResourceListAdapter(getContext(), PROFILE_ADAPTER);
        mAdapter.setLoadingIndicator(mLoadingIndicator);

        firebaseHelper = new FirebaseHelper(mAdapter);

        // Connect the adapter with the RecyclerView
        mRecentInteractionRecylcerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager
        mRecentInteractionRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final SwipeRefreshLayout mySwipeRefreshLayout = rootView.findViewById(R.id.resourcelist_swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firebaseHelper.fetchUserResources();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        if (savedInstanceState != null){
            ArrayList<ResourceListItem> resources = savedInstanceState.
                    getParcelableArrayList("ProfileData");
            mAdapter.setData(resources);
        } else {
            firebaseHelper.fetchUserResources();
        }

        return rootView;
    }

    private void hideBackButton() {
        if (getActivity() instanceof RecappActivity) {
            android.support.v7.app.ActionBar supportActionBar =
                    ((RecappActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        firebaseHelper.fetchUserResources();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("ProfileData", mAdapter.getResources());
        super.onSaveInstanceState(outState);
    }

}
