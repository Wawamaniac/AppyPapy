package com.android.appypapy.ui.view;

import android.content.Context;
import android.graphics.Camera;
import android.util.AttributeSet;
import org.opencv.android.CameraBridgeViewBase;

public class CameraBridgeView extends CameraBridgeViewBase
{
    public CameraBridgeView(Context context, int cameraId)
    {
	super(context, cameraId);
    }

    public CameraBridgeView(Context context, AttributeSet attrs)
    {
	super(context, attrs);
    }

    @Override
    protected boolean connectCamera(int width, int height)
    {
	return true;
    }

    @Override
    protected void disconnectCamera()
    {

    }
}
