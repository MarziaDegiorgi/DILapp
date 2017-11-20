package com.polimi.dilapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoView introVideoView = (VideoView) findViewById(R.id.intro);
        introVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro));
        introVideoView.start();
    }
}
