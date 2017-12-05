package com.polimi.dilapp.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.concurrent.atomic.AtomicInteger;

public class Child {
    static AtomicInteger nextId = new AtomicInteger();
    private String name;
    private int age;
    private int id;
    private int level;
    //private Bitmap photo;
    private Boolean currentPlayer; //set to true if the child is the current gamer
    //TODO: Decide if add also an avatar or other information

    public Child(String name, int age /*Bitmap photo*/){
        this.currentPlayer = false;
        this.id = nextId.incrementAndGet();
        this.name=name;
        this.age= age;
        this.level = 0;
        //this.photo = photo;

        //TODO: Generate a unique id to associate to the name
    }

    //Name
    public String getName() {
        return this.name;
    }
    public void setName( String name) {this.name=name;}

    //Photo
    //public Bitmap getPhoto() { return  this.photo;}
    // public void setPhoto (Bitmap photo) {this.photo=photo;}

    //Age
    public int getAge() {
        return this.age;
    }
    public void setAge (int age) {this.age=age;}

    //Id
    public int getId() {
        return this.id;
    }
    public void setId (int age) {this.age=age;}

    //Level
    public int getLevel() {
        return this.level;
    }
    public void setLevel (int level) {this.level=level;}

    //CurrentPlayer
    public Boolean getCurrentPlayer() { return this.currentPlayer; }
    public void setCurrentPlayer(Boolean currentPlayer) { this.currentPlayer = currentPlayer; }
    public void currentPlayer () {this.currentPlayer = true; }



}
