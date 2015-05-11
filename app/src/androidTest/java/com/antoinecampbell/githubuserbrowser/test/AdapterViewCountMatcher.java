package com.antoinecampbell.githubuserbrowser.test;

import android.view.View;
import android.widget.AdapterView;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Custom view matcher used to verify a ViewAdapter's item count
 */
public class AdapterViewCountMatcher extends BaseMatcher<View> {

    private int count;
    private int actualCount;

    public AdapterViewCountMatcher(int count) {
        this.count = count;
        actualCount = -1;
    }

    @Override
    public boolean matches(Object o) {
        if (o instanceof AdapterView) {
            AdapterView view = (AdapterView) o;
            actualCount = view.getCount();
            return view.getCount() == count;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        if (actualCount == -1) {
            description.appendText("with count: " + count);
        } else {
            description.appendText("with count: " + count + " and actual count: " + actualCount);
        }

    }
}
