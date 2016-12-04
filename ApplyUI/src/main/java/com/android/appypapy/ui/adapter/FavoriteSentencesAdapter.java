package com.android.appypapy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.android.appypapy.R;
import com.android.appypapy.model.FavoriteSentence;
import com.android.appypapy.persistence.PersistenceManager;
import com.android.appypapy.ui.event.SpeakEventClickListener;
import com.android.appypapy.utils.FavoriteSentenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kln on 04/12/2016.
 */

public class FavoriteSentencesAdapter extends BaseExpandableListAdapter
{

    protected LayoutInflater layoutInflater;

    protected Context context;
    protected List<String> dataHeader;
    protected List<FavoriteSentence> favoriteSentences;
    protected Map<String, List<FavoriteSentence>> data;

    public FavoriteSentencesAdapter(Context context, List<String> dataHeader, List<FavoriteSentence> favoriteSentences,
				    Map<String, List<FavoriteSentence>> data)
    {
	this.context = context;
	this.dataHeader = dataHeader;
	this.favoriteSentences = favoriteSentences;
	this.data = data;

	this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount()
    {
	return this.data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
	return this.data.get(this.dataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
	return this.dataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
	return this.data.get(this.dataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
	return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
	return this.data.get(this.dataHeader.get(groupPosition)).get(childPosition).getSentenceId();
    }

    @Override
    public boolean hasStableIds()
    {
	return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
	String headerTitle = (String) getGroup(groupPosition);

	if (convertView == null)
	{
	    convertView = this.layoutInflater.inflate(R.layout.group_favorite_sentence, parent, false);
	}

	TextView lblListHeader = (TextView) convertView.findViewById(R.id.group_favorite_sentence_header);
	lblListHeader.setText(headerTitle);

	return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			     ViewGroup parent)
    {
	final FavoriteSentence favoriteSentence = (FavoriteSentence) getChild(groupPosition, childPosition);

	if (convertView == null)
	{
	    convertView = this.layoutInflater.inflate(R.layout.item_favorite_sentence, parent, false);
	}

	TextView sentence = (TextView) convertView.findViewById(R.id.sentence);
	sentence.setText(favoriteSentence.getSentence());

	View speak = convertView.findViewById(R.id.speak);
	speak.setOnClickListener(new SpeakEventClickListener(favoriteSentence.getSentence()));

	View delete = convertView.findViewById(R.id.delete);
	delete.setOnClickListener(new View.OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		PersistenceManager.getManager().delete(favoriteSentence);

		favoriteSentences.remove(favoriteSentence);
		FavoriteSentenceUtils.organizeByFolder(favoriteSentences, data);
		notifyDataSetChanged();
	    }
	});

	return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
	return true;
    }

    @Override
    public void notifyDataSetChanged()
    {
	this.dataHeader = new ArrayList<>(this.data.keySet());
	super.notifyDataSetChanged();
    }
}
