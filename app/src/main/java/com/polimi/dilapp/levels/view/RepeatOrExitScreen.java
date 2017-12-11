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

public class RepeatOrExitScreen extends AppCompatActivity {
//TODO: add a video that explains that the child did the previous activity wrong
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();

        final String activity = intent.getStringExtra("Activity");
        setContentView(R.layout.repeat_or_exit_screen);

        Animation animation = AnimationUtils.loadAnimation(RepeatOrExitScreen.this, R.anim.half_rotation_right);
        ImageView imageOne = findViewById(R.id.imageOne);
        imageOne.setVisibility(View.VISIBLE);
        imageOne.setAnimation(animation);
        imageOne.startAnimation(animation);

        ImageView imageTwo = findViewById(R.id.imageTwo);
        imageTwo.setVisibility(View.VISIBLE);
        imageTwo.setAnimation(animation);
        imageTwo.startAnimation(animation);

        ImageView imageThree = findViewById(R.id.imageThree);
        imageThree.setVisibility(View.VISIBLE);
        imageThree.setAnimation(animation);
        imageThree.startAnimation(animation);

        Button repeatButton = findViewById(R.id.repeatButton);
        repeatButton.setOnClickListener(new View.OnClickListener() {
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
