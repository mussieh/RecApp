package com.mussieh.recapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.mussieh.recapp.adapter.ScreenChangePagerAdapter;
import com.mussieh.recapp.data.Book;
import com.mussieh.recapp.data.GlideApp;
import com.mussieh.recapp.fragment.BookItemDetailFragment;
import com.mussieh.recapp.fragment.SearchSuggestionFragment;
import com.mussieh.recapp.viewholder.BookViewHolder;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Activity that displays Book item detail content based on screen orientation and size
 * The activity hosts a BookItemDetailFragment
 */
public class BookItemDetailActivity extends AppCompatActivity {
    private static final String TAG = BookItemDetailActivity.class.getSimpleName();
    private static final int BOOK_DETAIL_FRAGMENT_ID = 0;
    private static final int SEARCH_SUGGESTION_FRAGMENT_ID = 1;
    private int previousFragmentItem;
    private RecappActivity recappActivity;
    private LockableViewPager mViewPager;
    private ScreenChangePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookitem_detail);

        mViewPager = findViewById(R.id.bookitem_detail_viewpager);
        mViewPager.setPagingEnabled(false);
        mPagerAdapter = new ScreenChangePagerAdapter
                (getSupportFragmentManager());

//        ImageView toolbarImageView = (ImageView)  findViewById(R.id.toolbar_image_view);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Saves previously loaded data during screen orientation change
        if (savedInstanceState == null) {

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            Book bookItem = new Book();
            fillBookData(getIntent(), bookItem);
            arguments.putParcelable(BookItemDetailFragment.KEY_BOOK_ITEM, bookItem);
            BookItemDetailFragment fragment = BookItemDetailFragment.newInstance();
            fragment.setArguments(arguments);
            mPagerAdapter.addFragment(fragment, "Book Detail Fragment");
            mPagerAdapter.addFragment(SearchSuggestionFragment.newInstance(),
                    "Search Suggestion Fragment");
            previousFragmentItem = BOOK_DETAIL_FRAGMENT_ID;
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.setCurrentItem(BOOK_DETAIL_FRAGMENT_ID);
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
    private void fillBookData(Intent intent, Book bookItem) {
        bookItem.setTitle(intent.getStringExtra(BookViewHolder.KEY_BOOK_TITLE));
        bookItem.setAuthor(intent.getStringExtra(BookViewHolder.KEY_BOOK_AUTHOR));
        bookItem.setAverageRating(intent.getDoubleExtra(BookViewHolder.KEY_BOOK_RATINGS, 0.0));
        bookItem.setDescription(intent.getStringExtra(BookViewHolder.KEY_BOOK_DESCRIPTION));
        bookItem.setImageURL(intent.getStringExtra(BookViewHolder.KEY_BOOK_IMAGE_URL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RecappActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
