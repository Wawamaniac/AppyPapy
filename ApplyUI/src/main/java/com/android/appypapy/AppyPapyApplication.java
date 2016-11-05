package com.android.appypapy;

import android.Manifest;
import android.app.Application;
import com.android.appypapy.lipreader.LipReaderLibrary;

/**
 * Created by kln on 30/10/2016.
 */

public class AppyPapyApplication extends Application
{

    @Override
    public void onCreate()
    {
	super.onCreate();

	System.loadLibrary("feature_extractor");
    }
}
