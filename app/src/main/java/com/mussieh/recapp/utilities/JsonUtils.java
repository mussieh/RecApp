package com.mussieh.recapp.utilities;

import com.google.gson.Gson;
import com.mussieh.recapp.SearchResultsActivity;
import com.mussieh.recapp.adapter.ItemListAdapter;
import com.mussieh.recapp.data.Book;
import com.mussieh.recapp.data.ResourceListItem;
import com.mussieh.recapp.data.SearchDisplayItem;
import com.mussieh.recapp.data.Video;
import com.mussieh.recapp.data.Website;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Helper class for parsing JSON data
 * Based on the Android Developer Fundamentals Course
 * https://www.gitbook.com/@google-developer-training
 * author: Mussie Habtemichael
 * Date: 03/04/2018
 */

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();
    private static final String SEARCH_RESULTS_KEY = "hits";
    private static final String RESOURCE_TYPE_ATTRIBUTE = "resourceType";
    private static final String BOOK_RESULT_TYPE = "book";
    private static final String VIDEO_RESULT_TYPE = "video";
    private static final String WEBSITE_RESULT_TYPE = "website";

    private String jsonData;

    /**
     * Parses and returns resources that match the query string
     * @param searchResultJsonObject the search result JSON object
     * @param queryString the query string
     * @param adapter the search adapter
     * @return a list of resources
     */
    public static ArrayList<ResourceListItem> getResourceData(JSONObject searchResultJsonObject,
                                                              String queryString,
                                                              ItemListAdapter adapter) {
        JSONArray itemsArray;
        Gson gson = new Gson();
        ArrayList<ResourceListItem> resources = new ArrayList<>();

        if (adapter.getAdapterName().equals(SearchResultsActivity.SEARCH_LIST_ADAPTER)) {
            SearchDisplayItem searchDisplayItem = new SearchDisplayItem(queryString);
            resources.add(searchDisplayItem);
        }

        try {
            itemsArray = searchResultJsonObject.getJSONArray(SEARCH_RESULTS_KEY);

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject resourceItem =  itemsArray.getJSONObject(i);

                try {
                    switch (resourceItem.getString(RESOURCE_TYPE_ATTRIBUTE)) {
                        case BOOK_RESULT_TYPE:
                            Book bookItem = gson.fromJson(resourceItem.toString(), Book.class);
                            resources.add(bookItem);
                            break;
                        case VIDEO_RESULT_TYPE:
                            Video videoItem = gson.fromJson(resourceItem.toString(), Video.class);
                            resources.add(videoItem);
                            break;
                        case WEBSITE_RESULT_TYPE:
                            Website websiteItem = gson.fromJson(resourceItem.toString(),
                                    Website.class);
                            resources.add(websiteItem);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resources;
    }

}
