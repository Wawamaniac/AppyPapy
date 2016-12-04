package com.android.appypapy.messaging.listener;

import com.android.appypapy.messaging.AppyMessage;

/**
 * Created by kln on 27/11/2016.
 */

public interface AppyMessageBoxListener<T extends AppyMessage>
{

    void handleMessage(T message);

}
