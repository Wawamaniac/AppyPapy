package com.android.appypapy.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.android.appypapy.AppyPapyApplication;
import com.android.appypapy.utils.AppyLog;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class PersistenceManager
{
    private static final String TAG = PersistenceManager.class.getSimpleName();

    private static final PersistenceManager SINGLETON = new PersistenceManager();

    private AppyPapyDatabaseHelper appyPapyDatabaseHelper = null;
    private SQLiteDatabase appyPapyDatabase = null;

    protected PersistenceManager()
    {

    }

    public static PersistenceManager getManager()
    {
	return SINGLETON;
    }

    public <T> List<T> getAll(Class modelClass)
    {
	ConnectionSource connectionSource = initDatabaseConnection(modelClass);
	Dao<T, Integer> modelDao = AppyPapyDao.createDao(connectionSource, modelClass);

	if (modelDao != null)
	{
	    try
	    {
		return modelDao.queryForAll();
	    }
	    catch (SQLException e)
	    {
		AppyLog.exception(TAG, "getById", "Exception while trying to get data", e);
		return null;
	    }
	}
	else
	{
	    String error = "Model DAO of class " + modelClass.getSimpleName() +
		    " is null, cannot get model.";

	    AppyLog.exception(TAG, "getById", error, new NullPointerException(error));

	    return null;
	}
    }

    public <T> T getById(Class modelClass, int id)
    {
	ConnectionSource connectionSource = initDatabaseConnection(modelClass);
	Dao<T, Integer> modelDao = AppyPapyDao.createDao(connectionSource, modelClass);

	if (modelDao != null)
	{
	    try
	    {
		return modelDao.queryForId(id);
	    }
	    catch (SQLException e)
	    {
		AppyLog.exception(TAG, "getById", "Exception while trying to get data", e);
		return null;
	    }
	}
	else
	{
	    String error = "Model DAO of class " + modelClass.getSimpleName() +
		    " is null, cannot get model.";

	    AppyLog.exception(TAG, "getById", error, new NullPointerException(error));

	    return null;
	}
    }

    public <T> boolean upsert(T model)
    {
	ConnectionSource connectionSource = initDatabaseConnection(model.getClass());
	Dao<T, Integer> modelDao = AppyPapyDao.createDao(connectionSource, model.getClass());

	if (modelDao != null)
	{
	    try
	    {
		Dao.CreateOrUpdateStatus status = modelDao.createOrUpdate(model);

		return status.isCreated() || status.isUpdated();
	    }
	    catch (SQLException e)
	    {
		AppyLog.exception(TAG, "upsert", "Exception while trying to upsert data", e);
		return false;
	    }
	}
	else
	{
	    String error = "Model DAO of class " + model.getClass().getSimpleName() +
		    " is null, cannot upsert model.";

	    AppyLog.exception(TAG, "upsert", error, new NullPointerException(error));

	    return false;
	}
    }

    public <T> boolean delete(T model)
    {
	ConnectionSource connectionSource = initDatabaseConnection(model.getClass());
	Dao<T, Integer> modelDao = AppyPapyDao.createDao(connectionSource, model.getClass());

	if (modelDao != null)
	{
	    try
	    {
		int status = modelDao.delete(model);

		if (status > 1)
		{
		    AppyLog.warn(TAG, "delete",
			    "More than one row deleted when deleting model " + model.getClass().getSimpleName());
		}

		return status > 0;
	    }
	    catch (SQLException e)
	    {
		AppyLog.exception(TAG, "delete", "Exception while trying to delete data", e);
		return false;
	    }
	}
	else
	{
	    String error = "Model DAO of class " + model.getClass().getSimpleName() +
		    " is null, cannot delete model.";

	    AppyLog.exception(TAG, "delete", error, new NullPointerException(error));

	    return false;
	}
    }

    protected ConnectionSource initDatabaseConnection(Class model)
    {
	ConnectionSource connectionSource = new AndroidConnectionSource(getApplyPapyDatabase());

	try
	{
	    TableUtils.createTableIfNotExists(connectionSource, model);
	}
	catch (SQLException e)
	{
	    AppyLog.exception(TAG, "initDatabaseConnection", "Error while trying to create database if not existed",
		    e);
	}

	return connectionSource;
    }

    protected SQLiteDatabase getApplyPapyDatabase()
    {
	if (this.appyPapyDatabase != null)
	{
	    return this.appyPapyDatabase;
	}
	else if (this.appyPapyDatabaseHelper != null)
	{
	    this.appyPapyDatabase = this.appyPapyDatabaseHelper.getWritableDatabase();
	    return this.appyPapyDatabase;
	}
	else
	{
	    this.appyPapyDatabaseHelper =
		    new AppyPapyDatabaseHelper(AppyPapyApplication.getAppyPapyApplicationContext(),
			    AppyPapyDatabaseHelper.DATABASE_NAME, null, AppyPapyDatabaseHelper.DATABASE_VERSION,
			    new AppyPapyDatabaseErrorHandler());
	    this.appyPapyDatabase = this.appyPapyDatabaseHelper.getWritableDatabase();
	    return this.appyPapyDatabase;
	}
    }

}
