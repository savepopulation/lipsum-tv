package tv.lipsum.app.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import tv.lipsum.app.R;
import tv.lipsum.app.activity.BaseActivity;
import tv.lipsum.app.activity.DashboardActivity;
import tv.lipsum.app.adapter.CategoryAdapter;
import tv.lipsum.app.constants.ApplicationConstants;
import tv.lipsum.app.controller.VideosControllerImp;
import tv.lipsum.app.interfaces.QuickReturnListener;
import tv.lipsum.app.interfaces.VideosController;
import tv.lipsum.app.interfaces.VideosView;
import tv.lipsum.app.parse.ParseVideo;
import tv.lipsum.app.utils.HidingScrollListener;
import tv.lipsum.app.utils.Tools;

/**
 * Created by tyln on 20.10.2015.
 */
public class CategoryFragment extends BaseFragment implements VideosView, QuickReturnListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String BUNDLE_NAME = "name";
    private int mId;
    private String mName;
    private ProgressBar progressBar;
    private VideosController mController;
    private final List<ParseVideo> mVideos = new ArrayList<>();
    private CategoryAdapter mAdapter;
    private HidingScrollListener mHidingScrollListener;
    private SwipeRefreshLayout mSwipeLayout;

    public static CategoryFragment newInstance(int id, String name) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ApplicationConstants.BUNDLE_ID, id);
        args.putString(BUNDLE_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mId = args.getInt(ApplicationConstants.BUNDLE_ID, 0);
            mName = args.getString(BUNDLE_NAME, "");
        }
        mController = new VideosControllerImp(this);
        mAdapter = new CategoryAdapter(mVideos, getActivity());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_category;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (view == null)
            return;


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe);
        mSwipeLayout.setProgressViewOffset(false, 0, (int) getActivity().getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        mSwipeLayout.setOnRefreshListener(this);
        initRecyclerView(view);
        mController.onVideosRequested(mId, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mName)) {
            ((BaseActivity) getActivity()).track(mName);
        }
    }

    @Override
    public void onVideosReceived(List<ParseVideo> objects) {
        mVideos.clear();
        mVideos.addAll(objects);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHideRefreshLayout(boolean isShow) {
        mSwipeLayout.setRefreshing(isShow);
    }

    @Override
    public void showIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDefaultError(String e) {
        alert(e);
    }

    private void initRecyclerView(View view) {
        final int mToolbarHeight = Tools.getToolbarHeight(getActivity());
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        ((DashboardActivity) getActivity()).mViewPager.setPadding(mRecyclerView.getPaddingLeft(),
                mToolbarHeight,
                mRecyclerView.getPaddingRight(),
                mRecyclerView.getPaddingBottom());

        mHidingScrollListener = new HidingScrollListener(getActivity(), linearLayoutManager) {
            @Override
            public void onMoved(int distance) {
                ((DashboardActivity) getActivity()).mToolbarContainer.setTranslationY(-distance);
            }

            @Override
            public void onShow() {
                ((DashboardActivity) getActivity()).mToolbarContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void onHide() {
                ((DashboardActivity) getActivity()).mToolbarContainer.animate()
                        .translationY(-mToolbarHeight)
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();
            }

            @Override
            public void onLoadMore() {
            }
        };
        mRecyclerView.setOnScrollListener(mHidingScrollListener);
    }

    @Override
    public void onReturn() {
        mHidingScrollListener.onShow();
    }

    @Override
    public void onRefresh() {
        mController.onVideosRequested(mId, true);
    }
}
