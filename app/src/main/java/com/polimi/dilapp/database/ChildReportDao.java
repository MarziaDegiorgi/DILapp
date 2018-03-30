package com.polimi.dilapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.ArrayList;


@Dao
public interface ChildReportDao {

    @Query("SELECT progress FROM childReport WHERE id = :id")
    String getProgress(int id);

    @Query("SELECT a FROM childReport WHERE id = :id")
    int getErrorsA(int id);

    @Query("SELECT b FROM childReport WHERE id = :id")
    int getErrorsB(int id);

    @Query("SELECT c FROM childReport WHERE id = :id")
    int getErrorsC(int id);

    @Query("SELECT d FROM childReport WHERE id = :id")
    int getErrorsD(int id);

    @Query("SELECT e FROM childReport WHERE id = :id")
    int getErrorsE(int id);

    @Query("SELECT f FROM childReport WHERE id = :id")
    int getErrorsF(int id);

    @Query("SELECT g FROM childReport WHERE id = :id")
    int getErrorsG(int id);

    @Query("SELECT h FROM childReport WHERE id = :id")
    int getErrorsH(int id);

    @Query("SELECT i FROM childReport WHERE id = :id")
    int getErrorsI(int id);

    @Query("SELECT l FROM childReport WHERE id = :id")
    int getErrorsL(int id);

    @Query("SELECT m FROM childReport WHERE id = :id")
    int getErrorsM(int id);

    @Query("SELECT n FROM childReport WHERE id = :id")
    int getErrorsN(int id);

    @Query("SELECT o FROM childReport WHERE id = :id")
    int getErrorsO(int id);

    @Query("SELECT p FROM childReport WHERE id = :id")
    int getErrorsP(int id);

    @Query("SELECT q FROM childReport WHERE id = :id")
    int getErrorsQ(int id);

    @Query("SELECT r FROM childReport WHERE id = :id")
    int getErrorsR(int id);

    @Query("SELECT s FROM childReport WHERE id = :id")
    int getErrorsS(int id);

    @Query("SELECT t FROM childReport WHERE id = :id")
    int getErrorsT(int id);

    @Query("SELECT u FROM childReport WHERE id = :id")
    int getErrorsU(int id);

    @Query("SELECT v FROM childReport WHERE id = :id")
    int getErrorsV(int id);

    @Query("SELECT z FROM childReport WHERE id = :id")
    int getErrorsZ(int id);

    @Query("SELECT one FROM childReport WHERE id = :id")
    int getErrorsOne(int id);

    @Query("SELECT two FROM childReport WHERE id = :id")
    int getErrorsTwo(int id);

    @Query("SELECT three FROM childReport WHERE id = :id")
    int getErrorsThree(int id);

    @Query("SELECT four FROM childReport WHERE id = :id")
    int getErrorsFour(int id);

    @Query("SELECT five FROM childReport WHERE id = :id")
    int getErrorsFive(int id);

    @Query("SELECT six FROM childReport WHERE id = :id")
    int getErrorsSix(int id);

    @Query("SELECT seven FROM childReport WHERE id = :id")
    int getErrorsSeven(int id);

    @Query("SELECT eight FROM childReport WHERE id = :id")
    int getErrorsEight(int id);

    @Query("SELECT nine FROM childReport WHERE id = :id")
    int getErrorsNine(int id);

    @Query("SELECT zero FROM childReport WHERE id = :id")
    int getErrorsZero(int id);

    @Query("SELECT banana FROM childReport WHERE id = :id")
    int getErrorsBanana(int id);

    @Query("SELECT lemon FROM childReport WHERE id = :id")
    int getErrorsLemon(int id);

    @Query("SELECT corn FROM childReport WHERE id = :id")
    int getErrorsCorn(int id);

    @Query("SELECT grapefruit FROM childReport WHERE id = :id")
    int getErrorsGrapefruit(int id);

    @Query("SELECT watermelon FROM childReport WHERE id = :id")
    int getErrorsWatermelon(int id);

    @Query("SELECT strawberry FROM childReport WHERE id = :id")
    int getErrorsStrawberry(int id);

    @Query("SELECT apple FROM childReport WHERE id = :id")
    int getErrorsApple(int id);

    @Query("SELECT pepper FROM childReport WHERE id = :id")
    int getErrorsPepper(int id);

    @Query("SELECT tomato FROM childReport WHERE id = :id")
    int getErrorsTomato(int id);

    @Query("SELECT orange FROM childReport WHERE id = :id")
    int getErrorsOrange(int id);

    @Query("SELECT carrot FROM childReport WHERE id = :id")
    int getErrorsCarrot(int id);

    @Query("SELECT onion FROM childReport WHERE id = :id")
    int getErrorsOnion(int id);

    @Query("SELECT tangerine FROM childReport WHERE id = :id")
    int getErrorsTangerine(int id);

    @Query("SELECT eggplant FROM childReport WHERE id = :id")
    int getErrorsEggplant(int id);

    @Query("SELECT asparagus FROM childReport WHERE id = :id")
    int getErrorsAsparagus(int id);

    @Query("SELECT broccoli FROM childReport WHERE id = :id")
    int getErrorsBroccoli(int id);

    @Query("SELECT cucumber FROM childReport WHERE id = :id")
    int getErrorsCucumber(int id);

    @Query("SELECT pear FROM childReport WHERE id = :id")
    int getErrorsPear(int id);

    @Query("SELECT greenpea FROM childReport WHERE id = :id")
    int getErrorsGreenpea(int id);

    @Query("SELECT fennel FROM childReport WHERE id = :id")
    int getErrorsFennel(int id);

    @Query("SELECT potato FROM childReport WHERE id = :id")
    int getErrorsPotato(int id);

    //Setters
    @Query("UPDATE childReport SET progress = :progress WHERE id = :id")
    void setProgress(int id, String progress);

    @Query("UPDATE childReport SET a = :number WHERE id = :id")
    void setErrorsA(int id, int number);

    @Query("UPDATE childReport SET b = :number WHERE id = :id")
    void setErrorsB(int id, int number);

    @Query("UPDATE childReport SET c = :number WHERE id = :id")
    void setErrorsC(int id, int number);

    @Query("UPDATE childReport SET d = :number WHERE id = :id")
    void setErrorsD(int id, int number);

    @Query("UPDATE childReport SET e = :number WHERE id = :id")
    void setErrorsE(int id, int number);

    @Query("UPDATE childReport SET f = :number WHERE id = :id")
    void setErrorsF(int id, int number);

    @Query("UPDATE childReport SET g = :number WHERE id = :id")
    void setErrorsG(int id, int number);

    @Query("UPDATE childReport SET h = :number WHERE id = :id")
    void setErrorsH(int id, int number);

    @Query("UPDATE childReport SET i = :number WHERE id = :id")
    void setErrorsI(int id, int number);

    @Query("UPDATE childReport SET l = :number WHERE id = :id")
    void setErrorsL(int id, int number);

    @Query("UPDATE childReport SET m = :number WHERE id = :id")
    void setErrorsM(int id, int number);

    @Query("UPDATE childReport SET n = :number WHERE id = :id")
    void setErrorsN(int id, int number);

    @Query("UPDATE childReport SET o = :number WHERE id = :id")
    void setErrorsO(int id, int number);

    @Query("UPDATE childReport SET p = :number WHERE id = :id")
    void setErrorsP(int id, int number);

    @Query("UPDATE childReport SET q = :number WHERE id = :id")
    void setErrorsQ(int id, int number);

    @Query("UPDATE childReport SET r = :number WHERE id = :id")
    void setErrorsR(int id, int number);

    @Query("UPDATE childReport SET s = :number WHERE id = :id")
    void setErrorsS(int id, int number);

    @Query("UPDATE childReport SET t = :number WHERE id = :id")
    void setErrorsT(int id, int number);

    @Query("UPDATE childReport SET u = :number WHERE id = :id")
    void setErrorsU(int id, int number);

    @Query("UPDATE childReport SET v = :number WHERE id = :id")
    void setErrorsV(int id, int number);

    @Query("UPDATE childReport SET z = :number WHERE id = :id")
    void setErrorsZ(int id, int number);

    @Query("UPDATE childReport SET one = :number WHERE id = :id")
    void setErrorsOne(int id, int number);

    @Query("UPDATE childReport SET two = :number WHERE id = :id")
    void setErrorsTwo(int id, int number);

    @Query("UPDATE childReport SET three = :number WHERE id = :id")
    void setErrorsThree(int id, int number);

    @Query("UPDATE childReport SET four = :number WHERE id = :id")
    void setErrorsFour(int id, int number);

    @Query("UPDATE childReport SET five = :number WHERE id = :id")
    void setErrorsFive(int id, int number);

    @Query("UPDATE childReport SET six = :number WHERE id = :id")
    void setErrorsSix(int id, int number);

    @Query("UPDATE childReport SET seven = :number WHERE id = :id")
    void setErrorsSeven(int id, int number);

    @Query("UPDATE childReport SET eight = :number WHERE id = :id")
    void setErrorsEight(int id, int number);

    @Query("UPDATE childReport SET nine = :number WHERE id = :id")
    void setErrorsNine(int id, int number);

    @Query("UPDATE childReport SET zero = :number WHERE id = :id")
    void setErrorsZero(int id, int number);

    @Query("UPDATE childReport SET banana = :number WHERE id = :id")
    void setErrorsBanana(int id, int number);

    @Query("UPDATE childReport SET corn = :number WHERE id = :id")
    void setErrorsCorn(int id, int number);

    @Query("UPDATE childReport SET lemon = :number WHERE id = :id")
    void setErrorsLemon(int id, int number);

    @Query("UPDATE childReport SET grapefruit = :number WHERE id = :id")
    void setErrorsGrapefruit(int id, int number);

    @Query("UPDATE childReport SET watermelon = :number WHERE id = :id")
    void setErrorsWatermelon(int id, int number);

    @Query("UPDATE childReport SET strawberry = :number WHERE id = :id")
    void setErrorsStrawberry(int id, int number);

    @Query("UPDATE childReport SET apple = :number WHERE id = :id")
    void setErrorsApple(int id, int number);

    @Query("UPDATE childReport SET pepper = :number WHERE id = :id")
    void setErrorsPepper(int id, int number);

    @Query("UPDATE childReport SET tomato = :number WHERE id = :id")
    void setErrorsTomato(int id, int number);

    @Query("UPDATE childReport SET orange = :number WHERE id = :id")
    void setErrorsOrange(int id, int number);

    @Query("UPDATE childReport SET carrot = :number WHERE id = :id")
    void setErrorsCarrot(int id, int number);

    @Query("UPDATE childReport SET onion = :number WHERE id = :id")
    void setErrorsOnion(int id, int number);

    @Query("UPDATE childReport SET tangerine = :number WHERE id = :id")
    void setErrorsTangerine(int id, int number);

    @Query("UPDATE childReport SET eggplant = :number WHERE id = :id")
    void setErrorsEggplant(int id, int number);

    @Query("UPDATE childReport SET asparagus = :number WHERE id = :id")
    void setErrorsAsparagus(int id, int number);

    @Query("UPDATE childReport SET cucumber = :number WHERE id = :id")
    void setErrorsCucumber(int id, int number);

    @Query("UPDATE childReport SET broccoli = :number WHERE id = :id")
    void setErrorsBroccoli(int id, int number);

    @Query("UPDATE childReport SET pear = :number WHERE id = :id")
    void setErrorsPear(int id, int number);

    @Query("UPDATE childReport SET greenpea = :number WHERE id = :id")
    void setErrorsGreenpea(int id, int number);

    @Query("UPDATE childReport SET fennel = :number WHERE id = :id")
    void setErrorsFennel(int id, int number);

    @Query("UPDATE childReport SET potato = :number WHERE id = :id")
    void setErrorsPotato(int id, int number);

   }