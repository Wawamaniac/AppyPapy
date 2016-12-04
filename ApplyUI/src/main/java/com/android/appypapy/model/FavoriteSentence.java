package com.android.appypapy.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by kln on 27/11/2016.
 */
@DatabaseTable(tableName = "favorite_sentences")
public class FavoriteSentence
{

    @DatabaseField(columnName = "id", generatedId = true, id = true)
    protected int id;

    @DatabaseField(columnName = "folder", dataType = DataType.STRING)
    protected String folder;

    @DatabaseField(columnName = "sentence", dataType = DataType.STRING)
    protected String sentence;

    public FavoriteSentence()
    {
	this.id = -1;
	this.folder = null;
	this.sentence = "";
    }

    public FavoriteSentence(String folder, String sentence)
    {
	this.id = -1;
	this.folder = folder;
	this.sentence = sentence;
    }

    public FavoriteSentence(int id, String folder, String sentence)
    {
	this.id = id;
	this.folder = folder;
	this.sentence = sentence;
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public String getFolder()
    {
	return folder;
    }

    public void setFolder(String folder)
    {
	this.folder = folder;
    }

    public String getSentence()
    {
	return sentence;
    }

    public void setSentence(String sentence)
    {
	this.sentence = sentence;
    }
}
