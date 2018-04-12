package com.polimi.dilapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

    @Dao
    public interface ReportThreeOneDao {

        @Insert(onConflict = IGNORE)
        void insertReport(ReportThreeOneEntity reportThreeOneEntity);

        @Query("DELETE FROM reportThreeOne WHERE id = :id")
        void delete(int id);

        @Query("SELECT one FROM reportThreeOne WHERE id = :id")
        int getErrorsOne(int id);

        @Query("SELECT two FROM reportThreeOne WHERE id = :id")
        int getErrorsTwo(int id);

        @Query("SELECT three FROM reportThreeOne WHERE id = :id")
        int getErrorsThree(int id);

        @Query("SELECT four FROM reportThreeOne WHERE id = :id")
        int getErrorsFour(int id);

        @Query("SELECT five FROM reportThreeOne WHERE id = :id")
        int getErrorsFive(int id);

        @Query("SELECT six FROM reportThreeOne WHERE id = :id")
        int getErrorsSix(int id);

        @Query("SELECT seven FROM reportThreeOne WHERE id = :id")
        int getErrorsSeven(int id);

        @Query("SELECT eight FROM reportThreeOne WHERE id = :id")
        int getErrorsEight(int id);

        @Query("SELECT nine FROM reportThreeOne WHERE id = :id")
        int getErrorsNine(int id);

        @Query("SELECT zero FROM reportThreeOne WHERE id = :id")
        int getErrorsZero(int id);

        @Query("UPDATE reportThreeOne SET one = :number WHERE id = :id")
        void setErrorsOne(int id, int number);

        @Query("UPDATE reportThreeOne SET two = :number WHERE id = :id")
        void setErrorsTwo(int id, int number);

        @Query("UPDATE reportThreeOne SET three = :number WHERE id = :id")
        void setErrorsThree(int id, int number);

        @Query("UPDATE reportThreeOne SET four = :number WHERE id = :id")
        void setErrorsFour(int id, int number);

        @Query("UPDATE reportThreeOne SET five = :number WHERE id = :id")
        void setErrorsFive(int id, int number);

        @Query("UPDATE reportThreeOne SET six = :number WHERE id = :id")
        void setErrorsSix(int id, int number);

        @Query("UPDATE reportThreeOne SET seven = :number WHERE id = :id")
        void setErrorsSeven(int id, int number);

        @Query("UPDATE reportThreeOne SET eight = :number WHERE id = :id")
        void setErrorsEight(int id, int number);

        @Query("UPDATE reportThreeOne SET nine = :number WHERE id = :id")
        void setErrorsNine(int id, int number);

        @Query("UPDATE reportThreeOne SET zero = :number WHERE id = :id")
        void setErrorsZero(int id, int number);

    }

