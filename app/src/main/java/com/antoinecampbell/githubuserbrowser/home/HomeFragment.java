package com.antoinecampbell.githubuserbrowser.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.detail.DetailActivity;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.model.UsersResponse;
import com.antoinecampbell.githubuserbrowser.service.DaggerServiceComponent;
import com.antoinecampbell.githubuserbrowser.service.GithubService;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeFragment extends Fragment
        implements Callback<UsersResponse>, AdapterView.OnItemClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private static final int ITEMS_PER_PAGE = 50;
    private int page;
    // Purposely set to default visibility so it is available in the test which shares the package
    ListAdapter adapter;
    @Inject
    GithubService githubService;

    @InjectView(android.R.id.empty)
    View emptyView;
    @InjectView(R.id.fragment_home_gridview)
    GridView gridView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            page = 1;
            adapter = null;
            githubService = null;
        }
        DaggerServiceComponent.create().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);

        gridView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        githubService.getCharlotteUsers(page, ITEMS_PER_PAGE, this);
    }

    @Override
    public void success(UsersResponse usersResponse, Response response) {
        if (usersResponse.getItems() != null) {
            adapter = new HomeFragmentGridViewAdapter(getActivity(), usersResponse.getItems());
            gridView.setEmptyView(emptyView);
            gridView.setAdapter(adapter);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        adapter = new HomeFragmentGridViewAdapter(getActivity(), new ArrayList<User>());
        gridView.setEmptyView(emptyView);
        gridView.setAdapter(adapter);
        Log.e(TAG, error.getMessage());
        Log.e(TAG, error.getUrl());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = (User) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtras(DetailActivity.newInstanceBundle(user));
        startActivity(intent);
    }
}
