package com.mussieh.recapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.youtube.player.YouTubePlayer;
import com.mussieh.recapp.R;
import com.mussieh.recapp.adapter.VideoListAdapter;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.SharedPreferencesHelper;

import java.util.ArrayList;

/**
 * Fragment for hosting and displaying Video data
 * Note: This Fragment is functional but not complete
 */
public class VideoFragment extends Fragment {
    private static final String TAG = VideoFragment.class.getSimpleName();
    private ProgressBar mLoadingIndicator;
    private RecyclerView mVideoRecylcerView;
    private VideoListAdapter mVideoListAdapter;
    private YouTubePlayer YPlayer;
    private static final String YoutubeDeveloperKey = "xyz";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private FirebaseHelper firebaseHelper;

    // Required empty public constructor
    public VideoFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        rootView.setTag(TAG);

        // Get a handle to the RecyclerView
        mVideoRecylcerView = rootView.findViewById(R.id.video_recycler_view);
        // Get a handle to the ProgressBar
        mLoadingIndicator = rootView.findViewById(R.id.pb_video_loading_indicator);
        // Create an adapter
        mVideoListAdapter = new VideoListAdapter(getContext());
        mVideoListAdapter.setLoadingIndicator(mLoadingIndicator);

        firebaseHelper = new FirebaseHelper(mVideoListAdapter);

        // Connect the adapter with the RecyclerView
        mVideoRecylcerView.setAdapter(mVideoListAdapter);
        // Give the RecyclerView a default layout manager
        mVideoRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final SwipeRefreshLayout mySwipeRefreshLayout = rootView.findViewById(R.id.video_swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPreferencesHelper.loadUserChoice(getContext(), firebaseHelper,
                        FirebaseHelper.KEY_VIDEOS,
                        FirebaseHelper.KEY_VIDEO_RANK);
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        // Saves previously loaded data during screen orientation change
        if (savedInstanceState != null){
            ArrayList<ResourceListItem> videos = savedInstanceState.getParcelableArrayList("Videos");
            mVideoListAdapter.setData(videos);
        } else {
            String userChoice = SharedPreferencesHelper.getValue(getContext(),
                    SettingsFragment.KEY_SUMMARY,
                    "None");
            firebaseHelper.fetchAppResources(userChoice, FirebaseHelper.KEY_VIDEOS,
                    FirebaseHelper.KEY_VIDEO_RANK);
        }

        Log.d(TAG, "Returning rootview");


        return rootView;
    }

    /**
     * Returns a new instance of VideoFragment
     * @return a VideoFragment
     */
    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
        public void onResume() {
        super.onResume();
        SharedPreferencesHelper.loadUserChoice(getContext(), firebaseHelper,
                FirebaseHelper.KEY_VIDEOS,
                FirebaseHelper.KEY_VIDEO_RANK);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("Videos", mVideoListAdapter.getResources());
        super.onSaveInstanceState(outState);
    }
}
