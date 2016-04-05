package com.example.make201512.makeblock_adele;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.CardView;
import android.util.Log;
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

    ExpandableLayout expandableViewGroup;

    ExpandableLayout moveWidgets;
    ExpandableLayout motorsWidgets;
    ExpandableLayout servoWidgets;

    ImageView joystickIcon;

    //Move模块含有的控件组
    int[] robotWidgetsIconId;
    int[] motorsWidgetsIconId;
    int[] servoWidgetsIconId;

    //Display模块含有的控件组
    int[] ledDiscWidgetsIconId;
    int[] ledStripWidgetsIconId;
    int[] buzzerWidgetsIconId;
    int[] sevenSegmentLedWidgetsIconId;

    //Sense模块含有的控件组
    int[] ultrasonicSensorWidgetsIconId;
    int[] brightnessSensorWidgetsIconId;
    int[] loudnessSensorWidgetsIconId;
    int[] temperatureSensorWidgetsIconId;
    int[] radarWarningWidgetsIconId;
    int[] speedBarWidgetsIconId;
    int[] linefollowSensorStateWidgetIconId;

    //Control模块含有的控件组
    int[] switchStateWidgetsIconId;
    int[] pirStateWidgetsIconId;
    int[] buttonStateWidgetsIconId;
    int[] limitSwitchStateWidgetsIconId;
    int[] joystickXValueWidgetsIconId;
    int[] joystickYValueWidgetsIconId;
    int[] potentiometerWidgetsIconId;

    //Custom模块含有的控件组
    int[] joystickWidgetsIconId;
    int[] buttonWidgetsIconId;
    int[] switchWidgetsIconId;
    int[] sliderWidgetsIconId;
    int[] displayWidgetsIconId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_widget_page,container,false);

        position = getArguments().getInt("position");

        textView = (TextView) rootView.findViewById(R.id.widget_page);

        textView.setText("Fragment" + position);

        expandableViewGroup = (ExpandableLayout) rootView.findViewById(R.id.widget_page_linear_layout);

        getCorrespondingWidgetsGroup("");

        for (int i = 0;i < robotWidgetsIconId.length;i++){

            Bitmap widget = BitmapFactory.decodeResource(getResources(),robotWidgetsIconId[i]);
            ImageView widgetHolder = new ImageView(getActivity());
            widgetHolder.setImageBitmap(widget);
            setLayoutParams(widgetHolder);

            expandableViewGroup.addView(widgetHolder);

        }

        return rootView;
    }

    public void setLayoutParams(View view){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        layoutParams.topMargin = (int) (8 * scale + 0.5f);

        Log.e(TAG,"Margin为" + layoutParams.topMargin + "");

        view.setLayoutParams(layoutParams);
    }

    public void getCorrespondingWidgetsGroup(String mainBoard){
        switch (mainBoard){
            case "mbot":{
                break;
            }

            default:{
                robotWidgetsIconId = new int[]{R.drawable.ic_joystick,R.drawable.ic_direction,R.drawable.ic_switch_ultrasonic,R.drawable.ic_switch_line_follow};
                break;
            }
        }
    }

}
