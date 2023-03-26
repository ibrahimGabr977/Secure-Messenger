package com.example.graduationproject.MessagesActivityComponents;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationproject.Activities.MessagesActivity;
import com.example.graduationproject.ApplicationAIComponents.AutoActivityMonitor;
import com.example.graduationproject.ApplicationAIComponents.ManualActivityMonitor;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.Message;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.MessageViewModel;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.MessageViewModelFactory;
import com.example.graduationproject.ApplicationSystem.GeneralSystemMethods;
import com.example.graduationproject.ApplicationSystem.MyFirebaseBuilder;
import com.example.graduationproject.R;
import java.util.List;

public class MessagesViewModel {

    private final String friendId, chatCode;
    private final Activity activity;
    private final RecyclerView recyclerView;
    private final MessageViewModel messageViewModel;
    private final EditText edit_message;





    public MessagesViewModel(Activity activity){

        this.activity = activity;

            messagesRecordActivity(ManualActivityMonitor.getInstance().isAlreadyRecord());


        friendId=activity.getIntent().getStringExtra("friendId"); //get clicked user id
        String friendName = activity.getIntent().getStringExtra("friendName"); //get clicked user name
        chatCode=activity.getIntent().getStringExtra("chatCode"); //get clicked user name


        TextView nameTextView = activity.findViewById(R.id.messages_text_name);
        ImageView friendImageView = activity.findViewById(R.id.messages_image_friend);

        nameTextView.setText(friendName);
        friendImageView.setImageResource(Integer.parseInt(activity.getIntent().getStringExtra("friendImage")));

        recyclerView = activity.findViewById(R.id.messages_recyclerView);



        messageViewModel = new ViewModelProvider((MessagesActivity)activity, new MessageViewModelFactory(activity.getApplication()))
                .get(MessageViewModel.class);



        //initialize sending buttons options voice , text and ....etc
        ImageView btnSend=activity.findViewById(R.id.messages_imageB_send);
        ImageView btnAttach=activity.findViewById(R.id.messages_imageB_attach);
        ImageView btnVoice=activity.findViewById(R.id.messages_imageB_record);
        TextView btnInfo = activity.findViewById(R.id.messages_textB_more);
        TextView btnBack = activity.findViewById(R.id.messages_click_back);





        //handle start writing to hide or show send button its not work on virtual keyboard try it on real mobile

        edit_message =activity.findViewById(R.id.messages_edit_typing);

        edit_message.setOnClickListener(v -> {
            //=============================================================================================================================
            if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                    (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto")
                            && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                ManualActivityMonitor.ActivityMonitorChecker(activity,
                        AutoActivityMonitor.editMessage());
            }
            //===============================================================================================================================
        });









        //on click send message button
        btnSend.setOnClickListener(v -> {

            if (edit_message.getText().toString().trim().length() == 0){
                //=============================================================================================================================
                if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                        (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto")
                                && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                    ManualActivityMonitor.ActivityMonitorChecker(activity,
                            AutoActivityMonitor.editMessageInValidSend());
                }
                //===============================================================================================================================

                Toast.makeText(activity, "Please type something to send", Toast.LENGTH_SHORT).show();

            }else {

                //=============================================================================================================================
                if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                        (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto")
                                && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                    ManualActivityMonitor.ActivityMonitorChecker(activity,
                            AutoActivityMonitor.editMessageValidSend());
                }
                //===============================================================================================================================


                if (!GeneralSystemMethods.isInternetAvailable(activity)) //check internet connection before sending
                    Toast.makeText(activity, "Please check your internet connection then try again", Toast.LENGTH_SHORT).show();

                else
                MyFirebaseBuilder.sendMessage(activity, friendId, chatCode, edit_message, messageViewModel);
            }




        });




        btnInfo.setOnClickListener(v -> {
            //=============================================================================================================================
            if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                    (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto")
                            && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                ManualActivityMonitor.ActivityMonitorChecker(activity,
                        AutoActivityMonitor.clickFriendInfo());
            }
            //===============================================================================================================================
        });

        btnBack.setOnClickListener(v -> {
            //=============================================================================================================================
            onBackClicked();
            activity.finish();

            //===============================================================================================================================
        });



        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //=============================================================================================================================
                if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                        (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto")
                                && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                    ManualActivityMonitor.ActivityMonitorChecker(activity,
                            AutoActivityMonitor.editMessageAttach());
                }
                //===============================================================================================================================
            }
        });


        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //=============================================================================================================================
                if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                        (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto")
                                && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                    ManualActivityMonitor.ActivityMonitorChecker(activity,
                            AutoActivityMonitor.editMessageRecord());
                }
                //===============================================================================================================================
            }
        });


    }




    public void fetchMessages(){

        MessagesListAdapter adapter = new MessagesListAdapter(new MessagesListAdapter.MessageDiff(), activity, recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        MyFirebaseBuilder.fetchOnlineMessages(friendId, chatCode, messageViewModel);

        messageViewModel.getAllCurrentMessages(friendId).observe((MessagesActivity) activity, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {

                adapter.submitList(messages);
            }
        });

    }



    public void onBackClicked(){
        //=============================================================================================================================
        if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto")
                        && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

            ManualActivityMonitor.ActivityMonitorChecker(activity,
                    AutoActivityMonitor.clickBackMessageActivity());
        }
        //===============================================================================================================================
        edit_message.clearFocus();


    }


    private void messagesRecordActivity(boolean isRecord) {

        TextView recorder = activity.findViewById(R.id.messages_textB_activityRecording);

        if (isRecord){
            recorder.setVisibility(View.VISIBLE);

            recorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ManualActivityMonitor.setNewMonitorCriteria(activity, ManualActivityMonitor.getInstance().getCurrentActivity());

                    ManualActivityMonitor.getInstance().setAlreadyRecord(false);
                    ManualActivityMonitor.getInstance().setAlreadyChecked(true);
                    ManualActivityMonitor.getInstance().setCurrentActivity(null);
                    recorder.setVisibility(View.GONE);
                }
            });
        }else
            recorder.setVisibility(View.GONE);


    }


}
