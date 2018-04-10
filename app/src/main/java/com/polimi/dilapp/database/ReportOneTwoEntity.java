package com.polimi.dilapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "reportOneTwo")
public class ReportOneTwoEntity {

    @PrimaryKey
    private int id;

    @ColumnInfo(name="progress")
    private String progress;

    @ColumnInfo(name= "yellow")
    private int yellow;

    @ColumnInfo(name= "red")
    private int red;

    @ColumnInfo(name= "orangec")
    private int orangec;

    @ColumnInfo(name= "violet")
    private int violet;

    @ColumnInfo(name= "white")
    private int white;

    @ColumnInfo(name= "brown")
    private int brown;

    @ColumnInfo(name= "green")
    private int green;

    public ReportOneTwoEntity(int id){
        this.id = id;
    }


    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getOrangec() {
        return orangec;
    }

    public void setOrangec(int orangec) {
        this.orangec = orangec;
    }

    public int getViolet() {
        return violet;
    }

    public void setViolet(int violet) {
        this.violet = violet;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public int getBrown() {
        return brown;
    }

    public void setBrown(int brown) {
        this.brown = brown;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }
}
