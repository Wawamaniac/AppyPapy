package com.android.appypapy.ui.callback;

import android.content.Context;
import com.android.appypapy.utils.AppyLog;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;

public class OpenCvLoaderCallback extends BaseLoaderCallback
{

    private static final String TAG = OpenCvLoaderCallback.class.getSimpleName();

    protected CameraBridgeViewBase mCameraBridgeViewBase;

    public OpenCvLoaderCallback(Context AppContext, CameraBridgeViewBase mCameraBridgeViewBase)
    {
	super(AppContext);
	this.mCameraBridgeViewBase = mCameraBridgeViewBase;
    }

    @Override
    public void onManagerConnected(int iStatus)
    {
	switch (iStatus)
	{
	    case LoaderCallbackInterface.SUCCESS:
		AppyLog.info(TAG, "onManagerConnected", "OpenCV loaded successfully");
		this.mCameraBridgeViewBase.enableView();
		break;
	    default:
		AppyLog.error(TAG, "onManagerConnected", "OpenCV not loaded");
		super.onManagerConnected(iStatus);
		break;
	}
    }
}
