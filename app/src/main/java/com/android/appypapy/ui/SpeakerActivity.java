package com.android.appypapy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.appypapy.R;
import com.android.appypapy.ui.generic.AppyActivity;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class SpeakerActivity extends AppyActivity
{

    private static final String TAG = SpeakerActivity.class.getSimpleName();
    private static final int TTS_DATA_CHECK_CODE = 111;

    protected TextToSpeech textToSpeech;

    protected EditText input;
    protected Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_speaker);

	this.input = (EditText) findViewById(R.id.input);
	this.button = (Button) findViewById(R.id.button);
	this.button.setOnClickListener(this.onButtonClickedListener);

	this.textToSpeech = new TextToSpeech(SpeakerActivity.this, this.onInitListener);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	if (requestCode == TTS_DATA_CHECK_CODE)
	{
	    if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
	    {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
		{
		    Set<Locale> avaiableLanguages = this.textToSpeech.getAvailableLanguages();
		    Iterator<Locale> avaiableLanguagesIterator = avaiableLanguages.iterator();

		    while (avaiableLanguagesIterator.hasNext())
		    {
			Log.d(TAG, "Avaiable language found : " + avaiableLanguagesIterator.next().toString());
		    }
		}

		if (this.textToSpeech.isLanguageAvailable(Locale.FRANCE) == TextToSpeech.LANG_COUNTRY_AVAILABLE
			|| this.textToSpeech.isLanguageAvailable(Locale.FRANCE) == TextToSpeech.LANG_AVAILABLE)
		{
		    this.textToSpeech.setLanguage(Locale.FRENCH);
		}
		else
		{
		    Toast.makeText(SpeakerActivity.this, "La langue française n'est pas supportée par cet appareil.",
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

    protected final View.OnClickListener onButtonClickedListener = new View.OnClickListener()
    {
	@Override
	public void onClick(View view)
	{
	    SpeakerActivity.this.textToSpeech
		    .speak(SpeakerActivity.this.input.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
	}
    };

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
}
