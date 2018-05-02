package com.polimi.dilapp.levelmap;

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
