package tv.lipsum.app.interfaces;

import java.util.List;

/**
 * Created by tyln on 25.10.2015.
 */
public interface BaseController<T> {
    List<T> cleanData(List<T> data);
}
