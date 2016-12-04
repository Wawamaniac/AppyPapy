package com.android.appypapy.messaging;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by kln on 27/11/2016.
 */

public class AppyMessageHandler extends Handler
{

    private static final AppyMessageHandler APPY_MESSAGE_HANDLER = new AppyMessageHandler(Looper.getMainLooper());

    public static AppyMessageHandler getInstance()
    {
	return APPY_MESSAGE_HANDLER;
    }

    public AppyMessageHandler(Looper looper)
    {
	super(looper);
    }

    @Override
    public void handleMessage(Message message)
    {
	switch (message.what)
	{
	    case AppySentenceMessage.APPY_MESSAGE_SENTENCE:
		sendMessageToBox(message);
		break;
	    default:
		super.handleMessage(message);
		break;
	}
    }

    protected void sendMessageToBox(Message message)
    {
	AppyMessageBox.getInstance().dispatchMessage(message);
    }
}
