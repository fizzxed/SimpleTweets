package com.codepath.apps.simpletweet.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpletweet.R;
import com.codepath.apps.simpletweet.TwitterApplication;
import com.codepath.apps.simpletweet.TwitterClient;
import com.codepath.apps.simpletweet.fragments.UserTimelineFragment;
import com.codepath.apps.simpletweet.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    User selectedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbProfile);
        setSupportActionBar(toolbar);




        // Get the user from the activity that launches this
        selectedUser = (User) getIntent().getSerializableExtra("user");
        populateProfileHeader();
        populateBackgroundUrl();

        CollapsingToolbarLayout cbProfile = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        cbProfile.setTitle(selectedUser.getName());


        // create the user timeline fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(selectedUser.getScreenName());

        // Display UserFragment in activity (dynamically)
        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimelineFragment);
            ft.commit();
        }

    }

    protected void setBackground() {
        ImageView ivBackground = (ImageView) findViewById(R.id.ivBackground);
        Glide.with(this).load(selectedUser.getBackground_mobile()).into(ivBackground);
    }

    public void populateProfileHeader() {
        TextView tvHeaderName = (TextView) findViewById(R.id.tvHeaderName);
        TextView tvHeaderScreenName = (TextView) findViewById(R.id.tvHeaderScreenName);
        TextView tvFollowings = (TextView) findViewById(R.id.tvFollowing);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvStatus = (TextView) findViewById(R.id.tvHeaderStatus);
        ImageView ivHeaderProfile = (ImageView) findViewById(R.id.ivHeaderImage);
        tvHeaderName.setText(selectedUser.getName());
        tvHeaderScreenName.setText("@" + selectedUser.getScreenName());
        tvFollowers.setText(selectedUser.getFollowersCount() + " Followers");
        tvFollowings.setText(selectedUser.getFollowingsCount() + " Following");
        tvStatus.setText(selectedUser.getTagline());
        Glide.with(this).load(selectedUser.getProfileImageUrl()).into(ivHeaderProfile);
    }

    private void populateBackgroundUrl() {
        TwitterClient client = TwitterApplication.getRestClient(); // Singleton client
        client.getUserBackground(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                selectedUser.backgroundFromJson(response);
                setBackground();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // Goes here if user has not uploaded a background image
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        }, selectedUser.getScreenName());

    }

}
