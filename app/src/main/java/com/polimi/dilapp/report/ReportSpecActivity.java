package com.polimi.dilapp.report;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReportSpecActivity extends AppCompatActivity implements IReport.View{
        private IReport.Presenter presenter;
        private AppDatabase db;
        private  int currentPlayer;
        private DataPoint[] errorList;
        private String currentDate;
        private ImageView shareButton;


    @Override
        public void onCreate(Bundle onSavedInstanceState) {
            super.onCreate(onSavedInstanceState);
        presenter = new ReportSpecPresenter(this);
        setContentView(R.layout.activity_main_report);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            shareButton = findViewById(R.id.share_button);


        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = sdf.format(currentTime);


        db = AppDatabase.getAppDatabase(this);
            currentPlayer = DatabaseInitializer.getCurrentPlayer(db);
            TextView title = findViewById(R.id.title_graph);
            title.setText(R.string.error_title);
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

            Intent intent = getIntent();
            int level = intent.getIntExtra("level", 0);
            int max = 0;
            int size = 0;
            String type;
            List<String> objects = new ArrayList<>();
            String[] listOfObjects;
            switch (level){
                case 11:
                    size = 22;
                    type = "l'oggetto";
                    listOfObjects = getResources().getStringArray(R.array.fruits_it);
                    objects = Arrays.asList(listOfObjects);
                    errorList = DatabaseInitializer.getAllErrorsOneOne(db);
                    for(DataPoint s : errorList ){
                            if (s.getY() > max) {
                                Double y = s.getY();
                                max = y.intValue() + 3;
                            }
                        }
                        break;
                case 12:
                    size = 8;
                    type = "il colore";
                    listOfObjects = getResources().getStringArray(R.array.colors_it);
                    objects = Arrays.asList(listOfObjects);
                    errorList = DatabaseInitializer.getAllErrorsOneTwo(db);
                    for(DataPoint s : errorList ){
                        if (s.getY() > max) {
                            Double y = s.getY();
                            max = y.intValue() + 3;
                        }
                    }
                    break;
                case 13:
                    size = 22;
                    type = "l'oggetto";
                    listOfObjects = getResources().getStringArray(R.array.fruits_it);
                    objects = Arrays.asList(listOfObjects);
                    errorList = DatabaseInitializer.getAllErrorsOneThree(db);
                    for(DataPoint s : errorList ){
                        if (s.getY() > max) {
                            Double y = s.getY();
                            max = y.intValue() + 3;
                        }
                    }
                    break;
                case 21:
                    size = 11;
                    type = "il numero";
                    listOfObjects = getResources().getStringArray(R.array.all_numbers_items);
                    objects = Arrays.asList(listOfObjects);
                    errorList = DatabaseInitializer.getAllErrorsTwoOne(db);
                    for(DataPoint s : errorList ){
                        if (s.getY() > max) {
                            Double y = s.getY();
                            max = y.intValue() + 3;
                        }
                    }
                    break;
                case 22:
                    size = 21;
                    type = "la lettera";
                    listOfObjects = getResources().getStringArray(R.array.all_letters_items);
                    objects = Arrays.asList(listOfObjects);
                    errorList = DatabaseInitializer.getAllErrorsTwoTwo(db);
                    for(DataPoint s : errorList ){
                        if (s.getY() > max) {
                            Double y = s.getY();
                            max = y.intValue() + 3;
                        }
                    }
                    break;
                case 23:
                    size = 21;
                    type = "la lettera";
                    listOfObjects = getResources().getStringArray(R.array.all_letters_items);
                    objects = Arrays.asList(listOfObjects);
                    errorList = DatabaseInitializer.getAllErrorsTwoThree(db);
                    for(DataPoint s : errorList ){
                        if (s.getY() > max) {
                            Double y = s.getY();
                            max = y.intValue() + 3;
                        }
                    }
                    break;
                case 31:
                    size = 21;
                    type = "il numero";
                    listOfObjects = getResources().getStringArray(R.array.all_numbers_items);
                    objects = Arrays.asList(listOfObjects);
                    errorList = DatabaseInitializer.getAllErrorsThreeOne(db);
                    for(DataPoint s : errorList ){
                        if (s.getY() > max) {
                            Double y = s.getY();
                            max = y.intValue() + 3;
                        }
                    }
                    break;
                case 32:
                    size = 22;
                    type = "l'oggetto";
                    listOfObjects = getResources().getStringArray(R.array.fruits_it);
                    objects = Arrays.asList(listOfObjects);
                    errorList = DatabaseInitializer.getAllErrorsThreeTwo(db);
                    for(DataPoint s : errorList ){
                        if (s.getY() > max) {
                            Double y = s.getY();
                            max = y.intValue() + 3;
                        }
                    }
                    break;
                default:
                    size= 23;
                    type = "l'oggetto";
            }


        GraphView graph = findViewById(R.id.graph);
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
                graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
                graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                final List<String> finalObjects = objects;
                final String finalType = type;
                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {

                        Double x = dataPoint.getX();
                        Double y = dataPoint.getY();
                        showDataPoint(finalObjects.get(x.intValue() - 1), String.valueOf(y.intValue()), finalType);
                    }
                });
                series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                    }
                });

                series.setSpacing(10);
            }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progress = new ProgressDialog(getContext());
                progress.setTitle("Caricamento reportistica");
                progress.setMessage("Creazione del PDF in corso...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
                LinearLayout v1 = findViewById(R.id.child_credentials);
                LinearLayout v2 = findViewById(R.id.report);
                presenter.takeScreenshot(v1, v2, progress);
            }
        });
        }

        public void showPopup(View view){
            Context wrapper = new ContextThemeWrapper(this, R.style.PopUpMenuStyle);
            PopupMenu popup = new PopupMenu(wrapper, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.actions_report, popup.getMenu());
            popup.show();
        }

        public void onClickMenuItem (MenuItem item) {

            switch (item.getItemId()) {
                case R.id.settings:
                    Intent intent = new Intent(this, ReportSettingsActivity.class);
                    this.startActivity(intent);
                    break;
                default:
                    break;
            }
        }


    private void showDataPoint(String object, String numberOfErrors, String type){
        final Dialog dialog = new Dialog(ReportSpecActivity.this);
        dialog.setContentView(R.layout.pop_up);
        TextView tv = dialog.findViewById(R.id.textView);
        StringBuilder sb = new StringBuilder();
        sb.append(DatabaseInitializer.getChildById(db, currentPlayer).getName());
        sb.append(" non ha riconosciuto per ");
        sb.append(numberOfErrors);
        if(numberOfErrors.equals(String.valueOf(1))){
            sb.append(" volta ");
        }else {
            sb.append(" volte ");
        }
        sb.append(type);
        sb.append(" ");
        sb.append(object.toUpperCase());
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

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        startActivity(new Intent(ReportSpecActivity.this, ReportLevelMapActivity.class));

        CircleImageView circleImageView = findViewById(R.id.profile_image);
        GraphView graph = findViewById(R.id.graph);
        circleImageView.setImageDrawable(null);
        graph.removeAllSeries();
        errorList = null;
        db= null;
        presenter = null;

        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void openPdf() {
        File pdfDir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "Internosco");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(pdfDir, "report.pdf"));
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void sharePdf() {
        File pdfDir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "Internosco");
        Intent email = new Intent(Intent.ACTION_SEND);
        String emailParent = DatabaseInitializer.getEmail(db);
        if(emailParent != null){
            email.putExtra(Intent.EXTRA_EMAIL, emailParent);
        }
        email.putExtra(Intent.EXTRA_SUBJECT, "Reportistica di "+ DatabaseInitializer.getNameCurrentPlayer(db)+" del "+currentDate);
        Uri uri = Uri.fromFile(new File(pdfDir, "report.pdf"));
        email.putExtra(Intent.EXTRA_STREAM, uri);
        email.setType("application/pdf");
        email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(email);
    }

    public int getCurrentPlayer(){
        return  currentPlayer;
    }

    public AppDatabase getDatabase(){
        return  db;
    }

    public DataPoint[] getErrorList(){
        return errorList;
    }

}
