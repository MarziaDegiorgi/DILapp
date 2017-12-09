package com.polimi.dilapp.levels.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.VideoView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.IGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        video.setVisibility(View.VISIBLE);
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
        image.setImageDrawable(context.getResources().getDrawable(resourceID));
        image.setVisibility(View.VISIBLE);
        image.setAnimation(animation);
        image.startAnimation(animation);
    }

    /**
     *  Set video in given context in case of not correct answer in order then to repeat the request
     * @param context of the activity
     * @param image
     */
    void setVideoWrongAnswerToRepeat(final ImageView image, Context context){
        image.setVisibility(View.VISIBLE);
        //TODO: here we have to put the video for not correct answer
        MediaPlayer request = MediaPlayer.create(context, R.raw.children_wrong_answer);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                image.setVisibility(View.INVISIBLE);
                presenter.askCurrentElement();
                mp.release();

            }
        });
    }
    /**
     *  Set video in given context in case of not correct answer in order then to go on
     * @param context of the activity
     * @param image
     */
    void setVideoWrongAnswerAndGoOn(final ImageView image, Context context){
        image.setVisibility(View.VISIBLE);
        //TODO: here we have to put the video for not correct answer
        MediaPlayer request = MediaPlayer.create(context, R.raw.children_wrong_answer);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                image.setVisibility(View.INVISIBLE);
                presenter.chooseElement();
                mp.release();

            }
        });
    }
    /**
     *  Disable the ImageView received as parameter
     * @param imageView
     */
    void disableView(ImageView imageView){
        imageView.setVisibility(View.INVISIBLE);
    }
    /**
     *  Set the correct answer video and then pass to the next element calling the presenter
     * @param context of the activity
     * @param image
     */

    void setVideoCorrectAnswer(final ImageView image, Context context){
        // per ora c'e solo audio va aggiunto il video
        image.setVisibility(View.VISIBLE);
        MediaPlayer request = MediaPlayer.create(context, R.raw.video_correct_answer);
        request.start();

        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                image.setVisibility(View.INVISIBLE);
                presenter.chooseElement();
            }
        });
    }

    ArrayList<String> getPartialArray(String[] array){
        List<String> completeArray = new ArrayList<>(Arrays.asList(array));
        Collections.sort(completeArray);
        int startIndex = 0;
        int endIndex = completeArray.size() / 2;
        return new ArrayList<>(completeArray.subList(startIndex, endIndex));
    }

}
