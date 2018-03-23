package com.polimi.dilapp.levelmap;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.levels.view.ActivityOneFour;
import com.polimi.dilapp.levels.view.ActivityOneOne;
import com.polimi.dilapp.levels.view.ActivityOneThree;
import com.polimi.dilapp.levels.view.ActivityOneTwo;
import com.polimi.dilapp.levels.view.ActivityTwoOne;
import com.polimi.dilapp.levels.view.ActivityTwoThree;
import com.polimi.dilapp.levels.view.ActivityTwoTwo;
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
        listTitles = new ArrayList<>(listItems.keySet());
        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(levelMapView.getContext(), listTitles, listItems);
        levelMapView.showAdapter(adapter);
    }

    @Override
    public void onItemSelected(ExpandableListView parent, int mainCategoryPosition, int subcategoryPosition) {
        String itemSelected = listItems.get(listTitles.get(mainCategoryPosition)).get(subcategoryPosition);
        Intent intent;
        switch (itemSelected){
            case "NOMI":
                intent = new Intent(levelMapView.getContext(), ActivityOneOne.class);
                levelMapView.getContext().startActivity(intent);
                break;
            case "COLORI":
                intent = new Intent(levelMapView.getContext(), ActivityOneTwo.class);
                levelMapView.getContext().startActivity(intent);
                break;
            case "FORME":
                intent = new Intent(levelMapView.getContext(), ActivityOneThree.class);
                levelMapView.getContext().startActivity(intent);
                break;
            case "COMPONI":
                intent = new Intent(levelMapView.getContext(), ActivityOneFour.class);
                levelMapView.getContext().startActivity(intent);
                break;
            case "NUMERI":
                intent = new Intent(levelMapView.getContext(), ActivityTwoOne.class);
                levelMapView.getContext().startActivity(intent);
                break;
            case "ALFABETO":
                intent = new Intent(levelMapView.getContext(), ActivityTwoTwo.class);
                levelMapView.getContext().startActivity(intent);
                break;
            case "PAROLE":
                intent = new Intent(levelMapView.getContext(), ActivityTwoThree.class);
                levelMapView.getContext().startActivity(intent);
                break;
            case "IMPARARE A CONTARE": break;
            case "LISTA DELLA SPESA": break;
            case "IN CUCINA CON PATTY": break;
            default:break;
        }
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
