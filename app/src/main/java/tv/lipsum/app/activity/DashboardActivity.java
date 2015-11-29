package tv.lipsum.app.activity;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.astuetz.PagerSlidingTabStrip;

import java.util.HashMap;
import java.util.List;

import tv.lipsum.app.R;
import tv.lipsum.app.adapter.TabsAdapter;
import tv.lipsum.app.controller.CategoryControllerImp;
import tv.lipsum.app.helper.NavitagionHelper;
import tv.lipsum.app.interfaces.CategoryController;
import tv.lipsum.app.interfaces.CategoryView;
import tv.lipsum.app.parse.ParseCategory;
import tv.lipsum.app.parse.ParseVideo;
import tv.lipsum.app.utils.ToolbarTabsPageChangeListener;


public class DashboardActivity extends BaseActivity implements CategoryView {
    private PagerSlidingTabStrip mTabs;
    private ProgressBar mProgressBar;
    public ViewPager mViewPager;
    public View mToolbarContainer;
    public static final HashMap<Integer, List<ParseVideo>> mVideos = new HashMap<>();

    @Override

    protected int getLayoutRes() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_main;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.app_name;
    }

    @Override
    protected int getToolbarType() {
        return TYPE_EMPTY;
    }

    @Override
    protected boolean isTrackable() {
        return true;
    }

    @Override
    protected int getScreenName() {
        return R.string.screen_name_dashboard;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mToolbarContainer = findViewById(R.id.toolbar_main);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(new ToolbarTabsPageChangeListener(mToolbarContainer));

        CategoryController mCategoryController = new CategoryControllerImp(this);
        mCategoryController.onCategoriesRequested();
    }

    @Override
    public void onCategoriesReceived(List<ParseCategory> objects) {
        TabsAdapter mAdapter = new TabsAdapter(getSupportFragmentManager(), objects);
        mViewPager.setAdapter(mAdapter);
        mTabs.setViewPager(mViewPager);
    }

    @Override
    public void showIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIndicator() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDefaultError(String e) {
        alert(e);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (mViewPager.getCurrentItem() != 0) {
                mViewPager.setCurrentItem(0);
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                NavitagionHelper.navigateToSettings(DashboardActivity.this);
                break;

            /*
            case R.id.action_add:
                DialogFragment mDialogFragment = new SuggestionDialogFragment();
                mDialogFragment.setCancelable(true);
                mDialogFragment.show(getSupportFragmentManager(), SuggestionDialogFragment.TAG);
                break;
            */

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
