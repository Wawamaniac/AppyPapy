package com.android.appypapy.utils;

import android.util.Log;

/**
 * Created by kln on 30/10/2016.
 */

public class AppyLog
{

    public static void debug(String szClass, String szMethod, String szMessage)
    {
	Log.d(szClass, szMethod + " - " + szMessage);
    }

    public static void info(String szClass, String szMethod, String szMessage)
    {
	Log.i(szClass, szMethod + " - " + szMessage);
    }

    public static void warn(String szClass, String szMethod, String szMessage)
    {
	Log.w(szClass, szMethod + " - " + szMessage);
    }

    public static void error(String szClass, String szMethod, String szMessage)
    {
	Log.e(szClass, szMethod + " - " + szMessage);
    }

    public static void error(String szClass, String szMethod, String szMessage, Throwable e)
    {
	Log.e(szClass, szMethod + " - " + szMessage + " : " + e.getMessage(), e);
    }

}
