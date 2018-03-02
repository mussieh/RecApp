package com.mussieh.recapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mussieh.recapp.R;
import com.mussieh.recapp.data.BookItem;
import com.mussieh.recapp.data.BookListOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mussie on 2/18/2018.
 *
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.WordViewHolder> {

    private List<BookItem> mBookList;
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
        holder.titleEditTextView.setText(current.getTitle());
        holder.rankTextView.setText(String.valueOf(current.getRank()));
        holder.authorTextView.setText(current.getAuthor());
        holder.bookTypeTextView.setText(current.getBookType());
    }

    /**
     * Method to pass data from a data source to an adapter (this adapter).
     * It is used to fill an adapter with data received from the results of a background task
     * @param data the data object to be used by the adapter
     */
    public void setData(List<BookItem> data) {
        Log.d("Set Data", "WTh");
        mBookList = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        public final EditText titleEditTextView;
//        public final ImageView bookImageView;
        public final TextView rankTextView;
        public final TextView authorTextView;
        public final TextView bookTypeTextView;
        final BookListAdapter mAdapter;

        public WordViewHolder(View itemView, BookListAdapter adapter) {
            super(itemView);
            titleEditTextView = (EditText) itemView.findViewById(R.id.book_type_view);
            rankTextView = (TextView) itemView.findViewById(R.id.rank_text_view);
            authorTextView = (TextView) itemView.findViewById(R.id.author_name_view);
            bookTypeTextView = (TextView) itemView.findViewById(R.id.book_type_view);
            this.mAdapter = adapter;
        }

    }
}
