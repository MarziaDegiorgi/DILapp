package com.polimi.dilapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.polimi.dilapp.data.Child;

import java.util.List;

@Dao
public interface ChildDao {
    @Query("SELECT * FROM child")
    List<ChildEntity> getAll();

    /*@Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    ChildEntity findByName(String first, String last);*/

    @Insert
    void insertChild(ChildEntity child);

    @Delete
    void delete(ChildEntity child);
}