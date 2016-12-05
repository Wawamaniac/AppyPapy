package com.android.appypapy.ui.popup;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.appypapy.R;

/**
 * Created by kln on 10/04/2016.
 */
public class AppyConfirmationPopup extends AppyPopup
{

    public static abstract class OnPopupHandledListener extends OnBasePopupHandledListener
    {
	public abstract void onPopupValidated(boolean button1clicked);
    }

    protected String button2Text;

    public AppyConfirmationPopup(String title, String message, String button1Text, String button2Text,
				 boolean cancelable)
    {
	this(title, message, button1Text, button2Text, cancelable, null);
    }

    public AppyConfirmationPopup(String title, String message, String button1Text, String button2Text,
				 boolean cancelable, OnPopupHandledListener onPopupHandledListener)
    {
	super(title, message, button1Text, cancelable, onPopupHandledListener);
	this.button2Text = button2Text;
    }

    @Override
    protected void bindDialog(final Context context, final AlertDialog alertDialog, LayoutInflater inflater,
			      LinearLayout popupBaseLayout, TextView title, TextView message, Button button1)
    {
	final OnPopupHandledListener listener = (OnPopupHandledListener) this.onPopupHandledListener;

	Button button2 = (Button) popupBaseLayout.findViewById(R.id.yoozpopup_button2);
	button2.setVisibility(View.VISIBLE);
	button2.setText(this.button2Text);

	button1.setOnClickListener(new View.OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		alertDialog.dismiss();

		if (listener != null)
		{
		    listener.onPopupValidated(true);
		}
	    }
	});

	button2.setOnClickListener(new View.OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		alertDialog.dismiss();

		if (listener != null)
		{
		    listener.onPopupValidated(false);
		}
	    }
	});
    }
}
