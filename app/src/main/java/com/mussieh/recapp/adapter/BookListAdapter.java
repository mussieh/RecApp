package com.mussieh.recapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mussieh.recapp.R;
import com.mussieh.recapp.data.Book;
import com.mussieh.recapp.data.GlideApp;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.viewholder.BookViewHolder;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Mussie on 2/18/2018.
 * Adapter for handling Book data binding through a BookViewHolder
 */
public class BookListAdapter extends ItemListAdapter  {
    private static final String TAG = BookListAdapter.class.getSimpleName();
    private boolean mTwoPane;

    /**
     * Construct the adapter from the given context
     * @param context the screen context
     */
    public BookListAdapter(Context context) {
        super(context);
    }


    @Override
    @NonNull
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = super.getLayoutInflater();
        View mItemView = inflater.inflate(R.layout.booklist_item, parent, false);
        return new BookViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArrayList<ResourceListItem> mBookList = super.getResources();
        BookViewHolder bookViewHolder = (BookViewHolder) holder;
        bookViewHolder.setDataSet(mBookList);
        Book current = (Book) mBookList.get(position);
        GlideApp.with(bookViewHolder.itemView).load(current.getImageURL()).
                placeholder(new ColorDrawable(Color.rgb(238,238,238))).
                transition(withCrossFade()).
                into(bookViewHolder.getBookImageView());
        bookViewHolder.getTitleTextView().setText(current.getTitle());
        bookViewHolder.getRankTextView().setText(String.valueOf(current.getRank()));
        bookViewHolder.getAuthorTextView().setText(current.getAuthor());
        bookViewHolder.getBookTypeTextView().setText(current.getBookType());
        bookViewHolder.getAmazonRatingsView().setText(String.valueOf(current.getAverageRating()));
    }

    @Override
    public String getAdapterName() {
        return TAG;
    }

    /**
     * Sets the two pane navigation state
     * @param state the boolean two pane state
     */
    public void setTwoPaneState(boolean state) {
        mTwoPane = state;
    }

    /**
     * Checks if the application supports two pane navigation (tablet mode)
     * @return the two pane boolean state
     */
    public boolean getTwoPaneState() {
        return mTwoPane;
    }
}
