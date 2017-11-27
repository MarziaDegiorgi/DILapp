package com.polimi.dilapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class LevelmapActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelmap);

        Button playButton = (Button) findViewById(R.id.playButton);

        startAnimation();
    }

   private void startAnimation() {
       ImageView carrotImage = (ImageView) findViewById(R.id.carrot);
       ImageView appleImage = (ImageView) findViewById(R.id.apple);
       ImageView pearImage = (ImageView) findViewById(R.id.pear);

       Animation animationRight = AnimationUtils.loadAnimation(LevelmapActivity.this, R.anim.half_rotation_right);
       Animation animationLeft = AnimationUtils.loadAnimation(LevelmapActivity.this, R.anim.half_rotation_left);

       //set animation repeating to infinite


       //start Images animation
       carrotImage.startAnimation(animationRight);
       appleImage.startAnimation(animationRight);
       pearImage.startAnimation(animationLeft);
   }

   public void showPopup( View view){
       PopupMenu popup = new PopupMenu(this, view);
       MenuInflater inflater = popup.getMenuInflater();
       inflater.inflate(R.menu.actions, popup.getMenu());
       popup.show();
   }
}
