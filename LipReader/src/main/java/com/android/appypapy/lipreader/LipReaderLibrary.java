package com.android.appypapy.lipreader;

import android.content.Context;
import android.view.SurfaceView;
import com.android.applypapy.lipreader.R;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kln on 05/11/2016.
 */

public class LipReaderLibrary
{
    private static final String TAG = LipReaderLibrary.class.getSimpleName();

    private FeatureExtractor featureExtractor;

    private LipReaderLibrary()
    {

    }

    public FeatureExtractor getFeatureExtractor()
    {
	return this.featureExtractor;
    }

    public static LipReaderLibrary get(Context context, SurfaceView cameraView)
    {
	LipReaderLibrary lipReaderLibrary = new LipReaderLibrary();

	try
	{
	    InputStream is = context.getResources().openRawResource(R.raw.haarcascade_mcs_mouth);
	    File cascadeDir = context.getDir(Constants.MouthDetector.TEMP_DIRECTORY, Context.MODE_PRIVATE);
	    File cascadeFile = new File(cascadeDir, Constants.MouthDetector.TEMP_FILENAME);

	    if (cascadeFile.exists())
	    {
		// TODO : log
		if (cascadeFile.delete())
		{
		    // TODO : log
		}
	    }

	    if (cascadeFile.createNewFile())
	    {
		// TODO : log
	    }

	    IOUtils.copy(is, new FileOutputStream(cascadeFile));

	    lipReaderLibrary.featureExtractor =
		    new FeatureExtractor(cascadeFile.getAbsolutePath(), cameraView.getHeight(), cameraView.getWidth());
	}
	catch (IOException e)
	{
	    LipReaderLogger.exception(TAG, "init", "Error while managing cascade file", e);
	}

	return lipReaderLibrary;
    }

}
