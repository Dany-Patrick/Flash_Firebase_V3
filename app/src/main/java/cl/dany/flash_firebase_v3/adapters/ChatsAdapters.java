package cl.dany.flash_firebase_v3.adapters;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.squareup.picasso.Picasso;

import cl.dany.flash_firebase_v3.R;
import cl.dany.flash_firebase_v3.data.CurrentUser;
import cl.dany.flash_firebase_v3.data.Nodes;
import cl.dany.flash_firebase_v3.models.Chat;
import cl.dany.flash_firebase_v3.views.main.chat.ChatsListener;

public class ChatsAdapters extends FirebaseRecyclerAdapter<Chat, ChatsAdapters.ChatHolder> {
    private ChatsListener listener;


    public ChatsAdapters(LifecycleOwner lifecycleOwner, ChatsListener listener) {
        super(new FirebaseRecyclerOptions.Builder<Chat>()
                    .setQuery(new Nodes().userChat(new CurrentUser().uid()),Chat.class)
                    .setLifecycleOwner(lifecycleOwner)
                    .build()
        );
        this.listener = listener;

    }


    @Override
    protected void onBindViewHolder(@NonNull final ChatHolder holder, int position, @NonNull Chat model) {

        if(model.isNotification())
        {
            holder.view.setVisibility(View.VISIBLE);
        }else
        {
            holder.view.setVisibility(View.GONE);
        }
        Picasso.get().load(model.getPhoto()).centerCrop().fit().into(holder.bubbleImageView);
        holder.textView.setText(model.getReceiver());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat auxChat = getItem(holder.getAdapterPosition());
                listener.clicked(auxChat.getKey(), auxChat.getReceiver());
            }
        });


    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chart,parent, false);
        return new ChatHolder(view);
    }


    public class ChatHolder extends RecyclerView.ViewHolder{
        private BubbleImageView bubbleImageView;
        private TextView textView;
        private View view;
        public ChatHolder(View itemView) {
            super(itemView);
            bubbleImageView = itemView.findViewById(R.id.photoBiv);
            textView = itemView.findViewById(R.id.emailTv);
            view = itemView.findViewById(R.id.notificationV);

        }
    }

}
