package com.polimi.dilapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.VideoView;

/**
 * Created by Roberta on 17/11/2017.
 */

//This is Activity 1.1
public class ActivityAlfa extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alfa);
        Intent intent = getIntent();
        Log.d("Activity Alfa:","the onCreate()has been executed.");
        //When the activity is created the introduction video starts
        startNewVideo(R.id.video_box, R.raw.dummy_video);

        //When the introduction video finishes, the first session starts
        startNewVideo(R.id.video_box, R.raw.dummy_video);
    }

    private void startNewVideo(int identifier, int uriReference){
        VideoView video = (VideoView)findViewById(identifier);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+uriReference);
        video.setVideoURI(uri);
        video.start();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                return;
            }
        });

    }


}
