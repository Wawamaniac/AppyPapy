package com.android.appypapy.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import com.android.appypapy.R;
import com.android.appypapy.model.FavoriteSentence;
import com.android.appypapy.persistence.PersistenceManager;
import com.android.appypapy.ui.adapter.FavoriteSentencesAdapter;
import com.android.appypapy.utils.FavoriteSentenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kln on 27/11/2016.
 */

public class FavoriteSentencesFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
	LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_favorite_sentences, container, false);

	List<FavoriteSentence> favoriteSentences = PersistenceManager.getManager().getAll(FavoriteSentence.class);
	Map<String, List<FavoriteSentence>> favoriteSentencesByFolder =
		FavoriteSentenceUtils.organizeByFolder(favoriteSentences);

	ExpandableListView expandableListView = (ExpandableListView) layout.findViewById(R.id.favorite_sentences_list);
	expandableListView.setAdapter(
		new FavoriteSentencesAdapter(getActivity(), new ArrayList<>(favoriteSentencesByFolder.keySet()),
			favoriteSentencesByFolder));

	return layout;
    }

}
