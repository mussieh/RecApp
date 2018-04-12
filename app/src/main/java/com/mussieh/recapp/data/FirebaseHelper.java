package com.mussieh.recapp.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mussieh.recapp.adapter.BookListAdapter;
import com.mussieh.recapp.adapter.ItemListAdapter;
import com.mussieh.recapp.adapter.ResourceListAdapter;
import com.mussieh.recapp.adapter.VideoListAdapter;
import com.mussieh.recapp.adapter.WebsiteListAdapter;
import com.mussieh.recapp.fragment.ResourceListFragment;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mussie on 3/6/2018.
 * Helper class that handles all communication with Google's Firebase platform
 */
public class FirebaseHelper {
    private static final String TAG = FirebaseHelper.class.getSimpleName();
    private static final String KEY_APP_DATA = "app_data";
    private static final String KEY_USER_DATA = "user_data";

    public static final String KEY_BOOKS = "books";
    public static final String KEY_VIDEOS = "videos";
    public static final String KEY_WEBSITES = "websites";
    public static final String KEY_BOOK_RANK = "rank";
    public static final String KEY_VIDEO_RANK = "videoRank";
    public static final String KEY_WEBSITE_RANK = "websiteRank";

    private static final String KEY_ALGORITHMS_DATA_STRUCTURES = "algorithms_and_data_structures";
    private static final String KEY_ANDROID_DEVELOPMENT = "android_development";
    private static final String KEY_INTERVIEW_PREPARATION = "interview_preparation";
    private static final String KEY_WEB_DEVELOPMENT = "web_development";

    private static final String ALGORITHMS_DATA_STRUCTURES = "Algorithms & Data Structures";
    private static final String ANDROID_DEVELOPMENT = "Android Development";
    private static final String INTERVIEW_PREPARATION = "Interview Preparation";
    private static final String WEB_DEVELOPMENT = "Web Development";

    private FirebaseDatabase mDatabase;

    // do not change to static context as doing that will cause a memory leak
    private ItemListAdapter mResourceAdapter;

    /**
     * Constructs the FirebaseHelper object
     */
    public FirebaseHelper(ItemListAdapter adapter) {
        mDatabase = FirebaseDatabase.getInstance();
        mResourceAdapter = adapter;
    }

    /**
     * Adds a user resource to Firebase Realtime Database (no authentication during testing period)
     * @param resource the resource to add
     * @param resourceType the type of resource
     */
    public void addUserResource(ResourceListItem resource, String resourceType) {
        DatabaseReference personalListRef = mDatabase.getReference(KEY_USER_DATA).child("testUser").
                child("personal_list");
        switch (resourceType) {
            case KEY_BOOKS:
                Book bookItem = (Book) resource;
                bookItem.setTimeAddedToList(new Date().getTime());
                personalListRef.child(bookItem.getISBN13()).setValue(bookItem);
                break;
            case KEY_VIDEOS:
                Video videoItem = (Video) resource;
                videoItem.setTimeAddedToList(new Date().getTime());
                personalListRef.child(videoItem.getVideoId()).setValue(videoItem);
                break;
            case KEY_WEBSITES:
                Website websiteItem = (Website) resource;
                websiteItem.setTimeAddedToList(new Date().getTime());
                personalListRef.child(websiteItem.getWebsiteImageName()).setValue(websiteItem);
                break;
            default:
                break;
        }
    }

    /**
     * Removes a user resource from Firebase Realtime Database
     * (no authentication during testing period)
     * @param resource the resource to add
     * @param resourceType the data type of the resource
     * @param position the ArrayList position of the item to remove
     */
    public void removeUserResource(ResourceListItem resource, String resourceType,
                                          int position) {
        DatabaseReference personalListRef = mDatabase.getReference(KEY_USER_DATA).child("testUser").
                child("personal_list");

        switch (resourceType) {
            case KEY_BOOKS:
                Book bookItem = (Book) resource;
                personalListRef.child(bookItem.getISBN13()).removeValue();
                break;
            case KEY_VIDEOS:
                Video videoItem = (Video) resource;
                personalListRef.child(videoItem.getVideoId()).removeValue();
                break;
            case KEY_WEBSITES:
                Website websiteItem = (Website) resource;
                personalListRef.child(websiteItem.getWebsiteImageName()).removeValue();
                break;
            default:
                break;
        }

        if (mResourceAdapter != null && mResourceAdapter instanceof ResourceListAdapter) {
            if (mResourceAdapter.getAdapterName().equals(ResourceListFragment.PERSONAL_LIST_ADAPTER)
                    && !mResourceAdapter.getResources().isEmpty()) {
                mResourceAdapter.getResources().remove(position);
                mResourceAdapter.notifyItemRemoved(position);
            }
        }
    }

    /**
     * Gets the resource data detail
     * @param resourceListItem the resource list item
     * @return the resource detail String array
     */
    public String[] getResourceDetail(ResourceListItem resourceListItem) {
        String[] resourceDetail = new String[2];

        if (resourceListItem instanceof Book) {
            resourceDetail[0] = KEY_BOOKS;
            resourceDetail[1] = ((Book) resourceListItem).getISBN13();
        } else if (resourceListItem instanceof  Video) {
            resourceDetail[0] = KEY_VIDEOS;
            resourceDetail[1] = ((Video) resourceListItem).getVideoId();
        } else {
            resourceDetail[0] = KEY_WEBSITES;
            resourceDetail[1] = ((Website) resourceListItem).getWebsiteImageName();
        }
        return resourceDetail;
    }

    /**
     * Asynchronously fetches user resources and loads
     * the data when completed. (no authentication during testing period)
     */
    public void fetchUserResources() {
        Query personalListQuery = mDatabase.getReference(KEY_USER_DATA).child("testUser").
                child("personal_list").orderByChild("timeAddedToList");
        ValueEventListener resourceListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ResourceListItem> resources = new ArrayList<>();
                ResourceListItem resourceItem;
                String resourceKey;

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    resourceKey = childSnapshot.getKey();

                    if (resourceKey.length() == 13) { // if key is ISBN
                        resourceItem = childSnapshot.getValue(Book.class);
                    } else if (resourceKey.length() == 32) { // if key is MD5 hash
                        resourceItem = childSnapshot.getValue(Website.class);
                    } else { // Since Youtube's video id might be more than 11 characters soon
                        resourceItem = childSnapshot.getValue(Video.class);
                    }
                    resources.add(resourceItem);
                }
                loadResources(resources);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        personalListQuery.addListenerForSingleValueEvent(resourceListener);
    }

    /**
     * Asynchronously fetches app resources and loads
     * the data when completed.
     */
    public void fetchAppResources(String subjectName, final String resourceType,
                                         String orderKey) {
        String databaseChild = getDatabaseKeyFromChoiceSummary(subjectName);
        Log.d(TAG, databaseChild);
        Query rankedResourceQuery = mDatabase.getReference(KEY_APP_DATA).child(resourceType).
                child(databaseChild).orderByChild(orderKey);

        ValueEventListener resourceListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ResourceListItem> resources = new ArrayList<>();
                ResourceListItem resourceItem;
                Class dataModelClassType;

                switch (resourceType) {
                    case KEY_BOOKS:
                        dataModelClassType = Book.class;
                        break;
                    case KEY_VIDEOS:
                        dataModelClassType = Video.class;
                        break;
                    case KEY_WEBSITES:
                        dataModelClassType = Website.class;
                        break;
                    default:
                        dataModelClassType = Book.class;
                        break;
                }

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    resourceItem = (ResourceListItem) childSnapshot.getValue(dataModelClassType);
                    resources.add(resourceItem);
                }
                loadResources(resources);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };

        rankedResourceQuery.addListenerForSingleValueEvent(resourceListener);
    }

    /**
     * Loads the appropriate resources based on adapter type
     * @param resources the resources to load
     */
    private void loadResources(ArrayList<ResourceListItem> resources) {
        mResourceAdapter.setData(resources);
    }


    /**
     * Get the Firebase Realtime database key from the subject summary text in user settings
     * @param userChoice the chosen subject's summary text
     * @return the Firebase Realtime database key
     */
    private String getDatabaseKeyFromChoiceSummary(String userChoice) {
        String databaseKey;
        switch (userChoice) {
            case ALGORITHMS_DATA_STRUCTURES: databaseKey = KEY_ALGORITHMS_DATA_STRUCTURES;
            break;

            case ANDROID_DEVELOPMENT: databaseKey = KEY_ANDROID_DEVELOPMENT;
            break;

            case INTERVIEW_PREPARATION: databaseKey = KEY_INTERVIEW_PREPARATION;
            break;

            case WEB_DEVELOPMENT: databaseKey = KEY_WEB_DEVELOPMENT;
            break;

            default: databaseKey = KEY_INTERVIEW_PREPARATION;
            break;

        }
        return databaseKey;
    }
}
