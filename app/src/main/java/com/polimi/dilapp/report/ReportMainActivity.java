package com.polimi.dilapp.report;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;

import java.util.ArrayList;


public class ReportMainActivity extends AppCompatActivity implements IReport.View{
    private IReport.Presenter presenter;
    private AppDatabase db;
    private ArrayList<Float> progressList;
    private int numProgressInterest = 50;


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_main_report);
        presenter = new ReportMainPresenter(this);
        Log.i("[REPORT MAIN]","progress list :"+ progressList);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] data = new DataPoint[progressList.size()];
        LineGraphSeries<DataPoint> series;
        //TODO: check dimension on progressList
        for (int i = 0; i < progressList.size(); i++) {
            data[i] = new DataPoint(i+1, progressList.get(i));
        }
        series = new LineGraphSeries<>(data);
        graph.addSeries(series);
    }

    public void showPopup(View view){
        PopupMenu popup = new PopupMenu (this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions_report, popup.getMenu());
        popup.show();
    }

    public void onClickMenuItem (MenuItem item) {
        presenter.onItemMenuSelected(item);
    }


}
