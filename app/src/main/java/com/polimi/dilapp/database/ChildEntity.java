package com.polimi.dilapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "child")
public class ChildEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "birth")
    private String birth;

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


    //Age
    public String getBirth() {
        return this.birth;
    }
    public void setBirth (String birth) {this.birth=birth;}

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



}
