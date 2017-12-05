package com.polimi.dilapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicInteger;

@Entity(tableName = "child")
public class ChildEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "age")
    private int age;

    @ColumnInfo(name = "level")
    private int level;

    @ColumnInfo(name = "photo")
    private String photo;

    @ColumnInfo(name = "currentPlayer")
    private Boolean currentPlayer;  //set to true if the child is the current gamer


    //Name
    public String getName() {
        return this.name;
    }
    public void setName( String name) {this.name=name;}

    //Photo
    public String getPhoto() { return  this.photo;}
    public void setPhoto(String photo) {this.photo = photo;}

    /*public Bitmap getBitmapPhoto () {
        Bitmap bitmap = BitmapFactory.decodeByteArray(this.photo, 0, this.photo.length);
        return bitmap;
    }
    public void setBitmapPhoto (Bitmap bitmap) {
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , blob);
        byte[] bitmapdata = blob.toByteArray();
        this.photo = bitmapdata;
    }*/

    //Age
    public int getAge() {
        return this.age;
    }
    public void setAge (int age) {this.age=age;}

    //Id
    public int getId() {
        return this.id;
    }
    public void setId (int id) {this.id=id;}

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
