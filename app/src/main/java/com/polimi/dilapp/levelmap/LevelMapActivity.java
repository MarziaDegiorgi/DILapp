package com.polimi.dilapp.levelmap;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.polimi.dilapp.R;


public class LevelMapActivity extends AppCompatActivity implements ILevelMap.View {

   ILevelMap.Presenter presenter;
   ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_levelmap_view);
        listView = (ExpandableListView) findViewById(R.id.expandableListView);

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
}
