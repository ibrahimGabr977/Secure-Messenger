package com.example.graduationproject.ChatActivityComponents;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.graduationproject.Activities.ChatActivity;
import com.example.graduationproject.Activities.MessagesActivity;
import com.example.graduationproject.ApplicationAIComponents.AutoActivityMonitor;
import com.example.graduationproject.ApplicationAIComponents.ManualActivityMonitor;
import com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase.Friend;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.Message;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.MessageViewModel;
import com.example.graduationproject.ApplicationSystem.GeneralSystemMethods;
import com.example.graduationproject.ApplicationSystem.MyFirebaseBuilder;
import com.example.graduationproject.R;



public class ChatListAdapter extends ListAdapter<Friend,ChatListAdapter.ChatViewHolder> {

    private final MessageViewModel messageViewModel;


     ChatListAdapter(@NonNull DiffUtil.ItemCallback<Friend> diffCallback, MessageViewModel messageViewModel) {
        super(diffCallback);
        this.messageViewModel = messageViewModel;


    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewRoot = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_friend_model_holder, parent, false);

        //to make a friend chat model occupies a percentage height of the screen
        ViewGroup.LayoutParams params=viewRoot.getLayoutParams();
        params.height=(int)(((Activity)viewRoot.getContext())   //--get screen height from welcome activity intent
                .getIntent().getIntExtra("screenHeight",0)*0.115);
        viewRoot.setLayoutParams(params);



        return new ChatViewHolder(viewRoot);
    }



    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        Friend friend = getItem(position);
        holder.name.setText(friend.getFriend_name());

        messageViewModel.getLastMessage(friend.getFriend_id())
                .observe((ChatActivity)holder.itemView.getContext(), new Observer<Message>() {
            @Override
            public void onChanged(Message message) {
                if (message != null){
                    holder.last_message.setText(message.getMessage_body());
                    holder.last_message_date
                            .setText(GeneralSystemMethods.dateConverter(message.getMessage_timeMillis(), "Sub Date"));
                }else{
                    holder.last_message.setText("");
                    holder.last_message_date.setText("");
                }



            }
        });


        MyFirebaseBuilder.getUnreadMessages(friend.getFriend_id(), friend.getChatCode(), holder.unread_messages);

        //holder.friendImage.setImageURI(Uri.fromFile(new File(""+friend.getFriend_profile_image_path())));

        holder.friendImage.setImageResource(Integer.parseInt(friend.getFriend_profile_image_path()));


        holder.itemView.setOnClickListener((View.OnClickListener) v -> {

            //=============================================================================================================================
            if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                    (!ManualActivityMonitor.getSavedMonitorCriteria(holder.itemView.getContext()).equals("Auto") && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                ManualActivityMonitor.ActivityMonitorChecker((ChatActivity)holder.itemView.getContext(),
                        AutoActivityMonitor.friendRecyclerViewSelectFriend());
            }
            //===============================================================================================================================

            Intent intent = new Intent((ChatActivity)holder.itemView.getContext(), MessagesActivity.class);
            intent.putExtra("friendId", friend.getFriend_id());
            intent.putExtra("friendName", friend.getFriend_name());
            intent.putExtra("chatCode", friend.getChatCode());
            intent.putExtra("friendImage", friend.getFriend_profile_image_path());
            holder.itemView.getContext().startActivity(intent);
        });








    }



    static class FriendDiff extends DiffUtil.ItemCallback<Friend> {

        @Override
        public boolean areItemsTheSame(@NonNull Friend oldItem, @NonNull Friend newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Friend oldItem, @NonNull Friend newItem) {
            return oldItem.getFriend_id().equals(newItem.getFriend_id());
        }
    }




     class ChatViewHolder extends RecyclerView.ViewHolder {

        private final ImageView friendImage;
        private final TextView name, last_message, last_message_date, unread_messages;

        private ChatViewHolder(View itemView) {
            super(itemView);


            friendImage = itemView.findViewById(R.id.chatHolder_image_friend);
            name = itemView.findViewById(R.id.chatHolder_text_name);
            last_message = itemView.findViewById(R.id.chatHolder_text_last_message);
            last_message_date = itemView.findViewById(R.id.chatHolder_text_last_message_date);
            unread_messages = itemView.findViewById(R.id.chatHolder_text_unread_messages);





        }




    }
}
