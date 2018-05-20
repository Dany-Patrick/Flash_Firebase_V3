package cl.dany.flash_firebase_v3.views.main.finder;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cl.dany.flash_firebase_v3.data.CurrentUser;
import cl.dany.flash_firebase_v3.data.EmailProcesor;
import cl.dany.flash_firebase_v3.data.Nodes;
import cl.dany.flash_firebase_v3.data.PhotoPreference;
import cl.dany.flash_firebase_v3.models.Chat;
import cl.dany.flash_firebase_v3.models.LocalUser;

public class UserValidation {
    private FinderCallback callback;
    private Context context;

    public UserValidation(FinderCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    public void init(String email)
    {
        if(email.trim().length() > 0)
        {
            if(email.contains("@"))
            {
                String currentEmail = new CurrentUser().email();
                if(!email.equals(currentEmail))
                {
                    findUser(email);
                }else
                {
                    callback.error("¿Chat Contigo mismo?");
                }

            }else
            {
                callback.error("Email mal escrito");
            }


        }else {
            callback.error("Se necesita un email");
        }
    }
    public void findUser(String email)
    {
        new Nodes().user(new EmailProcesor().sanitizedEmail(email)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LocalUser otherUser = dataSnapshot.getValue(LocalUser.class);
                if(otherUser != null)
                {
                    createChats(otherUser );
                }else
                {
                    callback.notFound();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void createChats(LocalUser otherUser)
    {
        FirebaseUser currenUser = new CurrentUser().getCurrentUser();
        String photo = new PhotoPreference(context).getPhoto();
        String key = new EmailProcesor().keyEmails(otherUser.getEmail());
        Chat currentChat = new Chat();
        currentChat.setKey(key);
        currentChat.setPhoto(photo);
        currentChat.setNotification(false);
        currentChat.setReceiver(currenUser.getEmail());

        Chat otherChat = new Chat();
        otherChat.setKey(key);
        otherChat.setPhoto(photo);
        otherChat.setReceiver(currenUser.getEmail());
        otherChat.setNotification(true);

        new Nodes().userChat(currenUser.getUid()).child(key).setValue(currentChat);
        new Nodes().userChat(otherUser.getUid()).child(key).setValue(otherChat);

        callback.success();
    }
}
