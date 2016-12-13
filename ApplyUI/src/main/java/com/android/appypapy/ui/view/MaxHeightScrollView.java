package com.android.appypapy.ui.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by kln on 28/11/2016.
 */

public class MaxHeightScrollView extends ScrollView
{
    public MaxHeightScrollView(Context context)
    {
	super(context);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs)
    {
	super(context, attrs);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
	super(context, attrs, defStyleAttr);
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
//    {
//	super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
	int rootViewHeight = getRootView().getHeight();

	if (rootViewHeight > 500)
	{
	    heightMeasureSpec = MeasureSpec.makeMeasureSpec(rootViewHeight * 3 / 4, MeasureSpec.AT_MOST);
	}
	else
	{
	    heightMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.AT_MOST);
	}

	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
