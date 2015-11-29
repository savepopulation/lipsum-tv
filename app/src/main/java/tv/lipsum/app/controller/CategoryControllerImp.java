package tv.lipsum.app.controller;

import android.text.TextUtils;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import tv.lipsum.app.interfaces.CategoryController;
import tv.lipsum.app.interfaces.CategoryView;
import tv.lipsum.app.parse.ParseCategory;

/**
 * Created by tyln on 19.10.2015.
 */
public class CategoryControllerImp implements CategoryController {
    private final String CATEGORY_DATA_KEY = "category_data";
    private CategoryView view;

    public CategoryControllerImp(CategoryView view) {
        this.view = view;
    }


    @Override
    public void getCategories(final boolean isNotify) {
        if (isNotify) {
            view.showIndicator();
        }

        ParseQuery<ParseCategory> query = ParseCategory.generateQuery(false);
        query.findInBackground(new FindCallback<ParseCategory>() {
            @Override
            public void done(List<ParseCategory> objects, ParseException e) {
                if (e == null) {
                    if (isNotify) {
                        view.hideIndicator();
                    }

                    if (objects.size() > 0) {
                        List<ParseCategory> mList = cleanData(objects);
                        if (isNotify) {
                            view.onCategoriesReceived(mList);
                        }
                        saveCategoryDataToParseLocal(mList);
                    }
                } else {
                    if (isNotify) {
                        view.hideIndicator();
                        view.onDefaultError(e.getLocalizedMessage());
                    }
                }
            }
        });
    }

    @Override
    public void saveCategoryDataToParseLocal(List<ParseCategory> objects) {
        try {
            ParseObject.unpinAll(CATEGORY_DATA_KEY);
            ParseObject.pinAll(CATEGORY_DATA_KEY, objects);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onCategoriesRequested() {
        ParseQuery<ParseCategory> query = ParseCategory.generateQuery(true);
        query.findInBackground(new FindCallback<ParseCategory>() {
            @Override
            public void done(List<ParseCategory> objects, ParseException e) {
                boolean isCache = false;
                if (e == null) {
                    if (objects != null && objects.size() > 0) {
                        view.onCategoriesReceived(objects);
                        isCache = true;
                    }
                }
                getCategories(!isCache);
            }
        });
    }


    @Override
    public List<ParseCategory> cleanData(List<ParseCategory> data) {
        List<ParseCategory> mList = new ArrayList<>();
        for (ParseCategory pc : data) {
            if (pc != null && !TextUtils.isEmpty(pc.getName())) {
                mList.add(pc);
            }
        }
        return mList;
    }
}
