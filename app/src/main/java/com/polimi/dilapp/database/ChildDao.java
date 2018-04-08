package com.polimi.dilapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ChildDao {
    @Query("SELECT * FROM child")
    List<ChildEntity> getAll();

    /*@Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    ChildEntity findByName(String first, String last);*/
    @Query("UPDATE child SET currentPlayer = :currentPlayer  WHERE id = :id")
    void updateCurrentPlayer(int id, Boolean currentPlayer);

    @Query("UPDATE child SET currentPlayer = :f  WHERE currentPlayer = :t")
    void resetCurrentPlayer(Boolean f, Boolean t);

    @Query("SELECT id FROM child WHERE currentPlayer = :t")
    int getCurrentPlayer(Boolean t);

    @Query("SELECT name FROM child WHERE currentPlayer = :t")
    String getNameCurrentPlayer(Boolean t);

    @Query("SELECT birth FROM child WHERE currentPlayer = :t")
    String getBirthCurrentPlayer(Boolean t);

    @Query("SELECT level FROM child WHERE currentPlayer = :t")
    int getLevelCurrentPlayer(Boolean t);

    @Query("UPDATE child SET level = :level WHERE currentPlayer = :t")
    void setLevelCurrentPlayer(int level, Boolean t);

    @Query("SELECT object FROM child WHERE currentPlayer = :t")
    String getObjectCurrentPlayer(Boolean t);

    @Query("UPDATE child SET object = :object WHERE currentPlayer = :t")
    void setObjectCurrentPlayer(String object, Boolean t);

    @Query("SELECT subString FROM child WHERE currentPlayer = :t")
    String getSubStringCurrentPlayer(Boolean t);

    @Query("UPDATE child SET subString = :subString WHERE currentPlayer = :t")
    void setSubStringCurrentPlayer(String subString, Boolean t);

    @Query("SELECT progress FROM child WHERE id = :id")
    String getProgress(int id);

    @Query("UPDATE child SET progress = :progress WHERE id = :id")
    void setProgress(int id, String progress);

    @Query("SELECT progressDate FROM child WHERE id = :id")
    String getProgressDate(int id);

    @Query("UPDATE child SET progressDate = :progressDate WHERE id = :id")
    void setProgressDate(int id, String progressDate);

    @Query("SELECT correctAnswer FROM child WHERE id = :id")
    String getCorrectAnswer(int id);

    @Query("UPDATE child SET correctAnswer = :correctAnswer WHERE id = :id")
    void setCorrectAnswer(int id, String correctAnswer);

    @Query("SELECT time FROM child WHERE id = :id")
    String getTime(int id);

    @Query("UPDATE child SET time = :time WHERE id = :id")
    void setTime(int id, String time);

    @Insert(onConflict = IGNORE)
    void insertChild(ChildEntity child);

    @Delete
    void delete(ChildEntity child);
}