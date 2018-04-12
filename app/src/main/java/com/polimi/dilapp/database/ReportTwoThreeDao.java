package com.polimi.dilapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ReportTwoThreeDao {

    @Insert(onConflict = IGNORE)
    void insertReport(ReportTwoThreeEntity reportTwoThreeEntity);

    @Query("DELETE FROM reportTwoThree WHERE id = :id")
    void delete(int id);

    @Query("SELECT a FROM reportTwoThree WHERE id = :id")
    int getErrorsA(int id);

    @Query("SELECT b FROM reportTwoThree WHERE id = :id")
    int getErrorsB(int id);

    @Query("SELECT c FROM reportTwoThree WHERE id = :id")
    int getErrorsC(int id);

    @Query("SELECT d FROM reportTwoThree WHERE id = :id")
    int getErrorsD(int id);

    @Query("SELECT e FROM reportTwoThree WHERE id = :id")
    int getErrorsE(int id);

    @Query("SELECT f FROM reportTwoThree WHERE id = :id")
    int getErrorsF(int id);

    @Query("SELECT g FROM reportTwoThree WHERE id = :id")
    int getErrorsG(int id);

    @Query("SELECT h FROM reportTwoThree WHERE id = :id")
    int getErrorsH(int id);

    @Query("SELECT i FROM reportTwoThree WHERE id = :id")
    int getErrorsI(int id);

    @Query("SELECT l FROM reportTwoThree WHERE id = :id")
    int getErrorsL(int id);

    @Query("SELECT m FROM reportTwoThree WHERE id = :id")
    int getErrorsM(int id);

    @Query("SELECT n FROM reportTwoThree WHERE id = :id")
    int getErrorsN(int id);

    @Query("SELECT o FROM reportTwoThree WHERE id = :id")
    int getErrorsO(int id);

    @Query("SELECT p FROM reportTwoThree WHERE id = :id")
    int getErrorsP(int id);

    @Query("SELECT q FROM reportTwoThree WHERE id = :id")
    int getErrorsQ(int id);

    @Query("SELECT r FROM reportTwoThree WHERE id = :id")
    int getErrorsR(int id);

    @Query("SELECT s FROM reportTwoThree WHERE id = :id")
    int getErrorsS(int id);

    @Query("SELECT t FROM reportTwoThree WHERE id = :id")
    int getErrorsT(int id);

    @Query("SELECT u FROM reportTwoThree WHERE id = :id")
    int getErrorsU(int id);

    @Query("SELECT v FROM reportTwoThree WHERE id = :id")
    int getErrorsV(int id);

    @Query("SELECT z FROM reportTwoThree WHERE id = :id")
    int getErrorsZ(int id);

    @Query("UPDATE reportTwoThree SET a = :number WHERE id = :id")
    void setErrorsA(int id, int number);

    @Query("UPDATE reportTwoThree SET b = :number WHERE id = :id")
    void setErrorsB(int id, int number);

    @Query("UPDATE reportTwoThree SET c = :number WHERE id = :id")
    void setErrorsC(int id, int number);

    @Query("UPDATE reportTwoThree SET d = :number WHERE id = :id")
    void setErrorsD(int id, int number);

    @Query("UPDATE reportTwoThree SET e = :number WHERE id = :id")
    void setErrorsE(int id, int number);

    @Query("UPDATE reportTwoThree SET f = :number WHERE id = :id")
    void setErrorsF(int id, int number);

    @Query("UPDATE reportTwoThree SET g = :number WHERE id = :id")
    void setErrorsG(int id, int number);

    @Query("UPDATE reportTwoThree SET h = :number WHERE id = :id")
    void setErrorsH(int id, int number);

    @Query("UPDATE reportTwoThree SET i = :number WHERE id = :id")
    void setErrorsI(int id, int number);

    @Query("UPDATE reportTwoThree SET l = :number WHERE id = :id")
    void setErrorsL(int id, int number);

    @Query("UPDATE reportTwoThree SET m = :number WHERE id = :id")
    void setErrorsM(int id, int number);

    @Query("UPDATE reportTwoThree SET n = :number WHERE id = :id")
    void setErrorsN(int id, int number);

    @Query("UPDATE reportTwoThree SET o = :number WHERE id = :id")
    void setErrorsO(int id, int number);

    @Query("UPDATE reportTwoThree SET p = :number WHERE id = :id")
    void setErrorsP(int id, int number);

    @Query("UPDATE reportTwoThree SET q = :number WHERE id = :id")
    void setErrorsQ(int id, int number);

    @Query("UPDATE reportTwoThree SET r = :number WHERE id = :id")
    void setErrorsR(int id, int number);

    @Query("UPDATE reportTwoThree SET s = :number WHERE id = :id")
    void setErrorsS(int id, int number);

    @Query("UPDATE reportTwoThree SET t = :number WHERE id = :id")
    void setErrorsT(int id, int number);

    @Query("UPDATE reportTwoThree SET u = :number WHERE id = :id")
    void setErrorsU(int id, int number);

    @Query("UPDATE reportTwoThree SET v = :number WHERE id = :id")
    void setErrorsV(int id, int number);

    @Query("UPDATE reportTwoThree SET z = :number WHERE id = :id")
    void setErrorsZ(int id, int number);
}
