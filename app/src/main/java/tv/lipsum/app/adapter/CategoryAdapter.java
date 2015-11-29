package tv.lipsum.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import tv.lipsum.app.R;
import tv.lipsum.app.helper.NavitagionHelper;
import tv.lipsum.app.parse.ParseVideo;

/**
 * Created by tyln on 21.10.2015.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<ParseVideo> mObjects;
    private Context mContext;


    public CategoryAdapter(List<ParseVideo> mObjects, Context mContext) {
        this.mObjects = mObjects;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_header, parent, false);
                return new RecyclerHeaderViewHolder(view);

            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.row_recyclerview_category, parent, false);
                return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isPositionHeader(position)) {
            ViewHolder mViewHolder = (ViewHolder) holder;
            ParseVideo mVideo = mObjects.get(position - 1);
            mViewHolder.textViewTitle.setText(mVideo.getName());
            if (!TextUtils.isEmpty(mVideo.getDesc())) {
                mViewHolder.textviewDesc.setText(mVideo.getDesc());
            } else {
                mViewHolder.textviewDesc.setText("");
            }
            ImageLoader.getInstance().displayImage(mVideo.getThumbNailUrl(), mViewHolder.imageViewThumbnail);
            mViewHolder.textviewDuration.setText(mVideo.getConvertedDuration());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return mObjects.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageViewThumbnail;
        private TextView textViewTitle;
        private TextView textviewDesc;
        private TextView textviewDuration;

        public ViewHolder(View convertView) {
            super(convertView);
            this.textViewTitle = (TextView) convertView.findViewById(R.id.textview_title);
            this.textviewDesc = (TextView) convertView.findViewById(R.id.textview_desc);
            this.imageViewThumbnail = (ImageView) convertView.findViewById(R.id.imageview_thumbnail);
            this.textviewDuration = (TextView) convertView.findViewById(R.id.textview_duration);
            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ParseVideo mVideo = mObjects.get(getPosition() - 1);
            NavitagionHelper.navigateToVideoActivity(mContext, mVideo.getId());
        }
    }

    public class RecyclerHeaderViewHolder extends RecyclerView.ViewHolder {
        public RecyclerHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}

