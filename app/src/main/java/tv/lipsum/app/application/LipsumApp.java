package tv.lipsum.app.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.UUID;

import io.fabric.sdk.android.Fabric;
import tv.lipsum.app.R;
import tv.lipsum.app.constants.ApplicationConstants;
import tv.lipsum.app.helper.SharedPrefHelper;
import tv.lipsum.app.parse.ParseCategory;
import tv.lipsum.app.parse.ParseVideo;
import tv.lipsum.app.parse.Suggestion;

/**
 * Created by tyln on 19.10.2015.
 */
public class LipsumApp extends Application {
    public static String cookieId;
    public static SharedPrefHelper mSharedPrefHelper;
    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mSharedPrefHelper = new SharedPrefHelper(getApplicationContext(), ApplicationConstants.MASTER_KEY);
        cookieId = getCookieId();
        initParse();
        initImageLoader();
    }

    private void initParse() {
        ParseObject.registerSubclass(ParseCategory.class);
        ParseObject.registerSubclass(ParseVideo.class);
        ParseObject.registerSubclass(Suggestion.class);
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(this, "parce_key", "parse_key");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    private void initImageLoader() {
        DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        //File cacheDirectory = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "LipsumTV/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .threadPoolSize(3)
                .defaultDisplayImageOptions(mOptions)
                .build();

        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
        L.writeLogs(false);
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    private static String getCookieId() {
        String cookieId = mSharedPrefHelper.load(ApplicationConstants.COOKIE_ID);
        if (TextUtils.isEmpty(cookieId)) {
            cookieId = UUID.randomUUID().toString();
            mSharedPrefHelper.save(ApplicationConstants.COOKIE_ID, cookieId);
        }
        return cookieId;
    }
}
