package tv.lipsum.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import tv.lipsum.app.fragment.CategoryFragment;
import tv.lipsum.app.parse.ParseCategory;

/**
 * Created by tyln on 20.10.2015.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private List<ParseCategory> mObjects;

    public TabsAdapter(FragmentManager fm, List<ParseCategory> mObjects) {
        super(fm);
        this.mObjects = mObjects;
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryFragment.newInstance(mObjects.get(position).getId(), mObjects.get(position).getName());
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mObjects.get(position).getName();
    }
}
