package com.mussieh.recapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mussieh.recapp.R;
import com.mussieh.recapp.adapter.ResourceListAdapter;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.ResourceListItem;

import java.util.ArrayList;

/**
 * Fragment for hosting and displaying recent list data
 * Note: This Fragment is not complete and only mirrors the structure of other Fragments
 * This class does not represent its final content and is only used for testing purposes
 */
public class RecentInteractionFragment extends Fragment {
    private static final String TAG = RecentInteractionFragment.class.getSimpleName();
    public static final String RECENT_INTERACTION_ADAPTER = "recent_interaction_adapter";
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecentInteractionRecylcerView;
    private ResourceListAdapter mAdapter;
    private FirebaseHelper firebaseHelper;

    public RecentInteractionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent, container, false);
        rootView.setTag(TAG);

        // Get a handle to the RecyclerView
        mRecentInteractionRecylcerView = rootView.findViewById(R.id.recent_interaction_recycler_view);

        // Get a handle to the ProgressBar
        mLoadingIndicator = rootView.findViewById(R.id.recent_interaction_indicator);
        // Create an adapter
        mAdapter = new ResourceListAdapter(getContext(), RECENT_INTERACTION_ADAPTER);
        mAdapter.setLoadingIndicator(mLoadingIndicator);

        firebaseHelper = new FirebaseHelper(mAdapter);

        // Connect the adapter with the RecyclerView
        mRecentInteractionRecylcerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager
        mRecentInteractionRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (savedInstanceState != null){
            ArrayList<ResourceListItem> resources = savedInstanceState.
                    getParcelableArrayList("RecentInteractionList");
            mAdapter.setData(resources);
        } else {
            firebaseHelper.fetchUserResources();
        }

        return rootView;
    }

    public static RecentInteractionFragment newInstance() {
        return new RecentInteractionFragment();
    }

    public void loadResources() {
        firebaseHelper.fetchUserResources();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("RecentInteractionList", mAdapter.getResources());
        super.onSaveInstanceState(outState);
    }

}
