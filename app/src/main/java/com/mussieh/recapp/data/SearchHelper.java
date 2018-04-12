package com.mussieh.recapp.data;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.google.gson.JsonObject;
import com.mussieh.recapp.adapter.ItemListAdapter;
import com.mussieh.recapp.adapter.ResourceListAdapter;
import com.mussieh.recapp.utilities.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//todo: Remove static context

/**
 * Helper class for searching indexed application data on Algolia
 * This class is for testing purposes only. 
 * Future versions should not have references to objects in a static context
 * as that will cause a memory leak.
 */
public class SearchHelper {
    private static final String TAG =SearchHelper.class.getSimpleName();

    /**
     * Algolia Specs
     * Note: In a production app, this should be fetched from an online resource.
     * Developer keys should not be in class files.
     */
    private static final Client searchClient = new Client("YourApplicationID", "YourAPIKey");
    private static final Index searchIndex = searchClient.getIndex("your_index_name");
    private static com.algolia.search.saas.Query queryObj = new com.algolia.search.saas.Query("");

    /**
     * Empty Search Helper Constructor
     */
    private SearchHelper() {

    }

    /**
     * Search application resources on Algolia
     * @param queryString the query string to use
     * @param adapter the adapter for checking the adapter name
     */
    public static void searchResources(final String queryString, final ItemListAdapter adapter) {
        CompletionHandler completionHandler = new CompletionHandler() {
            @Override
            public void requestCompleted(JSONObject jsonObject, AlgoliaException e) {
                ArrayList<ResourceListItem> resources = JsonUtils.getResourceData(jsonObject,
                        queryString, adapter);
                adapter.setData(resources);
            }
        };

        queryObj.setQuery(queryString);
        queryObj.setHitsPerPage(6);
        searchIndex.searchAsync(queryObj, completionHandler);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }
}
