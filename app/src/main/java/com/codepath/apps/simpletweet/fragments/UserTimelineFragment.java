package com.codepath.apps.simpletweet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.simpletweet.TwitterApplication;
import com.codepath.apps.simpletweet.TwitterClient;
import com.codepath.apps.simpletweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

/**
 * Created by earroyof on 6/30/16.
 */
public class UserTimelineFragment extends TweetsListFragment{
    private TwitterClient client;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get client
        client = TwitterApplication.getRestClient(); // Singleton client
        populateTimeline();
    }

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen name", screenName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    // Send an API request to get timeline json
    // Fill the listview by creating the tweet objects from the json
    private void populateTimeline() {
        String screenName =  getArguments().getString("screen name");
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("mydebug", response.toString());
                addAll(Tweet.fromJsonArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("myDebug", errorResponse.toString());
            }
        });
    }
}
