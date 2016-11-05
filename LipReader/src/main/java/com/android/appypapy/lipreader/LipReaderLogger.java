package com.android.appypapy.lipreader;

import android.util.Log;

public class LipReaderLogger
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

    public static void exception(String szClass, String szMethod, String szMessage, Throwable e)
    {
	Log.e(szClass, szMethod + " - " + szMessage + " : " + e.getMessage(), e);
    }
}
