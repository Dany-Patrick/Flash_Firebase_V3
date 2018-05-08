package cl.dany.flash_firebase_v3.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Nodes {
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference users()
    {
        return root.child("users");
    }
    public DatabaseReference user(String key)
    {
        return users().child(key);
    }
}
