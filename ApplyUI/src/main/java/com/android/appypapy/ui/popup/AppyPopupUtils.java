package com.android.appypapy.ui.popup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.appypapy.R;
import com.android.appypapy.utils.AppyLog;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by KLN on 08/03/2016.
 */
public class AppyPopupUtils
{

    private static final String TAG = AppyPopupUtils.class.getSimpleName();

    public interface OnPopupManagedListener<Result>
    {
	void onShow();

	void onValidated(Result result);

	void onCancelled();
    }

    public static abstract class OnBaseConfirmationPopupManagedListener implements OnPopupManagedListener<Boolean>
    {
	@Override
	public void onShow()
	{

	}

	@Override
	public void onCancelled()
	{

	}
    }

    public static abstract class OnBaseInformationPopupManagedListener implements OnPopupManagedListener<Void>
    {
	@Override
	public void onShow()
	{

	}

	@Override
	public void onCancelled()
	{

	}
    }

    public static abstract class OnBaseInputPopupManagedListener implements OnPopupManagedListener<String>
    {
	@Override
	public void onShow()
	{

	}

	@Override
	public void onCancelled()
	{

	}
    }

    public static AlertDialog informationPopup(Context context, String title, String message)
    {
	return informationPopup(context, title, message, null);
    }

    public static AlertDialog informationPopup(Context context, String title, String message,
					       final OnPopupManagedListener onPopupManagedListener)
    {
	return informationPopup(context, title, message, "OK", onPopupManagedListener);
    }

    public static AlertDialog informationPopup(Context context, String title, String message, String okText,
					       final OnPopupManagedListener onPopupManagedListener)
    {
	return informationPopup(context, title, message, okText, true, onPopupManagedListener);
    }

    public static AlertDialog informationPopup(Context context, String title, String message, String okText,
					       boolean cancelable, final OnPopupManagedListener onPopupManagedListener)
    {
	AppyLog.debug(TAG, "informationPopup", "Call to informationPopup : " + title);

	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	builder.setTitle(title).setMessage(message).setPositiveButton(okText, new DialogInterface.OnClickListener()
	{
	    @Override
	    public void onClick(DialogInterface dialog, int id)
	    {
		if (onPopupManagedListener != null)
		{
		    onPopupManagedListener.onValidated(true);
		}
	    }
	}).setCancelable(cancelable).setOnCancelListener(new DialogInterface.OnCancelListener()
	{
	    @Override
	    public void onCancel(DialogInterface dialog)
	    {
		if (onPopupManagedListener != null)
		{
		    onPopupManagedListener.onCancelled();
		}
	    }
	});

	if (onPopupManagedListener != null)
	{
	    onPopupManagedListener.onShow();
	}

	return builder.show();
    }

    public static AlertDialog confirmationPopup(Context context, String title, String message,
						final OnPopupManagedListener onPopupManagedListener)
    {
	return confirmationPopup(context, title, message, "Oui", "Non", true, onPopupManagedListener);
    }

    public static AlertDialog confirmationPopup(Context context, String title, String message, String yesText,
						String noText, final OnPopupManagedListener onPopupManagedListener)
    {
	return confirmationPopup(context, title, message, yesText, noText, true, onPopupManagedListener);
    }

    public static AlertDialog confirmationPopup(Context context, String title, String message, String yesText,
						String noText, boolean cancelable,
						final OnPopupManagedListener onPopupManagedListener)
    {
	AppyLog.debug(TAG, "confirmationPopup", "Call to confirmationPopup : " + title);

	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	builder.setTitle(title).setMessage(message).setPositiveButton(yesText, new DialogInterface.OnClickListener()
	{
	    @Override
	    public void onClick(DialogInterface dialog, int id)
	    {
		if (onPopupManagedListener != null)
		{
		    onPopupManagedListener.onValidated(true);
		}
	    }
	}).setNegativeButton(noText, new DialogInterface.OnClickListener()
	{
	    @Override
	    public void onClick(DialogInterface dialog, int id)
	    {
		if (onPopupManagedListener != null)
		{
		    onPopupManagedListener.onValidated(false);
		}
	    }
	}).setCancelable(cancelable).setOnCancelListener(new DialogInterface.OnCancelListener()
	{
	    @Override
	    public void onCancel(DialogInterface dialog)
	    {
		if (onPopupManagedListener != null)
		{
		    onPopupManagedListener.onCancelled();
		}
	    }
	});

	if (onPopupManagedListener != null)
	{
	    onPopupManagedListener.onShow();
	}

	return builder.show();
    }

    public static AlertDialog inputPopup(final Context context, String title, String message, String inputHint,
					 String yesText, boolean cancelable, final boolean allowEmptyInput,
					 final OnBaseInputPopupManagedListener onPopupManagedListener)
    {
	AppyLog.debug(TAG, "inputPopup", "Call to confirmationPopup : " + title);

	LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.item_input, null, false);
	TextView textView = (TextView) layout.findViewById(R.id.message);
	textView.setText(message);
	final EditText input = (EditText) layout.findViewById(R.id.input);
	input.setHint(inputHint);

	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	builder.setTitle(title).setView(layout).setPositiveButton(yesText, new DialogInterface.OnClickListener()
	{
	    @Override
	    public void onClick(DialogInterface dialog, int id)
	    {
		String inputStr = input.getText().toString();

		if (!allowEmptyInput && StringUtils.isBlank(inputStr))
		{
		    AppyToast.toast(context, "Veuillez renseigner une valeur", false);
		    return;
		}

		if (onPopupManagedListener != null)
		{
		    onPopupManagedListener.onValidated(inputStr);
		}
	    }
	}).setCancelable(cancelable).setOnCancelListener(new DialogInterface.OnCancelListener()
	{
	    @Override
	    public void onCancel(DialogInterface dialog)
	    {
		if (onPopupManagedListener != null)
		{
		    onPopupManagedListener.onCancelled();
		}
	    }
	});

	if (onPopupManagedListener != null)
	{
	    onPopupManagedListener.onShow();
	}

	return builder.show();
    }

}
