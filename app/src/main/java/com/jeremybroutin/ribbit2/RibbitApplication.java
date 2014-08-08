package com.jeremybroutin.ribbit2;

import android.app.Application;

import com.parse.Parse;

public class RibbitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "OIisxXxsdHG7qpu34mmJlWLPeBp04yt3xXYHTn6W", "XTvivAz1yuGu3uHR9GSA4xhVAC2IGFb2NMP5mjKj");

    }
}
