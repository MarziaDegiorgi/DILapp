package com.polimi.dilapp.report;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.levelmap.ReportLevelMapActivity;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        button.setVisibility(View.GONE);

            presenter = new ReportSpecPresenter(this);

            Intent intent = getIntent();
            int level = intent.getIntExtra("level", 0);
            int max = 0;
            int size = 0;
            List<String> objects = new ArrayList<>();
            switch (level){
                case 11:
                    size = 22;
                    String[] listOfObjects = getResources().getStringArray(R.array.fruits_it);
                    objects = Arrays.asList(listOfObjects);
                    errorList = DatabaseInitializer.getAllErrorsOneOne(db);
                    for(DataPoint s : errorList ){
                            if (s.getY() > max) {
                                Double y = s.getY();
                                max = y.intValue() + 3;
                            }
                        }
                    }
        GraphView graph = (GraphView) findViewById(R.id.graph);
            if(errorList!= null) {
                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(errorList);
                graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
                graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

                graph.addSeries(series);
                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMaxY(max);
                graph.getViewport().setMinY(0.0f);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMaxX(size);
                graph.getViewport().setMinX(0.0);
                final List<String> finalObjects = objects;
                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {

                        Double x = dataPoint.getX();
                        Double y = dataPoint.getY();
                        showDataPoint(finalObjects.get(x.intValue() - 1), String.valueOf(y.intValue()));
                    }
                });
                series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                    }
                });

                series.setSpacing(30);
            }
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

    private void showDataPoint(String object, String numberOfErrors){
        final Dialog dialog = new Dialog(ReportSpecActivity.this);
        dialog.setContentView(R.layout.pop_up);
        TextView tv = dialog.findViewById(R.id.textView);
        StringBuilder sb = new StringBuilder();
        sb.append(DatabaseInitializer.getChildById(db, currentPlayer).getName());
        sb.append(" non ha riconosciuto per ");
        sb.append(numberOfErrors);
        sb.append(" volte l'oggetto ");
        sb.append(object);
        tv.setText(sb.toString());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    }
