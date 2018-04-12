package com.polimi.dilapp.levelmap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.levels.view.ActivityOneOne;
import com.polimi.dilapp.levels.view.ActivityOneThree;
import com.polimi.dilapp.levels.view.ActivityOneTwo;
import com.polimi.dilapp.levels.view.ActivityThreeOne;
import com.polimi.dilapp.levels.view.ActivityThreeTwo;
import com.polimi.dilapp.levels.view.ActivityTwoOne;
import com.polimi.dilapp.levels.view.ActivityTwoThree;
import com.polimi.dilapp.levels.view.ActivityTwoTwo;
import com.polimi.dilapp.report.ReportMainActivity;
import com.polimi.dilapp.report.ReportSpecActivity;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.util.HashMap;
import java.util.List;


public class LevelMapActivity extends AppCompatActivity implements ILevelMap.View {

   ILevelMap.Presenter presenter;
   ExpandableListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_levelmap_view);
        listView = findViewById(R.id.expandableListView);

        // Set up the presenter
        presenter = new LevelMapPresenter(this);
        presenter.initData();

        startAnimation();
    }

    private void startAnimation() {

        ImageView kiteAnimation = findViewById(R.id.kite_animation);
        kiteAnimation.setVisibility(View.VISIBLE);
        Animation extraAnimation = AnimationUtils.loadAnimation(LevelMapActivity.this, R.anim.move);
        kiteAnimation.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        kiteAnimation.setAnimation(extraAnimation);
        kiteAnimation.startAnimation(extraAnimation);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showAdapter(CustomExpandableListAdapter adapter) {
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int mainCategoryPosition,
                                        int subcategoryPosition, long id) {
                onItemSelected(parent, mainCategoryPosition,subcategoryPosition);
                return false;
            }
        });
    }

    @Override
    public String getString() {
        return "LevelMapActivity";
    }

    public void goBack(View view) {
        AlphaAnimation buttonClick = new AlphaAnimation(1F,0.8F);
        view.startAnimation(buttonClick);
        onClickBack();
        presenter = null;
        finish();
    }

    @Override
    public void onItemSelected(ExpandableListView parent, int mainCategoryPosition, int subcategoryPosition) {
        HashMap<String, List<String>> listItems = presenter.getListItems();
        List<String> listTitles = presenter.getListTitles();
        String itemSelected = listItems.get(listTitles.get(mainCategoryPosition)).get(subcategoryPosition);
        Intent intent = new Intent(this, ReportSpecActivity.class);
            switch (itemSelected) {
                case "NOMI":
                    intent = new Intent(this, ActivityOneOne.class);
                    break;
                case "COLORI":
                    intent = new Intent(this, ActivityOneTwo.class);
                    break;
                case "FORME":
                    intent = new Intent(this, ActivityOneThree.class);
                    break;
                case "NUMERI":
                    intent = new Intent(this, ActivityTwoOne.class);
                    break;
                case "ALFABETO":
                    intent = new Intent(this, ActivityTwoTwo.class);
                    break;
                case "PAROLE":
                    intent = new Intent(this, ActivityTwoThree.class);
                    break;
                case "CONTIAMO INSIEME":
                    intent = new Intent(this, ActivityThreeOne.class);
                    break;
                case "CUCINA CON NOSCO":
                    intent = new Intent(this, ActivityThreeTwo.class);
                    break;
                default:
                    break;
            }
            startActivity(intent);
            finish();

    }

    @Override
    public void onClickBack() {
        Intent intent;
        intent = new Intent(this, StartGameActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent;
        intent = new Intent(getApplicationContext(), ReportMainActivity.class);
        startActivity(intent);
        finish();
    }
}
