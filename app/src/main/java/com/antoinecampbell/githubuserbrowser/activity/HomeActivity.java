package com.antoinecampbell.githubuserbrowser.activity;

import android.app.Activity;
import android.os.Bundle;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.fragment.HomeFragment;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance()).commit();
        }
    }

}
