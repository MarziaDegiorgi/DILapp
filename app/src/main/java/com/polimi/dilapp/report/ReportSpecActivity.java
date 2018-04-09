package com.polimi.dilapp.report;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReportSpecActivity extends AppCompatActivity implements IReportSpec.View{
        private IReportSpec.Presenter presenter;
        private AppDatabase db;
        private int numProgressInterest = 50;
        private  int currentPlayer;
        private DataPoint[] errorList;



    @Override
        public void onCreate(Bundle onSavedInstanceState) {
            super.onCreate(onSavedInstanceState);
            setContentView(R.layout.activity_spec_report);
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

            presenter = new ReportSpecPresenter(this);

            Intent intent = getIntent();
            int level = intent.getIntExtra("level", 0);
            switch (level){
                case 11:
                    errorList = DatabaseInitializer.getAllErrorsOneOne(db);
            }



        GraphView graph = (GraphView) findViewById(R.id.graph);
            if(errorList == null){
                errorList = new DataPoint[3];
                errorList[0] = new DataPoint(1, 3);
                errorList[1] = new DataPoint(2, 8);
                errorList[2] = new DataPoint(3, 5);
            }
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(errorList);
            graph.addSeries(series);

// styling
            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {
                    return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                }
            });

            series.setSpacing(50);

// draw values on top
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);
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

            startActivity(new Intent(ReportSpecActivity.this, ReportMainActivity.class));

            CircleImageView circleImageView = findViewById(R.id.profile_image);
            GraphView graph = findViewById(R.id.graph);
            circleImageView.setImageDrawable(null);
            graph.removeAllSeries();

            errorList = null;
            db= null;
            presenter = null;

            finish();
        }

    }
