package cl.dany.flash_firebase_v3.views.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cl.dany.flash_firebase_v3.R;
import cl.dany.flash_firebase_v3.views.main.chat.ChatsFragment;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String key = getIntent().getStringExtra(ChatsFragment.CHAT_KEY);
        String email = getIntent().getStringExtra(ChatsFragment.CHAT_RECEIVER);
        getSupportActionBar().setTitle(email);
    }
}
