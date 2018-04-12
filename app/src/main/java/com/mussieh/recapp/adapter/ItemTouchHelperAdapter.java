package com.mussieh.recapp.adapter;

/**
 * Created by Mussie on 3/27/2018.
 * Defines methods used for helping achieve swipe and remove animations in a list
 */
public interface ItemTouchHelperAdapter {

    /**
     * Switches items based on the two position parameters
     * @param fromPosition the initial position
     * @param toPosition the position to move to
     */
    void onItemMove(int fromPosition, int toPosition);

    /**
     * Removes (dismisses) an item at the given position
     * @param position the position of the item to dismiss
     */
    void onItemDismiss(int position);
}
