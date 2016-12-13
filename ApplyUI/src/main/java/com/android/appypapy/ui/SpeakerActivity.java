package com.android.appypapy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;
import com.android.appypapy.R;
import com.android.appypapy.messaging.AppyMessageBox;
import com.android.appypapy.messaging.AppyMessageBoxListener;
import com.android.appypapy.messaging.AppySentenceMessage;
import com.android.appypapy.messaging.FavoriteSentenceMessage;
import com.android.appypapy.ui.adapter.SpeakerPagerAdapter;
import com.android.appypapy.ui.generic.AppyActivity;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class SpeakerActivity extends AppyActivity implements AppyMessageBoxListener
{

    private static final String TAG = SpeakerActivity.class.getSimpleName();
    private static final int TTS_DATA_CHECK_CODE = 111;

    protected TextToSpeech textToSpeech;

    protected ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activiry_speaker);

	this.viewPager = (ViewPager) findViewById(R.id._activity_speaker_pager);
	this.viewPager.setAdapter(new SpeakerPagerAdapter(getSupportFragmentManager()));

	AppyMessageBox.getInstance().registerListener(SpeakerActivity.this);

	this.textToSpeech = new TextToSpeech(SpeakerActivity.this, this.onInitListener);
    }

    @Override
    protected void onStop()
    {
	super.onStop();

	AppyMessageBox.getInstance().unregisterListener(SpeakerActivity.this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	if (requestCode == TTS_DATA_CHECK_CODE)
	{
	    if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
	    {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
		{
		    Set<Locale> availableLanguages = this.textToSpeech.getAvailableLanguages();
		    Iterator<Locale> availableLanguagesIterator = availableLanguages.iterator();

		    while (availableLanguagesIterator.hasNext())
		    {
			Log.d(TAG, "Available language found : " + availableLanguagesIterator.next().toString());
		    }
		}

		if (this.textToSpeech.isLanguageAvailable(Locale.FRANCE) == TextToSpeech.LANG_COUNTRY_AVAILABLE
			|| this.textToSpeech.isLanguageAvailable(Locale.FRANCE) == TextToSpeech.LANG_AVAILABLE)
		{
		    this.textToSpeech.setLanguage(Locale.FRENCH);
		}
		else
		{
		    Toast.makeText(SpeakerActivity.this, "French language is not supported by this device",
			    Toast.LENGTH_LONG).show();
		}
	    }
	    else
	    {
		Intent installTtsDataIntent = new Intent();
		installTtsDataIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
		startActivity(installTtsDataIntent);
	    }
	}
    }

    protected final TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener()
    {
	@Override
	public void onInit(int status)
	{
	    if (status == TextToSpeech.SUCCESS)
	    {
		int result = SpeakerActivity.this.textToSpeech.setLanguage(Locale.FRANCE);
		if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
		{
		    Intent installIntent = new Intent();
		    installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
		    startActivity(installIntent);
		}
	    }
	    else
	    {
		Log.e(TAG, "Initilization Failed!");
	    }
	}
    };

    @Override
    public boolean handleMessage(AppySentenceMessage message)
    {
	// TODO : manage sound volume
	this.textToSpeech.speak(message.getSentence(), TextToSpeech.QUEUE_FLUSH, null);
	return true;
    }

    @Override
    public boolean handleMessage(FavoriteSentenceMessage message)
    {
	this.viewPager.setCurrentItem(1);
	return true;
    }
}
