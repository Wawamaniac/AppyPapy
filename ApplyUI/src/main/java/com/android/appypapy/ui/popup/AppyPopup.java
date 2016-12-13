package com.android.appypapy.ui.popup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.appypapy.R;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by kln on 10/04/2016.
 */
public abstract class AppyPopup
{

    public interface OnPopupHandledListener
    {
	void onPopupCancelled();

	void onPopupShowed();
    }

    public static abstract class OnBasePopupHandledListener implements OnPopupHandledListener
    {
	@Override
	public void onPopupCancelled()
	{

	}

	@Override
	public void onPopupShowed()
	{

	}
    }

    protected String title;
    protected String message;
    protected String button1Text;
    protected boolean cancelable;
    protected OnPopupHandledListener onPopupHandledListener;

    public AppyPopup(String title, String message, String button1Text, boolean cancelable)
    {
	this(title, message, button1Text, cancelable, null);
    }

    public AppyPopup(String title, String message, String button1Text, boolean cancelable,
		     OnPopupHandledListener onPopupHandledListener)
    {
	this.title = title;
	this.message = message;
	this.button1Text = button1Text;
	this.cancelable = cancelable;
	this.onPopupHandledListener = onPopupHandledListener;
    }

    public void show(Context context)
    {
	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	LinearLayout popupBaseLayout = (LinearLayout) inflater.inflate(R.layout.popup_generic, null, false);

	DisplayMetrics metrics = context.getResources().getDisplayMetrics();
	int screenWidth = (int) (metrics.widthPixels * 0.90);

	AlertDialog dialog = builder.create();
	dialog.setView(popupBaseLayout, 0, 0, 0, 0);
	dialog.getWindow().setLayout(screenWidth, ViewGroup.LayoutParams.MATCH_PARENT);
	dialog.getWindow().setBackgroundDrawableResource(R.drawable.trasparent_background);

	TextView title = (TextView) popupBaseLayout.findViewById(R.id.popup_title);
	title.setText(this.title);
	TextView message = (TextView) popupBaseLayout.findViewById(R.id.popup_message);

	if (StringUtils.isBlank(this.message))
	{
	    message.setVisibility(View.GONE);
	}
	else
	{
	    message.setText(this.message);
	}

	Button button1 = (Button) popupBaseLayout.findViewById(R.id.popup_button1);
	button1.setText(this.button1Text);

	dialog.setCancelable(this.cancelable);
	dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
	{
	    @Override
	    public void onCancel(DialogInterface dialog)
	    {
		if (onPopupHandledListener != null)
		{
		    onPopupHandledListener.onPopupCancelled();
		}
	    }
	});

	bindDialog(context, dialog, inflater, popupBaseLayout, title, message, button1);

	dialog.setOnShowListener(new DialogInterface.OnShowListener()
	{
	    @Override
	    public void onShow(DialogInterface dialog)
	    {
		if (onPopupHandledListener != null)
		{
		    onPopupHandledListener.onPopupShowed();
		}
	    }
	});

	dialog.show();
    }

    protected abstract void bindDialog(Context context, AlertDialog alertDialog, LayoutInflater inflater,
				       LinearLayout popupBaseLayout, TextView title, TextView message, Button button1);

}
