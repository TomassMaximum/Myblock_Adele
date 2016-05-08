package com.example.make201512.makeblock_adele;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by make201512 on 2016/4/14.
 */
public class MyImageView extends ImageView {

    boolean widgetIsTouched = false;

    public void setWidgetIsTouched(boolean status){
        widgetIsTouched = status;
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (widgetIsTouched){
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (widgetIsTouched){
            return super.dispatchTouchEvent(event);
        }
        return false;
    }
}
