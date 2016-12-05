package com.android.appypapy.ui.popup;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kln on 10/04/2016.
 */
public class AppyInfoPopup extends AppyPopup
{

    public static abstract class OnPopupHandledListener extends OnBasePopupHandledListener
    {
	public abstract void onPopupValidated();
    }

    public AppyInfoPopup(String title, String message, String button1Text, boolean cancelable)
    {
	this(title, message, button1Text, cancelable, null);
    }

    public AppyInfoPopup(String title, String message, String button1Text, boolean cancelable,
			 OnPopupHandledListener onPopupHandledListener)
    {
	super(title, message, button1Text, cancelable, onPopupHandledListener);
    }

    @Override
    protected void bindDialog(final Context context, final AlertDialog alertDialog, LayoutInflater inflater,
			      LinearLayout popupBaseLayout, TextView title, TextView message, Button button1)
    {
	final OnPopupHandledListener listener = (OnPopupHandledListener) this.onPopupHandledListener;

	button1.setOnClickListener(new View.OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		alertDialog.dismiss();

		if (listener != null)
		{
		    listener.onPopupValidated();
		}
	    }
	});
    }
}
