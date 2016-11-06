package com.android.appypapy.ui;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.android.appypapy.R;
import com.android.appypapy.lipreader.FeatureExtractor;
import com.android.appypapy.lipreader.LipReaderContext;
import com.android.appypapy.lipreader.LipReaderLibrary;
import com.android.appypapy.lipreader.LipVideoSample;
import com.android.appypapy.ui.callback.BaseOpenCvCameraViewListener;
import com.android.appypapy.ui.callback.OpenCvLoaderCallback;
import com.android.appypapy.ui.generic.AppyActivity;
import com.android.appypapy.utils.AppyLog;
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
    protected Button mRecordButton;

    protected LipReaderLibrary mLipReaderLibrary;
    protected FeatureExtractor mFeatureExtractor;
    protected LipVideoSample mLipVideoSample;

    protected boolean mIsRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_video);

	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	this.mBaseOpenCvCameraViewListener = new BaseOpenCvCameraViewListener();

	this.mRecordButton = (Button) findViewById(R.id.record_button);
	this.mRecordButton.setOnClickListener(new View.OnClickListener()
	{
	    @Override
	    public void onClick(View view)
	    {
		onRecordButtonClicked();
	    }
	});

	this.mCameraBridgeViewBase = (JavaCameraView) findViewById(R.id.camera_bridge_view_base);
	this.mCameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
	this.mCameraBridgeViewBase.setCvCameraViewListener(this.mBaseOpenCvCameraViewListener);

	this.mCameraBridgeViewBase.post(new Runnable()
	{
	    @Override
	    public void run()
	    {
		VideoActivity.this.mLipReaderLibrary =
			LipReaderLibrary.get(getApplicationContext(), VideoActivity.this.mCameraBridgeViewBase);
		VideoActivity.this.mFeatureExtractor = VideoActivity.this.mLipReaderLibrary.getFeatureExtractor();
	    }
	});
    }

    protected void onRecordButtonClicked()
    {
	if (this.mIsRecording)
	{
	    LipReaderContext.init(getApplicationContext());
	    String szText = LipReaderContext.classify(this.mLipVideoSample);
	    AppyLog.info(TAG, "TEXT", szText);
	}
	else
	{
	    this.mLipVideoSample = new LipVideoSample("KLN" + System.currentTimeMillis());
	}

	this.mIsRecording = !this.mIsRecording;
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
