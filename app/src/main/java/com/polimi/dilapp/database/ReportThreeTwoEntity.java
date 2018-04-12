package com.polimi.dilapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "reportThreeTwo")
public class ReportThreeTwoEntity {

    @PrimaryKey
    private int id;

    @ColumnInfo(name="progress")
    private String progress;

    @ColumnInfo(name= "banana")
    private int banana;

    @ColumnInfo(name= "lemon")
    private int lemon;

    @ColumnInfo(name= "corn")
    private int corn;

    @ColumnInfo(name= "grapefruit")
    private int grapefruit;

    @ColumnInfo(name= "watermelon")
    private int watermelon;

    @ColumnInfo(name= "strawberry")
    private int strawberry;

    @ColumnInfo(name= "apple")
    private int apple;

    @ColumnInfo(name= "pepper")
    private int pepper;

    @ColumnInfo(name= "tomato")
    private int tomato;

    @ColumnInfo(name= "orange")
    private int orange;

    @ColumnInfo(name= "carrot")
    private int carrot;

    @ColumnInfo(name= "onion")
    private int onion;

    @ColumnInfo(name= "tangerine")
    private int tangerine;

    @ColumnInfo(name= "eggplant")
    private int eggplant;

    @ColumnInfo(name= "asparagus")
    private int asparagus;

    @ColumnInfo(name= "broccoli")
    private int broccoli;

    @ColumnInfo(name= "cucumber")
    private int cucumber;

    @ColumnInfo(name= "pear")
    private int pear;

    @ColumnInfo(name= "greenpea")
    private int greenpea;

    @ColumnInfo(name= "fennel")
    private int fennel;

    @ColumnInfo(name= "potato")
    private int potato;

    public ReportThreeTwoEntity(int id){
        this.id = id;
    }

    public int getBanana() {
        return banana;
    }

    public void setBanana(int banana) {
        this.banana = banana;
    }

    public int getLemon() {
        return lemon;
    }

    public void setLemon(int lemon) {
        this.lemon = lemon;
    }

    public int getCorn() {
        return corn;
    }

    public void setCorn(int corn) {
        this.corn = corn;
    }

    public int getGrapefruit() {
        return grapefruit;
    }

    public void setGrapefruit(int grapefruit) {
        this.grapefruit = grapefruit;
    }

    public int getWatermelon() {
        return watermelon;
    }

    public void setWatermelon(int watermelon) {
        this.watermelon = watermelon;
    }

    public int getStrawberry() {
        return strawberry;
    }

    public void setStrawberry(int strawberry) {
        this.strawberry = strawberry;
    }

    public int getApple() {
        return apple;
    }

    public void setApple(int apple) {
        this.apple = apple;
    }

    public int getPepper() {
        return pepper;
    }

    public void setPepper(int pepper) {
        this.pepper = pepper;
    }

    public int getTomato() {
        return tomato;
    }

    public void setTomato(int tomato) {
        this.tomato = tomato;
    }

    public int getOrange() {
        return orange;
    }

    public void setOrange(int orange) {
        this.orange = orange;
    }

    public int getCarrot() {
        return carrot;
    }

    public void setCarrot(int carrot) {
        this.carrot = carrot;
    }

    public int getOnion() {
        return onion;
    }

    public void setOnion(int onion) {
        this.onion = onion;
    }

    public int getTangerine() {
        return tangerine;
    }

    public void setTangerine(int tangerine) {
        this.tangerine = tangerine;
    }

    public int getEggplant() {
        return eggplant;
    }

    public void setEggplant(int eggplant) {
        this.eggplant = eggplant;
    }

    public int getAsparagus() {
        return asparagus;
    }

    public void setAsparagus(int asparagus) {
        this.asparagus = asparagus;
    }

    public int getBroccoli() {
        return broccoli;
    }

    public void setBroccoli(int broccoli) {
        this.broccoli = broccoli;
    }

    public int getCucumber() {
        return cucumber;
    }

    public void setCucumber(int cucumber) {
        this.cucumber = cucumber;
    }

    public int getPear() {
        return pear;
    }

    public void setPear(int pear) {
        this.pear = pear;
    }

    public int getGreenpea() {
        return greenpea;
    }

    public void setGreenpea(int greenpea) {
        this.greenpea = greenpea;
    }

    public int getFennel() {
        return fennel;
    }

    public void setFennel(int fennel) {
        this.fennel = fennel;
    }

    public int getPotato() {
        return potato;
    }

    public void setPotato(int potato) {
        this.potato = potato;
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
}
