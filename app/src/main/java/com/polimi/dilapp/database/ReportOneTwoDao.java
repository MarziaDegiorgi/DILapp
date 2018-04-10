package com.polimi.dilapp.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ReportOneTwoDao {

    @Insert(onConflict = IGNORE)
    void insertReport(ReportOneTwoEntity reportOneTwoEntity);

    @Delete
    void delete(ReportOneTwoEntity reportOneTwoEntity);

    @Query("SELECT yellow FROM reportOneTwo WHERE id = :id")
    int getErrorsYellow(int id);

    @Query("SELECT orangec FROM reportOneTwo WHERE id = :id")
    int getErrorsOrangec(int id);

    @Query("SELECT violet FROM reportOneTwo WHERE id = :id")
    int getErrorsViolet(int id);

    @Query("SELECT green FROM reportOneTwo WHERE id = :id")
    int getErrorsGreen(int id);

    @Query("SELECT white FROM reportOneTwo WHERE id = :id")
    int getErrorsWhite(int id);

    @Query("SELECT brown FROM reportOneTwo WHERE id = :id")
    int getErrorsBrown(int id);

    @Query("UPDATE reportOneTwo SET yellow = :number WHERE id = :id")
    void setErrorsYellow(int id, int number);

    @Query("UPDATE reportOneTwo SET orangec = :number WHERE id = :id")
    void setErrorsOrangec(int id, int number);

    @Query("UPDATE reportOneTwo SET green = :number WHERE id = :id")
    void setErrorsGreen(int id, int number);

    @Query("UPDATE reportOneTwo SET white = :number WHERE id = :id")
    void setErrorsWhite(int id, int number);

    @Query("UPDATE reportOneTwo SET brown = :number WHERE id = :id")
    void setErrorsBrown(int id, int number);

    @Query("UPDATE reportOneTwo SET violet = :number WHERE id = :id")
    void setErrorsViolet(int id, int number);

}
