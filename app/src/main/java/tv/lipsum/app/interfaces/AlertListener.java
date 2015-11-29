package tv.lipsum.app.interfaces;

import android.content.DialogInterface;

/**
 * Created by tyln on 19.10.2015.
 */
public interface AlertListener {
    void PositiveMethod(DialogInterface dialog, int id);

    void NegativeMethod(DialogInterface dialog, int id);
}