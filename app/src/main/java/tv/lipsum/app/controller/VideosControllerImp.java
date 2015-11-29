package tv.lipsum.app.controller;

import android.text.TextUtils;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import tv.lipsum.app.activity.DashboardActivity;
import tv.lipsum.app.interfaces.VideosController;
import tv.lipsum.app.interfaces.VideosView;
import tv.lipsum.app.parse.ParseVideo;

/**
 * Created by tyln on 21.10.2015.
 */
public class VideosControllerImp implements VideosController {
    private VideosView mView;

    public VideosControllerImp(VideosView mView) {
        this.mView = mView;
    }

    @Override
    public void onVideosRequested(final int categoryId, final boolean isRefresh) {
        final List<ParseVideo> mVideos = DashboardActivity.mVideos.get(categoryId);
        if (mVideos != null && mVideos.size() > 0 && !isRefresh) {
            mView.onVideosReceived(mVideos);
        } else {
            if (!isRefresh) {
                mView.showIndicator();
            }
            ParseQuery<ParseVideo> query = ParseVideo.generateGetVideosByCategoryQuery(categoryId);
            query.findInBackground(new FindCallback<ParseVideo>() {
                @Override
                public void done(List<ParseVideo> objects, ParseException e) {
                    if (!isRefresh) {
                        mView.hideIndicator();
                    } else {
                        mView.showHideRefreshLayout(false);
                    }
                    if (e == null) {
                        if (objects != null && objects.size() > 0) {
                            List<ParseVideo> mList = cleanData(objects);
                            if (DashboardActivity.mVideos.containsKey(categoryId)) {
                                DashboardActivity.mVideos.remove(categoryId);
                            }
                            DashboardActivity.mVideos.put(categoryId, mList);
                            mView.onVideosReceived(mList);
                        }
                    } else {
                        mView.onDefaultError(e.getLocalizedMessage());
                    }
                }
            });
        }
    }


    @Override
    public List<ParseVideo> cleanData(List<ParseVideo> data) {
        List<ParseVideo> mList = new ArrayList<>();
        for (ParseVideo pv : data) {
            if (pv != null && !TextUtils.isEmpty(pv.getName()) && !TextUtils.isEmpty(pv.getId())) {
                mList.add(pv);
            }
        }
        return mList;
    }
}
