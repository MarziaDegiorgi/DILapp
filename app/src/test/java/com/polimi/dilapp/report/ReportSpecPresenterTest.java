package com.polimi.dilapp.report;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.LinearLayout;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.ChildDao;
import com.polimi.dilapp.database.DatabaseInitializer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AppDatabase.class, DatabaseInitializer.class, Pattern.class, Matcher.class, ContextCompat.class,
        Context.class,Room.class, AsyncTask.class, ReportSpecPresenter.class, Runnable.class,
        ReportSpecPresenter.TakeScreenshot.class})
public class ReportSpecPresenterTest {
    @Before
    public void test(){

        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(Pattern.class);
        PowerMockito.mockStatic(ContextCompat.class);
        PowerMockito.mockStatic(Matcher.class);
        PowerMockito.mockStatic(AsyncTask.class);
        PowerMockito.mockStatic(ReportSpecPresenter.class);

        when(AppDatabase.getAppDatabase(any(Context.class))).thenReturn(appDatabaseImpl);
        when(appDatabaseImpl.childDao()).thenReturn(childDao);
        when(DatabaseInitializer.getCurrentPlayer(appDatabaseImpl)).thenReturn(1);

        reportPresenter = new ReportSpecPresenter(view);
    }
    @Mock
    private LinearLayout mockedLayout;
    @Mock
    private ProgressDialog progress;
    @Mock
    private ReportSpecPresenter reportPresenter;
    @Mock
    private AppDatabase appDatabaseImpl;
    @Mock
    private ChildDao childDao;
    @Mock
    private IReport.View view;
    @Mock
    private AsyncTask asyncTask;
    @Mock
    private Runnable runnable;
    @Mock
    private ReportSpecPresenter.TakeScreenshot takeScreenshot;

    @Test
    public void onDestroy() throws Exception {
        reportPresenter.onDestroy();
        assertNull(reportPresenter.getView());
    }

    @Test
    public void takeScreenshot() throws Exception {
        //TODO: Unable to mock ASyncTask calling to execute() method
    }

}