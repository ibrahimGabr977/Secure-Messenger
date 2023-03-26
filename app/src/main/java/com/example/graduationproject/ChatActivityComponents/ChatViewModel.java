package com.example.graduationproject.ChatActivityComponents;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.graduationproject.Activities.ChatActivity;
import com.example.graduationproject.Activities.MessagesActivity;
import com.example.graduationproject.ApplicationAIComponents.AutoActivityMonitor;
import com.example.graduationproject.ApplicationAIComponents.ManualActivityMonitor;
import com.example.graduationproject.ApplicationModels.User;
import com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase.Friend;
import com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase.FriendViewModel;
import com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase.FriendViewModelFactory;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.MessageViewModel;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.MessageViewModelFactory;
import com.example.graduationproject.ApplicationSystem.ApplicationDefender;
import com.example.graduationproject.ApplicationSystem.GeneralSystemMethods;
import com.example.graduationproject.ApplicationSystem.MyFirebaseBuilder;
import com.example.graduationproject.ApplicationSystem.SharedData;
import com.example.graduationproject.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatViewModel {

    private final Activity activity;
    private final RecyclerView recyclerView;
    private final EditText editSearch;
    private final  FriendViewModel friendViewModel;


    public ChatViewModel(Activity activity) {
        this.activity = activity;
        recyclerView = activity.findViewById(R.id.chat_recyclerView);
        editSearch = activity.findViewById(R.id.chat_edit_search);



        ImageView profileImage = activity.findViewById(R.id.chat_image_profile);

        profileImage.setImageResource(Integer.parseInt(SharedData.getProfileImageResource(activity)));


        friendViewModel =  new ViewModelProvider((ChatActivity)activity, new FriendViewModelFactory(activity.getApplication()))
                .get(FriendViewModel.class);


        ImageView clickNotification = activity.findViewById(R.id.chat_imageB_notifications);
        ImageView clickSettings = activity.findViewById(R.id.chat_imageB_settings);




        editSearch.setOnClickListener(v -> {
            if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                    (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto") && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                ManualActivityMonitor.ActivityMonitorChecker(activity, AutoActivityMonitor.editSearch());
            }
        });


        clickNotification.setOnClickListener(v -> {
            if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                    (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto") && !ManualActivityMonitor.getInstance().isAlreadyChecked())){

                ManualActivityMonitor.ActivityMonitorChecker(activity, AutoActivityMonitor.clickNotifications());


            }


        });


        clickSettings.setOnClickListener(v -> {
            if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                    (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto") && !ManualActivityMonitor.getInstance().isAlreadyChecked())){

                ManualActivityMonitor.ActivityMonitorChecker(activity, AutoActivityMonitor.clickSettings());
            }


            ApplicationDefender.openSecurityWindow(activity, "Security Code", "Please enter your security code to " +
                    "continue. If you annoy from this message before opening settings" +
                    "you can disable security methods from settings\"\n", "open settings");



        });

        setTabLayout();


    }



    private void setTabLayout(){
        TabLayout tabLayout = activity.findViewById(R.id.chat_tablayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        //=============================================================================================================================
                        if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                                (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto") && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                            ManualActivityMonitor.ActivityMonitorChecker(activity,
                                    AutoActivityMonitor.clickMessagesTab());
                        }
                        //===============================================================================================================================
                        break;

                    case 1:
                        //=============================================================================================================================
                        if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                                (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto") && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                            ManualActivityMonitor.ActivityMonitorChecker(activity,
                                    AutoActivityMonitor.clickStoriesTab());
                        }
                        //===============================================================================================================================
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }




    public void fetchFriends(){
            chatRecordActivity(ManualActivityMonitor.getInstance().isAlreadyRecord());


        MyFirebaseBuilder.fetchNewFriends(activity, friendViewModel);

        MessageViewModel messageViewModel = new ViewModelProvider((ChatActivity)activity,
                new MessageViewModelFactory(activity.getApplication())).get(MessageViewModel.class);

        ChatListAdapter adapter = new ChatListAdapter(new ChatListAdapter.FriendDiff(), messageViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();




        friendViewModel.getAllFriends().observe((ChatActivity) activity, new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {
                adapter.submitList(friends);

            }
        });

    }





    @SuppressLint("ClickableViewAccessibility")
    public  void searchForFriends (){

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users");


        editSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int[] textLocation = new int[2];
                    editSearch.getLocationOnScreen(textLocation);

                    if (event.getRawX() >= textLocation[0] + editSearch.getWidth()
                            - editSearch.getTotalPaddingRight()){

                        if (!GeneralSystemMethods.isInternetAvailable(activity))
                            Toast.makeText(activity, "Please check your internet connection then try again", Toast.LENGTH_SHORT).show();


                        else {
                            if (editSearch.getText().toString().trim().length() == 5)


                                reference.child(""+editSearch.getText().toString())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()){

                                                    editSearch.clearFocus();
                                                    editSearch.setText("");
                                                    //=============================================================================================================================
                                                    if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                                                            (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto") && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                                                        ManualActivityMonitor.ActivityMonitorChecker(activity,
                                                                AutoActivityMonitor.editSearchValidExistSearch());
                                                    }
                                                    //===============================================================================================================================

                                                    User user = snapshot.getValue(User.class);
                                                    openSearchWindow(user.getUserId(), user.getName(), user.getProfileImageResource());

                                                }else {

                                                    //==============================================================================================================================
                                                    if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                                                            (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto") && !ManualActivityMonitor.getInstance().isAlreadyChecked())){

                                                        ManualActivityMonitor.ActivityMonitorChecker(activity, AutoActivityMonitor.editSearchNotExistSearch());
                                                    }
                                                    //================================================================================================================================


                                                    Toast.makeText(activity, "No results found, please use a correct id number",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                            else {
                                //===========================================================================================================================
                                if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                                        (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto")
                                                && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

                                    ManualActivityMonitor.ActivityMonitorChecker(activity, AutoActivityMonitor.editSearchInvalidSearch());
                                }
                                //==============================================================================================================================


                                Toast.makeText(activity, "Invalid id, please use a valid 5 digits id number",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }


                        return true;
                    }

                }
                return false;
            }
        });







    }





    private void openSearchWindow (String userId, String name, String imageResource){

           Dialog searchWindowDialog=new Dialog(activity);
            searchWindowDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            searchWindowDialog.setContentView(R.layout.search_window);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

            searchWindowDialog.getWindow().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.search_windowbg));

            lp.copyFrom(searchWindowDialog.getWindow().getAttributes());
            lp.width = (int) (activity.getIntent().getIntExtra("screenWidth",0)*0.8);
            lp.height = (int)(activity.getIntent().getIntExtra("screenHeight",0)*0.3);

            TextView searchName=searchWindowDialog.findViewById(R.id.searchWindow_text_name);
            TextView searchSend=searchWindowDialog.findViewById(R.id.searchWindow_click_send);
            ImageView imageView=searchWindowDialog.findViewById(R.id.searchWindow_image);


            searchSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendViewModel.getASpecificFriend(userId).observe((ChatActivity) activity, new Observer<Friend>() {
                        @Override
                        public void onChanged(Friend friend) {
                            String chatCode = null;

                            if (friend == null){


                                chatCode = createAChatCode(SharedData.getCurrentUserId(activity), userId);

                                Friend newFriend = new Friend(userId, name, imageResource, chatCode);

                                friendViewModel.insert(newFriend);

                            }else{
                                chatCode = friend.getChatCode();
                            }
                            searchWindowDialog.dismiss();
                            Intent intent = new Intent((ChatActivity) activity, MessagesActivity.class);
                            intent.putExtra("friendId", userId);
                            intent.putExtra("friendName", name);
                            intent.putExtra("chatCode", chatCode);
                            intent.putExtra("friendImage", imageResource);
                            activity.startActivity(intent);

                        }
                    });
                }
            });


            searchName.setText(name);
            imageView.setImageResource(Integer.parseInt(imageResource));


            //MyFirebaseBuilder.loadImageInto(userId, imageView);



            searchWindowDialog.show();
            searchWindowDialog.getWindow().setAttributes(lp);



    }


    private String createAChatCode(String first_id, String second_id){

        return  first_id+"_"+second_id;
    }



    public void backPressed() {
        //========================================================================================================
        if (ManualActivityMonitor.getInstance().isAlreadyRecord() ||
                (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto")
                        && !ManualActivityMonitor.getInstance().isAlreadyChecked())) {

            ManualActivityMonitor.ActivityMonitorChecker(activity, AutoActivityMonitor.clickBackChatActivity());
        }
        //==========================================================================================================

        editSearch.clearFocus();
    }





    private void chatRecordActivity(boolean isRecord) {

        TextView recorder = activity.findViewById(R.id.chat_textB_activityRecording);

        if (isRecord) {
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
