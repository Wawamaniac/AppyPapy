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

    @DatabaseField(columnName = "sentenceId", generatedId = true)
    protected int sentenceId;

    @DatabaseField(columnName = "folder", dataType = DataType.STRING)
    protected String folder;

    @DatabaseField(columnName = "sentence", dataType = DataType.STRING)
    protected String sentence;

    public FavoriteSentence()
    {
	this.sentenceId = -1;
	this.folder = null;
	this.sentence = "";
    }

    public FavoriteSentence(String folder, String sentence)
    {
	this.sentenceId = -1;
	this.folder = folder;
	this.sentence = sentence;
    }

    public FavoriteSentence(int id, String folder, String sentence)
    {
	this.sentenceId = id;
	this.folder = folder;
	this.sentence = sentence;
    }

    public int getSentenceId()
    {
	return sentenceId;
    }

    public void setSentenceId(int sentenceId)
    {
	this.sentenceId = sentenceId;
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
