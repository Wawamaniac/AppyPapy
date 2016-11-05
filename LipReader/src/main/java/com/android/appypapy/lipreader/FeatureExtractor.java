package com.android.appypapy.lipreader;

import org.opencv.core.Mat;

/**
 * Created by kln on 05/11/2016.
 */

public class FeatureExtractor
{

    private long mNativeObj = 0;

    public FeatureExtractor(String cascadeName, int w, int h)
    {
	this.mNativeObj = nativeCreateObject(cascadeName, w, h);
    }

    public void detect(Mat grayImage, Mat rgbaImage, int[] points)
    {
	nativeDetect(mNativeObj, grayImage.getNativeObjAddr(), rgbaImage.getNativeObjAddr(), points);
    }

    public void release()
    {
	nativeDestroyObject(this.mNativeObj);
	this.mNativeObj = 0;
    }

    private static native long nativeCreateObject(String cascadeName, int w, int h);

    private static native void nativeDestroyObject(long thiz);

    private static native void nativeDetect(long thiz, long grayImage, long rgbaImage, int[] points);
}
