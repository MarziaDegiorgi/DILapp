package com.polimi.dilapp.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

@Dao
public interface ReportTwoTwoDao {
    
    @Query("SELECT a FROM reportTwoTwo WHERE id = :id")
    int getErrorsA(int id);

    @Query("SELECT b FROM reportTwoTwo WHERE id = :id")
    int getErrorsB(int id);

    @Query("SELECT c FROM reportTwoTwo WHERE id = :id")
    int getErrorsC(int id);

    @Query("SELECT d FROM reportTwoTwo WHERE id = :id")
    int getErrorsD(int id);

    @Query("SELECT e FROM reportTwoTwo WHERE id = :id")
    int getErrorsE(int id);

    @Query("SELECT f FROM reportTwoTwo WHERE id = :id")
    int getErrorsF(int id);

    @Query("SELECT g FROM reportTwoTwo WHERE id = :id")
    int getErrorsG(int id);

    @Query("SELECT h FROM reportTwoTwo WHERE id = :id")
    int getErrorsH(int id);

    @Query("SELECT i FROM reportTwoTwo WHERE id = :id")
    int getErrorsI(int id);

    @Query("SELECT l FROM reportTwoTwo WHERE id = :id")
    int getErrorsL(int id);

    @Query("SELECT m FROM reportTwoTwo WHERE id = :id")
    int getErrorsM(int id);

    @Query("SELECT n FROM reportTwoTwo WHERE id = :id")
    int getErrorsN(int id);

    @Query("SELECT o FROM reportTwoTwo WHERE id = :id")
    int getErrorsO(int id);

    @Query("SELECT p FROM reportTwoTwo WHERE id = :id")
    int getErrorsP(int id);

    @Query("SELECT q FROM reportTwoTwo WHERE id = :id")
    int getErrorsQ(int id);

    @Query("SELECT r FROM reportTwoTwo WHERE id = :id")
    int getErrorsR(int id);

    @Query("SELECT s FROM reportTwoTwo WHERE id = :id")
    int getErrorsS(int id);

    @Query("SELECT t FROM reportTwoTwo WHERE id = :id")
    int getErrorsT(int id);

    @Query("SELECT u FROM reportTwoTwo WHERE id = :id")
    int getErrorsU(int id);

    @Query("SELECT v FROM reportTwoTwo WHERE id = :id")
    int getErrorsV(int id);

    @Query("SELECT z FROM reportTwoTwo WHERE id = :id")
    int getErrorsZ(int id);

    @Query("UPDATE reportTwoTwo SET a = :number WHERE id = :id")
    void setErrorsA(int id, int number);

    @Query("UPDATE reportTwoTwo SET b = :number WHERE id = :id")
    void setErrorsB(int id, int number);

    @Query("UPDATE reportTwoTwo SET c = :number WHERE id = :id")
    void setErrorsC(int id, int number);

    @Query("UPDATE reportTwoTwo SET d = :number WHERE id = :id")
    void setErrorsD(int id, int number);

    @Query("UPDATE reportTwoTwo SET e = :number WHERE id = :id")
    void setErrorsE(int id, int number);

    @Query("UPDATE reportTwoTwo SET f = :number WHERE id = :id")
    void setErrorsF(int id, int number);

    @Query("UPDATE reportTwoTwo SET g = :number WHERE id = :id")
    void setErrorsG(int id, int number);

    @Query("UPDATE reportTwoTwo SET h = :number WHERE id = :id")
    void setErrorsH(int id, int number);

    @Query("UPDATE reportTwoTwo SET i = :number WHERE id = :id")
    void setErrorsI(int id, int number);

    @Query("UPDATE reportTwoTwo SET l = :number WHERE id = :id")
    void setErrorsL(int id, int number);

    @Query("UPDATE reportTwoTwo SET m = :number WHERE id = :id")
    void setErrorsM(int id, int number);

    @Query("UPDATE reportTwoTwo SET n = :number WHERE id = :id")
    void setErrorsN(int id, int number);

    @Query("UPDATE reportTwoTwo SET o = :number WHERE id = :id")
    void setErrorsO(int id, int number);

    @Query("UPDATE reportTwoTwo SET p = :number WHERE id = :id")
    void setErrorsP(int id, int number);

    @Query("UPDATE reportTwoTwo SET q = :number WHERE id = :id")
    void setErrorsQ(int id, int number);

    @Query("UPDATE reportTwoTwo SET r = :number WHERE id = :id")
    void setErrorsR(int id, int number);

    @Query("UPDATE reportTwoTwo SET s = :number WHERE id = :id")
    void setErrorsS(int id, int number);

    @Query("UPDATE reportTwoTwo SET t = :number WHERE id = :id")
    void setErrorsT(int id, int number);

    @Query("UPDATE reportTwoTwo SET u = :number WHERE id = :id")
    void setErrorsU(int id, int number);

    @Query("UPDATE reportTwoTwo SET v = :number WHERE id = :id")
    void setErrorsV(int id, int number);

    @Query("UPDATE reportTwoTwo SET z = :number WHERE id = :id")
    void setErrorsZ(int id, int number);

}
