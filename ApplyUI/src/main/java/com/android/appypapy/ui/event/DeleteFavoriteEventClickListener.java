package com.android.appypapy.ui.event;

import android.content.Context;
import android.view.View;
import com.android.appypapy.R;
import com.android.appypapy.messaging.AppyMessageBox;
import com.android.appypapy.messaging.FavoriteSentenceMessage;
import com.android.appypapy.model.FavoriteSentence;
import com.android.appypapy.persistence.PersistenceManager;
import com.android.appypapy.ui.popup.AppyConfirmationPopup;

/**
 * Created by kln on 11/12/2016.
 */

public class DeleteFavoriteEventClickListener extends AppyConfirmationPopup.OnPopupHandledListener
	implements View.OnClickListener

{

    protected Context context;
    protected FavoriteSentence favoriteSentence;

    public DeleteFavoriteEventClickListener(Context context, FavoriteSentence favoriteSentence)
    {
	this.context = context;
	this.favoriteSentence = favoriteSentence;
    }

    @Override
    public void onClick(View v)
    {
	AppyConfirmationPopup popup = new AppyConfirmationPopup(this.context.getString(R.string.app_name),
		this.context.getString(R.string.sure_delete_favorite), this.context.getString(R.string.yes),
		this.context.getString(R.string.no), true, DeleteFavoriteEventClickListener.this);
	popup.show(this.context);
    }

    @Override
    public void onPopupValidated(boolean deleteFavorite)
    {
	if (deleteFavorite)
	{
	    PersistenceManager.getManager().delete(this.favoriteSentence);

	    FavoriteSentenceMessage message = new FavoriteSentenceMessage(this.favoriteSentence,
		    FavoriteSentenceMessage.FavoriteSentenceCrud.DELETED);
	    AppyMessageBox.getInstance().post(message);
	}
    }
}
