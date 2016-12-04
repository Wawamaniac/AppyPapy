package com.android.appypapy.messaging;

import android.os.Message;
import com.android.appypapy.model.FavoriteSentence;

/**
 * Created by kln on 04/12/2016.
 */

public class NewFavoriteSentenceMessage extends AppyMessage
{

    protected FavoriteSentence favoriteSentence;

    public NewFavoriteSentenceMessage(FavoriteSentence favoriteSentence)
    {
	this.favoriteSentence = favoriteSentence;
    }

    public NewFavoriteSentenceMessage(Message message)
    {
	super(message);
	this.favoriteSentence = (FavoriteSentence) message.obj;
    }

    public FavoriteSentence getFavoriteSentence()
    {
	return favoriteSentence;
    }

    public void setFavoriteSentence(FavoriteSentence favoriteSentence)
    {
	this.favoriteSentence = favoriteSentence;
    }

    @Override
    protected void bindMessage(Message message)
    {
	message.what = APPY_MESSAGE_NEW_FAVORITE_SENTENCE;
	message.obj = this.favoriteSentence;
	message.arg1 = 0;
	message.arg2 = 0;
    }

}