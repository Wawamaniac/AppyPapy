package com.android.appypapy.utils;

import com.android.appypapy.model.FavoriteSentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kln on 04/12/2016.
 */

public class FavoriteSentenceUtils
{

    public static Map<String, List<FavoriteSentence>> organizeByFolder(List<FavoriteSentence> favoriteSentences,
								       Map<String, List<FavoriteSentence>>
									       favoriteSentencesByFolder)
    {
	favoriteSentencesByFolder.clear();

	int size = favoriteSentences.size();
	FavoriteSentence favoriteSentence;
	String folder;
	for (int i = 0; i < size; i++)
	{
	    favoriteSentence = favoriteSentences.get(i);
	    folder = favoriteSentence.getFolder();

	    if (favoriteSentencesByFolder.get(folder) == null)
	    {
		favoriteSentencesByFolder.put(folder, new ArrayList<FavoriteSentence>(1));
	    }

	    favoriteSentencesByFolder.get(folder).add(favoriteSentence);
	}

	return favoriteSentencesByFolder;
    }


}
