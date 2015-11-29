package tv.lipsum.app.activity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tv.lipsum.app.R;
import tv.lipsum.app.adapter.SettingsListViewAdapter;
import tv.lipsum.app.constants.ApplicationConstants;
import tv.lipsum.app.model.SettingsItem;

/**
 * Created by tyln on 26.10.2015.
 */
public class SettingsActivity extends BaseActivity implements ListView.OnItemClickListener {
    public static final int ID_ABOUT = 0;
    public static final int ID_RATE = 1;
    public static final int ID_NOTIFICATIONS = 2;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_settings;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected int getMenu() {
        return ApplicationConstants.NO_RES;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_settings;
    }

    @Override
    protected int getToolbarType() {
        return TYPE_BACK;
    }

    @Override
    protected boolean isTrackable() {
        return true;
    }

    @Override
    protected int getScreenName() {
        return R.string.screen_name_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView mListView = (ListView) findViewById(R.id.listview_settings);
        final List<SettingsItem> mSettingsItems = new ArrayList<>();
        //mSettingsItems.add(new SettingsItem(ID_NOTIFICATIONS, R.mipmap.ic_notifications_none_black_24dp, getString(R.string.settings_item_notifications), SettingsListViewAdapter.TYPE_SWITCH));
        mSettingsItems.add(new SettingsItem(ID_ABOUT, R.mipmap.ic_info_outline_black_24dp, getString(R.string.settings_item_info), SettingsListViewAdapter.TYPE_ICON));
        mSettingsItems.add(new SettingsItem(ID_RATE, R.mipmap.ic_star_outline_black_24dp, getString(R.string.settings_item_rate), SettingsListViewAdapter.TYPE_ICON));
        final SettingsListViewAdapter mAdapter = new SettingsListViewAdapter(SettingsActivity.this, mSettingsItems, R.layout.row_listview_settings);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SettingsItem item = (SettingsItem) adapterView.getItemAtPosition(i);
        switch (item.getId()) {
            case ID_ABOUT:
                showAboutUs();
                break;

            case ID_RATE:
                rateApp();
                break;

        }
    }

    private void showAboutUs() {
        new AlertDialog.Builder(SettingsActivity.this)
                .setTitle(getString(R.string.dialog_title_about))
                .setMessage(getString(R.string.about_app))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void rateApp() {
        String packageName = getPackageName();
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, uri);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(mIntent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }
}
