package com.polimi.dilapp.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

@Dao
public interface ReportTwoOneDao {

    @Query("SELECT one FROM reportTwoOne WHERE id = :id")
    int getErrorsOne(int id);

    @Query("SELECT two FROM reportTwoOne WHERE id = :id")
    int getErrorsTwo(int id);

    @Query("SELECT three FROM reportTwoOne WHERE id = :id")
    int getErrorsThree(int id);

    @Query("SELECT four FROM reportTwoOne WHERE id = :id")
    int getErrorsFour(int id);

    @Query("SELECT five FROM reportTwoOne WHERE id = :id")
    int getErrorsFive(int id);

    @Query("SELECT six FROM reportTwoOne WHERE id = :id")
    int getErrorsSix(int id);

    @Query("SELECT seven FROM reportTwoOne WHERE id = :id")
    int getErrorsSeven(int id);

    @Query("SELECT eight FROM reportTwoOne WHERE id = :id")
    int getErrorsEight(int id);

    @Query("SELECT nine FROM reportTwoOne WHERE id = :id")
    int getErrorsNine(int id);

    @Query("SELECT zero FROM reportTwoOne WHERE id = :id")
    int getErrorsZero(int id);

    @Query("UPDATE reportTwoOne SET one = :number WHERE id = :id")
    void setErrorsOne(int id, int number);

    @Query("UPDATE reportTwoOne SET two = :number WHERE id = :id")
    void setErrorsTwo(int id, int number);

    @Query("UPDATE reportTwoOne SET three = :number WHERE id = :id")
    void setErrorsThree(int id, int number);

    @Query("UPDATE reportTwoOne SET four = :number WHERE id = :id")
    void setErrorsFour(int id, int number);

    @Query("UPDATE reportTwoOne SET five = :number WHERE id = :id")
    void setErrorsFive(int id, int number);

    @Query("UPDATE reportTwoOne SET six = :number WHERE id = :id")
    void setErrorsSix(int id, int number);

    @Query("UPDATE reportTwoOne SET seven = :number WHERE id = :id")
    void setErrorsSeven(int id, int number);

    @Query("UPDATE reportTwoOne SET eight = :number WHERE id = :id")
    void setErrorsEight(int id, int number);

    @Query("UPDATE reportTwoOne SET nine = :number WHERE id = :id")
    void setErrorsNine(int id, int number);

    @Query("UPDATE reportTwoOne SET zero = :number WHERE id = :id")
    void setErrorsZero(int id, int number);

}
