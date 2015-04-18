package com.antoinecampbell.githubuserbrowser.home;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.antoinecampbell.githubuserbrowser.R;

public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_home_fragment_container,
                            HomeFragment.newInstance(), HomeFragment.class.getSimpleName())
                    .commit();
        }
    }

}
