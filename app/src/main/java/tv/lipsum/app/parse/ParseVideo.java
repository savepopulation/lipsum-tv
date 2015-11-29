package tv.lipsum.app.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.concurrent.TimeUnit;

import tv.lipsum.app.constants.ParseConstants;

/**
 * Created by tyln on 21.10.2015.
 */

@ParseClassName("Video")
public class ParseVideo extends ParseObject {
    private final String YOUTUBE_ID = "youtubeId";
    private final String NAME = "name";
    private final String DESC = "desc";
    private final String DURATION = "duration";
    private static final String CATEGORY_ID = "categoryId";

    public ParseVideo() {
    }

    public String getId() {
        return getString(YOUTUBE_ID);
    }

    public void setId(String id) {
        put(YOUTUBE_ID, id);
    }

    public String getName() {
        return getString(NAME);
    }

    public void setName(String name) {
        put(NAME, name);
    }

    public String getDesc() {
        return getString(DESC);
    }

    public void setDesc(String desc) {
        put(DESC, desc);
    }

    public long getDuration() {
        return getLong(DURATION);
    }

    public void setDuration(long duration) {
        put(DURATION, duration);
    }

    public int getCategoryId() {
        return getInt(CATEGORY_ID);
    }

    public void setCategoryId(int categoryId) {
        put(CATEGORY_ID, categoryId);
    }

    public static ParseQuery<ParseVideo> generateGetVideosByCategoryQuery(int categoryId) {
        ParseQuery<ParseVideo> query = ParseQuery.getQuery(ParseVideo.class);
        query.whereEqualTo(ParseConstants.IS_DELETED, false);
        query.addDescendingOrder(ParseConstants.CREATED_At);
        if (categoryId != ParseConstants.CATEGORY_ID_HOME) {
            query.whereEqualTo(CATEGORY_ID, categoryId);
        }
        return query;
    }

    public String getThumbNailUrl() {
        return "http://img.youtube.com/vi/" + getId() + "/maxresdefault.jpg";
    }

    public String getConvertedDuration() {
        String mDuration = "00:00";
        try {
            mDuration = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(getDuration()),
                    TimeUnit.MILLISECONDS.toSeconds(getDuration()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getDuration()))
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mDuration;
    }
}
