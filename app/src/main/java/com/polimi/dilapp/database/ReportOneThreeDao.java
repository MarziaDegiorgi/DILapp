package com.polimi.dilapp.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

@Dao
public interface ReportOneThreeDao {

    @Query("SELECT banana FROM reportOneThree WHERE id = :id")
    int getErrorsBanana(int id);

    @Query("SELECT lemon FROM reportOneThree WHERE id = :id")
    int getErrorsLemon(int id);

    @Query("SELECT corn FROM reportOneThree WHERE id = :id")
    int getErrorsCorn(int id);

    @Query("SELECT grapefruit FROM reportOneThree WHERE id = :id")
    int getErrorsGrapefruit(int id);

    @Query("SELECT watermelon FROM reportOneThree WHERE id = :id")
    int getErrorsWatermelon(int id);

    @Query("SELECT strawberry FROM reportOneThree WHERE id = :id")
    int getErrorsStrawberry(int id);

    @Query("SELECT apple FROM reportOneThree WHERE id = :id")
    int getErrorsApple(int id);

    @Query("SELECT pepper FROM reportOneThree WHERE id = :id")
    int getErrorsPepper(int id);

    @Query("SELECT tomato FROM reportOneThree WHERE id = :id")
    int getErrorsTomato(int id);

    @Query("SELECT orange FROM reportOneThree WHERE id = :id")
    int getErrorsOrange(int id);

    @Query("SELECT carrot FROM reportOneThree WHERE id = :id")
    int getErrorsCarrot(int id);

    @Query("SELECT onion FROM reportOneThree WHERE id = :id")
    int getErrorsOnion(int id);

    @Query("SELECT tangerine FROM reportOneThree WHERE id = :id")
    int getErrorsTangerine(int id);

    @Query("SELECT eggplant FROM reportOneThree WHERE id = :id")
    int getErrorsEggplant(int id);

    @Query("SELECT asparagus FROM reportOneThree WHERE id = :id")
    int getErrorsAsparagus(int id);

    @Query("SELECT broccoli FROM reportOneThree WHERE id = :id")
    int getErrorsBroccoli(int id);

    @Query("SELECT cucumber FROM reportOneThree WHERE id = :id")
    int getErrorsCucumber(int id);

    @Query("SELECT pear FROM reportOneThree WHERE id = :id")
    int getErrorsPear(int id);

    @Query("SELECT greenpea FROM reportOneThree WHERE id = :id")
    int getErrorsGreenpea(int id);

    @Query("SELECT fennel FROM reportOneThree WHERE id = :id")
    int getErrorsFennel(int id);

    @Query("SELECT potato FROM reportOneThree WHERE id = :id")
    int getErrorsPotato(int id);

    @Query("UPDATE reportOneThree SET banana = :number WHERE id = :id")
    void setErrorsBanana(int id, int number);

    @Query("UPDATE reportOneThree SET corn = :number WHERE id = :id")
    void setErrorsCorn(int id, int number);

    @Query("UPDATE reportOneThree SET lemon = :number WHERE id = :id")
    void setErrorsLemon(int id, int number);

    @Query("UPDATE reportOneThree SET grapefruit = :number WHERE id = :id")
    void setErrorsGrapefruit(int id, int number);

    @Query("UPDATE reportOneThree SET watermelon = :number WHERE id = :id")
    void setErrorsWatermelon(int id, int number);

    @Query("UPDATE reportOneThree SET strawberry = :number WHERE id = :id")
    void setErrorsStrawberry(int id, int number);

    @Query("UPDATE reportOneThree SET apple = :number WHERE id = :id")
    void setErrorsApple(int id, int number);

    @Query("UPDATE reportOneThree SET pepper = :number WHERE id = :id")
    void setErrorsPepper(int id, int number);

    @Query("UPDATE reportOneThree SET tomato = :number WHERE id = :id")
    void setErrorsTomato(int id, int number);

    @Query("UPDATE reportOneThree SET orange = :number WHERE id = :id")
    void setErrorsOrange(int id, int number);

    @Query("UPDATE reportOneThree SET carrot = :number WHERE id = :id")
    void setErrorsCarrot(int id, int number);

    @Query("UPDATE reportOneThree SET onion = :number WHERE id = :id")
    void setErrorsOnion(int id, int number);

    @Query("UPDATE reportOneThree SET tangerine = :number WHERE id = :id")
    void setErrorsTangerine(int id, int number);

    @Query("UPDATE reportOneThree SET eggplant = :number WHERE id = :id")
    void setErrorsEggplant(int id, int number);

    @Query("UPDATE reportOneThree SET asparagus = :number WHERE id = :id")
    void setErrorsAsparagus(int id, int number);

    @Query("UPDATE reportOneThree SET cucumber = :number WHERE id = :id")
    void setErrorsCucumber(int id, int number);

    @Query("UPDATE reportOneThree SET broccoli = :number WHERE id = :id")
    void setErrorsBroccoli(int id, int number);

    @Query("UPDATE reportOneThree SET pear = :number WHERE id = :id")
    void setErrorsPear(int id, int number);

    @Query("UPDATE reportOneThree SET greenpea = :number WHERE id = :id")
    void setErrorsGreenpea(int id, int number);

    @Query("UPDATE reportOneThree SET fennel = :number WHERE id = :id")
    void setErrorsFennel(int id, int number);

    @Query("UPDATE reportOneThree SET potato = :number WHERE id = :id")
    void setErrorsPotato(int id, int number);

}
