package com.mussieh.recapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mussieh.recapp.R;
import com.mussieh.recapp.adapter.BookListAdapter;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {

    private static final String TAG = "BookFragment";
    private final LinkedList<String> mBookList = new LinkedList<>();
    private RecyclerView mRecylcerView;
    private BookListAdapter mAdapter;
    private int mCount = 0;


    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_book, container, false);
        rootView.setTag(TAG);

        for (int i = 0; i < 100; i++) {
            mBookList.addLast("Book " + mCount++);
            Log.d("BookList", mBookList.getLast());
        }
        // Get a handle to the RecyclerView
        mRecylcerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed
        mAdapter = new BookListAdapter(getContext(), mBookList);
        // Connect the adapter with the RecyclerView
        mRecylcerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager
        mRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Inflate the layout for this fragment

        return rootView;
    }

}
