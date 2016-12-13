package com.android.appypapy.messaging;

import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kln on 27/11/2016.
 */

public class AppyMessageBox
{

    private static final AppyMessageBox APPY_MESSAGE_BOX = new AppyMessageBox();

    protected List<AppyMessageBoxListener> listeners = new ArrayList<>(0);

    public static AppyMessageBox getInstance()
    {
	return APPY_MESSAGE_BOX;
    }

    protected AppyMessageHandler mHandler;

    protected AppyMessageBox()
    {
	this.mHandler = new AppyMessageHandler(Looper.getMainLooper());
    }

    public void registerListener(AppyMessageBoxListener listener)
    {
	this.listeners.add(listener);
    }

    public void unregisterListener(AppyMessageBoxListener listener)
    {
	this.listeners.remove(listener);
    }

    public void post(AppyMessage message)
    {
	message.bindMessage(message.message);

	this.mHandler.sendMessage(message.message);
    }

    protected void dispatchMessage(Message message)
    {
	switch (message.what)
	{
	    case AppySentenceMessage.APPY_MESSAGE_SENTENCE:
		notifySentenceMessage(new AppySentenceMessage(message));
		break;
	    case AppySentenceMessage.APPY_MESSAGE_NEW_FAVORITE_SENTENCE:
		notifyNewFavoriteMessage(new FavoriteSentenceMessage(message));
		break;
	}
    }

    protected void notifySentenceMessage(AppySentenceMessage message)
    {
	for (AppyMessageBoxListener listener : this.listeners)
	{
	    listener.handleMessage(message);
	}
    }

    protected void notifyNewFavoriteMessage(FavoriteSentenceMessage message)
    {
	for (AppyMessageBoxListener listener : this.listeners)
	{
	    listener.handleMessage(message);
	}
    }

}
