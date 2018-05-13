package com.polimi.dilapp.report;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.arch.persistence.room.Database;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import com.polimi.dilapp.levelmap.ReportLevelMapActivity;
import com.polimi.dilapp.levels.view.ActivityOneOne;
import com.polimi.dilapp.main.NewAccountActivity;
import com.polimi.dilapp.startgame.StartGameActivity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class ReportMainActivity extends AppCompatActivity implements IReport.View {
    private IReport.Presenter presenter;
    private AppDatabase db;
    private ArrayList<Float> progressList;
    private ArrayList<Date> dateList;
    private ArrayList<Integer> correctAnswerList;
    private ArrayList<Integer> timeList;
    private int numProgressInterest = 50;
    private int currentPlayer;
    private ImageView shareButton;


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_main_report);
        shareButton = findViewById(R.id.share_button);



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
                Intent intent = new Intent(ReportMainActivity.this, ReportLevelMapActivity.class);
                startActivity(intent);
                finish();
            }
        });

        presenter = new ReportMainPresenter(this);
        progressList = DatabaseInitializer.getProgress(db, currentPlayer);
        correctAnswerList = DatabaseInitializer.getCorrectAnswer(db, currentPlayer);
        timeList = DatabaseInitializer.getTime(db, currentPlayer);

        try {
            dateList = DatabaseInitializer.getProgressDate(db, currentPlayer);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("[REPORT MAIN]", "Progress list :" + progressList);
        Log.i("[REPORT MAIN]", "Date list :" + dateList);

        GraphView graph = findViewById(R.id.graph);
        DataPoint[] data = new DataPoint[progressList.size()];
        LineGraphSeries<DataPoint> series;
        //TODO: check dimension on progressList
        float max = 1;
        for (int i = 0; i < progressList.size(); i++) {
            float progress = progressList.get(i);
            Date date = dateList.get(i);
            data[i] = new DataPoint(date, progress);
            Log.i("[REPORT MAIN]", "Data :" + data[i]);
            if (progress > max) {
                max = progress + 0.5f;
            }
        }
        series = new LineGraphSeries<>(data);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String date = df.format(dataPoint.getX());
                double f = dataPoint.getY();
                showDataPoint(date, String.valueOf(f));
            }
        });
        series.setDrawDataPoints(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(max);
        graph.getViewport().setMinY(0.0f);
        graph.getViewport().setXAxisBoundsManual(true);
        if (!dateList.isEmpty()) {
            graph.getViewport().setMaxX(dateList.get(dateList.size() - 1).getTime());
            graph.getViewport().setMinX(dateList.get(0).getTime());
        }
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Giorni");
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        Log.i("[REPORT MAIN]", "DateList size: " + String.valueOf(dateList.size()));
        Log.i("[REPORT MAIN]", "DateList: " + dateList);
        //graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.addSeries(series);


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout v1 = findViewById(R.id.child_credentials);
                LinearLayout v2 = findViewById(R.id.report);
                presenter.takeScreenshot(v1, v2);
            }
        });
    }

    public void showPopup(View view) {
        Context wrapper = new ContextThemeWrapper(this, R.style.PopUpMenuStyle);
        PopupMenu popup = new PopupMenu(wrapper, view);

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions_report, popup.getMenu());
        popup.show();
    }

    private void showDataPoint(String date, String value) {
        final Dialog dialog = new Dialog(ReportMainActivity.this);
        dialog.setContentView(R.layout.pop_up);
        String actualCorrectAnswer = "";
        String actualTime = "";
        for (int i = 0; i < correctAnswerList.size(); i++) {
            if (((float) (correctAnswerList.get(i) * 10) / timeList.get(i)) == Double.parseDouble(value)) {
                actualCorrectAnswer = String.valueOf(correctAnswerList.get(i));
                actualTime = String.valueOf(timeList.get(i));
            }
        }
        TextView tv = dialog.findViewById(R.id.textView);
        StringBuilder sb = new StringBuilder();
        sb.append("\bData: \b");
        sb.append(date);
        sb.append("\n\bRisposte esatte: \b");
        sb.append(actualCorrectAnswer);
        sb.append("\n\bTempo impiegato: \b");
        sb.append(actualTime);
        sb.append(" secondi");
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

    public void onClickMenuItem(MenuItem item) {
        presenter.onItemMenuSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        CircleImageView circleImageView = findViewById(R.id.profile_image);
        GraphView graph = findViewById(R.id.graph);
        circleImageView.setImageDrawable(null);
        graph.removeAllSeries();
        presenter = null;
        progressList = null;
        correctAnswerList = null;
        timeList = null;
        dateList = null;
        db = null;

        startActivity(new Intent(ReportMainActivity.this, StartGameActivity.class));
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void openPdf(){
        File pdfDir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "Internosco");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(pdfDir,  "report.pdf"));
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void sharePdf(){
        File pdfDir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "Internosco");
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, "receiver_email_address");
        email.putExtra(Intent.EXTRA_SUBJECT, "subject");
        email.putExtra(Intent.EXTRA_TEXT, "email body");
        Uri uri = Uri.fromFile(new File(pdfDir,  "report.pdf"));
        email.putExtra(Intent.EXTRA_STREAM, uri);
        email.setType("application/pdf");
        email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(email);
    }



    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}

