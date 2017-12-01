package com.polimi.dilapp.levelmap;


import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.polimi.dilapp.startgame.StartGameActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelMapPresenter implements  ILevelMap.Presenter{
    ILevelMap.View levelMapView;
    ExpandableListData levelList;
    List<String> listTitles;
    HashMap<String, List<String>> listItems;
    CustomExpandableListAdapter adapter;

    public LevelMapPresenter( ILevelMap.View view){

        this.levelMapView= view;
    }

    @Override
    public void initData() {
        listItems = ExpandableListData.getData();
        listTitles = new ArrayList<String>(listItems.keySet());
        adapter = new CustomExpandableListAdapter(levelMapView.getContext(), listTitles,listItems);
        levelMapView.showAdapter(adapter);
    }

    @Override
    public void onItemSelected(ExpandableListView parent, int mainCategoryPosition, int subcategoryPosition) {
        //TODO: Corresponding to the item selected redirect to the associated activity
        Toast.makeText(
                levelMapView.getContext().getApplicationContext(),
                listTitles.get(mainCategoryPosition)
                    + " -> "
                    + listItems.get(
                            listTitles.get(mainCategoryPosition)).get(subcategoryPosition),
                Toast.LENGTH_SHORT
                ).show();
    }

    @Override
    public void onClickBack() {
        Intent intent = new Intent(levelMapView.getContext(), StartGameActivity.class);
        levelMapView.getContext().startActivity(intent);
    }
}
