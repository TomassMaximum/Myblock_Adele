package com.example.make201512.makeblock_adele;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by make201512 on 2016/4/14.
 */
public class MyScrollView extends ScrollView {

    boolean widgetIsLongClicked = false;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !widgetIsLongClicked && super.onTouchEvent(ev);
    }

    public void setWidgetIsLongClicked(boolean status){
        widgetIsLongClicked = status;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return !widgetIsLongClicked && super.dispatchTouchEvent(ev);
    }
}
