package com.android.appypapy;

import android.app.Application;
import android.content.Context;

/**
 * Created by kln on 30/10/2016.
 */

public class AppyPapyApplication extends Application
{

    private static Context APPLICATION_CONTEXT;

    @Override
    public void onCreate()
    {
	super.onCreate();

	APPLICATION_CONTEXT = getApplicationContext();

	System.loadLibrary("feature_extractor");
    }

    public static Context getAppyPapyApplicationContext()
    {
	return APPLICATION_CONTEXT;
    }
}
