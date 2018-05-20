package cl.dany.flash_firebase_v3.views.main.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.dany.flash_firebase_v3.R;
import cl.dany.flash_firebase_v3.adapters.ChatsAdapters;
import cl.dany.flash_firebase_v3.views.chat.ChatActivity;

public class ChatsFragment extends Fragment implements ChatsListener{

    public static final String CHAT_KEY = "cl.dany.flash_firebase_v3.views.main.chat.KEY.CHAT_KEY";
    public static final String CHAT_RECEIVER = "cl.dany.flash_firebase_v3.views.main.chat.KEY.CHAT_RECEIVER";

public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerVW);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        ChatsAdapters adapter = new ChatsAdapters( getActivity(),this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void clicked(String key, String mail) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra(CHAT_KEY, key);
        intent.putExtra(CHAT_RECEIVER,mail);
        startActivity(intent);
    }
}
