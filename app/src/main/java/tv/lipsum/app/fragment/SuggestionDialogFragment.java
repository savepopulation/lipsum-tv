package tv.lipsum.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tv.lipsum.app.R;
import tv.lipsum.app.controller.AddVideoControllerImp;
import tv.lipsum.app.interfaces.AddVideoController;
import tv.lipsum.app.interfaces.AddVideoView;

/**
 * Created by tyln on 25.11.15.
 */
public class SuggestionDialogFragment extends DialogFragment implements View.OnClickListener, AddVideoView {
    public static final String TAG = "SuggestionDialogFragment";
    private EditText mEditTextYoutubeUrl;
    private EditText mEditTextSender;
    private Button mSendButton;
    private AddVideoController mAddVideoController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddVideoController = new AddVideoControllerImp(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_suggestion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (view == null)
            return;

        mEditTextYoutubeUrl = (EditText) view.findViewById(R.id.edittext_link);
        mEditTextSender = (EditText) view.findViewById(R.id.edittext_sender);
        mSendButton = (Button) view.findViewById(R.id.button_send);
        mSendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_send:
                mAddVideoController.suggestVideo(mEditTextYoutubeUrl.getText().toString().trim(), mEditTextSender.getText().toString().trim());
                break;
        }
    }

    @Override
    public void onVideoAdded() {
        if (isAdded()) {
            alert(getString(R.string.success_add_video));
            dismiss();
        }
    }

    @Override
    public void onInvalidYoutubeUrl() {
        if (isAdded()) {
            alert(getString(R.string.error_validation_youtube_url));
        }
    }

    @Override
    public void showIndicator() {
        mSendButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideIndicator() {

    }

    @Override
    public void onDefaultError(String e) {
        if (isAdded()) {
            alert(e);
        }
    }

    private void alert(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
