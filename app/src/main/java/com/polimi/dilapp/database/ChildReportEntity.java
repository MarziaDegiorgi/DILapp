package com.polimi.dilapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;


@Entity(tableName = "childReport")
 public class ChildReportEntity {

    @PrimaryKey
    private int id;

    @ColumnInfo(name="progress")
    private String progress;

    @ColumnInfo(name= "a")
    private int a;

    @ColumnInfo(name= "b")
    private int b;

    @ColumnInfo(name= "c")
    private int c;

    @ColumnInfo(name= "d")
    private int d;

    @ColumnInfo(name= "e")
    private int e;

    @ColumnInfo(name= "f")
    private int f;

    @ColumnInfo(name= "g")
    private int g;

    @ColumnInfo(name= "h")
    private int h;

    @ColumnInfo(name= "i")
    private int i;

    @ColumnInfo(name= "l")
    private int l;

    @ColumnInfo(name= "m")
    private int m;

    @ColumnInfo(name= "n")
    private int n;

    @ColumnInfo(name= "o")
    private int o;

    @ColumnInfo(name= "p")
    private int p;

    @ColumnInfo(name= "q")
    private int q;

    @ColumnInfo(name= "r")
    private int r;

    @ColumnInfo(name= "s")
    private int s;

    @ColumnInfo(name= "t")
    private int t;

    @ColumnInfo(name= "u")
    private int u;

    @ColumnInfo(name= "v")
    private int v;

    @ColumnInfo(name= "z")
    private int z;

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


    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {this.progress = progress;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getO() {
        return o;
    }

    public void setO(int o) {
        this.o = o;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
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

}
