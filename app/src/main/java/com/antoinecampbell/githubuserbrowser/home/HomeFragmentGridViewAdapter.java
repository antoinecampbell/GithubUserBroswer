package com.antoinecampbell.githubuserbrowser.home;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.service.ServiceUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeFragmentGridViewAdapter extends BaseAdapter {

    private static final String TAG = HomeFragmentGridViewAdapter.class.getSimpleName();

    private LayoutInflater mLayoutInflater;
    private List<User> users;
    private int mImageSize;
    private Picasso mPicasso;

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    public HomeFragmentGridViewAdapter(Context context, List<User> users) {
        this.users = users;
        mLayoutInflater = LayoutInflater.from(context);

        int imageSize = context.getResources().getInteger(R.integer.cardview_iamge_size);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mImageSize = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageSize, displayMetrics);
        Log.d(TAG, "ImageSize: " + mImageSize);

        mPicasso = ServiceUtils.getPicasso(context);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.home_grid_item, parent, false);
            holder = new ViewHolder();
            holder.imageView =
                    (ImageView) convertView
                            .findViewById(R.id.home_grid_item_avatar_imageview);
            holder.textView =
                    (TextView) convertView
                            .findViewById(R.id.home_grid_item_username_textview);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = getItem(position);
        holder.textView.setText(user.getLogin());
        if (!TextUtils.isEmpty(user.getAvatarUrl())) {
            Uri imageUri = ServiceUtils
                    .getSizedImageUri(parent.getContext(), user.getAvatarUrl(), mImageSize);
            mPicasso.load(imageUri).into(holder.imageView);
        }


        return convertView;
    }


}
