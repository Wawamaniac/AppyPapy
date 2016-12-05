package com.android.appypapy.ui.popup;

import android.app.AlertDialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.android.appypapy.R;

import java.util.List;

/**
 * Created by kln on 10/04/2016.
 */
public class AppySelectPopup extends AppyPopup
{

    public static abstract class OnSelectPopupHandledListener extends OnBasePopupHandledListener
    {
	public abstract void onPopupValidated(int selectedPosition, AppyListItemDisplayable selectedItem);
    }

    private List<AppyListItemDisplayable> items;

    public AppySelectPopup(String title, String message, String button1Text, boolean cancelable,
			   List<AppyListItemDisplayable> items)
    {
	this(title, message, button1Text, cancelable, items, null);
    }

    public AppySelectPopup(String title, String message, String button1Text, boolean cancelable,
			   List<AppyListItemDisplayable> items,
			   OnSelectPopupHandledListener onSelectPopupHandledListener)
    {
	super(title, message, button1Text, cancelable, onSelectPopupHandledListener);
	this.items = items;
    }

    @Override
    protected void bindDialog(final Context context, final AlertDialog alertDialog, LayoutInflater inflater,
			      LinearLayout popupBaseLayout, TextView title, TextView message, Button button1)
    {
	LinearLayout container = (LinearLayout) popupBaseLayout.findViewById(R.id.yoozpopup_container);
	container.removeAllViews();

	final RadioGroup radioGroup = new RadioGroup(context);
	radioGroup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.WRAP_CONTENT));

	int id = 0;
	for (AppyListItemDisplayable item : this.items)
	{
	    radioGroup.addView(displayableItemToRadioButton(context, item, id));
	    id++;
	}

	container.addView(radioGroup);

	final OnSelectPopupHandledListener listener = (OnSelectPopupHandledListener) this.onPopupHandledListener;

	button1.setOnClickListener(new View.OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		int index = radioGroup.getCheckedRadioButtonId(); // id = index

		if (index != -1)
		{
		    alertDialog.dismiss();

		    if (listener != null)
		    {
			listener.onPopupValidated(index, items.get(index));
		    }
		}
		else
		{
		    AppyToast.toast(context, "Veuillez renseigner une valeur", true);
		}
	    }
	});
    }

    protected static RadioButton displayableItemToRadioButton(Context context, AppyListItemDisplayable item, int id)
    {
	float px25 =
		TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, context.getResources().getDisplayMetrics());
	float px10 =
		TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());

	RadioGroup.LayoutParams layoutParams =
		new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, (int) px25);
	layoutParams.setMargins(0, 0, 0, (int) px10);

	RadioButton radioButton = new RadioButton(context);
	radioButton.setId(id);
	radioButton.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
	radioButton.setLayoutParams(layoutParams);
	radioButton.setText(" " + item.display());
	radioButton.setTextColor(context.getResources().getColor(R.color.grey_dark));
	radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
	radioButton.setButtonDrawable(context.getResources().getDrawable(R.drawable.radiobutton_style));

	return radioButton;
    }
}
