package com.polimi.dilapp.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.util.Log;

import com.polimi.dilapp.report.ReportMainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ReportOneOneDao {

    @Insert(onConflict = IGNORE)
    void insertReport(ReportOneOneEntity reportOneOneEntity);

    @Delete
    void delete(ReportOneOneEntity reportOneOneEntity);

    @Query("SELECT * FROM reportOneOne")
    List<ReportOneOneEntity> getAll();

    @Query("SELECT banana FROM reportOneOne WHERE id = :id")
    int getErrorsBanana(int id);

    @Query("SELECT lemon FROM reportOneOne WHERE id = :id")
    int getErrorsLemon(int id);

    @Query("SELECT corn FROM reportOneOne WHERE id = :id")
    int getErrorsCorn(int id);

    @Query("SELECT grapefruit FROM reportOneOne WHERE id = :id")
    int getErrorsGrapefruit(int id);

    @Query("SELECT watermelon FROM reportOneOne WHERE id = :id")
    int getErrorsWatermelon(int id);

    @Query("SELECT strawberry FROM reportOneOne WHERE id = :id")
    int getErrorsStrawberry(int id);

    @Query("SELECT apple FROM reportOneOne WHERE id = :id")
    int getErrorsApple(int id);

    @Query("SELECT pepper FROM reportOneOne WHERE id = :id")
    int getErrorsPepper(int id);

    @Query("SELECT tomato FROM reportOneOne WHERE id = :id")
    int getErrorsTomato(int id);

    @Query("SELECT orange FROM reportOneOne WHERE id = :id")
    int getErrorsOrange(int id);

    @Query("SELECT carrot FROM reportOneOne WHERE id = :id")
    int getErrorsCarrot(int id);

    @Query("SELECT onion FROM reportOneOne WHERE id = :id")
    int getErrorsOnion(int id);

    @Query("SELECT tangerine FROM reportOneOne WHERE id = :id")
    int getErrorsTangerine(int id);

    @Query("SELECT eggplant FROM reportOneOne WHERE id = :id")
    int getErrorsEggplant(int id);

    @Query("SELECT asparagus FROM reportOneOne WHERE id = :id")
    int getErrorsAsparagus(int id);

    @Query("SELECT broccoli FROM reportOneOne WHERE id = :id")
    int getErrorsBroccoli(int id);

    @Query("SELECT cucumber FROM reportOneOne WHERE id = :id")
    int getErrorsCucumber(int id);

    @Query("SELECT pear FROM reportOneOne WHERE id = :id")
    int getErrorsPear(int id);

    @Query("SELECT greenpea FROM reportOneOne WHERE id = :id")
    int getErrorsGreenpea(int id);

    @Query("SELECT fennel FROM reportOneOne WHERE id = :id")
    int getErrorsFennel(int id);

    @Query("SELECT potato FROM reportOneOne WHERE id = :id")
    int getErrorsPotato(int id);

    @Query("UPDATE reportOneOne SET banana = :number WHERE id = :id")
    void setErrorsBanana(int id, int number);

    @Query("UPDATE reportOneOne SET corn = :number WHERE id = :id")
    void setErrorsCorn(int id, int number);

    @Query("UPDATE reportOneOne SET lemon = :number WHERE id = :id")
    void setErrorsLemon(int id, int number);

    @Query("UPDATE reportOneOne SET grapefruit = :number WHERE id = :id")
    void setErrorsGrapefruit(int id, int number);

    @Query("UPDATE reportOneOne SET watermelon = :number WHERE id = :id")
    void setErrorsWatermelon(int id, int number);

    @Query("UPDATE reportOneOne SET strawberry = :number WHERE id = :id")
    void setErrorsStrawberry(int id, int number);

    @Query("UPDATE reportOneOne SET apple = :number WHERE id = :id")
    void setErrorsApple(int id, int number);

    @Query("UPDATE reportOneOne SET pepper = :number WHERE id = :id")
    void setErrorsPepper(int id, int number);

    @Query("UPDATE reportOneOne SET tomato = :number WHERE id = :id")
    void setErrorsTomato(int id, int number);

    @Query("UPDATE reportOneOne SET orange = :number WHERE id = :id")
    void setErrorsOrange(int id, int number);

    @Query("UPDATE reportOneOne SET carrot = :number WHERE id = :id")
    void setErrorsCarrot(int id, int number);

    @Query("UPDATE reportOneOne SET onion = :number WHERE id = :id")
    void setErrorsOnion(int id, int number);

    @Query("UPDATE reportOneOne SET tangerine = :number WHERE id = :id")
    void setErrorsTangerine(int id, int number);

    @Query("UPDATE reportOneOne SET eggplant = :number WHERE id = :id")
    void setErrorsEggplant(int id, int number);

    @Query("UPDATE reportOneOne SET asparagus = :number WHERE id = :id")
    void setErrorsAsparagus(int id, int number);

    @Query("UPDATE reportOneOne SET cucumber = :number WHERE id = :id")
    void setErrorsCucumber(int id, int number);

    @Query("UPDATE reportOneOne SET broccoli = :number WHERE id = :id")
    void setErrorsBroccoli(int id, int number);

    @Query("UPDATE reportOneOne SET pear = :number WHERE id = :id")
    void setErrorsPear(int id, int number);

    @Query("UPDATE reportOneOne SET greenpea = :number WHERE id = :id")
    void setErrorsGreenpea(int id, int number);

    @Query("UPDATE reportOneOne SET fennel = :number WHERE id = :id")
    void setErrorsFennel(int id, int number);

    @Query("UPDATE reportOneOne SET potato = :number WHERE id = :id")
    void setErrorsPotato(int id, int number);


}
