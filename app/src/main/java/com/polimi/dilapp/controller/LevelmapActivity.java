package com.polimi.dilapp.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.polimi.dilapp.R;
import com.polimi.dilapp.model.BounceInterpolator;
import com.polimi.dilapp.model.CustomExpandableListAdapter;
import com.polimi.dilapp.model.ExpandableListData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelmapActivity extends AppCompatActivity {

    //two possible view
    private View levelMapView, changeLevelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        levelMapView = getLayoutInflater().inflate(R.layout.activity_levelmap, null);
        changeLevelView = getLayoutInflater().inflate(R.layout.change_level_list_view, null);

        //the default view of the activity is the level map
        setContentView(levelMapView);

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

    /**
     *  Change content view if "Change level" is selected
     */

    public void showLevelList(MenuItem item) {
        ExpandableListView expandableListView;
        ExpandableListAdapter expandableListAdapter;
        final List<String> expandableListTitle;
        final HashMap<String, List<String>> expandableListDetail;

        setContentView(changeLevelView);
        expandableListView = (ExpandableListView) findViewById(R.id.ExpandableListView);
        expandableListDetail = ExpandableListData.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        // Add listener to expande each category
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
        //TODO: Add listener to subcategory
    }

    //TODO:Redirect to the right activity when Play Button is clicked

}
