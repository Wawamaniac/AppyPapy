package com.android.appypapy.persistence;

import com.android.appypapy.utils.AppyLog;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by kln on 04/12/2016.
 */

public class AppyPapyDao<Model, ID> extends BaseDaoImpl<Model, ID>
{

    private static final String TAG = AppyPapyDao.class.getSimpleName();

    protected AppyPapyDao(Class<Model> dataClass) throws SQLException
    {
	super(dataClass);
    }

    protected AppyPapyDao(ConnectionSource connectionSource, Class<Model> dataClass) throws SQLException
    {
	super(connectionSource, dataClass);
    }

    protected AppyPapyDao(ConnectionSource connectionSource, DatabaseTableConfig<Model> tableConfig) throws SQLException
    {
	super(connectionSource, tableConfig);
    }

    public static AppyPapyDao createDao(ConnectionSource connectionSource, Class modelClass)
    {
	try
	{
	    return new AppyPapyDao(connectionSource, modelClass);
	}
	catch (SQLException e)
	{
	    AppyLog.error(TAG, "createDao",
		    "Error while instantiating DAO of model " + modelClass.getSimpleName() + " : " + e.getMessage());
	}

	return null;
    }
}
