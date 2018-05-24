package com.polimi.dilapp.levels.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.main.CreateAccountActivity;
import com.polimi.dilapp.startgame.StartGameActivity;

public class EndLevelScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();

        final String activity = intent.getStringExtra("Activity");
        final String buttonName = intent.getStringExtra("ButtonName");

        setContentView(R.layout.end_level_screen);

        Button nextButton = findViewById(R.id.nextButton);
        Button exitButton = findViewById(R.id.exitButton);

        Animation animationBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation animationRight = AnimationUtils.loadAnimation(EndLevelScreen.this, R.anim.half_rotation_right);
        Animation animationLeft = AnimationUtils.loadAnimation(EndLevelScreen.this, R.anim.half_rotation_left);
        Animation animationLeftAndRight = AnimationUtils.loadAnimation(EndLevelScreen.this, R.anim.lion_rotation);


        ImageView imageTwo = findViewById(R.id.pearIm);
        imageTwo.setVisibility(View.VISIBLE);
        imageTwo.setAnimation(animationLeft);
        imageTwo.startAnimation(animationLeft);

        ImageView imageThree = findViewById(R.id.appleIm);
        imageThree.setVisibility(View.VISIBLE);
        imageThree.setAnimation(animationLeftAndRight);
        imageThree.startAnimation(animationLeftAndRight);

        ImageView imageOne = findViewById(R.id.carrotIm);
        imageOne.setVisibility(View.VISIBLE);
        imageOne.setAnimation(animationRight);
        imageOne.startAnimation(animationRight);

       nextButton.startAnimation(animationBounce);
       exitButton.startAnimation(animationBounce);


        nextButton.setText(buttonName);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    intent = new Intent(getApplicationContext(), Class.forName(activity));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                overridePendingTransition(R.anim.right_enter, R.anim.right_exit);
                startActivity(intent);
            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                overridePendingTransition(R.anim.right_enter, R.anim.right_exit);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
