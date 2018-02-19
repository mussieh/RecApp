package com.mussieh.recapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mussieh.recapp.fragment.BookFragment;
import com.mussieh.recapp.fragment.VideoFragment;
import com.mussieh.recapp.fragment.WebsiteFragment;

/**
 * author: Mussie Habtemichael
 * Version: 02/18/2018
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BookFragment();
            case 1:
                return new VideoFragment();
            case 2:
                return new WebsiteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
         return mNumOfTabs;
    }
}
