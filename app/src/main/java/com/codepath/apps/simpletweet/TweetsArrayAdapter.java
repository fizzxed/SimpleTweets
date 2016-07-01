package com.codepath.apps.simpletweet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweet.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by earroyof on 6/27/16.
 */
// Taking Tweet objects and turns into views displayed in list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {

        super(context, 0, tweets);

    }

    // Override getview

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent, false);
        }
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvScreenname = (TextView) convertView.findViewById(R.id.tvScreenname);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);

        tvUsername.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        String screenAt = "@" + tweet.getUser().getScreenName();

        tvScreenname.setText(screenAt);
        String formattedTime = TimeFormatter.getTimeDifference(tweet.getCreatedAt());
        tvTimestamp.setText(formattedTime);

        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        return convertView;
    }
}
