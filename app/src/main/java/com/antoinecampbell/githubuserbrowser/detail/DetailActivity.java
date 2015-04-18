package com.antoinecampbell.githubuserbrowser.detail;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.model.User;

public class DetailActivity extends ActionBarActivity {

    private static final String KEY_ARG_USER = "KEY_ARG_USER";

    public static Bundle newInstanceBundle(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ARG_USER, user);

        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        User user = getIntent().getExtras().getParcelable(KEY_ARG_USER);
        setTitle(user.getLogin());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_detail_fragment_container,
                            DetailFragment.newInstance(user)).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
