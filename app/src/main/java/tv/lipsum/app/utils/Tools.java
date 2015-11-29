package tv.lipsum.app.utils;

import android.content.Context;
import android.text.TextUtils;

import tv.lipsum.app.R;

/**
 * Created by tyln on 28.10.2015.
 */
public class Tools {
    public static int getToolbarHeight(Context context) {
        // toolbar height+statusbar height
        int toolbarHeight = (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        return toolbarHeight;
    }

    public static boolean isYoutubeLink(String youtubeUrl) {
        final String YOUTUBE_PREFIX = "https://www.youtube.com/watch?v=";
        return !TextUtils.isEmpty(youtubeUrl) && youtubeUrl.contains(YOUTUBE_PREFIX);
    }
}
