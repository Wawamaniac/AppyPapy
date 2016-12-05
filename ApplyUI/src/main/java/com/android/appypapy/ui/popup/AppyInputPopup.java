package com.android.appypapy.ui.popup;

import android.app.AlertDialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.appypapy.R;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by kln on 10/04/2016.
 */
public class AppyInputPopup extends AppyPopup
{

    public static abstract class OnInputPopupHandledListener extends OnBasePopupHandledListener
    {
	public abstract void onPopupValidated(String input);
    }

    private String hint;
    private boolean allowEmpty;

    public AppyInputPopup(String title, String message, String button1Text, boolean cancelable, String hint,
			  boolean allowEmpty)
    {
	this(title, message, button1Text, cancelable, hint, allowEmpty, null);
    }

    public AppyInputPopup(String title, String message, String button1Text, boolean cancelable, String hint,
			  boolean allowEmpty, OnInputPopupHandledListener onInputPopupHandledListener)
    {
	super(title, message, button1Text, cancelable, onInputPopupHandledListener);
	this.hint = hint;
	this.allowEmpty = allowEmpty;
    }

    @Override
    protected void bindDialog(final Context context, final AlertDialog alertDialog, LayoutInflater inflater,
			      LinearLayout popupBaseLayout, TextView title, TextView message, Button button1)
    {
	final EditText input = (EditText) popupBaseLayout.findViewById(R.id.yoozpopup_input);
	input.setVisibility(View.VISIBLE);
	input.setHint(this.hint);

	final OnInputPopupHandledListener listener = (OnInputPopupHandledListener) this.onPopupHandledListener;

	input.setOnEditorActionListener(new TextView.OnEditorActionListener()
	{
	    @Override
	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	    {
		String keyedValue = input.getText().toString();

		if (allowEmpty || (!allowEmpty && StringUtils.isNotBlank(keyedValue)))
		{
		    alertDialog.dismiss();

		    if (listener != null)
		    {
			listener.onPopupValidated(keyedValue);
		    }
		}
		else
		{
		    AppyToast.toast(context, "Veuillez renseigner une valeur", true);
		}

		return true;
	    }
	});

	button1.setOnClickListener(new View.OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		String keyedValue = input.getText().toString();

		if (allowEmpty || StringUtils.isNotBlank(keyedValue))
		{
		    alertDialog.dismiss();

		    if (listener != null)
		    {
			listener.onPopupValidated(keyedValue);
		    }
		}
		else
		{
		    AppyToast.toast(context, "Veuillez renseigner une valeur", true);
		}
	    }
	});
    }
}
