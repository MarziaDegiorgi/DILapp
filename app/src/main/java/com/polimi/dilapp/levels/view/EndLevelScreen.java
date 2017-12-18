package com.polimi.dilapp.levels.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.main.CreateAccountActivity;

public class EndLevelScreen extends AppCompatActivity {
//TODO: add a video that explains that the child did the previous activity wrong
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();

        final String activity = intent.getStringExtra("Activity");
        final String buttonName = intent.getStringExtra("ButtonName");

        setContentView(R.layout.end_level_screen);

        Animation animationOne = AnimationUtils.loadAnimation(EndLevelScreen.this, R.anim.jump_and_rotate);
        ImageView imageOne = findViewById(R.id.imageOne);
        imageOne.setVisibility(View.VISIBLE);
        imageOne.setAnimation(animationOne);
        imageOne.startAnimation(animationOne);

        Animation animationTwo = AnimationUtils.loadAnimation(EndLevelScreen.this, R.anim.half_rotation_right);
        ImageView imageTwo = findViewById(R.id.imageTwo);
        imageTwo.setVisibility(View.VISIBLE);
        imageTwo.setAnimation(animationTwo);
        imageTwo.startAnimation(animationTwo);

        ImageView imageThree = findViewById(R.id.imageThree);
        imageThree.setVisibility(View.VISIBLE);
        imageThree.setAnimation(animationTwo);
        imageThree.startAnimation(animationTwo);

        Button nextButton = findViewById(R.id.nextButton);
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
                startActivity(intent);
            }
        });

        //TODO: verify the correct activity to begin clicking on Exit.
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
