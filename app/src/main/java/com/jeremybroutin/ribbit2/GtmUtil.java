package com.jeremybroutin.ribbit2;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;


public class GtmUtil {

    private GtmUtil(){}

    private static final String TAG = GtmUtil.class.getName();

        //push openScreen event
    public static void pushOpenScreenEvent(Context context, String screenName){
        TagManager tagManager = TagManager.getInstance(context);
        DataLayer dataLayer = tagManager.getDataLayer();
        dataLayer.pushEvent("openScreen", DataLayer.mapOf("screenName", screenName));
        String printDataLayer = tagManager.getDataLayer().toString();
        Log.i(TAG, printDataLayer);
    }

    //push custom event
    public static void pushCustomEvent(Context context, String eventCategory, String eventAction, String eventLabel){
        TagManager tagManager = TagManager.getInstance(context);
        DataLayer dataLayer = tagManager.getDataLayer();
        dataLayer.push(DataLayer.mapOf("eventCategory", eventCategory, "eventAction", eventAction, "eventLabel", eventLabel));
        tagManager.dispatch();
    }

}
