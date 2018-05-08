package cl.dany.flash_firebase_v3.Data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CurrentUser {
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public void CurrentUser(FirebaseUser currentuser) {
        this.currentUser = currentuser;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public String uid(){return currentUser.getUid();}

    public String email()
    {
        return getCurrentUser().getEmail();
    }
    public String sanitizedEmail(String email)
    {
        return email().replace("@","AT").replace(".","DOT");
    }
}
