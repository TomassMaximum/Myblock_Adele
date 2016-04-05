package com.example.make201512.makeblock_adele;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by make201512 on 2016/4/2.
 */
public class WidgetFragment extends Fragment {

    private static final String TAG = "WidgetFragment";

    TextView textView;

    int position;

    ExpandableLayout ExpandableViewGroup;

    ExpandableLayout moveWidgets;
    ExpandableLayout motorsWidgets;
    ExpandableLayout servoWidgets;

    ImageView joystickIcon;

    int[] widgetsIconId = {R.drawable.ic_joystick,R.drawable.ic_switch_ultrasonic,R.drawable.ic_switch_line_follow,
                    R.drawable.};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_widget_page,container,false);

        position = getArguments().getInt("position");

        textView = (TextView) rootView.findViewById(R.id.widget_page);

        textView.setText("Fragment" + position);

        moveWidgets = (ExpandableLayout) rootView.findViewById(R.id.widget_page_linear_layout);

        for (int i = 0;i < 10;i++){
            TextView textView = new TextView(getActivity());
            textView = (TextView) setLayoutParams(textView);
            textView.setText("TEXT" + i);
            moveWidgets.addView(textView);
        }

        return rootView;
    }

    public View setLayoutParams(View view){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        layoutParams.topMargin = (int) (8 * scale + 0.5f);
        view.setLayoutParams(layoutParams);

        return view;
    }

}
