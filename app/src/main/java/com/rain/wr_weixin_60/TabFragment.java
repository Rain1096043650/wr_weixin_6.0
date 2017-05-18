package com.rain.wr_weixin_60;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created  on 2017/5/18.
 *
 * @author Rain
 */

public class TabFragment extends Fragment {


    public static final String TITLE = "title";
    private String mTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments()!= null){
            mTitle = getArguments().getString(TITLE);
        }

        TextView textView = new TextView(getActivity());
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.parseColor("#ffffffff"));
        textView.setText(mTitle);
        return textView;
    }
}
