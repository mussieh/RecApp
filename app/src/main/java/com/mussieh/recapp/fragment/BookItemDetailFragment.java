package com.mussieh.recapp.fragment;


import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mussieh.recapp.R;
import com.mussieh.recapp.RecappActivity;
import com.mussieh.recapp.data.Book;
import com.mussieh.recapp.data.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Fragment for hosting and displaying Book Item Detail data
 * Note: This Fragment is functional but not complete
 */
public class BookItemDetailFragment extends Fragment {

    private static final String TAG = BookItemDetailFragment.class.getSimpleName();
    public static final String KEY_BOOK_ITEM = "book_item";
    private Book bookItem;
    private TextView bookTitleTextview;
    private TextView bookAuthorTextview;
    private RatingBar bookRatingBar;
    private ImageView bookImageView;
    private TextView bookDescriptionTextview;

    // Required empty public constructor
    public BookItemDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(KEY_BOOK_ITEM)) {
            bookItem = getArguments().getParcelable(KEY_BOOK_ITEM);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);

        assert rootView != null;
        CollapsingToolbarLayout appBarLayout = rootView.findViewById(R.id.book_detail_toolbar_layout);
        if (appBarLayout != null && bookItem != null) {
            appBarLayout.setTitle(bookItem.getTitle());
        }

        android.support.v7.widget.Toolbar toolbar = rootView.findViewById(R.id.book_detail_toolbar);
        AppCompatActivity parentActivity = ( (AppCompatActivity) getActivity() );
        parentActivity.setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar supportActionBar = parentActivity.getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        setHasOptionsMenu(true);

        if (bookItem != null) {
            Log.d(TAG, bookItem.toString());
            bookTitleTextview = rootView.findViewById(R.id.book_detail_title_tv);
            bookAuthorTextview = rootView.findViewById(R.id.book_detail_author_tv);
            bookDescriptionTextview = rootView.
                    findViewById(R.id.book_detail_description_tv);
            bookRatingBar = rootView.findViewById(R.id.book_detail_ratings_bar);
            bookImageView = rootView.findViewById(R.id.book_detail_image_view);

            bookTitleTextview.setText(bookItem.getTitle());
            bookAuthorTextview.setText(bookItem.getAuthor());
            bookDescriptionTextview.setText(bookItem.getDescription());

            GlideApp.with(rootView).load(bookItem.getImageURL()).
                    placeholder(new ColorDrawable(Color.rgb(238,238,238))).
                    transition(withCrossFade()).
                    into(bookImageView);
        }

        return rootView;
    }


    /**
     * Returns a new instance of BookItemDetailFragment
     * @return a BookItemDetailFragment
     */
    public static BookItemDetailFragment newInstance() {
        return new BookItemDetailFragment();
    }

}
