package com.mussieh.recapp.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.mussieh.recapp.BookItemDetailActivity;
import com.mussieh.recapp.R;
import com.mussieh.recapp.RecappActivity;
import com.mussieh.recapp.adapter.BookListAdapter;
import com.mussieh.recapp.adapter.ItemListAdapter;
import com.mussieh.recapp.adapter.ResourceListAdapter;
import com.mussieh.recapp.data.Book;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.fragment.BookItemDetailFragment;

import java.util.ArrayList;

/**
 * Created by Mussie on 3/22/2018.
 * ViewHolder with references to Book item attributes, as well as Book item methods
 */
public class BookViewHolder extends ItemViewHolder {
    private static final String TAG = BookViewHolder.class.getSimpleName();
    public static final String KEY_BOOK_TITLE = "book_title";
    public static final String KEY_BOOK_AUTHOR = "book_author";
    public static final String KEY_BOOK_RATINGS = "book_ratings";
    public static final String KEY_BOOK_DESCRIPTION = "book_description";
    public static final String KEY_BOOK_IMAGE_URL = "book_url";

    private boolean mTwoPane;
    private CardView bookCardView;
    private TextView titleTextView;
    private ImageView bookImageView;
    private TextView rankTextView;
    private TextView authorTextView;
    private TextView bookTypeTextView;
    private TextView amazonRatingsView;
    private ImageButton bookMenuButton;
    private ItemListAdapter mAdapter;
    private ArrayList<ResourceListItem> mBookList;

    /**
     * Constructs a BookViewHolder
     * @param itemView the Book item view
     * @param adapter the resource adapter
     */
    public BookViewHolder(final View itemView, ItemListAdapter adapter) {
        super(itemView, adapter);
        this.mAdapter = adapter;
        mBookList = mAdapter.getResources();
        bookCardView = itemView.findViewById(R.id.book_cardview);
        titleTextView = itemView.findViewById(R.id.book_title_view);
        authorTextView = itemView.findViewById(R.id.author_name_view);
        bookTypeTextView = itemView.findViewById(R.id.book_type_view);
        bookImageView = itemView.findViewById(R.id.book_image_view);
        rankTextView = itemView.findViewById(R.id.rank_text_view);
        amazonRatingsView = itemView.findViewById(R.id.tv_amazon_rating);
        bookMenuButton = itemView.findViewById(R.id.book_image_button);
        bookMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookViewHolder.super.showPopupMenu(bookMenuButton, getLayoutPosition(), mBookList,
                        FirebaseHelper.KEY_BOOKS);
            }
        });

        setupBookCardView();

        // todo: refactor to another method
        if (mAdapter instanceof ResourceListAdapter) {
            rankTextView.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)
                    bookCardView.getLayoutParams();
            int bookCardViewTopMarginPx = mAdapter.getContext().getResources().
                    getDimensionPixelSize(R.dimen.book_cardview_top_margin);
            int bookCardViewBottomMarginPx = mAdapter.getContext().getResources().
                    getDimensionPixelSize(R.dimen.book_cardview_bottom_margin);
            layoutParams.setMargins(layoutParams.leftMargin, bookCardViewTopMarginPx,
                    layoutParams.rightMargin, bookCardViewBottomMarginPx);
            bookCardView.setLayoutParams(layoutParams);
        }
    }

    private void setupBookCardView() {
        // todo: refactor to another method
        if (mAdapter instanceof BookListAdapter) {
            final RecappActivity parentActivity = (RecappActivity) mAdapter.getContext();
            mTwoPane = ((BookListAdapter) mAdapter).getTwoPaneState();

            bookCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book bookItem = (Book) mBookList.get(getLayoutPosition());
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(BookItemDetailFragment.KEY_BOOK_ITEM, bookItem);

                    if (mTwoPane) {
                        ActionBar supportActionBar =  parentActivity.getSupportActionBar();
                        if (supportActionBar != null) {
                            supportActionBar.setDisplayHomeAsUpEnabled(false);
                        }
                        BookItemDetailFragment bookItemDetailFragment =
                                BookItemDetailFragment.newInstance();
                        bookItemDetailFragment.setArguments(arguments);
                        parentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_book_detail_container,
                                        bookItemDetailFragment)
                                .commit();
                        Log.d(TAG, "Book Clicked");
                    } else {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, BookItemDetailActivity.class);
                        setIntentExtras(intent, bookItem);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    /**
     * Note: The easier way to transfer objects between activities is through
     * Parcelables (putExtra (key, parcelable)) but there is a problem in this process and the
     * reason for this manual parceling method.
     * (Book class implements parcelable so the problem is probably related
     * to missing references inside bookItem as some items are null while others retain their values
     * during the parceling process
     * Even though the 'Book' class implements both 'in' and 'out' Parcelable parameters,
     * the issue still remains
     * @param intent the current intent
     * @param bookItem the Book object
     */
    private void setIntentExtras(Intent intent, Book bookItem) {
        intent.putExtra(KEY_BOOK_TITLE, bookItem.getTitle());
        intent.putExtra(KEY_BOOK_AUTHOR, bookItem.getAuthor());
        intent.putExtra(KEY_BOOK_DESCRIPTION, bookItem.getDescription());
        intent.putExtra(KEY_BOOK_RATINGS, bookItem.getAverageRating());
        intent.putExtra(KEY_BOOK_IMAGE_URL, bookItem.getImageURL());
    }

    /**
     * Sets the resources for this view holder
     * @param resources the resource items
     */
    public void setDataSet(final ArrayList<ResourceListItem> resources) {
        mBookList = resources;
    }

    /**
     * Get the book title TextView
     * @return book title TextView
     */
    public TextView getTitleTextView() {
        return titleTextView;
    }

    /**
     * Get the book ImageView
     * @return the book ImageView
     */
    public ImageView getBookImageView() {
        return bookImageView;
    }

    /**
     * Get the book rank TextView
     * @return the book rank TextView
     */
    public TextView getRankTextView() {
        return rankTextView;
    }

    /**
     * Get the book author TextView
     * @return the book author TextView
     */
    public TextView getAuthorTextView() {
        return authorTextView;
    }

    /**
     * Get the book type TextView
     * @return the book type TextView
     */
    public TextView getBookTypeTextView() {
        return bookTypeTextView;
    }

    /**
     * Get the book rating TextView
     * @return the book rating TextView
     */
    public TextView getAmazonRatingsView() {
        return amazonRatingsView;
    }

    /**
     * Get the book menu button
     * @return the book menu button
     */
    public ImageButton getBookMenuButton() {
        return bookMenuButton;
    }
}
