package com.android.appypapy.ui.event;

import android.view.View;
import android.widget.EditText;

/**
 * Created by kln on 04/12/2016.
 */

public class InputSpeakEventListener extends SpeakEventClickListener
{

    protected EditText input;

    public InputSpeakEventListener(EditText input)
    {
	super("");
	this.input = input;
    }

    @Override
    public void onClick(View v)
    {
	this.sentence = this.input.getText().toString();
	super.onClick(v);
    }
}
