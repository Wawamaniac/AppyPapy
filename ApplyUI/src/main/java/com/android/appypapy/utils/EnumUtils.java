package com.android.appypapy.utils;

import com.android.appypapy.messaging.AppySentenceMessage;

/**
 * Created by kln on 27/11/2016.
 */

public class EnumUtils
{

    public static AppySentenceMessage.SentenceReadTypes ordinalToSentenceReadType(int ordinal)
    {
	AppySentenceMessage.SentenceReadTypes[] values = AppySentenceMessage.SentenceReadTypes.values();

	for (AppySentenceMessage.SentenceReadTypes sentenceReadType : values)
	{
	    if (sentenceReadType.ordinal() == ordinal)
	    {
		return sentenceReadType;
	    }
	}

	return AppySentenceMessage.SentenceReadTypes.MUTE;
    }

}
