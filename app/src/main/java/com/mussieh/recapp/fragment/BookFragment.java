package com.mussieh.recapp.fragment;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mussieh.recapp.R;
import com.mussieh.recapp.RecappActivity;
import com.mussieh.recapp.adapter.BookListAdapter;
import com.mussieh.recapp.data.BookItem;
import com.mussieh.recapp.data.BookListOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<BookItem>> {

    private static final String TAG = BookFragment.class.getSimpleName();
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecylcerView;
    private BookListAdapter mAdapter;
    private BookListOpenHelper mDB;


    public BookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDB = new BookListOpenHelper(getActivity());

        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().getLoader(0).startLoading();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_book, container, false);
        rootView.setTag(TAG);

        // Get a handle to the RecyclerView
        mRecylcerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        // Get a handle to the ProgressBar
        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        // Create an adapter and supply the data to be displayed
        mAdapter = new BookListAdapter(getContext(), mDB);
        // Connect the adapter with the RecyclerView
        mRecylcerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager
        mRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDB.closeDatabase();
    }

    @Override
    public Loader<List<BookItem>> onCreateLoader(int id, Bundle args) {
        return new LoaderBook(getActivity(), mDB);
    }

    @Override
    public void onLoadFinished(Loader<List<BookItem>> loader, List<BookItem> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<BookItem>> loader) {
    }

    public static class LoaderBook extends AsyncTaskLoader<List<BookItem>> {
        private BookListOpenHelper dbHelper;

        public LoaderBook(Context context, BookListOpenHelper databaseHelper) {
            super(context);
            dbHelper = databaseHelper;
            onForceLoad();
        }

        @Override
        public List<BookItem> loadInBackground() {
            List<BookItem> results;
            Log.d(TAG, "Querying database");
            results = dbHelper.queryBooks(" \"dummy\" ");
            return results;
        }
    }

}
