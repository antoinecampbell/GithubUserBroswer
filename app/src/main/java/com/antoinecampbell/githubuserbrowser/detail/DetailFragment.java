package com.antoinecampbell.githubuserbrowser.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.service.ServiceUtils;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailFragment extends Fragment {

    private static final String KEY_ARG_USER = "KEY_ARG_USER";
    private User user;
    @InjectView(R.id.fragment_detail_avatar_imageview)
    ImageView avatarImageView;

    public static DetailFragment newInstance(User user) {
        DetailFragment fragment = new DetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(KEY_ARG_USER, user);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = getArguments().getParcelable(KEY_ARG_USER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.inject(this, view);

        Picasso picasso = ServiceUtils.getPicasso(getActivity());
        int imageSize = getActivity().getResources().getInteger(R.integer.cardview_iamge_size);
        Uri imageUri = ServiceUtils.getSizedImageUri(getActivity(), user.getAvatarUrl(), imageSize);
        picasso.load(imageUri).into(avatarImageView);

        return view;
    }
}
