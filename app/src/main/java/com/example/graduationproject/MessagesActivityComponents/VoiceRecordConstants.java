package com.example.graduationproject.MessagesActivityComponents;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import com.example.graduationproject.R;


public class VoiceRecordConstants {




    //#1 (605.0/441.0) Chipmunk
    //#2 (2) Bee
    //#3 (242.0/441.0)  Slow Motion (Evil) (beast)
    //#4 (1.6) Ghost
    //#5 (400.0/147.0) Elephent
    //#6 (320.0/441.0) Robot
    //#7 (19.5/21.0)  Motor Bike
    //#8 (200.0/63.0) Pikachu
    //#9 (340.0/147.0) Minion
    //#10 (626.0/441.0) old lady


    public static final int RECORDER_SAMPLERATE = AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC);
    public static final int RECORDER_CHANNELS_IN = AudioFormat.CHANNEL_IN_MONO;
    public static final int RECORDER_CHANNELS_OUT = AudioFormat.CHANNEL_OUT_MONO;
    public static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    public static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;

    // Initialize minimum buffer size in bytes.
    public static int  bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
            RECORDER_CHANNELS_IN, RECORDER_AUDIO_ENCODING);







    public static final double [] FILTERS_VALUES = {605.0/441.0, 2, 242.0/441.0, 1.6, 400.0/147.0, 320.0/441.0, 19.5/21.0, 200.0/63.0,
            340.0/147.0, 626.0/441.0};

    public static final int [] FILTERS_IDS = { R.id.filter_textB_chipmunk, R.id.filter_textB_bee,
            R.id.filter_textB_beast, R.id.filter_textB_ghost, R.id.filter_textB_elephant,
            R.id.filter_textB_robot, R.id.filter_textB_motorBike, R.id.filter_textB_pikachu,
            R.id.filter_textB_minion, R.id.filter_textB_oldLady };









}
