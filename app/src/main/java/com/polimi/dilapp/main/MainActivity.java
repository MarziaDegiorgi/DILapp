package com.polimi.dilapp.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.polimi.dilapp.BuildConfig;
import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;

public class MainActivity extends AppCompatActivity implements IMain.View{

    IMain.Presenter presenter;

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private SlideAdapter slideAdapter;
    private Button previous;
    private Button next;
    int currentPage;

    @VisibleForTesting
    public Boolean slideshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);

        checkFirstRun();

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.i("[OnPageChange]", "called addDots");
            presenter.addDotsIndicator(MainActivity.this, dotsLayout, position);
            currentPage = position;

            if(position==0){
                next.setEnabled(false);
                next.setVisibility(View.INVISIBLE);
                next.setText("");

            }else if(position == presenter.getDotsNumber()-1){
                next.setEnabled(true);
                next.setVisibility(View.VISIBLE);

                next.setText("Fine");

            } else {
                next.setEnabled(true);
                next.setVisibility(View.INVISIBLE);
                next.setText("");

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
        presenter.storeCurrentPlayer(savedInstanceState);
    }

    // This callback is called only when there is a saved instance previously saved using
    // onSaveInstanceState(). We restore some state in onCreate() while we can optionally restore
    // other state here, possibly usable after onStart() has completed.
    // The savedInstanceState Bundle is same as the _1 used in onCreate().
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.resumeCurrentPlayer(savedInstanceState);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setColorCurrentDot(TextView[] textView, int position, boolean current) {
        if(current){
            textView[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null){
            presenter = new MainPresenter(this);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter == null){
            presenter = new MainPresenter(this);}
        presenter.resetCurrentPlayer();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            slideshow = false;
            Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class );
            startActivity(intent);

        } else if (savedVersionCode == DOESNT_EXIST) {
            slideshow = true;
            next = findViewById(R.id.next);
            viewPager = findViewById(R.id.slide_view_pager);
            dotsLayout = findViewById(R.id.dots);
            slideAdapter = new SlideAdapter(this);

            viewPager.setAdapter(slideAdapter);

            presenter.addDotsIndicator(MainActivity.this, dotsLayout, 0);
            viewPager.addOnPageChangeListener(viewListener);

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(currentPage + 1);
                    Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class );
                    startActivity(intent);
                }
            });

        } else if (currentVersionCode > savedVersionCode) {
            Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class );
            startActivity(intent);
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }
}
