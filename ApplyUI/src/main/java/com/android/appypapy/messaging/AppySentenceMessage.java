package com.android.appypapy.messaging;

import android.os.Message;
import com.android.appypapy.utils.EnumUtils;

/**
 * Created by kln on 27/11/2016.
 */

public class AppySentenceMessage extends AppyMessage
{

    protected String mSentence;
    protected SentenceReadTypes mReadType;

    public AppySentenceMessage(String mSentence, SentenceReadTypes mReadType)
    {
	this.mSentence = mSentence;
	this.mReadType = mReadType;
    }

    public AppySentenceMessage(Message message)
    {
	super(message);
	this.mSentence = (String) message.obj;
	this.mReadType = EnumUtils.ordinalToSentenceReadType(message.arg1);
    }

    public String getSentence()
    {
	return mSentence;
    }

    public void setSentence(String mSentence)
    {
	this.mSentence = mSentence;
    }

    public SentenceReadTypes getReadType()
    {
	return mReadType;
    }

    public void setReadType(SentenceReadTypes mReadType)
    {
	this.mReadType = mReadType;
    }

    @Override
    protected void bindMessage(Message message)
    {
	message.what = APPY_MESSAGE_SENTENCE;
	message.obj = this.mSentence;
	message.arg1 = this.mReadType.ordinal();
	message.arg2 = 0;
    }

    public enum SentenceReadTypes
    {
	MUTE, LOW, NORMAL, LOUD, EXTREME
    }

}
