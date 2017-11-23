package com.polimi.dilapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LevelmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelmap);

        ImageView carrotImage = (ImageView) findViewById(R.id.carrot);
        Animation carrotAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_definition);
        carrotImage.startAnimation(carrotAnimation);

    }
}
