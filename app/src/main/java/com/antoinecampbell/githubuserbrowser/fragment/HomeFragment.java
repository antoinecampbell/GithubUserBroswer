package com.antoinecampbell.githubuserbrowser.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.adapter.HomeRecyclerViewAdapter;
import com.antoinecampbell.githubuserbrowser.model.UsersResponse;
import com.antoinecampbell.githubuserbrowser.service.GithubService;
import com.antoinecampbell.githubuserbrowser.service.ServiceUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeFragment extends Fragment implements Callback<UsersResponse>, View.OnClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final int ITEMS_PER_PAGE = 50;

    int page;

    @InjectView(R.id.home_fragment_recyclerview)
    RecyclerView recyclerView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            page = 1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(view.getContext(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        GithubService githubService = ServiceUtil.getGithubService();
        githubService.getCharlotteUsers(page, ITEMS_PER_PAGE, this);

        // Use sample response stored in assets file instead of calling service
        /*
        ObjectMapper mapper = ServiceUtil.getObjectMapper();
        try {
            UsersResponse response = mapper.readValue(getActivity().getAssets().open("response.json"), UsersResponse.class);
            success(response, null);
        } catch (IOException e) {
            Log.e(TAG, "Error loading json", e);
        }
        */

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void success(UsersResponse usersResponse, Response response) {
        if (usersResponse.getItems() != null) {
            recyclerView.setAdapter(new HomeRecyclerViewAdapter(getActivity(), usersResponse.getItems()));
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(TAG, error.getMessage());
        Log.e(TAG, error.getUrl());
    }

    @Override
    public void onClick(View v) {

    }
}
