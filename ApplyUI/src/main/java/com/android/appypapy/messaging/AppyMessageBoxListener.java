package com.android.appypapy.messaging;

/**
 * Created by kln on 27/11/2016.
 */

public interface AppyMessageBoxListener
{

    boolean handleMessage(AppySentenceMessage message);

    boolean handleMessage(FavoriteSentenceMessage message);

}
