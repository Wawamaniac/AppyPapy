package com.android.appypapy.ui.popup;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kln on 01/03/2016.
 */
public class AppyToast
{

    public static void toast(Context context, int stringRes, boolean longDuration)
    {
	toast(context, context.getString(stringRes), longDuration);
    }

    public static void toast(Context context, String message, boolean longDuration)
    {
	Toast.makeText(context, message, longDuration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

}
