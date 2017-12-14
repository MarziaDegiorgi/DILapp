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

    @Query("SELECT level FROM child WHERE currentPlayer = :t")
    int getLevelCurrentPlayer(Boolean t);

    @Query("UPDATE child SET level = :level  WHERE currentPlayer = :t")
    void setLevelCurrentPlayer(int level, Boolean t);

    @Insert(onConflict = IGNORE)
    void insertChild(ChildEntity child);

    @Delete
    void delete(ChildEntity child);
}