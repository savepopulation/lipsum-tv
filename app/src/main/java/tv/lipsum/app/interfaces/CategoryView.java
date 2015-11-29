package tv.lipsum.app.interfaces;

import java.util.List;

import tv.lipsum.app.parse.ParseCategory;

/**
 * Created by tyln on 19.10.2015.
 */
public interface CategoryView extends BaseView {
    void onCategoriesReceived(List<ParseCategory> objects);
}
