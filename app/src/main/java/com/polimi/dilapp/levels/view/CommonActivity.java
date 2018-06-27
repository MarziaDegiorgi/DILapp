package com.polimi.dilapp.levels.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
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
import java.util.Collections;
import java.util.List;

/**
 *  This class is a View class that contains the common methods used by all the activity views
 */
class CommonActivity {
    private IGame.Presenter presenter;

    private Handler myHandler;
    private Animation animationIn;
    private Animation animationOut;

    CommonActivity(IGame.Presenter presenter){
        this.presenter = presenter;
        myHandler = new Handler();
    }

    ArrayList<String> getList(String[] array) {

        return new ArrayList<>(Arrays.asList(array));
    }


    void setAnimations(AppCompatActivity activity){
    animationIn = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.alpha_enter);
    animationOut = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.alpha_exit);
    }


    /**
     * Start the introductory video of each level
     * @param uri video to start
     * @param sequence of object in the actual level
     * @param activity to which it refer
     */
   void startIntro(Uri uri, final List<String> sequence, AppCompatActivity activity){
       final VideoView video = activity.findViewById(R.id.video_box);
       video.setVisibility(View.VISIBLE);
       video.setVideoURI(uri);
       video.setAnimation(animationIn);
       video.startAnimation(animationIn);
       video.start();
       video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           //When the introduction video finishes the first session begins
           @Override
           public void onCompletion(MediaPlayer mp) {
               video.setAnimation(animationOut);
               video.startAnimation(animationOut);
               video.clearAnimation();
               video.setVisibility(View.INVISIBLE);
               presenter.startGame(sequence);
               mp.release();
           }
       });
    }

    void startMainVideo(Uri uri, AppCompatActivity activity){
        final VideoView video = activity.findViewById(R.id.video_box);
        video.setVisibility(View.VISIBLE);
        video.setVideoURI(uri);
        video.setAnimation(animationIn);
        video.startAnimation(animationIn);
        video.start();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video.setAnimation(animationOut);
                video.startAnimation(animationOut);
                video.clearAnimation();
                video.setVisibility(View.INVISIBLE);
                mp.release();

                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.chooseElement();
                    }
                },200);
            }
        });
    }

    /**
     * Start the main video of each session contained within a level
     * @param activity to refer
     * @param animation to use
     * @param resourceID of the image
     * @param context of the activity
     */
    void startMainAnimation(AppCompatActivity activity, Animation animation, int resourceID, Context context){

        ImageView image = activity.findViewById(R.id.animation_box);
        image.setImageDrawable(ContextCompat.getDrawable(context, resourceID));
        image.setVisibility(View.VISIBLE);
        image.setAnimation(animation);
        image.startAnimation(animation);
    }

    /**
     *  Set response in given context in case of not correct answer in order then to repeat the request
     * @param context of the activity
     */
    void setWrongAnswerToRepeat(Context context){

        MediaPlayer request = MediaPlayer.create(context, R.raw.request_wrong_answer_repeat);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                presenter.askCurrentElement();
                mp.release();
            }
        });
    }
    /**
     *  Set response in given context in case of not correct answer in order then to go on
     * @param context of the activity
     */
    void setWrongAnswerAndGoOn(Context context){
        MediaPlayer request = MediaPlayer.create(context, R.raw.request_wrong_answer_go_on);
        request.start();

        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //TODO: ADD CLEAR LION ANIMATION
                presenter.chooseElement();
                mp.release();
            }
        });
    }

    /**
     *  Disable the ImageView received as parameter
     *  and reset the image view
     * @param imageView to clear by all animation
     */
    void disableView(ImageView imageView){
        imageView.clearAnimation();
        imageView.setVisibility(View.INVISIBLE);
        imageView.setImageDrawable(null);
    }

    /**
     *  Set the correct answer response and then pass to the next element calling the presenter
     * @param context of the activity
     * @param image to set as response
     */
    void setCorrectAnswer(final ImageView image, Context context){
        image.setVisibility(View.VISIBLE);
        //audio response
        MediaPlayer request = MediaPlayer.create(context, R.raw.request_correct_answer);
        request.start();

        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                image.clearAnimation();
                image.setImageDrawable(null);
                disableView(image);
                image.setVisibility(View.INVISIBLE);
               //delay the choose of the next element of 1 sec
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.chooseElement();
                    }
                },1000);
            }
        });
    }

    /**
     *  Return the partial ArrayList<String> of the array received as parameter.
     *   @param array of the Activity     *
     */
    ArrayList<String> getPartialArray(String[] array){
        List<String> completeArray = new ArrayList<>(Arrays.asList(array));
        Collections.sort(completeArray);
        int startIndex = 0;
        int endIndex = completeArray.size() / 4;
        return new ArrayList<>(completeArray.subList(startIndex, endIndex));
    }

    /**
     * Visualize The Lion in the background of the game
     * @param activity of referrence
     */
    void enableLionBackground(AppCompatActivity activity){
        ImageView lionHeadImage = activity.findViewById(R.id.lion_head_game);
        ImageView lionTailImage = activity.findViewById(R.id.tale_game);
        ImageView lionBodyImage = activity.findViewById(R.id.lion_body_game);

        lionBodyImage.setVisibility(View.VISIBLE);
        lionTailImage.setVisibility(View.VISIBLE);
        lionHeadImage.setVisibility(View.VISIBLE);
    }

    /**
     * Enable the head animation of the lion in the background
     * @param context of the activity
     * @param activity of referrence
     */
    void enableLionHeadAnimation(Context context, AppCompatActivity activity){
        ImageView lionHeadImage = activity.findViewById(R.id.lion_head_game);

        lionHeadImage.setVisibility(View.VISIBLE);
        Animation animationLionHead = AnimationUtils.loadAnimation(context, R.anim.lion_rotation_waiting);
        lionHeadImage.setAnimation(animationLionHead);
        lionHeadImage.startAnimation(animationLionHead);
    }

    /**
     * Enable the tail animation of the lion in the background
     * @param context of the activity
     * @param activity of referrence
     */
    void enableLionTailAnimation(AppCompatActivity activity, Context context){
        ImageView lionTailImage = activity.findViewById(R.id.tale_game);

        Animation animationLionWait = AnimationUtils.loadAnimation(context, R.anim.tale_rotation);
        lionTailImage.setAnimation(animationLionWait);
        lionTailImage.setVisibility(View.VISIBLE);
        lionTailImage.startAnimation(animationLionWait);
    }

    /**
     * Clear animation of the head
     * @param activity of referrence
     */
    void disableLionHeadAnimation(AppCompatActivity activity){
        ImageView lionHeadImage = activity.findViewById(R.id.lion_head_game);
        lionHeadImage.setVisibility(View.VISIBLE);
        lionHeadImage.clearAnimation();
    }

    /**
     * Clear the animation of the tail
     * @param activity of referrence
     */
   void disableLionTailAnimation(AppCompatActivity activity){
       ImageView lionTailImage = activity.findViewById(R.id.tale_game);
       lionTailImage.setVisibility(View.VISIBLE);
       lionTailImage.clearAnimation();
    }

    /**
     * Enable Kites animation in the background of the screen
     * @param activity of referrence
     * @param context of the activity
     */
    void enableKiteAnimationBackground(AppCompatActivity activity, Context context){
        ImageView animationViewExtra = activity.findViewById(R.id.animation_box_two);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimation = AnimationUtils.loadAnimation(context , R.anim.move);
        animationViewExtra.setImageDrawable(ContextCompat.getDrawable(context ,R.drawable.kite));
        animationViewExtra.setAnimation(extraAnimation);
        animationViewExtra.startAnimation(extraAnimation);

        ImageView animationViewExtraTwo = activity.findViewById(R.id.animation_box_three);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimationTwo = AnimationUtils.loadAnimation(context, R.anim.move);
        animationViewExtraTwo.setImageDrawable(ContextCompat.getDrawable(context ,R.drawable.kite));
        animationViewExtraTwo.setAnimation(extraAnimationTwo);
        animationViewExtraTwo.startAnimation(extraAnimationTwo);
    }

    void disableKiteExtraView(AppCompatActivity activity){
        ImageView animationViewExtra = activity.findViewById(R.id.animation_box_two);
        ImageView animationViewExtraTwo = activity.findViewById(R.id.animation_box_three);
        animationViewExtra.clearAnimation();
        animationViewExtraTwo.clearAnimation();
        animationViewExtra.setImageDrawable(null);
        animationViewExtraTwo.setImageDrawable(null);
    }

    void disableLionExtraView(AppCompatActivity activity){
        disableLionHeadAnimation(activity);
        disableLionTailAnimation(activity);
    }

    void onDestroy() {
        myHandler = null;
        presenter = null;
    }

}
