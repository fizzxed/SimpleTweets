package com.codepath.apps.simpletweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.simpletweet.R;
import com.codepath.apps.simpletweet.TwitterApplication;
import com.codepath.apps.simpletweet.TwitterClient;
import com.codepath.apps.simpletweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        Button btTweet = (Button) findViewById(R.id.btTweet);
        final EditText etCompose = (EditText) findViewById(R.id.etCompose);

        client = TwitterApplication.getRestClient();


        assert btTweet != null;
        btTweet.setTransformationMethod(null);
        btTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic for sending the tweet in edit text
                client.postTweet(etCompose.getText().toString(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Tweet responseTweet = Tweet.fromJson(response);
                        Intent data = new Intent();
                        data.putExtra("tweet", responseTweet);
                        setResult(42, data);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }
        });
    }
}
