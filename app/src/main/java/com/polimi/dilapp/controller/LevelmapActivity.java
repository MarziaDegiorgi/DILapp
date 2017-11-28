package com.polimi.dilapp.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.polimi.dilapp.R;
import com.polimi.dilapp.model.BounceInterpolator;

public class LevelmapActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelmap);

        startAnimation();
    }

    /**
     * Start the animations in the level map activity
     */
   private void startAnimation() {
       ImageView carrotImage = (ImageView) findViewById(R.id.carrot);
       ImageView appleImage = (ImageView) findViewById(R.id.apple);
       ImageView pearImage = (ImageView) findViewById(R.id.pear);
       Button playButton = (Button) findViewById(R.id.playButton);

       // Load animations
       final Animation animationBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
       final Animation animationRight = AnimationUtils.loadAnimation(LevelmapActivity.this, R.anim.half_rotation_right);
       final Animation animationLeft = AnimationUtils.loadAnimation(LevelmapActivity.this, R.anim.half_rotation_left);

       // Add the personilized interpolator for "animationBounce"
       BounceInterpolator interpolator = new BounceInterpolator(0.7, 10);
       animationBounce.setInterpolator(interpolator);

       // Start animations
       playButton.startAnimation(animationBounce);
       carrotImage.startAnimation(animationRight);
       appleImage.startAnimation(animationRight);
       pearImage.startAnimation(animationLeft);
   }

    /**
     *  Display a popup menu when the menu button is clicked
     * @param view
     */
   public void showPopup( View view){
       PopupMenu popup = new PopupMenu(this, view);
       MenuInflater inflater = popup.getMenuInflater();
       inflater.inflate(R.menu.actions, popup.getMenu());
       popup.show();
   }

   //TODO:Redirect to the right activity when Play Button is clicked

}
