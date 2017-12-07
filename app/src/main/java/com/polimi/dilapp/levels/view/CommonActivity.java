package com.polimi.dilapp.levels.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.IGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  This class is a View class that contains the common methods used by all the activity views
 */
class CommonActivity {
    private IGame.Presenter presenter;

    CommonActivity(IGame.Presenter presenter){
        this.presenter = presenter;
    }

    ArrayList<String> getList(String[] array) {
        return new ArrayList<String>(Arrays.asList(array));
    }

   void startIntro(Uri uri, final ArrayList<String> sequence, AppCompatActivity activity){
       VideoView video = activity.findViewById(R.id.video_box);
       video.setVideoURI(uri);
       video.start();
       video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           //When the introduction video finishes the first session begins
           @Override
           public void onCompletion(MediaPlayer mp) {
              presenter.startGame(sequence);
              mp.release();
           }
       });
    }

    void startMainVideo(Uri uri, AppCompatActivity activity){
        final VideoView video = activity.findViewById(R.id.video_box);
        video.setVideoURI(uri);
        video.start();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video.setVisibility(View.INVISIBLE);
                presenter.chooseElement();
                mp.release();
            }
        });
    }

    void startMainAnimation(AppCompatActivity activity, Animation animation, int resourceID, Context context){

        ImageView image = activity.findViewById(R.id.animation_box);
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable( context.getResources().getDrawable(resourceID));
        image.setVisibility(View.VISIBLE);
        image.setAnimation(animation);
        image.startAnimation(animation);
    }

    /**
     *  Set animation in given context in case of not correct answer
     * @param context of the activity
     * @param activity class
     */
    void setNotCorrectAnswerAnimation(Context context,AppCompatActivity activity){

        ImageView image = activity.findViewById(R.id.animation_box);
        image.setVisibility(View.VISIBLE);
        image.getResources().getDrawable(R.drawable.not_correct_answer);

        Animation animationNotCorrect = AnimationUtils.loadAnimation(context, R.anim.slide);
        image.setAnimation(animationNotCorrect);
        image.startAnimation(animationNotCorrect);
        MediaPlayer request = MediaPlayer.create(context, R.raw.not_correct_answer);
        request.start();
        final ImageView finalImage = image;
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                finalImage.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     *  Set the correct answer animation and then pass to the next element calling the presenter
     * @param context of the activity
     * @param activity class
     */
    void setCorrectAnswerAnimation(Context context, AppCompatActivity activity){

        ImageView image = activity.findViewById(R.id.animation_box);
        image.setVisibility(View.VISIBLE);
        image.getResources().getDrawable(R.drawable.correct_answer);
        Animation animationCorrect = AnimationUtils.loadAnimation(context, R.anim.slide);
        image.setAnimation(animationCorrect);
        image.startAnimation(animationCorrect);
        MediaPlayer request = MediaPlayer.create(context, R.raw.correct_answer);
        request.start();
        final ImageView finalImage = image;
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finalImage.setVisibility(View.INVISIBLE);
                mp.release();
                presenter.chooseElement();
            }
        });
    }


}
