package com.android.appypapy.utils;

import com.android.appypapy.messaging.AppySentenceMessage;
import com.android.appypapy.messaging.FavoriteSentenceMessage;

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

    public static FavoriteSentenceMessage.FavoriteSentenceCrud ordinalToFavoriteSentenceCrud(int ordinal)
    {
	FavoriteSentenceMessage.FavoriteSentenceCrud[] values = FavoriteSentenceMessage.FavoriteSentenceCrud.values();

	for (FavoriteSentenceMessage.FavoriteSentenceCrud favoriteSentenceCrud : values)
	{
	    if (favoriteSentenceCrud.ordinal() == ordinal)
	    {
		return favoriteSentenceCrud;
	    }
	}

	return FavoriteSentenceMessage.FavoriteSentenceCrud.UPDATED;
    }

}
