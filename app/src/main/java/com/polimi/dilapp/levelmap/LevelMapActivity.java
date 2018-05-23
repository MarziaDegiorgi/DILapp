package com.polimi.dilapp.levelmap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.levels.view.ActivityOneOne;
import com.polimi.dilapp.levels.view.ActivityOneThree;
import com.polimi.dilapp.levels.view.ActivityOneTwo;
import com.polimi.dilapp.levels.view.ActivityThreeOne;
import com.polimi.dilapp.levels.view.ActivityThreeTwo;
import com.polimi.dilapp.levels.view.ActivityTwoOne;
import com.polimi.dilapp.levels.view.ActivityTwoThree;
import com.polimi.dilapp.levels.view.ActivityTwoTwo;
import com.polimi.dilapp.main.NewAccountActivity;
import com.polimi.dilapp.report.ReportMainActivity;
import com.polimi.dilapp.report.ReportSpecActivity;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.util.HashMap;
import java.util.List;


public class LevelMapActivity extends AppCompatActivity implements ILevelMap.View {

   ILevelMap.Presenter presenter;
   ExpandableListView listView;
   private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_levelmap_view);
        listView = findViewById(R.id.expandableListView);
        db = AppDatabase.getAppDatabase(this);

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
        int currentLevel = DatabaseInitializer.getLevelCurrentPlayer(db);
        String itemSelected = listItems.get(listTitles.get(mainCategoryPosition)).get(subcategoryPosition);
        Intent intent;

        //TODO:uncomment level blocking and delete next switch
            /*switch (itemSelected) {
                case "NOMI":
                    if(currentLevel >= 11){
                        intent = new Intent(this, ActivityOneOne.class);
                        startActivity(intent);
                        finish();
                    }else{
                        showPopUp(R.string.blocked_level);
                    }
                    break;
                case "COLORI":
                    if(currentLevel >= 12){
                        intent = new Intent(this, ActivityOneTwo.class);
                        startActivity(intent);
                        finish();
                    }else{
                        showPopUp(R.string.blocked_level);
                    }
                    break;
                case "FORME":
                    if(currentLevel >= 13){
                        intent = new Intent(this, ActivityOneThree.class);
                        startActivity(intent);
                        finish();
                    }else{
                        showPopUp(R.string.blocked_level);
                    }
                    break;
                case "NUMERI":
                    if(currentLevel >= 21){
                        intent = new Intent(this, ActivityTwoOne.class);
                        startActivity(intent);
                        finish();
                    }else{
                        showPopUp(R.string.blocked_level);
                    }
                    break;
                case "ALFABETO":
                    if(currentLevel >= 22){
                        intent = new Intent(this, ActivityTwoTwo.class);
                        startActivity(intent);
                        finish();
                    }else{
                        showPopUp(R.string.blocked_level);
                    }
                    break;
                case "PAROLE":
                    if(currentLevel >= 23){
                        intent = new Intent(this, ActivityTwoThree.class);
                        startActivity(intent);
                        finish();
                    }else{
                        showPopUp(R.string.blocked_level);
                    }
                    break;
                case "CONTIAMO INSIEME":
                    if(currentLevel >= 31){
                        intent = new Intent(this, ActivityThreeOne.class);
                        startActivity(intent);
                        finish();
                    }else{
                        showPopUp(R.string.blocked_level);
                    }
                    break;
                case "CUCINA CON NOSCO":
                    if(currentLevel >= 32){
                        intent = new Intent(this, ActivityThreeTwo.class);
                        startActivity(intent);
                        finish();
                    }else{
                        showPopUp(R.string.blocked_level);
                    }
                    break;
                default:
                    break;
            }*/

        switch (itemSelected) {
            case "NOMI":
                    intent = new Intent(this, ActivityOneOne.class);
                    startActivity(intent);
                    finish();
                break;
            case "COLORI":
                    intent = new Intent(this, ActivityOneTwo.class);
                    startActivity(intent);
                    finish();
                break;
            case "FORME":
                    intent = new Intent(this, ActivityOneThree.class);
                    startActivity(intent);
                    finish();
                break;
            case "NUMERI":
                    intent = new Intent(this, ActivityTwoOne.class);
                    startActivity(intent);
                    finish();
                break;
            case "ALFABETO":
                    intent = new Intent(this, ActivityTwoTwo.class);
                    startActivity(intent);
                    finish();
                break;
            case "PAROLE":
                    intent = new Intent(this, ActivityTwoThree.class);
                    startActivity(intent);
                    finish();
                    break;
            case "CONTIAMO INSIEME":
                    intent = new Intent(this, ActivityThreeOne.class);
                    startActivity(intent);
                    finish();
                    break;
            case "CUCINA CON NOSCO":
                    intent = new Intent(this, ActivityThreeTwo.class);
                    startActivity(intent);
                    finish();
                break;
            default:
                break;
        }
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

    private void showPopUp(int stringId){
        final Dialog dialog = new Dialog(LevelMapActivity.this);
        dialog.setContentView(R.layout.pop_up);
        TextView tv = (TextView)dialog.findViewById(R.id.textView);
        tv.setText(stringId);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button close = (Button)dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
