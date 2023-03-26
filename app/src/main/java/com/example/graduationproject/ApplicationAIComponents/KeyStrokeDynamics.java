package com.example.graduationproject.ApplicationAIComponents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.graduationproject.ApplicationModels.User;
import com.example.graduationproject.ApplicationSystem.GeneralSystemMethods;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class KeyStrokeDynamics {

    private static SharedPreferences currentUserKeyStrokes;
    private static int startTypingTime, stopTypingTime;


    public static void setNewKeyStrokesCriteria(Context context, String monitorCriteria){

        currentUserKeyStrokes =context.getApplicationContext().getSharedPreferences("keyStrokesData",0);
        SharedPreferences.Editor editor=currentUserKeyStrokes.edit();
        editor.putString("keyStrokesCriteria",monitorCriteria);
        editor.apply();
    }


    public static String getSavedKeyStorkesCriteria(Context context) {
        //get current user id return "newUser" if not exist (defValue)
        currentUserKeyStrokes =context.getApplicationContext().getSharedPreferences("keyStrokesData",0);
        return currentUserKeyStrokes.getString("keyStrokesCriteria","Empty");
    }


    @SuppressLint("ClickableViewAccessibility")
    public static void keyStrokesDynamicChecker(Context context, EditText editText){


        ArrayList<Integer> typedCharsTimes=new ArrayList<>();
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 1){

                    stopTypingTime = (int) (System.currentTimeMillis());

                    if (s.toString().equals(" "))
                        typedCharsTimes.add(stopTypingTime - startTypingTime);


                }else if (s.toString().length() > 1){

                    startTypingTime = stopTypingTime;
                    stopTypingTime = (int) System.currentTimeMillis();

                    String currentChar = String.valueOf(s.toString().charAt(s.toString().length()-1));

                    if ( currentChar.equals(" "))
                        typedCharsTimes.add(stopTypingTime - startTypingTime);


                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}


        });



        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startTypingTime = (int) System.currentTimeMillis();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int[] textLocation = new int[2];
                    editText.getLocationOnScreen(textLocation);

                    if (event.getRawX() >= textLocation[0] + editText.getWidth()
                            - editText.getTotalPaddingRight()) {

                        int perfectKey;
                        if (typedCharsTimes.size() < 11)
                            Toast.makeText(context, "Please continue typing to more accurate", Toast.LENGTH_SHORT).show();

                        else {


                            for (Integer i: typedCharsTimes){


                            }


                        }

                    }
                }
                return false;
            }
        });






    }

}
