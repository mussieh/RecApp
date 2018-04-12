package com.mussieh.recapp.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mussieh.recapp.R;
import com.mussieh.recapp.RecappActivity;
import com.mussieh.recapp.adapter.ScreenChangePagerAdapter;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.SharedPreferencesHelper;

/**
 * Fragment for hosting and displaying Book, Video, and Website data
 * Note: This Fragment is functional but not complete
 */
public class MediaFragment extends Fragment {
    private static final String TAG = MediaFragment.class.getSimpleName();
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    // Required empty public constructor
    public MediaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_media, container, false);
        rootView.setTag(TAG);

        hideBackButton();

        tabLayout = rootView.findViewById(R.id.media_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.book_tab_label));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.video_tab_label));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.website_tab_label));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Setting a listener for clicks
        mViewPager = rootView.findViewById(R.id.media_viewpager);
        setupViewPager(mViewPager);

        return rootView;
    }

    /**
     * Hides the back button
     */
    private void hideBackButton() {
        if (getActivity() instanceof RecappActivity) {
            android.support.v7.app.ActionBar supportActionBar =
                    ((RecappActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    /**
     * Setup the view pager
     * @param viewPager the viewpager to return
     */
    private void setupViewPager(final ViewPager viewPager) {

        Fragment bookFragment = BookFragment.newInstance();
        Fragment videoFragment = VideoFragment.newInstance();
        Fragment websiteFragment = WebsiteFragment.newInstance();

        final ScreenChangePagerAdapter adapter = new ScreenChangePagerAdapter
                (getChildFragmentManager());
        adapter.addFragment(bookFragment, "Book Fragment");
        adapter.addFragment(videoFragment, "Video Fragment");
        adapter.addFragment(websiteFragment, "Website Fragment");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.d("Fragment: ", String.valueOf(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    /**
     * Returns a new instance of MediaFragment
     * @return a MediaFragment
     */
    public static MediaFragment newInstance() {
        return new MediaFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
