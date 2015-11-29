package tv.lipsum.app.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import tv.lipsum.app.R;
import tv.lipsum.app.application.LipsumApp;
import tv.lipsum.app.constants.ApplicationConstants;
import tv.lipsum.app.interfaces.AlertListener;

/**
 * Created by tyln on 19.10.2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_BACK = 1;

    protected Toolbar mToolbar;

    protected abstract int getLayoutRes();

    protected abstract int getToolbarId();

    protected abstract int getMenu();

    protected abstract int getToolbarTitle();

    protected abstract int getToolbarType();

    protected abstract boolean isTrackable();

    protected abstract int getScreenName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        mToolbar = (Toolbar) findViewById(getToolbarId());
        if (mToolbar != null) {
            setupToolbar(getToolbarType(), getString(getToolbarTitle()));
            setSupportActionBar(mToolbar);
        }

        if (isTrackable()) {
            track(getString(getScreenName()));
        }
    }

    public void alert(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenu() != ApplicationConstants.NO_RES) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void setupToolbar(int type, String title) {
        if (!TextUtils.isEmpty(title)) {
            mToolbar.setTitle(title);
        } else {
            mToolbar.setTitle(getString(R.string.app_name));
        }

        switch (type) {
            case TYPE_BACK:
                mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void getConfirmDialog(Context mContext,
                                 String title,
                                 String message,
                                 String positiveBtnCaption,
                                 String negativeBtnCaption,
                                 boolean isCancelable,
                                 final AlertListener target) {

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(title).setMessage(message)
                    .setPositiveButton(positiveBtnCaption, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            target.PositiveMethod(dialog, id);
                        }
                    }).setNegativeButton(negativeBtnCaption, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    target.NegativeMethod(dialog, id);
                }
            });

            AlertDialog alert = builder.create();
            alert.setCancelable(isCancelable);
            alert.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void track(String screenName) {
        Tracker mTracker = ((LipsumApp) getApplication()).getDefaultTracker();
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }
}
