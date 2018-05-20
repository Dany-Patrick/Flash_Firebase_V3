package cl.dany.flash_firebase_v3.views.main.drawer;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cl.dany.flash_firebase_v3.data.CurrentUser;
import cl.dany.flash_firebase_v3.data.EmailProcesor;
import cl.dany.flash_firebase_v3.data.Nodes;
import cl.dany.flash_firebase_v3.data.PhotoPreference;
import cl.dany.flash_firebase_v3.models.LocalUser;

public class UploadPhoto {
    private Context context;

    public UploadPhoto(Context context) {
        this.context = context;
    }
    public void toFirebase(String path)
    {
        final CurrentUser currentUser = new CurrentUser();
        String folder = new EmailProcesor().sanitizedEmail(currentUser.email()+"/");
        String photoName = "avatar.jpg";
        String baseUrl = "gs://flashfirebasev3.appspot.com/avatars/";
        String refUrl = baseUrl + folder + photoName;
        StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);
        storage.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String[] fullUrl = taskSnapshot.getDownloadUrl().toString().split("&token");
                String url = fullUrl[0];
                new PhotoPreference(context).photoSave(url);
                LocalUser user = new LocalUser();
                user.setEmail(currentUser.email());
                user.setName(currentUser.getCurrentUser().getDisplayName());
                user.setPhoto(url);
                user.setUid(currentUser.uid());
                String key = new EmailProcesor().sanitizedEmail(currentUser.email());
                new Nodes().user(key).setValue(user);
               // FirebaseDatabase.getInstance().getReference("users").child(key).setValue(user);

            }
        });
    }

}
