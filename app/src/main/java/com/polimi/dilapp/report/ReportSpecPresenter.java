package com.polimi.dilapp.report;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.LinearLayout;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReportSpecPresenter implements IReport.Presenter {

    private IReport.View view;
    private AppDatabase db;
    private ProgressDialog progress;
    private static float actualWidth;
    private static float actualHeight;
    private int currentPlayerId;


    public ReportSpecPresenter(IReport.View view) {
        this.view = view;
        db = AppDatabase.getAppDatabase(view.getContext());
        currentPlayerId =  DatabaseInitializer.getCurrentPlayer(db);
    }


    @Override
    public void onDestroy() {
        view = null;
    }


    public class TakeScreenshot extends AsyncTask<LinearLayout, Void, String> {

    @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public String doInBackground(LinearLayout... arg0) {

            LinearLayout v1 = arg0[0];
            LinearLayout v2 = arg0[1];
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = sdf.format(currentTime);

            //First Check if the external storage is writable
            String state = Environment.getExternalStorageState();
            if (!Environment.MEDIA_MOUNTED.equals(state)) {
                Log.i("REPORT MAIN", "The external storage isn't writable");
            }

            //Create a directory for your PDF
            File pdfDir = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS), "Internosco");
            if (!pdfDir.exists()) {
                pdfDir.mkdir();
            }

            //Now create the name of your PDF file that you will generate
            File pdfFile = new File(pdfDir, "report.pdf");


            try {
                Document document = new Document();
                actualWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
                ;
                actualHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();
                PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                document.open();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap screen;



                Drawable d = ContextCompat.getDrawable(view.getContext(), R.drawable.logo_red_report);
                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray0 = stream.toByteArray();
                Image i = Image.getInstance(byteArray0);

                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setWidths(new int[]{1, 2});
                table.addCell(createImageCell(byteArray0));
                table.addCell(createTextCell("Reportistica del " + currentDate + "\n\n"));
                document.add(table);
                stream.reset();

                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);

                v1.setDrawingCacheEnabled(true);
                screen = Bitmap.createBitmap(v1.getDrawingCache());
                v1.setDrawingCacheEnabled(false);
                screen.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray1 = stream.toByteArray();
                addImage(document, byteArray1, actualWidth, actualHeight / 5);
                stream.reset();

                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);

                String par = "";
                int lastTimePlayed = DatabaseInitializer.getLastTimePlayed(db, DatabaseInitializer.getCurrentPlayer(db));
                Date lastProgressDate = DatabaseInitializer.getLastProgressDate(db, currentPlayerId);
                if (DatabaseInitializer.getLastProgressDate(db, currentPlayerId) == null) {
                    par = "\n\n" + DatabaseInitializer.getNameCurrentPlayer(db) + " non ha ancora mai giocato con Internosco.";
                } else {
                    convertMillis(lastTimePlayed);
                    String date = sdf.format(lastProgressDate);
                    if (date.equals(currentDate)) {
                        par = "\n\nOggi, " + currentDate + ", " + DatabaseInitializer.getNameCurrentPlayer(db) + " ha giocato con Internosco per " + convertMillis(DatabaseInitializer.getLastTimePlayed(db, currentPlayerId)) +
                                ", collezionando un totale di " + DatabaseInitializer.getLastCorrectAnswer(db, currentPlayerId) + " risposte esatte.";
                    } else {
                        par = "\n\nL'ultimo accesso di " + DatabaseInitializer.getNameCurrentPlayer(db) + " è stato il giorno " + date + ". " + DatabaseInitializer.getNameCurrentPlayer(db) + " ha giocato per " + convertMillis(DatabaseInitializer.getLastTimePlayed(db, currentPlayerId)) +
                                " collezionando un totale di " + DatabaseInitializer.getLastCorrectAnswer(db, currentPlayerId) + " risposte esatte.";
                    }
                }
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                float fntSize, lineSpacing;
                fntSize = 16f;
                lineSpacing = 20f;
                Paragraph p2 = new Paragraph(new Phrase(lineSpacing, par,
                        FontFactory.getFont(FontFactory.HELVETICA, fntSize)));
                p2.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(p2);
                v2.setDrawingCacheEnabled(true);
                screen = Bitmap.createBitmap(v2.getDrawingCache());
                v2.setDrawingCacheEnabled(false);
                screen.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray2 = stream.toByteArray();
                addImage(document, byteArray2, actualWidth, actualHeight / 2f);
                document.add(Chunk.NEWLINE);
                Paragraph p3 = new Paragraph("Il team di Internosco           ");
                p3.setAlignment(Paragraph.ALIGN_RIGHT);
                document.add(p3);
                document.close();
                progressDismiss();
                view.sharePdf();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

    }

    private static void addImage(Document document,byte[] byteArray, float width, float hight )
    {
        Image image = null;
        try
        {
            image = Image.getInstance(byteArray);
        }
        catch (BadElementException | IOException e)
        {
            e.printStackTrace();
        }
        image.scaleAbsolute(width, hight);
        try
        {
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private String convertMillis(int sec){
        ArrayList<Integer> converted = new ArrayList<>();
        int hours = sec / 3600;
        int minutes = (sec % 3600) / 60;
        int seconds = sec % 60;
        converted.add(hours);
        converted.add(minutes);
        converted.add(seconds);

        StringBuilder sb = new StringBuilder();
        if(converted.get(0) != 0) {
            if(converted.get(0) == 1){
                sb.append(converted.get(0) + " ore");
                sb.append(", ");
            }else {
                sb.append(converted.get(0) + " ore");
                sb.append(", ");
            }
        }else if(converted.get(1) != 0){
            if(converted.get(1) == 1){
                sb.append(converted.get(1)+" minuto e ");
            }else{
                sb.append(converted.get(1)+" minuti e ");
            }
        }
        if(converted.get(2) == 1){
            sb.append(converted.get(2)+" secondo");
        }else{
            sb.append(converted.get(2)+" secondi");
        }
        return sb.toString();
    }


    private static PdfPCell createImageCell(byte[] path) throws DocumentException, IOException {
        Image img = Image.getInstance(path);
        PdfPCell cell = new PdfPCell(img, true);
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(actualHeight/11f);
        return cell;
    }


    private static PdfPCell createTextCell(String text) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    @Override
    public void takeScreenshot(LinearLayout v1, LinearLayout v2, ProgressDialog progress) {
        this.progress = progress;
        new ReportSpecPresenter.TakeScreenshot().execute(v1, v2);
    }

    private void progressDismiss(){
        progress.dismiss();
    }

    @VisibleForTesting
    public IReport.View getView(){
        return view;
    }


}
