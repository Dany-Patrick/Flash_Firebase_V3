package cl.dany.flash_firebase_v3.Views.Main.Drawer;

import android.content.Context;

import cl.dany.flash_firebase_v3.Data.PhotoPreference;

public class PhotoValidation {
    private Context context;
    private PhotoCallback callback;

    public PhotoValidation(Context context, PhotoCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void validate()
    {
        String url = new PhotoPreference(context).getPhoto();
        if(url != null)
        {
            callback.photoAvailiable(url);
        }else
        {
            callback.emptyPhoto();
        }
    }
}
