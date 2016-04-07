package com.example.make201512.makeblock_adele;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    LinearLayout expandableViewGroup;

    CardView cardView;

    LinearLayout cardHolder;

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

    private static final String IMAGEVIEW_TAG = "ImageView";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_widget_page,container,false);

        position = getArguments().getInt("position");

        textView = (TextView) rootView.findViewById(R.id.widget_page);

        textView.setText("Fragment" + position);

        expandableViewGroup = (LinearLayout) rootView.findViewById(R.id.widget_page_linear_layout);

        getCorrespondingWidgetsGroup("");

        expandableViewGroup.setGravity(Gravity.CENTER_HORIZONTAL);

        cardView = new CardView(getActivity());

        cardView.setPreventCornerOverlap(true);
        cardView.setBackgroundColor(getResources().getColor(R.color.cardView));


        cardHolder = new LinearLayout(getActivity());

        cardHolder.setOrientation(LinearLayout.VERTICAL);

        cardHolder.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int i = 0;i < robotWidgetsIconId.length;i++){

            Bitmap widget = BitmapFactory.decodeResource(getResources(),robotWidgetsIconId[i]);
            Log.e(TAG, "Bitmap宽高为：" + widget.getWidth() + "::::" + widget.getHeight());
            ImageView widgetHolder = new ImageView(getActivity());

            widgetHolder.setImageBitmap(widget);
            setLayoutParams(widgetHolder);

            widgetHolder.setTag(IMAGEVIEW_TAG);

            widgetHolder.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    ImageView view = (ImageView) v;

                    ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

                    ClipData dragData = new ClipData((CharSequence)v.getTag(), new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},item);

//                    ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(v,
//                            PropertyValuesHolder.ofFloat("scaleX", 1.5f),
//                            PropertyValuesHolder.ofFloat("scaleY", 1.5f));
//
//                    scaleUp.setDuration(300);
//                    scaleUp.start();



                    View.DragShadowBuilder myShadow = new MyShadowBuilder(view,getActivity());

                    v.startDrag(dragData,myShadow,null,0);

                    return false;
                }
            });

            cardHolder.addView(widgetHolder);

        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        layoutParams.topMargin = (int) (8 * scale + 0.5f);
        layoutParams.leftMargin = (int) (8 * scale + 0.5f);
        layoutParams.rightMargin = (int) (8 * scale + 0.5f);

        cardView.setLayoutParams(layoutParams);

        cardView.addView(cardHolder);

        expandableViewGroup.addView(cardView);

        return rootView;
    }

    public void setLayoutParams(ImageView view){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        layoutParams.topMargin = (int) (8 * scale + 0.5f);
        layoutParams.leftMargin = (int) (8 * scale + 0.5f);
        layoutParams.rightMargin = (int) (8 * scale + 0.5f);

        Log.e(TAG, "Margin为" + layoutParams.topMargin + "");

        view.setLayoutParams(layoutParams);
        view.setMaxWidth((int) (120 * scale + 0.5f));
        view.setAdjustViewBounds(true);
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

    private static class MyShadowBuilder extends View.DragShadowBuilder{

        ImageView view;
        Context context;

        MyShadowBuilder(ImageView view,Context context){
            this.view = view;
            this.context = context;
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
//            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_joystick);

            shadowSize.set(view.getWidth() * 2, view.getHeight() * 2);

            shadowTouchPoint.set(shadowSize.x / 2, shadowSize.y / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
//            view.setMaxWidth(500);
//            view.setAdjustViewBounds(false);
//
//            view.setLayoutParams(new LinearLayout.LayoutParams(500,500));
//
//
//
//            Log.e(TAG,bitmap.getHeight() + "::::OOOOOOOOOOOOOOO:::" + bitmap.getWidth());
//
//            view.setImageBitmap(bitmap);
            if (view != null) {
                view.draw(canvas);
            } else {
                Log.e(TAG, "Asked to draw drag shadow but no view");
            }
        }
    }
}
