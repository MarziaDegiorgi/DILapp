package com.polimi.dilapp.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "reportTwoOne")
public class ReportTwoOneEntity {

    @PrimaryKey
    private int id;

    @ColumnInfo(name= "zero")
    private int zero;

    @ColumnInfo(name= "one")
    private int one;

    @ColumnInfo(name= "two")
    private int two;

    @ColumnInfo(name= "three")
    private int three;

    @ColumnInfo(name= "four")
    private int four;

    @ColumnInfo(name= "five")
    private int five;

    @ColumnInfo(name= "six")
    private int six;

    @ColumnInfo(name= "seven")
    private int seven;

    @ColumnInfo(name= "eight")
    private int eight;

    @ColumnInfo(name= "nine")
    private int nine;

    public ReportTwoOneEntity(int id){
        this.id = id;
    }

    public int getZero() {
        return zero;
    }

    public void setZero(int zero) {
        this.zero = zero;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public int getThree() {
        return three;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public int getFour() {
        return four;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public int getFive() {
        return five;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public int getSix() {
        return six;
    }

    public void setSix(int six) {
        this.six = six;
    }

    public int getSeven() {
        return seven;
    }

    public void setSeven(int seven) {
        this.seven = seven;
    }

    public int getEight() {
        return eight;
    }

    public void setEight(int eight) {
        this.eight = eight;
    }

    public int getNine() {
        return nine;
    }

    public void setNine(int nine) {
        this.nine = nine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
