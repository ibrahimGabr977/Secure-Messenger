package com.example.graduationproject.ApplicationSystem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.graduationproject.ApplicationModels.OnlineMessage;
import com.example.graduationproject.ApplicationModels.User;
import com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase.Friend;
import com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase.FriendViewModel;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.Message;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.MessageViewModel;
import com.example.graduationproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class MyFirebaseBuilder {





    public static void createNewUserAccount(Activity activity, String name, String securityCode) {


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users");


        //create user id
        String makeUserId= createUserId();
        final String finalMakeUserId = makeUserId;

        //check if the created one exist or not
        //if exist make another one by recursive this method till created existing one


        reference.orderByChild("id").equalTo(makeUserId)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            createNewUserAccount(activity, name, securityCode); //recursive the method if id exist

                        } else {  //if not continue signing up

                            User user = new User(finalMakeUserId, name,
                                    ""+R.drawable.profile_image_test2);


                            reference.child(finalMakeUserId).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {


                                                //save created user information
                                                SharedData.saveNewUserData(activity, finalMakeUserId, name,securityCode,
                                                        ""+R.drawable.profile_image_test2);


                                                GeneralSystemMethods.startChat(activity);

                                            } else
                                                Toast.makeText(activity,
                                                        "" + task.getException(), Toast.LENGTH_SHORT).show();

                                        }
                                    });


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError firebaseError) {

                    }
                });




    }




    private static String createUserId(){

        String id = ""+((int)((Math.random()*100000)))+"";
        return id.length() == 5 ? id : createUserId();




    }





    public static void sendTextMessage(Activity activity, String to_id, String chatCode,
                                   EditText edit_message, MessageViewModel viewModel) {

        long message_id = System.currentTimeMillis();
        //initialize database
        DatabaseReference messagesRef= FirebaseDatabase.getInstance().getReference().child("Messages").child(chatCode)
                .child(""+message_id);




        //sending message


         OnlineMessage onlineMessage=new OnlineMessage(""+message_id,ServerValue.TIMESTAMP ,edit_message.getText().toString(),
                 SharedData.getCurrentUserId(activity),
                to_id, "Text");




        messagesRef.setValue(onlineMessage)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {


                        //hide keyboard when message send
                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edit_message.getWindowToken(), 0);

                        edit_message.setText("");
                        edit_message.clearFocus();

                        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                OnlineMessage message = snapshot.getValue(OnlineMessage.class);


                                Toast.makeText(activity, ""+messagesRef.getKey(), Toast.LENGTH_SHORT).show();
                                viewModel.insertMessage(new Message((Long) message.getMessage_date(),
                                        message.getMessage_body(), message.getFrom_id(), message.getTo_id(),
                                        message.getMessage_type()));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });






                    }


                });






    }





    public static void sendVoiceRecordMessage(String message_id){

    }





    public static void fetchOnlineMessages(String friend_id, String chatCode, MessageViewModel viewModel){

        //initialize database
        DatabaseReference messagesRef= FirebaseDatabase.getInstance().getReference().child("Messages").child(chatCode);

        messagesRef.orderByChild("from_id").equalTo(friend_id)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    OnlineMessage onlineMessage = dataSnapshot.getValue(OnlineMessage.class);

                        viewModel.insertMessage(new Message((Long) onlineMessage.getMessage_date(), onlineMessage.getMessage_body(),
                                onlineMessage.getFrom_id(), onlineMessage.getTo_id(), onlineMessage.getMessage_type()));

                        messagesRef.child(onlineMessage.getMessage_id()).removeValue();





                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }




    public static void fetchNewFriends(Activity activity, FriendViewModel viewModel){

        //initialize database
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();


        reference.child("Messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot messagesSnapshot) {
                for (DataSnapshot dss: messagesSnapshot.getChildren()){

                    String [] newFriend = dss.getKey().split("_");

                    if (newFriend[1].equals(SharedData.getCurrentUserId(activity)))

                        reference.child("Users").child(newFriend[0])
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot friendsSnapshot) {

                                User user = friendsSnapshot.getValue(User.class);

                                viewModel.insert(new Friend(user.getUserId(), user.getName(), user.getProfileImageResource(),
                                        dss.getKey()));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }







    public static void getUnreadMessages(String friend_id, String chatCode, TextView unread_messages) {

        //initialize database
        DatabaseReference messagesRef= FirebaseDatabase.getInstance().getReference().child("Messages").child(chatCode);

        messagesRef.orderByChild("from_id").equalTo(friend_id).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getChildrenCount() == 0)
                    unread_messages.setVisibility(View.INVISIBLE);
                else{
                    unread_messages.setVisibility(View.VISIBLE);
                    unread_messages.setText(""+snapshot.getChildrenCount());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    //Upload image to firebase storage --may i will use it on other uploading later else u can remove it and add it's code directly
    public  static void uploadImage(Uri imageUri, View uploadingProgressView, final String language, final Context context){

        //Show uploading progress views
        ConstraintLayout creatingProgressLayout=uploadingProgressView.findViewById(R.id.creatingProgressLayout); //container only for setting background
        final TextView creatingProgress=uploadingProgressView.findViewById(R.id.creatingProgress);  //the progress string with form "Creating 68.0%"
        final ProgressBar creatingProgressBar=uploadingProgressView.findViewById(R.id.creatingProgressBar); //the progress bar (horizontal)
        final Handler recreateDelay=new Handler();


        creatingProgressLayout.setVisibility(View.VISIBLE); //UnHide progress view



        String extension=imageUri.getLastPathSegment().substring(imageUri.getLastPathSegment().length()-3); //get file extension from last 3 letters
        final File compressedImage;

        //compress PNG
        if (extension.equals("png") || extension.equals("PNG")){

            compressedImage = new Compressor.Builder(context)
                    .setMaxWidth(450).setMaxHeight(650).setQuality(75).setCompressFormat(Bitmap.CompressFormat.PNG)
                    .setDestinationDirectoryPath(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()).build()
                    .compressToFile(new File(getPathFromGallery(context, imageUri)));

            extension="png";

            //compress jpeg, jpg and ...etc
        }else{
            compressedImage =Compressor.getDefault(context).compressToFile(new File(getPathFromGallery(context, imageUri)));
            extension="jpeg";

        }


        final Uri file=Uri.fromFile(compressedImage); //output compressed file


        UploadTask uploadTask =
                FirebaseStorage.getInstance().getReference()
                        .child("postsImages").child(""+postId+extension).putFile(file); //upload
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(context,
                        ""+context.getResources().getString(R.string.post_created_failed)
                                +" Because "+exception.getMessage(),
                        Toast.LENGTH_SHORT).show();

                postsReference.child(language+"Posts").child(postId).removeValue(); //remove post reference if image uploading fail



            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                if (compressedImage.exists()) //delete temporary compression file
                    compressedImage.delete();

                isCreated =true; //variable for notify success after recreate()

                recreateDelay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity) context).recreate();;
                    }
                },300L);


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //get uploading progress to views
                double progress =  (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();

                creatingProgressBar.setProgress((int) progress);
                creatingProgress.setText("Creating... "+(int) progress+".0 %");

            }
        });


    }



}
