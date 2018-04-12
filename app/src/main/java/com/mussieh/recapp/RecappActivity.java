package com.mussieh.recapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mussieh.recapp.adapter.ScreenChangePagerAdapter;
import com.mussieh.recapp.fragment.MediaFragment;
import com.mussieh.recapp.fragment.ProfileFragment;
import com.mussieh.recapp.fragment.RecentInteractionFragment;
import com.mussieh.recapp.fragment.ResourceListFragment;
import com.mussieh.recapp.fragment.SearchSuggestionFragment;
import com.mussieh.recapp.fragment.SettingsFragment;
import com.mussieh.recapp.utilities.BottomNavigationViewHelper;

/**
 * Main activity of Recapp
 * The activity is responsible for changing the application's content
 * through the ViewPager that swipes content with Fragment change.
 */
public class RecappActivity extends AppCompatActivity {

    private static final String TAG = RecappActivity.class.getSimpleName();
    private int previousFragmentItem;
    private BottomNavigationView bottomNavigationView;
    private LockableViewPager mViewPager;
    private ScreenChangePagerAdapter mPagerAdapter;
    private RecentInteractionFragment recentInteractionFragment;
    private MediaFragment mediaFragment;
    private ResourceListFragment myListFragment;
    private ProfileFragment profileFragment;
    private SettingsFragment settingsFragment;
    private SearchSuggestionFragment searchSuggestionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapp);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting a listener for clicks
        mViewPager = findViewById(R.id.main_viewpager);
        mViewPager.setPagingEnabled(false);
        setupViewPager(mViewPager);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(getBottomNavigationListener());
        bottomNavigationView.setSelectedItemId(R.id.action_media);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    /**
     * Setup the main viewpager that manages hosted Fragments
     * @param viewPager the main viewpager
     */
    private void setupViewPager(final ViewPager viewPager) {

        recentInteractionFragment = RecentInteractionFragment.newInstance();
        mediaFragment = MediaFragment.newInstance();
        myListFragment = ResourceListFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();
        settingsFragment = SettingsFragment.newInstance();
        searchSuggestionFragment = SearchSuggestionFragment.newInstance();

        mPagerAdapter = new ScreenChangePagerAdapter
                (getSupportFragmentManager());
        // todo: change the 'title' strings here to constants at the top
        mPagerAdapter.addFragment(recentInteractionFragment, "Recent Fragment");
        mPagerAdapter.addFragment(mediaFragment, "Media Fragment");
        mPagerAdapter.addFragment(myListFragment, "MyList Fragment");
        mPagerAdapter.addFragment(profileFragment, "Profile Fragment");
        mPagerAdapter.addFragment(settingsFragment, "Settings Fragment");
        mPagerAdapter.addFragment(searchSuggestionFragment, "Search Fragment");
        viewPager.setAdapter(mPagerAdapter);
    }

    /**
     * Attaching and returning the BottomNavigationView listener
     * The navigation view's listener fires the event that lets the ViewPager change main page
     * Fragments
     * @return the BottomNavigationView.OnNavigationItemSelectedListener
     */
    private BottomNavigationView.OnNavigationItemSelectedListener getBottomNavigationListener() {
        return new BottomNavigationView.
                OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_recent:
                        mViewPager.setCurrentItem(0, false);
                        // not done yet
//                        recentInteractionFragment.loadResources();
                        previousFragmentItem = 0;
                        return true;
                    case R.id.action_media:
                        mViewPager.setCurrentItem(1, false);
                        previousFragmentItem = 1;
                        return true;
                    case R.id.action_mylist:
                        mViewPager.setCurrentItem(2, false);
                        myListFragment.loadResources();
                        previousFragmentItem = 2;
                        return true;
                    case R.id.action_profile:
                        mViewPager.setCurrentItem(3, false);
                        previousFragmentItem = 3;
                        return true;
                    case R.id.action_settings:
                        mViewPager.setCurrentItem(4, false);
                        previousFragmentItem = 4;
                        return true;
                }
                return false;
            }
        };
    }

    /**
     * Gets the lockable viewpager
     * @return lockable viewpager
     */
    public LockableViewPager getLockableViewPager() {
        return mViewPager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Log.d(TAG, "collapsed" + previousFragmentItem);
                mViewPager.setCurrentItem(previousFragmentItem, false);
                return true;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        mViewPager.setCurrentItem(previousFragmentItem, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {
            Log.d(TAG, "searchPressed");
            mViewPager.setCurrentItem(5, false);
            return true;
        } else if (id == android.R.id.home) {
            Log.d(TAG, "homeAsUp");
            mViewPager.setCurrentItem(previousFragmentItem, false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
