package com.android.appypapy.ui.popup;

import android.app.AlertDialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.appypapy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kln on 10/04/2016.
 */
public class AppyCheckPopup extends AppyPopup
{

    public static abstract class OnCheckPopupHandledListener extends OnBasePopupHandledListener
    {
	public abstract void onPopupValidated(List<AppyListItemCheckable> selectedItems);
    }

    private List<AppyListItemCheckable> items;

    public AppyCheckPopup(String title, String message, String button1Text, boolean cancelable,
			  List<AppyListItemCheckable> items)
    {
	this(title, message, button1Text, cancelable, items, null);
    }

    public AppyCheckPopup(String title, String message, String button1Text, boolean cancelable,
			  List<AppyListItemCheckable> items, OnCheckPopupHandledListener onCheckPopupHandledListener)
    {
	super(title, message, button1Text, cancelable, onCheckPopupHandledListener);
	this.items = items;
    }

    @Override
    protected void bindDialog(final Context context, final AlertDialog alertDialog, LayoutInflater inflater,
			      LinearLayout popupBaseLayout, TextView title, TextView message, Button button1)
    {
	final LinearLayout container = (LinearLayout) popupBaseLayout.findViewById(R.id.yoozpopup_container);
	container.removeAllViews();

	int id = 0;
	for (AppyListItemCheckable item : this.items)
	{
	    container.addView(displayableItemToCheckbox(context, item, id, item.isCheckedByDefault()));
	    id++;
	}

	final OnCheckPopupHandledListener listener = (OnCheckPopupHandledListener) this.onPopupHandledListener;

	button1.setOnClickListener(new View.OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		List<AppyListItemCheckable> selectedItems = getCheckItems(container, items);

		alertDialog.dismiss();

		if (listener != null)
		{
		    listener.onPopupValidated(selectedItems);
		}
	    }
	});

	container.invalidate();
    }

    protected static List<AppyListItemCheckable> getCheckItems(ViewGroup container, List<AppyListItemCheckable> items)
    {
	List<AppyListItemCheckable> selectedItems = new ArrayList<>(0);

	View viewItem;
	CheckBox checkBoxItem;
	for (int i = 0; i < container.getChildCount(); i++)
	{
	    viewItem = container.getChildAt(i);
	    if (viewItem instanceof CheckBox)
	    {
		checkBoxItem = (CheckBox) viewItem;

		if (checkBoxItem.isChecked())
		{
		    selectedItems.add(items.get(checkBoxItem.getId())); // id = index
		}
	    }
	}

	return selectedItems;
    }

    protected static CheckBox displayableItemToCheckbox(Context context, AppyListItemDisplayable item, int id,
							boolean isChecked)
    {
	float px25 =
		TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, context.getResources().getDisplayMetrics());
	float px10 =
		TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());

	LinearLayout.LayoutParams layoutParams =
		new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) px25);
	layoutParams.setMargins(0, 0, 0, (int) px10);

	CheckBox checkBox = new CheckBox(context);
	checkBox.setId(id);
	checkBox.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
	checkBox.setLayoutParams(layoutParams);
	checkBox.setText(" " + item.display());
	checkBox.setTextColor(context.getResources().getColor(R.color.grey_dark));
	checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
	checkBox.setButtonDrawable(context.getResources().getDrawable(R.drawable.checkbox_style));
	checkBox.setChecked(isChecked);

	return checkBox;
    }
}
