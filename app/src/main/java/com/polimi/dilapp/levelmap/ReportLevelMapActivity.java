package com.polimi.dilapp.levelmap;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.polimi.dilapp.R;


public class ReportLevelMapActivity extends AppCompatActivity implements ILevelMap.View {

    ILevelMap.Presenter presenter;
    ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_levelmap_view);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setBackgroundColor(getResources().getColor(R.color.light_orange));
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
                presenter.onItemSelected(parent, mainCategoryPosition,subcategoryPosition);
                return false;
            }
        });
    }

    public String getString() {
        return "ReportLevelMapActivity";
    }

    public void goBack(View view) {
        AlphaAnimation buttonClick = new AlphaAnimation(1F,0.8F);
        view.startAnimation(buttonClick);
        presenter.onClickBack();
        presenter = null;
        finish();
    }
}
