package com.android.appypapy.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.android.appypapy.ui.fragment.FavoriteSentencesFragment;
import com.android.appypapy.ui.fragment.FreeSpeakFragment;

/**
 * Created by kln on 27/11/2016.
 */

public class SpeakerPagerAdapter extends FragmentPagerAdapter
{

    public SpeakerPagerAdapter(FragmentManager fm)
    {
	super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
	switch (position)
	{
	    case 0:
		return new FreeSpeakFragment();
	    case 1:
		return new FavoriteSentencesFragment();
	    default:
		return null;
	}
    }

    @Override
    public int getCount()
    {
	return 2;
    }
}