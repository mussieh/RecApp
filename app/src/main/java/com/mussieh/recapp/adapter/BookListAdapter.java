package com.mussieh.recapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mussieh.recapp.R;
import com.mussieh.recapp.data.BookItem;
import com.mussieh.recapp.data.BookListOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mussie on 2/18/2018.
 *
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.WordViewHolder> {

    private final List<BookItem> mBookList;
    private LayoutInflater mInflater;
    private BookListOpenHelper mDB;

    public BookListAdapter(Context context, BookListOpenHelper db) {
        mInflater = LayoutInflater.from(context);
        mDB = db;
        mBookList = new ArrayList<>();
    }


    @Override
    public BookListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(BookListAdapter.WordViewHolder holder, int position) {
        BookItem current = mBookList.get(position);
        holder.wordItemView.setText(current.getAuthor());
    }

    /**
     * Method to pass data from a data source to an adapter (this adapter).
     * It is used to fill an adapter with data received from the results of a background task
     * @param data the data object to be used by the adapter
     */
    public void setData(List<BookItem> data) {
        Log.d("Set Data", "WTh");
        for (BookItem item : data) {
            mBookList.add(item);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        public final TextView wordItemView;
        final BookListAdapter mAdapter;

        public WordViewHolder(View itemView, BookListAdapter adapter) {
            super(itemView);
            wordItemView = (TextView) itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }

    }
}
