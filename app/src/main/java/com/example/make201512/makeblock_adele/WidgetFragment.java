package com.example.make201512.makeblock_adele;


import android.app.Fragment;
import android.content.Context;
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

    TextView textView;

    int position;

    CardView robotCardView;

    LinearLayout linearLayout;

    ImageView joystickIcon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_widget_page,container,false);

        position = getArguments().getInt("position");

        textView = (TextView) rootView.findViewById(R.id.widget_page);

        textView.setText("Fragment" + position);

        linearLayout = (LinearLayout) rootView.findViewById(R.id.widget_page_linear_layout);

        addCardView();

        linearLayout.addView(robotCardView);

        return rootView;
    }

    public void addCardView(){

        robotCardView = new CardView(getActivity());

        CardView.LayoutParams cardViewLayoutParams = new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout cardViewLinearLayout = new LinearLayout(getActivity());

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        cardViewLinearLayout.setLayoutParams(linearLayoutParams);

        joystickIcon = new ImageView(getActivity());
        joystickIcon.setImageResource(R.drawable.ic_joystick);

        cardViewLinearLayout.addView(joystickIcon);

        robotCardView.setLayoutParams(cardViewLayoutParams);

        robotCardView.addView(cardViewLinearLayout);

    }
}
