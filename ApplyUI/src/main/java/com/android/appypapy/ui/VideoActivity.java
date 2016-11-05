package com.android.appypapy.ui;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.WindowManager;
import com.android.appypapy.R;
import com.android.appypapy.lipreader.FeatureExtractor;
import com.android.appypapy.lipreader.LipReaderLibrary;
import com.android.appypapy.ui.callback.BaseOpenCvCameraViewListener;
import com.android.appypapy.ui.callback.OpenCvLoaderCallback;
import com.android.appypapy.ui.generic.AppyActivity;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;

/**
 * Created by kln on 29/10/2016.
 */

public class VideoActivity extends AppyActivity
{

    private static final String TAG = VideoActivity.class.getSimpleName();

    private JavaCameraView mCameraBridgeViewBase;

    protected BaseOpenCvCameraViewListener mBaseOpenCvCameraViewListener;

    protected LipReaderLibrary lipReaderLibrary;
    protected FeatureExtractor featureExtractor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_video);

	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	this.mBaseOpenCvCameraViewListener = new BaseOpenCvCameraViewListener();

	this.mCameraBridgeViewBase = (JavaCameraView) findViewById(R.id.camera_bridge_view_base);
	this.mCameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
	this.mCameraBridgeViewBase.setCvCameraViewListener(this.mBaseOpenCvCameraViewListener);

	this.mCameraBridgeViewBase.post(new Runnable()
	{
	    @Override
	    public void run()
	    {
		VideoActivity.this.lipReaderLibrary =
			LipReaderLibrary.get(getApplicationContext(), VideoActivity.this.mCameraBridgeViewBase);
		VideoActivity.this.featureExtractor = VideoActivity.this.lipReaderLibrary.getFeatureExtractor();
	    }
	});
    }

    @Override
    protected void onResume()
    {
	super.onResume();
	OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, getApplicationContext(),
		new OpenCvLoaderCallback(VideoActivity.this, this.mCameraBridgeViewBase));
    }


    @Override
    public void onPause()
    {
	super.onPause();
	if (this.mCameraBridgeViewBase != null)
	{
	    this.mCameraBridgeViewBase.disableView();
	}
    }

    public void onDestroy()
    {
	super.onDestroy();
	if (this.mCameraBridgeViewBase != null)
	{
	    this.mCameraBridgeViewBase.disableView();
	}
    }
}
