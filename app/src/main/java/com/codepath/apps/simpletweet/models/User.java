package com.codepath.apps.simpletweet.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by earroyof on 6/27/16.
 */
public class User implements Serializable {

    // Same way as Tweet
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagline;
    private String background_ipad;
    private String background_mobile;
    private int followersCount;
    private int followingsCount;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getBackground_ipad() {
        return background_ipad;
    }

    public String getBackground_mobile() {
        return background_mobile;
    }

    public static User fromJson(JSONObject jsonObject) {
        // returns a user after extracting and filling values
        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.tagline = jsonObject.getString("description");
            u.followersCount = jsonObject.getInt("followers_count");
            u.followingsCount = jsonObject.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }

    public void backgroundFromJson(JSONObject jsonObject) {
        try {
            this.background_ipad = jsonObject.getJSONObject("sizes").getJSONObject("ipad_retina").getString("url");
            this.background_mobile = jsonObject.getJSONObject("sizes").getJSONObject("mobile_retina").getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
