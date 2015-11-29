package tv.lipsum.app.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import tv.lipsum.app.constants.ParseConstants;

/**
 * Created by tyln on 25.11.15.
 */

@ParseClassName("Suggestion")
public class Suggestion extends ParseObject {
    private final String URL = "youtubeUrl";
    private final String IS_VIEWED = "isViewed";
    private final String SENDER = "sender";

    public Suggestion() {

    }

    public String getUrl() {
        return getString(URL);
    }

    public void setUrl(String link) {
        put(URL, link);
    }

    public String getSender() {
        return getString(SENDER);
    }

    public void setSender(String name) {
        put(SENDER, name);
    }

    public String getCookieId() {
        return getString(ParseConstants.COOKIE_ID);
    }

    public void setCookieId(String cookieId) {
        put(ParseConstants.COOKIE_ID, cookieId);
    }

    public boolean getIsDeleted() {
        return getBoolean(ParseConstants.IS_DELETED);
    }

    public void setIsDeleted(boolean isDeleted) {
        put(ParseConstants.IS_DELETED, isDeleted);
    }

    public boolean getIsViewed() {
        return getBoolean(IS_VIEWED);
    }

    public void setIsViewed(boolean isViewed) {
        put(SENDER, isViewed);
    }
}
