package tv.lipsum.app.interfaces;

import java.util.List;

import tv.lipsum.app.parse.ParseVideo;

/**
 * Created by tyln on 21.10.2015.
 */
public interface VideosView extends BaseView {
    void onVideosReceived(List<ParseVideo> objects);

    void showHideRefreshLayout(boolean isShow);
}
