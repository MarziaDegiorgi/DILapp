package com.polimi.dilapp.levelmap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.polimi.dilapp.R;
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

import java.util.HashMap;
import java.util.List;


public class ReportLevelMapActivity extends AppCompatActivity implements ILevelMap.View {

    ILevelMap.Presenter presenter;
    ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_levelmap_view);
        RelativeLayout layout = findViewById(R.id.layout);
        //layout.setBackgroundResource(R.color.colorAccentTransparent);
        listView = findViewById(R.id.expandableListView);

        // Set up the presenter
        presenter = new LevelMapPresenter(this);
        presenter.initData();
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

    public String getString() {
        return "ReportLevelMapActivity";
    }

    public void goBack(View view) {
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
                intent.putExtra("level", 11);
                break;
            case "COLORI":
                intent.putExtra("level", 12);
                break;
            case "FORME":
                intent.putExtra("level", 13);
                break;
            case "NUMERI":
                intent.putExtra("level", 21);
                break;
            case "ALFABETO":
                intent.putExtra("level", 22);
                break;
            case "PAROLE":
                intent.putExtra("level", 23);
                break;
            case "CONTIAMO INSIEME":
                intent.putExtra("level", 31);
                break;
            case "CUCINA CON NOSCO":
                intent.putExtra("level", 32);
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
        Log.i("[REPORT MAIN]", "I'm in onClickBack");
        intent = new Intent(getApplicationContext(), ReportMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent;
        Log.i("[REPORT MAIN]", "I'm in onClickBack");
        intent = new Intent(getApplicationContext(), ReportMainActivity.class);
        startActivity(intent);
        finish();
    }
}


