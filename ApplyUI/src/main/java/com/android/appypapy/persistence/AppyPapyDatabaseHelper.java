package com.android.appypapy.persistence;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kln on 04/12/2016.
 */
public class AppyPapyDatabaseHelper extends SQLiteOpenHelper
{

    protected static final String DATABASE_NAME = "APPY_PAPY_DATABASE";
    protected static final int DATABASE_VERSION = 1;

    protected AppyPapyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
	super(context, name, factory, version);
    }

    protected AppyPapyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
				     DatabaseErrorHandler errorHandler)
    {
	super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
