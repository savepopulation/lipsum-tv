package tv.lipsum.app.utils;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by tyln on 28.10.2015.
 */
public class ToolbarTabsPageChangeListener implements ViewPager.OnPageChangeListener {
    private View mView;

    public ToolbarTabsPageChangeListener(View mView) {
        this.mView = mView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
