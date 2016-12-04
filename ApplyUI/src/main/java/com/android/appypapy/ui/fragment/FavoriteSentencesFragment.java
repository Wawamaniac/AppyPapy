package com.android.appypapy.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.appypapy.R;
import com.android.appypapy.messaging.AppyMessageBox;
import com.android.appypapy.messaging.NewFavoriteSentenceMessage;
import com.android.appypapy.messaging.listener.AppyNewFavoriteSentenceListener;
import com.android.appypapy.model.FavoriteSentence;
import com.android.appypapy.persistence.PersistenceManager;
import com.android.appypapy.ui.adapter.FavoriteSentencesAdapter;
import com.android.appypapy.utils.FavoriteSentenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kln on 27/11/2016.
 */

public class FavoriteSentencesFragment extends Fragment implements AppyNewFavoriteSentenceListener
{

    protected List<FavoriteSentence> favoriteSentences;
    protected Map<String, List<FavoriteSentence>> favoriteSentencesByFolder;

    protected ExpandableListView expandableListView;
    protected FavoriteSentencesAdapter favoriteSentencesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
	FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_favorite_sentences, container, false);

	this.favoriteSentences = PersistenceManager.getManager().getAll(FavoriteSentence.class);
	this.favoriteSentencesByFolder = FavoriteSentenceUtils
		.organizeByFolder(this.favoriteSentences, new HashMap<String, List<FavoriteSentence>>(0));

	this.expandableListView = (ExpandableListView) layout.findViewById(R.id.favorite_sentences_list);

	this.favoriteSentencesAdapter =
		new FavoriteSentencesAdapter(getActivity(), new ArrayList<>(this.favoriteSentencesByFolder.keySet()),
			this.favoriteSentences, this.favoriteSentencesByFolder);

	this.expandableListView.setAdapter(this.favoriteSentencesAdapter);

	if (!this.favoriteSentences.isEmpty())
	{
	    layout.findViewById(R.id.no_favorite_sentence).setVisibility(View.GONE);
	}

	AppyMessageBox.getInstance().registerListener(FavoriteSentencesFragment.this);

	return layout;
    }

    @Override
    public void handleMessage(NewFavoriteSentenceMessage message)
    {
	FavoriteSentence favoriteSentence = message.getFavoriteSentence();
	String folder = favoriteSentence.getFolder();

	this.favoriteSentences.add(favoriteSentence);

	if (this.favoriteSentencesByFolder.get(folder) == null)
	{
	    this.favoriteSentencesByFolder.put(folder, new ArrayList<FavoriteSentence>(1));
	}

	this.favoriteSentencesByFolder.get(folder).add(favoriteSentence);

	this.favoriteSentencesAdapter.notifyDataSetChanged();
    }
}
