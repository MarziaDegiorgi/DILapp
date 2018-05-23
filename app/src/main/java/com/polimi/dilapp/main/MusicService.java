package com.polimi.dilapp.main;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.polimi.dilapp.R;

import java.util.ArrayList;

/**
 * Music Service enable to run a background music in all the application and the activities to interact with it, allowing to stop
 * pause and play the music
 */
public class MusicService extends Service implements MediaPlayer.OnErrorListener {

    MediaPlayer mPlayer;
    private int length = 0;

    //final String ACTION_CMD;
    //final String CMD_NAME;
    //final String ARG_INDEX;


    @Override
    public void onCreate (){
        super.onCreate();

        //TODO: INSERT MUSIC IN uri
        mPlayer = MediaPlayer.create(this, null);
        mPlayer.setOnErrorListener(this);

        if(mPlayer!= null)
        {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100,100);
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
    public int onStartCommand (Intent intent, int flags, int startId)
    {
       /* if (intent != null) {
            String action = intent.getAction();
            String command = intent.getStringExtra(CMD_NAME);
            if (ACTION_CMD.equals(action)) {
                if (CMD_PAUSE.equals(command)) {
                    if (mplayer != null && mplayer.isPlaying()) {
                        pauseMusic();
                    }
                } else if (CMD_PLAY.equals(command)) {
                    mPlayer.start();
                    }
                    int index = startIntent.getIntExtra(ARG_INDEX, 0);

                }
            }
        }

        return START_STICKY;
    }*/
       return START_STICKY;
    }

    public void pauseMusic()
    {
        if(mPlayer.isPlaying())
        {
            mPlayer.pause();
            length=mPlayer.getCurrentPosition();

        }
    }

    public void resumeMusic()
    {
        if(!mPlayer.isPlaying())
        {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    public void stopMusic()
    {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
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
            }finally {
                mPlayer = null;
            }
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
}
