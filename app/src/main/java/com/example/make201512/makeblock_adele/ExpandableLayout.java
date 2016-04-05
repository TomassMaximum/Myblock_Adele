package com.example.make201512.makeblock_adele;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by make201512 on 2016/4/5.
 */
public class ExpandableLayout extends ViewGroup {

    private static final String TAG = "ExpandingLayout";

    public ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取父容器设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        Log.e(TAG,"宽：" + sizeWidth + "高：" + sizeHeight);

        //设置为wrap_content时，记录宽高
        int width = 0;
        int height = 0;

        //本ViewGroup的总体高度
        int lineHeight = 0;

        //子View的数量
        int childCount = getChildCount();

        //循环测量所有子View的宽高
        for (int i = 0;i < childCount;i++){

            //获取到当前child
            View child = getChildAt(i);

            //测量当前child的宽高
            measureChild(child,widthMeasureSpec,heightMeasureSpec);

            //获取child的LayoutParams
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            //当前子View实际占据的宽度和高度
            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            lineHeight += childHeight;

        }

        setMeasuredDimension(sizeWidth,lineHeight);
        Log.e(TAG,"sizeWidth:" + sizeWidth + "lineHeight:" + lineHeight + "sizeHeight:" + sizeHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //总共的高度
        int lineHeight = 0;

        //子View的个数
        int childCount = getChildCount();

        //遍历所有的子View，布局位置
        for (int i = 0;i < childCount;i++){

            //当前子View
            View child = getChildAt(i);

            //当前子View的LayoutParams
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();

            //当前子View的宽高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //当前子View的margins
            int marginLeft = marginLayoutParams.leftMargin;
            int marginTop = marginLayoutParams.topMargin;
            int marginRight = marginLayoutParams.rightMargin;
            int marginBottom = marginLayoutParams.bottomMargin;

            //当前子View的布局四周位置
            int left = marginLeft;
            int top = lineHeight;
            int right = marginLeft + childWidth + marginRight;

            //当前已布局子View布局整体高度
            lineHeight += childHeight + marginTop + marginBottom;
            int bottom = lineHeight;

            //布局当前子View
            child.layout(left,top,right,bottom);

            Log.e(TAG,"上下左右边距为：" + top + "::" + bottom +"::" + left +"::" + right);

        }

    }

}
