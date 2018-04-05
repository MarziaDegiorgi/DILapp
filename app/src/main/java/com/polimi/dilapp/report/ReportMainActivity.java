package com.polimi.dilapp.report;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.levels.view.ActivityOneOne;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class ReportMainActivity extends AppCompatActivity implements IReport.View{
    private IReport.Presenter presenter;
    private AppDatabase db;
    private ArrayList<Float> progressList;
    private ArrayList<Date> dateList;
    private int numProgressInterest = 50;
    private  int currentPlayer;


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_main_report);
        db = AppDatabase.getAppDatabase(this);
        currentPlayer = DatabaseInitializer.getCurrentPlayer(db);
        CircleImageView circleImageView = findViewById(R.id.profile_image);
        BitmapDrawable drawable = null;
        try {
            drawable = new BitmapDrawable(getResources(), DatabaseInitializer.getChildPhoto(getContentResolver(), DatabaseInitializer.getChildById(db, currentPlayer)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        circleImageView.setImageDrawable(drawable);
        TextView name = findViewById(R.id.name);
        name.setText(DatabaseInitializer.getNameCurrentPlayer(db));
        TextView birth = findViewById(R.id.date_of_birth);
        birth.setText(DatabaseInitializer.getBirthCurrentPlayer(db));
        Button button = findViewById(R.id.spec_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportMainActivity.this, ReportSpecActivity.class);
                intent.putExtra("level", DatabaseInitializer.getLevelCurrentPlayer(db));
                startActivity(intent);
            }
        });

        presenter = new ReportMainPresenter(this);
        //progressList = DatabaseInitializer.getProgress(db, currentPlayer);

progressList = new ArrayList<>();
dateList = new ArrayList<>();
        progressList.add(0.3f);
        progressList.add(0.5f);
        progressList.add(0.8f);
        progressList.add(0.6f);

        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        cal.add(Calendar.DATE, -2);
        Date dayBeforeYesterday = cal.getTime();
        cal.add(Calendar.DATE, -3);
        Date twoDaysBeforeYesterday = cal.getTime();


        dateList.add(twoDaysBeforeYesterday);
        dateList.add(dayBeforeYesterday);
        dateList.add(yesterday);
        dateList.add(today);
        /*try {
            dateList = DatabaseInitializer.getProgressDate(db, currentPlayer);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        Log.i("[REPORT MAIN]","Progress list :"+ progressList);
        Log.i("[REPORT MAIN]","Date list :"+ dateList);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] data = new DataPoint[progressList.size()];
        LineGraphSeries<DataPoint> series;
        //TODO: check dimension on progressList
        float max = 1;
        for (int i = 0; i < progressList.size(); i++) {
            float progress = progressList.get(i);
            Date date = dateList.get(i);
            data[i] = new DataPoint(date, progress);
            Log.i("[REPORT MAIN]","Data :"+ data[i]);
            if(progress > max){
                max = progress + 0.5f;
            }
        }

        series = new LineGraphSeries<>(data);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String date= df.format(dataPoint.getX());
                double f = dataPoint.getY();
                DecimalFormat dec = new DecimalFormat("#.##");
                String value = dec.format(f);
                Toast.makeText(getApplicationContext(), "Data: "+date+"\n"+"Punteggio: "+ value, Toast.LENGTH_SHORT).show();
            }
        });
        series.setDrawDataPoints(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(max);
        graph.getViewport().setMinY(0.0f);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        Log.i("[REPORT MAIN]", "DateList size: " + String.valueOf(dateList.size()));
        Log.i("[REPORT MAIN]", "DateList: " + dateList);
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
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

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ReportMainActivity.this, StartGameActivity.class));
        finish();
    }


}
