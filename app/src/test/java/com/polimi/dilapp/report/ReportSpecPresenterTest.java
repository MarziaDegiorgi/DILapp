package com.polimi.dilapp.report;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.ChildDao;
import com.polimi.dilapp.database.DatabaseInitializer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

//TODO: SOLVE PROBLEM AND CREATE TEST
public class ReportSpecPresenterTest {
    @Before
    public void test(){

        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(Pattern.class);
        PowerMockito.mockStatic(ContextCompat.class);
        PowerMockito.mockStatic(Matcher.class);

        when(AppDatabase.getAppDatabase(any(Context.class))).thenReturn(appDatabaseImpl);
        when(appDatabaseImpl.childDao()).thenReturn(childDao);
        when(DatabaseInitializer.getCurrentPlayer(appDatabaseImpl)).thenReturn(1);

        reportPresenter = new ReportSpecPresenter(view);
    }

    @Mock
    private ReportSpecPresenter reportPresenter;
    @Mock
    private AppDatabase appDatabaseImpl;
    @Mock
    private ChildDao childDao;
    @Mock
    private IReport.View view;


    public void onDestroy() throws Exception {
    }

    @Test
    public void takeScreenshot() throws Exception {
    }

}