package com.polimi.dilapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;

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

    @ColumnInfo(name = "object")
    private String object;

    @ColumnInfo(name = "photo")
    private String photo;

    @ColumnInfo(name = "currentPlayer")
    private Boolean currentPlayer;  //set to true if the child is the current gamer

    @ColumnInfo(name = "subString")
    private String subString;

    @ColumnInfo(name="progress")
    private String progress;

    @ColumnInfo(name="progressDate")
    private String progressDate;

    @ColumnInfo(name="correctAnswer")
    private String correctAnswer;

    @ColumnInfo(name="time")
    private String time;

    @ColumnInfo(name="repoEnabled")
    private String repoEnabled;

    @ColumnInfo(name="email")
    private String email;

    //Name
    public String getName() {
        return this.name;
    }
    public void setName( String name) {this.name=name;}

    //Photo
    public String getPhoto() { return  this.photo;}
    public void setPhoto(String photo) {this.photo = photo;}


    //Date of birth
    public String getBirth() {
        return this.birth;
    }
    public void setBirth (String birth) {this.birth=birth;}

    //Object of the level
    public String getObject() {
        return this.object;
    }
    public void setObject (String object) {this.object=object;}

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

    //SubString CurrentPlayer
    public String getSubString() { return this.subString; }
    public void setSubString(String subString) { this.subString = subString; }


    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProgressDate() {
        return progressDate;
    }

    public void setProgressDate(String progressDate) {
        this.progressDate = progressDate;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRepoEnabled() {
        return repoEnabled;
    }

    public void setRepoEnabled(String repoEnabled) {
        this.repoEnabled = repoEnabled;
    }
}
