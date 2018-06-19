package com.polimi.dilapp.main;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.polimi.dilapp.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Music Service enable to run a background music in all the application and the activities to interact with it, allowing to stop
 * pause and play the music
 */
public class MusicService extends Service implements MediaPlayer.OnErrorListener {

    MediaPlayer mPlayer;
    private int length = 0;
    private final IBinder mBinder = new ServiceBinder();
    protected static final String TAG ="[MusicService]";

    @Override
    public void onCreate (){
        super.onCreate();
        mPlayer = MediaPlayer.create(this, R.raw.background_music);
        mPlayer.setOnErrorListener(this);

        if(mPlayer!= null)
        {
            mPlayer.setLooping(true);
            mPlayer.setVolume(40,40);
        }


        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int
                    extra){

                onError(mPlayer, what, extra);
                return true;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
            mPlayer.start();
            Log.i(TAG, "[STARTED]");
       return START_STICKY;
    }

    public void pauseMusic()
    {
        if(mPlayer.isPlaying())
        {
            mPlayer.pause();
            length=mPlayer.getCurrentPosition();
            Log.i(TAG, "[PAUSED]");
        }
    }

    public void resumeMusic()
    {
        if(!mPlayer.isPlaying())
        {
            mPlayer.seekTo(length);
            mPlayer.start();
            Log.i(TAG, "[RESUMED]");
        }
    }

    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }

    public void stopMusic()
    {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        Log.i(TAG, "[STOPPED]");
    }


    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        if(mPlayer != null)
        {
            try{
                mPlayer.stop();
                mPlayer.release();
                Log.i(TAG, "[DESTROYED]");
            }finally {
                mPlayer = null;
            }
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if(mPlayer != null)
        {
            try{
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        }
        return false;
    }

   public class ServiceBinder extends Binder {
       public MusicService getService() {
            return MusicService.this;
        }
    }
}
