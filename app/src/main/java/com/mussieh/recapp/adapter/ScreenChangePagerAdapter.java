package com.mussieh.recapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mussieh.recapp.fragment.BookFragment;
import com.mussieh.recapp.fragment.VideoFragment;
import com.mussieh.recapp.fragment.WebsiteFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Mussie Habtemichael
 * Date: 02/18/2018
 * An adapter that contains Fragments that are manipulated with a ViewPager
 */
public class ScreenChangePagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    /**
     * Construct the ScreenChangePagerAdapter
     * @param fragmentManager the FragmentManger to use
     */
    public ScreenChangePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Adds a Fragment to the Fragment list
     * @param fragment the Fragment to add
     * @param title the title of the Fragment to add
     */
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    /**
     * Sets a Fragment to the specified position in the Fragment list
     * @param position the position to set a Fragment to
     * @param fragment the fragment to be used
     */
    public void setFragment(int position, Fragment fragment) {
        mFragmentList.set(position, fragment);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
         return mFragmentList.size();
    }
}
