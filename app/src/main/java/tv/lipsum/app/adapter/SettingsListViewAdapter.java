package tv.lipsum.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import tv.lipsum.app.R;
import tv.lipsum.app.application.LipsumApp;
import tv.lipsum.app.model.SettingsItem;

/**
 * Created by tyln on 28.10.2015.
 */
public class SettingsListViewAdapter extends ArrayAdapter<SettingsItem> {
    public static final int TYPE_ICON = 0;
    public static final int TYPE_SWITCH = 1;
    private Context context;
    private int resId;

    public SettingsListViewAdapter(Context context, List<SettingsItem> items, int resId) {
        super(context, resId, items);
        this.context = context;
        this.resId = resId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(resId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textviewTitle = (TextView) convertView.findViewById(R.id.textview_title);
            viewHolder.imageviewIcon = (ImageView) convertView.findViewById(R.id.imageview_icon);
            viewHolder.switchItem = (Switch) convertView.findViewById(R.id.layout_switch);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final SettingsItem item = getItem(position);
        if (item != null && viewHolder != null) {
            viewHolder.textviewTitle.setText(item.getTitle());
            viewHolder.imageviewIcon.setImageResource(item.getIconId());
            if (item.getType() == TYPE_ICON) {
                viewHolder.switchItem.setVisibility(View.GONE);
            } else {
                viewHolder.switchItem.setChecked(LipsumApp.mSharedPrefHelper.getBoolean(String.valueOf(item.getId())));
                viewHolder.switchItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        LipsumApp.mSharedPrefHelper.saveBoolean(String.valueOf(item.getId()), viewHolder.switchItem.isChecked());
                    }
                });
                viewHolder.switchItem.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    public static class ViewHolder {
        private TextView textviewTitle;
        private ImageView imageviewIcon;
        private Switch switchItem;
    }
}