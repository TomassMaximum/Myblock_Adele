package com.example.make201512.makeblock_adele;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by make201512 on 2016/4/8.
 */
public class WidgetImageView extends ImageView {

    private static final String TAG = "WidgetImageView";

    private float[] originPosition;

    float originX;
    float originY;

    public WidgetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public float[] getOriginPosition(){
        originPosition = new float[]{originX,originY};
        return originPosition;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = MotionEventCompat.getActionMasked(event);

        switch (action){
            case MotionEvent.ACTION_DOWN:{

                Log.e(TAG,"ACTION_DOWN BEING CALLED");

                final int index = MotionEventCompat.getActionIndex(event);

                final float x = MotionEventCompat.getX(event,index);
                final float y = MotionEventCompat.getY(event,index);

                originX = x;
                originY = y;

                break;
            }

            case MotionEvent.ACTION_MOVE:{
                Log.e(TAG,"ACTION_MOVE BEING CALLED");

                break;
            }


        }

        return true;
    }
}
