package com.codepath.apps.simpletweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.simpletweet.R;
import com.codepath.apps.simpletweet.SmartFragmentStatePagerAdapter;
import com.codepath.apps.simpletweet.TwitterApplication;
import com.codepath.apps.simpletweet.TwitterClient;
import com.codepath.apps.simpletweet.fragments.HomeTimelineFragment;
import com.codepath.apps.simpletweet.fragments.MentionsTimelineFragment;
import com.codepath.apps.simpletweet.models.Tweet;
import com.codepath.apps.simpletweet.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    public static final int REQUEST_COMPOSE = 442;
    private DrawerLayout mDrawer;
    private Toolbar tbTimeline;
    private NavigationView nvDrawer;
    private User loggedInUser;
    private TwitterClient client;
    private TweetsPagerAdapter adapterViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Set client
        client = TwitterApplication.getRestClient();
        // Get Current User
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                loggedInUser = User.fromJson(response);
                // My current user account information
            }
        });

        // Get viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        // Set viewpageradapter for the viewpager

        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager());

        vpPager.setAdapter(adapterViewPager);

        // find the pager sliding tabs
        TabLayout tabStrip  = (TabLayout) findViewById(R.id.sliding_tabs);

        // attach the pager tabs to the viewpager
        tabStrip.setupWithViewPager(vpPager);

        // Set toolbar
        tbTimeline = (Toolbar) findViewById(R.id.tbTimeline);
        setSupportActionBar(tbTimeline);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Find Drawer View
        nvDrawer= (NavigationView) findViewById(R.id.nvView);
        // Setup Drawer View
        setupDrawerContent(nvDrawer);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCompose);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                startActivityForResult(i, REQUEST_COMPOSE);
                overridePendingTransition(R.anim.slide_up, R.anim.hold);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                }
        );
    }

    private void selectDrawerItem(MenuItem item) {
        // Handle logic for selecting different things
        switch (item.getItemId()) {
            case R.id.nav_profile:
                mDrawer.closeDrawers();
                onProfileView();
                break;
            case R.id.nav_highlights:
                Toast.makeText(this, "Selected highlights", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_connect:
                Toast.makeText(this, "Selected connect", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_lists:
                Toast.makeText(this, "Selected lists", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Selected settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_help:
                Toast.makeText(this, "Selected help", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void onProfileView() {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("user", loggedInUser);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_COMPOSE && resultCode == 42) {
            Tweet resultTweet = (Tweet) data.getSerializableExtra("tweet");
            HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
            homeTimelineFragment.appendTweet(resultTweet);
        }
    }

    // Returns order of fragments in viewpager
    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {
        private String tabTitles[] = {"Home", "Mentions"};

        // Adapter gets the manager, uses to insert or remove fragments from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Controls the order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) return new HomeTimelineFragment();
            else if (position == 1) return new MentionsTimelineFragment();
            else return null;
        }


        // Returns the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        // Tells how many fragments there are to swipe between
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

}
