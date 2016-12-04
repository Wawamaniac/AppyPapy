package com.android.appypapy.messaging;

import android.os.Message;

/**
 * Created by kln on 27/11/2016.
 */

public abstract class AppyMessage
{

    public static final int APPY_MESSAGE_SENTENCE = 1;

    protected Message message;

    protected AppyMessage()
    {
	this.message = Message.obtain();
    }

    public AppyMessage(Message message)
    {
	this.message = message;
    }

    protected abstract void bindMessage(Message message);

    public final void post()
    {
	bindMessage(this.message);

	AppyMessageBox.getInstance().post(this);
    }

}