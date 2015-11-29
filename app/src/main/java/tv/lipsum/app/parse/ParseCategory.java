package tv.lipsum.app.parse;

import com.parse.ParseClassName;
import com.parse.ParseQuery;

import tv.lipsum.app.constants.ParseConstants;

/**
 * Created by tyln on 19.10.2015.
 */

@ParseClassName("Category")
public class ParseCategory extends com.parse.ParseObject {
    private final String ID = "categoryId";
    private final String NAME = "name";
    private final String DESC = "desc";
    private static final String ORDER = "order";

    public ParseCategory() {
    }

    public int getId() {
        return getInt(ID);
    }

    public void setId(int id) {
        put(ID, id);
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

    public int getOrder() {
        return getInt(ORDER);
    }

    public void setOrder(int order) {
        put(ORDER, order);
    }

    public static ParseQuery<ParseCategory> generateQuery(boolean isLocal) {
        ParseQuery<ParseCategory> query = ParseQuery.getQuery(ParseCategory.class);
        query.orderByAscending(ORDER);
        if (isLocal) {
            query.fromLocalDatastore();
        } else {
            query.whereEqualTo(ParseConstants.IS_DELETED, false);
        }
        return query;
    }
}
