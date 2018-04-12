package com.polimi.dilapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


    @Dao
    public interface ReportThreeTwoDao {

        @Insert(onConflict = IGNORE)
        void insertReport(ReportThreeTwoEntity reportThreeTwoEntity);

        @Query("DELETE FROM reportThreeTwo WHERE id = :id")
        void delete(int id);

        @Query("SELECT * FROM reportThreeTwo")
        List<ReportThreeTwoEntity> getAll();

        @Query("SELECT banana FROM reportThreeTwo WHERE id = :id")
        int getErrorsBanana(int id);

        @Query("SELECT lemon FROM reportThreeTwo WHERE id = :id")
        int getErrorsLemon(int id);

        @Query("SELECT corn FROM reportThreeTwo WHERE id = :id")
        int getErrorsCorn(int id);

        @Query("SELECT grapefruit FROM reportThreeTwo WHERE id = :id")
        int getErrorsGrapefruit(int id);

        @Query("SELECT watermelon FROM reportThreeTwo WHERE id = :id")
        int getErrorsWatermelon(int id);

        @Query("SELECT strawberry FROM reportThreeTwo WHERE id = :id")
        int getErrorsStrawberry(int id);

        @Query("SELECT apple FROM reportThreeTwo WHERE id = :id")
        int getErrorsApple(int id);

        @Query("SELECT pepper FROM reportThreeTwo WHERE id = :id")
        int getErrorsPepper(int id);

        @Query("SELECT tomato FROM reportThreeTwo WHERE id = :id")
        int getErrorsTomato(int id);

        @Query("SELECT orange FROM reportThreeTwo WHERE id = :id")
        int getErrorsOrange(int id);

        @Query("SELECT carrot FROM reportThreeTwo WHERE id = :id")
        int getErrorsCarrot(int id);

        @Query("SELECT onion FROM reportThreeTwo WHERE id = :id")
        int getErrorsOnion(int id);

        @Query("SELECT tangerine FROM reportThreeTwo WHERE id = :id")
        int getErrorsTangerine(int id);

        @Query("SELECT eggplant FROM reportThreeTwo WHERE id = :id")
        int getErrorsEggplant(int id);

        @Query("SELECT asparagus FROM reportThreeTwo WHERE id = :id")
        int getErrorsAsparagus(int id);

        @Query("SELECT broccoli FROM reportThreeTwo WHERE id = :id")
        int getErrorsBroccoli(int id);

        @Query("SELECT cucumber FROM reportThreeTwo WHERE id = :id")
        int getErrorsCucumber(int id);

        @Query("SELECT pear FROM reportThreeTwo WHERE id = :id")
        int getErrorsPear(int id);

        @Query("SELECT greenpea FROM reportThreeTwo WHERE id = :id")
        int getErrorsGreenpea(int id);

        @Query("SELECT fennel FROM reportThreeTwo WHERE id = :id")
        int getErrorsFennel(int id);

        @Query("SELECT potato FROM reportThreeTwo WHERE id = :id")
        int getErrorsPotato(int id);

        @Query("UPDATE reportThreeTwo SET banana = :number WHERE id = :id")
        void setErrorsBanana(int id, int number);

        @Query("UPDATE reportThreeTwo SET corn = :number WHERE id = :id")
        void setErrorsCorn(int id, int number);

        @Query("UPDATE reportThreeTwo SET lemon = :number WHERE id = :id")
        void setErrorsLemon(int id, int number);

        @Query("UPDATE reportThreeTwo SET grapefruit = :number WHERE id = :id")
        void setErrorsGrapefruit(int id, int number);

        @Query("UPDATE reportThreeTwo SET watermelon = :number WHERE id = :id")
        void setErrorsWatermelon(int id, int number);

        @Query("UPDATE reportThreeTwo SET strawberry = :number WHERE id = :id")
        void setErrorsStrawberry(int id, int number);

        @Query("UPDATE reportThreeTwo SET apple = :number WHERE id = :id")
        void setErrorsApple(int id, int number);

        @Query("UPDATE reportThreeTwo SET pepper = :number WHERE id = :id")
        void setErrorsPepper(int id, int number);

        @Query("UPDATE reportThreeTwo SET tomato = :number WHERE id = :id")
        void setErrorsTomato(int id, int number);

        @Query("UPDATE reportThreeTwo SET orange = :number WHERE id = :id")
        void setErrorsOrange(int id, int number);

        @Query("UPDATE reportThreeTwo SET carrot = :number WHERE id = :id")
        void setErrorsCarrot(int id, int number);

        @Query("UPDATE reportThreeTwo SET onion = :number WHERE id = :id")
        void setErrorsOnion(int id, int number);

        @Query("UPDATE reportThreeTwo SET tangerine = :number WHERE id = :id")
        void setErrorsTangerine(int id, int number);

        @Query("UPDATE reportThreeTwo SET eggplant = :number WHERE id = :id")
        void setErrorsEggplant(int id, int number);

        @Query("UPDATE reportThreeTwo SET asparagus = :number WHERE id = :id")
        void setErrorsAsparagus(int id, int number);

        @Query("UPDATE reportThreeTwo SET cucumber = :number WHERE id = :id")
        void setErrorsCucumber(int id, int number);

        @Query("UPDATE reportThreeTwo SET broccoli = :number WHERE id = :id")
        void setErrorsBroccoli(int id, int number);

        @Query("UPDATE reportThreeTwo SET pear = :number WHERE id = :id")
        void setErrorsPear(int id, int number);

        @Query("UPDATE reportThreeTwo SET greenpea = :number WHERE id = :id")
        void setErrorsGreenpea(int id, int number);

        @Query("UPDATE reportThreeTwo SET fennel = :number WHERE id = :id")
        void setErrorsFennel(int id, int number);

        @Query("UPDATE reportThreeTwo SET potato = :number WHERE id = :id")
        void setErrorsPotato(int id, int number);


    }
    
