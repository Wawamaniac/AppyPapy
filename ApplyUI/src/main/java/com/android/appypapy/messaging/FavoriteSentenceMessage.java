package com.android.appypapy.messaging;

import android.os.Message;
import com.android.appypapy.model.FavoriteSentence;
import com.android.appypapy.utils.EnumUtils;

/**
 * Created by kln on 04/12/2016.
 */

public class FavoriteSentenceMessage extends AppyMessage
{

    protected FavoriteSentence favoriteSentence;
    protected FavoriteSentenceCrud favoriteSentenceCrud;

    public FavoriteSentenceMessage(FavoriteSentence favoriteSentence, FavoriteSentenceCrud favoriteSentenceCrud)
    {
	this.favoriteSentence = favoriteSentence;
	this.favoriteSentenceCrud = favoriteSentenceCrud;
    }

    public FavoriteSentenceMessage(Message message)
    {
	super(message);
	this.favoriteSentence = (FavoriteSentence) message.obj;
	this.favoriteSentenceCrud = EnumUtils.ordinalToFavoriteSentenceCrud(message.arg1);
    }

    public FavoriteSentence getFavoriteSentence()
    {
	return favoriteSentence;
    }

    public void setFavoriteSentence(FavoriteSentence favoriteSentence)
    {
	this.favoriteSentence = favoriteSentence;
    }

    public FavoriteSentenceCrud getFavoriteSentenceCrud()
    {
	return favoriteSentenceCrud;
    }

    public void setFavoriteSentenceCrud(FavoriteSentenceCrud favoriteSentenceCrud)
    {
	this.favoriteSentenceCrud = favoriteSentenceCrud;
    }

    @Override
    protected void bindMessage(Message message)
    {
	message.what = APPY_MESSAGE_NEW_FAVORITE_SENTENCE;
	message.obj = this.favoriteSentence;
	message.arg1 = this.favoriteSentenceCrud.ordinal();
	message.arg2 = 0;
    }

    public enum FavoriteSentenceCrud
    {
	NONE, NEW, UPDATED, DELETED, MOVED
    }

}