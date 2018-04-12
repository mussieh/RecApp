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

import com.mussieh.recapp.R;
import com.mussieh.recapp.adapter.BookListAdapter;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.SharedPreferencesHelper;

import java.util.ArrayList;

/**
 * Fragment for hosting and displaying Book data
 * Note: This Fragment is functional but not complete
 */
public class BookFragment extends Fragment {

    private static final String TAG = BookFragment.class.getSimpleName();
    private ProgressBar mLoadingIndicator;
    private RecyclerView mBookRecylcerView;
    private BookListAdapter mBookAdapter;
    private FirebaseHelper firebaseHelper;

    // Required empty public constructor
    public BookFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_book, container, false);
        rootView.setTag(TAG);


        // Get a handle to the RecyclerView
        mBookRecylcerView = rootView.findViewById(R.id.book_recycler_view);

        // Get a handle to the ProgressBar
        mLoadingIndicator = rootView.findViewById(R.id.pb_book_loading_indicator);

        // Create an adapter
        mBookAdapter = new BookListAdapter(getActivity());
        mBookAdapter.setLoadingIndicator(mLoadingIndicator);

        firebaseHelper = new FirebaseHelper(mBookAdapter);

        if (rootView.findViewById(R.id.fragment_book_detail_container) != null) {

            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            Log.d(TAG, "TABLET MODE");
            mBookAdapter.setTwoPaneState(true);
        }

        // Connect the adapter with the RecyclerView
        mBookRecylcerView.setAdapter(mBookAdapter);

        // Give the RecyclerView a default layout manager
        mBookRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final SwipeRefreshLayout mySwipeRefreshLayout = rootView.findViewById(R.id.book_swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPreferencesHelper.loadUserChoice(getContext(), firebaseHelper,
                        FirebaseHelper.KEY_BOOKS,
                        FirebaseHelper.KEY_BOOK_RANK);
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        // Saves previously loaded data during screen orientation change
        if (savedInstanceState != null){
            ArrayList<ResourceListItem> books = savedInstanceState.getParcelableArrayList("Books");
            mBookAdapter.setData(books);
        } else {
            String userChoice = SharedPreferencesHelper.getValue(getContext(),
                    SettingsFragment.KEY_SUMMARY,
                    "None");
            firebaseHelper.fetchAppResources(userChoice, FirebaseHelper.KEY_BOOKS,
                    FirebaseHelper.KEY_BOOK_RANK);
        }

        Log.d(TAG, "Returning rootview");

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Returns a new instance of BookFragment
     * @return a BookFragment
     */
    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferencesHelper.loadUserChoice(getContext(), firebaseHelper,
                FirebaseHelper.KEY_BOOKS,
                FirebaseHelper.KEY_BOOK_RANK);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("Books", mBookAdapter.getResources());
        super.onSaveInstanceState(outState);
    }

}
