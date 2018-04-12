package com.mussieh.recapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created on 04/05/2018 by Mussie Habtemichael
 * A viewpager with the capability for having horizontal swipes disabled
 */
public class LockableViewPager extends ViewPager {
    private boolean isPagingEnabled = true;

    /**
     * Construct the LockableViewPager
     * @param context the screen context
     */
    public LockableViewPager(@NonNull Context context) {
        super(context);
    }

    /**
     * Constructs the LockableViewPager with required attributes
     * @param context the screen context
     * @param attrs required attributes
     */
    public LockableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    //todo: override performClick() as well

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    /**
     * Set the paging enabled status
     * @param pagingEnabledStatus the paging boolean status
     */
    public void setPagingEnabled(boolean pagingEnabledStatus) {
        this.isPagingEnabled = pagingEnabledStatus;
    }
}
