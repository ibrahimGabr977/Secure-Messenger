package com.example.graduationproject.MessagesActivityComponents;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.Message;
import com.example.graduationproject.ApplicationSystem.GeneralSystemMethods;
import com.example.graduationproject.ApplicationSystem.SharedData;
import com.example.graduationproject.R;


public class MessagesListAdapter extends ListAdapter<Message, RecyclerView.ViewHolder> {

    private final Context context;
    private final RecyclerView recyclerView;

    MessagesListAdapter(@NonNull DiffUtil.ItemCallback<Message> diffCallback, Context context, RecyclerView recyclerView) {
        super(diffCallback);

        this.context = context;
        this.recyclerView = recyclerView;

    }



    // Different types of rows
    private  static final int TYPE_ITEM_ME = 0;
    private  static final int TYPE_ITEM_FRIEND = 1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewRoot;
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {

            //set layouts in different view types
            case TYPE_ITEM_ME:

                //sender the blue one
                viewRoot = LayoutInflater.from(context).inflate(R.layout.message_me_holder, parent,
                        false);
                viewHolder = new MessageViewHolderMe(viewRoot);
                return viewHolder;

            //receiver the gray one
            case TYPE_ITEM_FRIEND:
                viewRoot = LayoutInflater.from(context).inflate(R.layout.message_friend_holder, parent, false);
                viewHolder = new MessageViewHolderFriend(viewRoot);
                return viewHolder;


        }
        return null;
    }



    @Override
    public int getItemViewType(int position) {

        //return the viewType according to the messages' owner id
        if (getItem(position).getFrom_id().equals(SharedData.getCurrentUserId(context)))
            return TYPE_ITEM_ME;

        else
            return TYPE_ITEM_FRIEND;

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message=getItem(position);



        if (holder.getItemViewType() == TYPE_ITEM_ME){



            ((MessageViewHolderMe)holder).myMessage.setText(message.getMessage_body()); //set my message


            //date show settings
            //show date before the first message in message list  if the date of message changed
            // by checking previous message
            if (position==0 || !getItem(position).getFrom_id().equals(getItem(position-1).getFrom_id())) {
                ((MessageViewHolderMe) holder).myMessageDate.setVisibility(View.VISIBLE);
                ((MessageViewHolderMe) holder).myMessageDate
                        .setText(GeneralSystemMethods.dateConverter(message.getMessage_timeMillis(), "Full Date"));

                //else if its not first message and date didn't change then hide date
            }else {
                ((MessageViewHolderMe)holder).myMessageDate.setVisibility(View.GONE);
                ((MessageViewHolderMe)holder).myMessageDate.setText("");

            }


        }else {




            ((MessageViewHolderFriend)holder).friendMessage.setText(message.getMessage_body());  //set friend message



            //date show settings
            //show date before the first message in message list  if the date of message
            // by checking previous message
            if (position==0 || !getItem(position).getFrom_id().equals(getItem(position-1).getFrom_id())) {
                ((MessageViewHolderFriend) holder).friendMessageDate.setVisibility(View.VISIBLE);
                ((MessageViewHolderFriend) holder).friendMessageDate
                        .setText(GeneralSystemMethods.dateConverter(message.getMessage_timeMillis(),"Full Date"));

            }else {
                ((MessageViewHolderFriend)holder).friendMessageDate.setVisibility(View.GONE);
                ((MessageViewHolderFriend)holder).friendMessageDate.setText("");

            }




                //findViewHolderForAdapterPosition method return the holder of certain position
                //hide and remove image and imageView of the previous message
                //on startup this will return null so i need to handle that

                if (((MessageViewHolderFriend)recyclerView.findViewHolderForAdapterPosition(position-1)) != null){

                    //receiver image show settings
                    //show image in the side of message if its the first message on messages' list
                    //or the ids of the current and previous messages are different
                    // this case if the receiver send one message only
                    if (position ==0 || !getItem(position).getTo_id().equals(getItem(position-1).getTo_id())){
                        ((MessageViewHolderFriend) holder).friendImage.setVisibility(View.VISIBLE);
                        ((MessageViewHolderFriend) holder).friendImage
                                .setImageResource(Integer.parseInt(((Activity)context)
                                        .getIntent().getStringExtra("friendImage")));


                        //else if receiver sent several messages show image in side of  the last message only
                        //checking that case by check if its not the first message and the receiver of this message
                        //is the same as its previous message

                    }else if (position != 0 && getItem(position).getTo_id().equals(getItem(position-1).getTo_id())) {
                        ((MessageViewHolderFriend) holder).friendImage.setVisibility(View.VISIBLE);
                        ((MessageViewHolderFriend) holder).friendImage
                                .setImageResource(Integer.parseInt(((Activity) context)
                                        .getIntent().getStringExtra("friendImage")));
                        ((MessageViewHolderFriend) recyclerView.findViewHolderForAdapterPosition(position - 1))
                                .friendImage.setVisibility(View.INVISIBLE);

                        ((MessageViewHolderFriend) recyclerView.findViewHolderForAdapterPosition(position - 1))
                                .friendImage.setImageResource(0);

                    }

                    //handle startup only because findViewHolder... will return null on startup because the view isn't created yet
            }else{
                    if (position == 0 || position == getItemCount()-1 ||
                            !getItem(position).getTo_id().equals(getItem(position+1).getTo_id())){
                        ((MessageViewHolderFriend) holder).friendImage.setVisibility(View.VISIBLE);
                        ((MessageViewHolderFriend) holder).friendImage
                                .setImageResource(Integer.parseInt(((Activity) context)
                                        .getIntent().getStringExtra("friendImage")));
                    }
                }

        }

    }



    static class MessageDiff extends DiffUtil.ItemCallback<Message> {

        @Override
        public boolean areItemsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
            return oldItem.getMessage_timeMillis() == (newItem.getMessage_timeMillis());
        }
    }




    //My messages viewHolder components
     private static class MessageViewHolderMe extends RecyclerView.ViewHolder {

        private final TextView myMessage, myMessageDate;

        private MessageViewHolderMe(@NonNull View itemView) {
            super(itemView);

            myMessage = itemView.findViewById(R.id.messageHolder_text_content_me);
            myMessageDate = itemView.findViewById(R.id.messageHolder_text_me_date);
        }


    }




    //Friend messages viewHolder components
     private static class MessageViewHolderFriend extends RecyclerView.ViewHolder {

        private final TextView friendMessage, friendMessageDate;
        private final ImageView friendImage;

        private MessageViewHolderFriend(@NonNull View itemView) {
            super(itemView);

            friendMessage = itemView.findViewById(R.id.messageHolder_text_content_friend);
            friendMessageDate = itemView.findViewById(R.id.messageHolder_text_friend_date);
            friendImage = itemView.findViewById(R.id.messageHolder_image_friend);
        }


    }

}
