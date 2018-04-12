package com.polimi.dilapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {ChildEntity.class, ReportOneOneEntity.class, ReportOneTwoEntity.class, ReportOneThreeEntity.class,
ReportTwoOneEntity.class, ReportTwoTwoEntity.class, ReportTwoThreeEntity.class, ReportThreeOneEntity.class, ReportThreeTwoEntity.class}, version = 6 )
@TypeConverters({Converters.class})

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ChildDao childDao();
    public abstract ReportOneOneDao reportOneOneDao();
    public abstract ReportOneTwoDao reportOneTwoDao();
    public abstract ReportOneThreeDao reportOneThreeDao();
    public abstract ReportTwoOneDao reportTwoOneDao();
    public abstract ReportTwoTwoDao reportTwoTwoDao();
    public abstract ReportTwoThreeDao reportTwoThreeDao();
    public abstract ReportThreeOneDao reportThreeOneDao();
    public abstract ReportThreeTwoDao reportThreeTwoDao();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "child-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
