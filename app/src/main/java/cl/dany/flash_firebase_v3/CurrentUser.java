package cl.dany.flash_firebase_v3;

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

    public void setCurrentUser(FirebaseUser currentUser) {
        this.currentUser = currentUser;
    }
}
