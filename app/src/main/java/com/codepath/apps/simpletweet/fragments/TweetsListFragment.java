package com.codepath.apps.simpletweet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.simpletweet.R;
import com.codepath.apps.simpletweet.TweetsArrayAdapter;
import com.codepath.apps.simpletweet.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earroyof on 6/28/16.
 */
public class TweetsListFragment extends Fragment {

    protected TweetsArrayAdapter aTweets;
    protected ArrayList<Tweet> tweets;
    protected ListView lvTweets;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create array list
        tweets = new ArrayList<>();
        // Construct adapter from data source
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        // find listview
        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        // Connect adapter
        lvTweets.setAdapter(aTweets);
        return view;
    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

}
