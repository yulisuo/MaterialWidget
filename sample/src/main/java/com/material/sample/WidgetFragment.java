package com.material.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by IntelliJ IDEA.
 * User: keith.
 * Date: 14-9-30.
 * Time: 9:48.
 */
public class WidgetFragment extends Fragment {

    private int mResourceId;

    public static WidgetFragment newInstance(int resourceId) {
        WidgetFragment widgetFragment = new WidgetFragment();
        widgetFragment.mResourceId = resourceId;
        return widgetFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(mResourceId, container, false);
    }
}
