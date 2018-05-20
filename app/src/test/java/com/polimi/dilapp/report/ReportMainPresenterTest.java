package com.polimi.dilapp.report;


import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.ChildDao;
import com.polimi.dilapp.database.DatabaseInitializer;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//TODO: SOLVE PROBLEM AND CREATE TEST
@RunWith(PowerMockRunner.class)
@PrepareForTest({MenuItem.class, Intent.class})
public class ReportMainPresenterTest {
    @Mock
    private IReport.View view;

    @Mock
    private ChildDao childDao;

    @Mock
    private ReportMainPresenter reportMainPresenter;

    @Mock
    private AppDatabase appDatabase;

    @Mock
    private int playerId;

    @Mock
    private DatabaseInitializer databaseInitializer;

    @Mock
    private MenuItem menuItem;

    @Mock
    private int num;

    @Mock
    private Context context;

    @Before
    public void test(){
        PowerMockito.mockStatic(MenuItem.class);
        PowerMockito.mockStatic(Intent.class);
        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(Pattern.class);
        PowerMockito.mockStatic(ContextCompat.class);
        PowerMockito.mockStatic(Matcher.class);
        PowerMockito.mockStatic(Room.class);
        PowerMockito.mockStatic(Context.class);

        when(AppDatabase.getAppDatabase(view.getContext())).thenReturn(appDatabase);
        when(Room.databaseBuilder(view.getContext(),AppDatabase.class, "child-database")
                .allowMainThreadQueries().build()).thenReturn(any(AppDatabase.class));
        when(appDatabase.childDao()).thenReturn(childDao);
        when(Log.i(any(String.class), any(String.class))).thenReturn(1);
        when(DatabaseInitializer.getCurrentPlayer(appDatabase)).thenReturn(playerId);

        reportMainPresenter = new ReportMainPresenter(view);
    }

    //Here we test onDestroy()
    @Test
    public void onDestroyTest(){
        reportMainPresenter.onDestroy();

        assertNull(reportMainPresenter.getView());
    }



}
