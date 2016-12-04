package com.android.appypapy.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.android.appypapy.R;
import com.android.appypapy.messaging.AppyMessageBox;
import com.android.appypapy.messaging.AppySentenceMessage;
import com.android.appypapy.model.FavoriteSentence;

/**
 * Created by kln on 27/11/2016.
 */

public class FreeSpeakFragment extends Fragment
{
    protected EditText input;
    protected Button button;
    protected Button clear;
    protected Button favorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
	LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_free_speak, container, false);

	this.input = (EditText) layout.findViewById(R.id.input);
	this.button = (Button) layout.findViewById(R.id.button);
	this.clear = (Button) layout.findViewById(R.id.clear);
	this.favorite = (Button) layout.findViewById(R.id.favorite);

	this.button.setOnClickListener(this.onButtonClickedListener);
	this.clear.setOnClickListener(this.onClearClickedListener);
	this.favorite.setOnClickListener(this.onFavoriteClickedListener);

	return layout;
    }

    protected final View.OnClickListener onButtonClickedListener = new View.OnClickListener()
    {
	@Override
	public void onClick(View view)
	{

	    AppySentenceMessage message = new AppySentenceMessage(FreeSpeakFragment.this.input.getText().toString(),
		    AppySentenceMessage.SentenceReadTypes.NORMAL);

	    AppyMessageBox.getInstance().post(message);
	}
    };

    protected final View.OnClickListener onClearClickedListener = new View.OnClickListener()
    {
	@Override
	public void onClick(View view)
	{
	    FreeSpeakFragment.this.input.setText("");
	}
    };

    protected final View.OnClickListener onFavoriteClickedListener = new View.OnClickListener()
    {
	@Override
	public void onClick(View view)
	{
	    String sentence = FreeSpeakFragment.this.input.getText().toString();

	    FavoriteSentence favoriteSentence = new FavoriteSentence(null, sentence);
	}
    };
}
