package com.mussieh.recapp.viewholder;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.mussieh.recapp.R;
import com.mussieh.recapp.adapter.ItemListAdapter;
import com.mussieh.recapp.data.Book;
import com.mussieh.recapp.data.FirebaseHelper;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.SharedPreferencesHelper;
import com.mussieh.recapp.data.Video;
import com.mussieh.recapp.data.Website;

import java.util.ArrayList;

/**
 * Created by Mussie on 3/26/2018.
 * ViewHolder with methods and attributes common to other ViewHolders (BookViewHolder, ...)
 */
abstract class ItemViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = ItemViewHolder.class.getSimpleName();
    private Context mContext;
    private FirebaseHelper firebaseHelper;

    /**
     * Fills the current context from the screen context
     * @param itemView the item view
     */
    ItemViewHolder(View itemView, ItemListAdapter adapter) {
        super(itemView);
        mContext = adapter.getContext();
        firebaseHelper = new FirebaseHelper(adapter);
    }

    /**
     * Shows the popup menu for the respective resource item views
     * @param view the item view
     * @param position the layout position of the view
     * @param resources the resource items
     * @param resourceType the resource type
     */
     void showPopupMenu(View view, final int position, ArrayList<ResourceListItem> resources,
                              String resourceType) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );

        Book bookItem;
        Video videoItem;
        Website websiteItem;
        String key;

        switch (resourceType) {
            case FirebaseHelper.KEY_BOOKS:
                bookItem = (Book) resources.get(position);
                key = bookItem.getISBN13();
                attachMenuItemListener(popup, key, bookItem, resourceType);
                break;
            case FirebaseHelper.KEY_VIDEOS:
                videoItem = (Video) resources.get(position);
                key = videoItem.getVideoId();
                attachMenuItemListener(popup, key, videoItem, resourceType);
                break;
            case FirebaseHelper.KEY_WEBSITES:
                websiteItem = (Website) resources.get(position);
                key = websiteItem.getWebsiteImageName();
                attachMenuItemListener(popup, key, websiteItem, resourceType);
                break;
            default:
                break;
        }
    }

    /**
     * Attaches a menu item listener to an item view
     * @param popup the popup menu
     * @param key the SharedPreferences file key for checking the appropriate menu to display
     * @param resourceItem the resource item to check for selection
     * @param resourceType the resource type
     */
    private void attachMenuItemListener(PopupMenu popup, final String key,
                                        final ResourceListItem resourceItem,
                                        final String resourceType) {
        Menu popupMenu = popup.getMenu();
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu);

        String itemSavedStatus = SharedPreferencesHelper.getValue(mContext, key, "None");

        if (itemSavedStatus.equals("exists")) {
            popupMenu.findItem(R.id.removeFromList_popup_menu_item).setVisible(true);
            popupMenu.findItem(R.id.addToList_popup_menu_item).setVisible(false);
        } else {
            popupMenu.findItem(R.id.removeFromList_popup_menu_item).setVisible(false);
            popupMenu.findItem(R.id.addToList_popup_menu_item).setVisible(true);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addToList_popup_menu_item:
                        Log.d("Menu", "Add to MyList clicked");
                        SharedPreferencesHelper.setValue(mContext, key, "exists");
                        firebaseHelper.addUserResource(resourceItem, resourceType);
                        Toast.makeText(mContext, mContext.getString(R.string.item_added_toast_message)
                                , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.removeFromList_popup_menu_item:
                        Log.d(TAG, "Remove from my list");
                        SharedPreferencesHelper.removeValue(mContext, key);
                        firebaseHelper.removeUserResource(resourceItem, resourceType,
                                getLayoutPosition());
                        Toast.makeText(mContext, mContext.getString(R.string.item_removed_toast_message)
                                , Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
                return false;
            }
        });

        popup.show();
    }
}
