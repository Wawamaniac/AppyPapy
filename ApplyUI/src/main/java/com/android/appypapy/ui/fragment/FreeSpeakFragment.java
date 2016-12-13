package com.android.appypapy.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.android.appypapy.R;
import com.android.appypapy.messaging.AppyMessageBox;
import com.android.appypapy.messaging.FavoriteSentenceMessage;
import com.android.appypapy.model.FavoriteSentence;
import com.android.appypapy.persistence.PersistenceManager;
import com.android.appypapy.ui.event.InputSpeakEventListener;
import com.android.appypapy.ui.popup.AppyInputPopup;
import com.android.appypapy.ui.popup.AppyToast;

/**
 * Created by kln on 27/11/2016.
 */

public class FreeSpeakFragment extends Fragment
{
    protected EditText input;
    protected Button button;
    protected Button clear;
    protected Button favorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
	LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_free_speak, container, false);

	this.input = (EditText) layout.findViewById(R.id.input);
	this.button = (Button) layout.findViewById(R.id.button);
	this.clear = (Button) layout.findViewById(R.id.clear);
	this.favorite = (Button) layout.findViewById(R.id.favorite);

	this.button.setOnClickListener(new InputSpeakEventListener(this.input));
	this.clear.setOnClickListener(this.onClearClickedListener);
	this.favorite.setOnClickListener(this.onFavoriteClickedListener);

	return layout;
    }

    protected final View.OnClickListener onClearClickedListener = new View.OnClickListener()
    {
	@Override
	public void onClick(View view)
	{
	    FreeSpeakFragment.this.input.setText("");
	}
    };

    protected final View.OnClickListener onFavoriteClickedListener = new View.OnClickListener()
    {
	@Override
	public void onClick(View view)
	{
	    String sentence = FreeSpeakFragment.this.input.getText().toString();

	    askFolderAndAddFavorite(sentence);
	}
    };

    protected void askFolderAndAddFavorite(final String sentence)
    {
	AppyInputPopup popup = new AppyInputPopup("Ajouter un favoris",
		"Veuillez renseigner le dossier dans lequel le favoris sera ajouté :", "Ajouter", true, "Favoris",
		false, new AppyInputPopup.OnInputPopupHandledListener()
	{
	    @Override
	    public void onPopupValidated(String folder)
	    {
		addFavorite(folder, sentence);
	    }
	});
	popup.show(getActivity());
    }

    protected void addFavorite(String folder, String sentence)
    {
	FavoriteSentence favoriteSentence = new FavoriteSentence(folder, sentence);
	PersistenceManager.getManager().upsert(favoriteSentence);

	FavoriteSentenceMessage message =
		new FavoriteSentenceMessage(favoriteSentence, FavoriteSentenceMessage.FavoriteSentenceCrud.NEW);

	AppyMessageBox.getInstance().post(message);

	AppyToast.toast(getActivity(), "Favoris ajouté dans le dossier : " + folder, true);
    }

}
