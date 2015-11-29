package tv.lipsum.app.interfaces;

import tv.lipsum.app.parse.ParseVideo;

/**
 * Created by tyln on 21.10.2015.
 */
public interface VideosController extends BaseController<ParseVideo> {
    void onVideosRequested(int categoryId, boolean isRefresh);

}
