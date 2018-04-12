package com.mussieh.recapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mussieh.recapp.R;
import com.mussieh.recapp.adapter.ItemTouchHelperCallback;
import com.mussieh.recapp.adapter.ResourceListAdapter;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.ResourceListItem;

import java.util.ArrayList;

/**
 * Fragment for hosting and displaying resource list data (with multiple types)
 * Note: This Fragment is functional but not complete
 */
public class ResourceListFragment extends Fragment {
    private static final String TAG = ResourceListFragment.class.getSimpleName();
    public static final String PERSONAL_LIST_ADAPTER = "personal_list_adapter";
    private ProgressBar mLoadingIndicator;
    private RecyclerView mResourceListRecylcerView;
    private ResourceListAdapter mAdapter;
    private FirebaseHelper firebaseHelper;

    public ResourceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.resource_list_items, container, false);
        rootView.setTag(TAG);

        // Get a handle to the RecyclerView
        mResourceListRecylcerView = rootView.findViewById(R.id.resourcelist_recycler_view);

        // Get a handle to the ProgressBar
        mLoadingIndicator = rootView.findViewById(R.id.rlist_loading_indicator);
        // Create an adapter
        mAdapter = new ResourceListAdapter(getContext(), PERSONAL_LIST_ADAPTER);
        mAdapter.setLoadingIndicator(mLoadingIndicator);

        firebaseHelper = mAdapter.getFirebaseHelper();

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mResourceListRecylcerView);

        // Connect the adapter with the RecyclerView
        mResourceListRecylcerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager
        mResourceListRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
                    getParcelableArrayList("ResourceList");
            mAdapter.setData(resources);
        } else {
            firebaseHelper.fetchUserResources();
        }

        return rootView;
    }

    /**
     * Get a ResourceListFragment instance
     * @return the ResourceListFragment instance
     */
    public static ResourceListFragment newInstance() {
        return new ResourceListFragment();
    }

    /**
     * Load resource list items into adapter
     */
    public void loadResources() {
        firebaseHelper.fetchUserResources();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("ResourceList", mAdapter.getResources());
        super.onSaveInstanceState(outState);
    }

}
