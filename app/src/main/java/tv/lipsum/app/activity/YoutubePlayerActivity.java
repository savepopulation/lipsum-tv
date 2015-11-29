

package tv.lipsum.app.activity;


import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import tv.lipsum.app.R;
import tv.lipsum.app.application.LipsumApp;
import tv.lipsum.app.constants.ApplicationConstants;


public class YoutubePlayerActivity extends YouTubeFailureRecoveryActivity {
    private String mVideoId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerview_demo);

        mVideoId = getIntent().getStringExtra(ApplicationConstants.BUNDLE_ID);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(ApplicationConstants.API_KEY_ANDROID, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            if (!TextUtils.isEmpty(mVideoId)) {
                player.loadVideo(mVideoId);
            }
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker mTracker = ((LipsumApp) getApplication()).getDefaultTracker();
        mTracker.setScreenName(getString(R.string.screen_name_video));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
