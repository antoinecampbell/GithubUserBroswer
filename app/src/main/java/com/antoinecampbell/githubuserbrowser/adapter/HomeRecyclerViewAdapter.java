package com.antoinecampbell.githubuserbrowser.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.activity.DetailActivity;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.service.ServiceUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewHolder>
        implements OnHomeRecyclerViewItemClick {

    private static final String TAG = HomeRecyclerViewAdapter.class.getSimpleName();

    private List<User> users;
    private Context context;
    Picasso picasso;
    private int imageSize;

    public HomeRecyclerViewAdapter(Context context, List<User> users) {
        super();
        this.users = users;
        this.context = context;
        imageSize = context.getResources().getInteger(R.integer.cardview_iamge_size);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        imageSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageSize, displayMetrics);
        Log.d(TAG, "ImageSize: " + imageSize);

        picasso = ServiceUtil.getPicasso(context);
    }

    @Override
    public HomeRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_recyclerview_item, parent, false);

        return new HomeRecyclerViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(HomeRecyclerViewHolder holder, int position) {
        User user = users.get(position);
        holder.usernameTextView.setText(user.getLogin());
        if (!TextUtils.isEmpty(user.getAvatarUrl())) {
            Uri imageUri = ServiceUtil.getSizedImageUri(context, user.getAvatarUrl(), imageSize);
            picasso.load(imageUri).placeholder(R.mipmap.ic_launcher).into(holder.avatarImageView);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void onItemClicked(int position) {
        User user = users.get(position);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtras(DetailActivity.newInstanceBundle(user));
        context.startActivity(intent);
    }

    static class HomeRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView avatarImageView;
        public TextView usernameTextView;
        public OnHomeRecyclerViewItemClick listener;

        public HomeRecyclerViewHolder(View itemView, OnHomeRecyclerViewItemClick listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.avatarImageView = (ImageView) itemView.findViewById(R.id.home_recyclerview_item_avatar_imageview);
            this.usernameTextView = (TextView) itemView.findViewById(R.id.home_recyclerview_item_username_textview);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if(listener != null) {
                listener.onItemClicked(getPosition());
            }
        }
    }

}
