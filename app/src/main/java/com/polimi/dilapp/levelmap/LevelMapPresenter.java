package com.polimi.dilapp.levelmap;


import android.app.Activity;
import android.content.Intent;
import android.widget.ExpandableListView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelMapPresenter implements  ILevelMap.Presenter{
    private ILevelMap.View levelMapView;
    private List<String> listTitles;
    private HashMap<String, List<String>> listItems;

    LevelMapPresenter(ILevelMap.View view){

        this.levelMapView= view;
    }

    @Override
    public void initData() {
        listItems = (HashMap<String, List<String>>) ExpandableListData.getData();
        listTitles = new ArrayList<>();
        listTitles.add("OGGETTI E COLORI");
        listTitles.add("LETTERE E NUMERI");
        listTitles.add("LOGICA");

        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(levelMapView.getContext(), listTitles, listItems);
        levelMapView.showAdapter(adapter);
    }

    public HashMap<String, List<String>> getListItems(){
        return listItems;
    }

    public List<String> getListTitles(){
        return listTitles;
    }
}
