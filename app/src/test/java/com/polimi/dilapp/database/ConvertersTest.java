package com.polimi.dilapp.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Room.class})
public class ConvertersTest {

    @Before
    public void test(){
        PowerMockito.mockStatic(Room.class);
        appDatabase = new AppDatabase() {
            @Override
            public ChildDao childDao() {
                return null;
            }

            @Override
            public ReportOneOneDao reportOneOneDao() {
                return null;
            }

            @Override
            public ReportOneTwoDao reportOneTwoDao() {
                return null;
            }

            @Override
            public ReportOneThreeDao reportOneThreeDao() {
                return null;
            }

            @Override
            public ReportTwoOneDao reportTwoOneDao() {
                return null;
            }

            @Override
            public ReportTwoTwoDao reportTwoTwoDao() {
                return null;
            }

            @Override
            public ReportTwoThreeDao reportTwoThreeDao() {
                return null;
            }

            @Override
            public ReportThreeOneDao reportThreeOneDao() {
                return null;
            }

            @Override
            public ReportThreeTwoDao reportThreeTwoDao() {
                return null;
            }

            @Override
            protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
                return null;
            }

            @Override
            protected InvalidationTracker createInvalidationTracker() {
                return null;
            }
        };
    }

    @Mock
    private AppDatabase appDatabase;

    
}
