package com.android.appypapy.persistence;

import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import com.android.appypapy.utils.AppyLog;

/**
 * Created by kln on 04/12/2016.
 */

public class AppyPapyDatabaseErrorHandler implements DatabaseErrorHandler
{

    private static final String TAG = AppyPapyDatabaseErrorHandler.class.getSimpleName();

    protected AppyPapyDatabaseErrorHandler()
    {

    }

    @Override
    public void onCorruption(SQLiteDatabase sqLiteDatabase)
    {
	// TODO : better error management
	AppyLog.error(TAG, "onCorruption", "Database " + sqLiteDatabase.getPath() + " is corrupted !");
    }
}
