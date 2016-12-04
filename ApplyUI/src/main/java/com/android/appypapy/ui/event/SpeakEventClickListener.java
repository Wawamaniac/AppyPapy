package com.android.appypapy.ui.event;

import android.view.View;
import com.android.appypapy.messaging.AppyMessageBox;
import com.android.appypapy.messaging.AppySentenceMessage;

/**
 * Created by kln on 04/12/2016.
 */

public class SpeakEventClickListener implements View.OnClickListener
{

    protected String sentence;

    public SpeakEventClickListener(String sentence)
    {
	this.sentence = sentence;
    }

    @Override
    public void onClick(View v)
    {
	AppySentenceMessage message =
		new AppySentenceMessage(this.sentence, AppySentenceMessage.SentenceReadTypes.NORMAL);

	AppyMessageBox.getInstance().post(message);
    }
}
