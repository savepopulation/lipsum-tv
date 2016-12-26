package tv.lipsum.app.helper;

import android.content.Context;
import android.content.Intent;

import tv.lipsum.app.activity.YoutubePlayerActivity;
import tv.lipsum.app.activity.SettingsActivity;
import tv.lipsum.app.constants.ApplicationConstants;

/**
 * Created by tyln on 26.10.2015.
 */
public final class NavitagionHelper {
    private NavitagionHelper() {
        // Empty private constructor.
    }

    public static void navigateToSettings(Context context) {
        Intent mIntent = new Intent(context, SettingsActivity.class);
        context.startActivity(mIntent);
    }

    public static void navigateToVideoActivity(Context context, String id) {
        Intent mIntent = new Intent(context, YoutubePlayerActivity.class);
        mIntent.putExtra(ApplicationConstants.BUNDLE_ID, id);
        context.startActivity(mIntent);
    }
}
