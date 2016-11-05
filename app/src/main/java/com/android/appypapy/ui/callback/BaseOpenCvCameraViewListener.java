package com.android.appypapy.ui.callback;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;

public class BaseOpenCvCameraViewListener implements CameraBridgeViewBase.CvCameraViewListener
{

    protected int mWidth;
    protected int mHeight;
    protected boolean bRunning;

    public BaseOpenCvCameraViewListener()
    {
	this.mWidth = -1;
	this.mHeight = -1;
	this.bRunning = false;
    }

    @Override
    public void onCameraViewStarted(int iWidth, int iHeight)
    {
	this.mWidth = iWidth;
	this.mHeight = iHeight;
	this.bRunning = true;
    }

    @Override
    public void onCameraViewStopped()
    {
	this.mWidth = -1;
	this.mHeight = -1;
	this.bRunning = false;
    }

    @Override
    public Mat onCameraFrame(Mat inputFrame)
    {
	return inputFrame;
    }
}
