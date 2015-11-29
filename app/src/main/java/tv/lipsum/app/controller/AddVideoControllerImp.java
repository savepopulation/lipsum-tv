package tv.lipsum.app.controller;

import tv.lipsum.app.interfaces.AddVideoController;
import tv.lipsum.app.interfaces.AddVideoView;
import tv.lipsum.app.parse.Suggestion;
import tv.lipsum.app.utils.Tools;

/**
 * Created by tyln on 25.11.15.
 */
public class AddVideoControllerImp implements AddVideoController {
    private AddVideoView mView;

    public AddVideoControllerImp(AddVideoView mView) {
        this.mView = mView;
    }


    @Override
    public void suggestVideo(String youtubeUrl, String sender) {
        if (Tools.isYoutubeLink(youtubeUrl)) {
            mView.showIndicator();
            mView.onVideoAdded();
            Suggestion mSuggestion = new Suggestion();
            mSuggestion.setUrl(youtubeUrl);
            mSuggestion.setSender(sender);
            mSuggestion.setIsViewed(false);
            mSuggestion.setIsDeleted(false);
            mSuggestion.saveInBackground();
        } else {
            mView.onInvalidYoutubeUrl();
        }
    }
}
