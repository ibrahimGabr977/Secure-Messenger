package com.example.graduationproject.MessagesActivityComponents;


import android.app.Activity;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.graduationproject.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.graduationproject.MessagesActivityComponents.VoiceRecordConstants.*;


public class VoiceMessage implements View.OnClickListener {



    //stopping here on creating voice message



    private final Activity activity;
   // private EditText typing_editText;
    private final ImageView /*record_button, attach_button,*/ show_filters;
    private final Chronometer record_chronometer;
    private final TextView cancel_voice;
    private final ConstraintLayout filters_container;
    //private final String filePath;
    private final File record_file;

    private static int last_position;
    private  AudioRecord recorder = null;
    private static Thread recordingThread = null;
    private static boolean isRecording = false;


    public VoiceMessage(Activity activity, EditText typing_editText, ImageView record_button, ImageView attach_button) {
        this.activity = activity;


        typing_editText.setVisibility(View.INVISIBLE);
        record_button.setVisibility(View.INVISIBLE);
        attach_button.setVisibility(View.INVISIBLE);


        record_chronometer = activity.findViewById(R.id.voice_record_chronometer);//set this
        cancel_voice = activity.findViewById(R.id.voice_record_cancel);
        show_filters = activity.findViewById(R.id.messages_imageB_showFilters);


        record_chronometer.setVisibility(View.VISIBLE);
        cancel_voice.setVisibility(View.VISIBLE);
        show_filters.setVisibility(View.VISIBLE);


        cancel_voice.setOnClickListener(this);
        show_filters.setOnClickListener(this);

        filters_container = activity.findViewById(R.id.filters_container);


        record_file = new File(activity.getApplication().getExternalFilesDir("Audio").getPath());
        if (!record_file.exists())
            record_file.mkdir();

    }





    public void startRecording() {

        // start the chronometer
        record_chronometer.setBase(SystemClock.elapsedRealtime());
        record_chronometer.start();


        // Initialize Audio Recorder.
        recorder = new AudioRecord(AUDIO_SOURCE, RECORDER_SAMPLERATE,
                RECORDER_CHANNELS_IN, RECORDER_AUDIO_ENCODING, bufferSize);
        // Starts recording from the AudioRecord instance.
        recorder.startRecording();

        isRecording = true;

        recordingThread = new Thread(new Runnable() {
            public void run() {
                writeAudioDataToFile();
            }
        }, "AudioRecorder Thread");
        recordingThread.start();
    }


    private void writeAudioDataToFile() {
        //Write the output audio in byte
//        String filePath = "/sdcard/8k16bitMono.pcm";
        byte saudioBuffer[] = new byte[bufferSize];

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (isRecording) {
            // gets the voice output from microphone to byte format
            recorder.read(saudioBuffer, 0, bufferSize);
            try {
                //  writes the data to file from buffer stores the voice buffer
                os.write(saudioBuffer, 0, bufferSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stopRecording() {
        //  stops the recording activity
        if (null != recorder) {
            isRecording = false;
            recorder.stop();
            recorder.release();
            recorder = null;
            recordingThread = null;
            //PlayShortAudioFileViaAudioTrack1("/sdcard/8k16bitMono.pcm");
        }
    }



     void playShortAudioFileViaAudioTrack(double filter){
        // We keep temporarily filePath globally as we have only two sample sounds now..
        if (filePath==null)
            return;

        //Reading the file..
        File file = new File(filePath); // for ex. path= "/sdcard/samplesound.pcm" or "/sdcard/samplesound.wav"
        byte[] byteData = new byte[(int) file.length()];


        FileInputStream in = null;
        try {
            in = new FileInputStream( file );
            in.read( byteData );
            in.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Set and push to audio track..
        int intSize = AudioTrack.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS_OUT,
                RECORDER_AUDIO_ENCODING);


        AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC,RECORDER_SAMPLERATE,
                RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING, intSize, AudioTrack.MODE_STREAM);



        if (at!=null) {



            at.setPlaybackRate((int) (at.getSampleRate() *  (filter)));
            at.play();
            // Write the byte array to the track
            at.write(byteData, 0, byteData.length);
            at.stop();
            at.release();
        }


    }








    private void displayFilters(){

        filters_container.setVisibility(View.VISIBLE);

        for (int filtersId : FILTERS_IDS) activity.findViewById(filtersId).setOnClickListener(this);
    }


    private void selectFilter(int position){
        stopRecording();

        if (position == last_position){
            playShortAudioFileViaAudioTrack(1);

        }else {

            activity.findViewById(FILTERS_IDS[position])
                    .setBackground(activity.getResources().getDrawable(R.drawable.messages_record_filter_selectedbg));

            activity.findViewById(FILTERS_IDS[last_position])
                    .setBackground(activity.getResources().getDrawable(R.drawable.messages_record_filterbg));
            playShortAudioFileViaAudioTrack(FILTERS_VALUES[position]);
        }






    }




    @Override
    public void onClick(View v) {

        if (v.getId() == cancel_voice.getId()){
            cancelVoiceAndDeleteTempFile();

        } if (v.getId() == show_filters.getId()){
            displayFilters();

        } else if  (v.getId() == R.id.filter_textB_chipmunk){
            selectFilter(0);
            last_position = 0;
        }
        else if (v.getId() == R.id.filter_textB_bee){
            selectFilter(1);
            last_position = 1;
        }
        else if (v.getId() == R.id.filter_textB_beast){
            selectFilter(2);
            last_position = 2;
        }
        else if (v.getId() == R.id.filter_textB_ghost){
            selectFilter(3);
            last_position = 3;
        }
        else if (v.getId() == R.id.filter_textB_elephant){
            selectFilter(4);
            last_position = 4;
        }
        else if (v.getId() == R.id.filter_textB_robot){
            selectFilter(5);
            last_position = 5;
        }
        else if (v.getId() == R.id.filter_textB_motorBike){
            selectFilter(6);
            last_position = 6;
        }
        else if (v.getId() == R.id.filter_textB_pikachu){
            selectFilter(7);
            last_position = 7;
        }
        else if (v.getId() == R.id.filter_textB_minion){
            selectFilter(8);
            last_position = 8;
        }
        else if (v.getId() == R.id.filter_textB_oldLady){
            selectFilter(9);
            last_position = 9;
        }




    }



}
