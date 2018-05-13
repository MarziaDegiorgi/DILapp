package com.polimi.dilapp.report;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaCodec;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.AppDatabase_Impl;
import com.polimi.dilapp.database.ChildDao;
import com.polimi.dilapp.database.DatabaseInitializer;

import junit.framework.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AppDatabase.class, Log.class, DatabaseInitializer.class, Pattern.class, Matcher.class, ContextCompat.class})
public class ReportSettingsPresenterTest {

    @Before
    public void test(){

        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(Pattern.class);
        PowerMockito.mockStatic(ContextCompat.class);
        PowerMockito.mockStatic(Matcher.class);

        when(Log.i(any(String.class), any(String.class))).thenReturn(1);
        when(AppDatabase.getAppDatabase(any(Context.class))).thenReturn(appDatabaseImpl);
        when(appDatabaseImpl.childDao()).thenReturn(childDao);

        reportSettingsPresenter = new ReportSettingsPresenter(view);
    }

    @Mock
    private ReportSettingsPresenter reportSettingsPresenter;

    @Mock
    private IReportSettings.View view;

    @Mock
    private AppDatabase appDatabase;

    @Mock
    private AppDatabase_Impl appDatabaseImpl;

    @Mock
    private ChildDao childDao;

    @Mock
    private Color color;

    @Mock
    private String string;

    @Mock
    private DatabaseInitializer databaseInitializer;

    @Mock
    private Pattern pattern;

    @Mock
    private Matcher matcher;

    @Mock
    private Button button;

    @Mock
    private EditText editText;

    @Mock
    private int num;

    @Mock
    private LinearLayout linearLayout;

    @Mock
    private Context context;

    @Mock
    private ContextCompat contextCompat;

    //Here we test enableAutoRepo()
  /* @Test
    public void enableAutoRepoTest(){
 //non riesco a mockare getEmail.
        when(DatabaseInitializer.getEmail(appDatabase)).thenReturn(string);
        when(DatabaseInitializer.getEmail(appDatabase).equals(string)).thenReturn(false);
        reportSettingsPresenter.enableAutoRepo(string);

        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.enableAutoRepo(appDatabase);

    }

    @Test
    public void enableAutoRepoTestIf(){
        //non riesco a mockare getEmail.
        databaseInitializer = new DatabaseInitializer();
        when(DatabaseInitializer.getEmail(appDatabase)).thenReturn(string);
        when(DatabaseInitializer.getEmail(appDatabase).equals(string)).thenReturn(true);
        reportSettingsPresenter.enableAutoRepo(string);

        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.enableAutoRepo(appDatabase);
        DatabaseInitializer.setEmail(appDatabase, string);

    }*/



    //Here we test disableAutoRepo()
   @Test
    public void disableAutoRepoTest(){
       reportSettingsPresenter.disableAutoRepo();

       verify(databaseInitializer, Mockito.times(1));
       DatabaseInitializer.disableAutoRepo(appDatabase);
   }


   //Here we test isAutoRepoEnabled()
    @Test
    public void isAutoRepoEnabledTest(){
       reportSettingsPresenter.isAutoRepoEnabled();

        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.isAutoRepoEnabled(appDatabase);
    }

    //Here we test isEmailValid()
    @Test
    public void isEmailNotValidTest(){
        String email = "ciao ij";
        ReportSettingsPresenter.isEmailValid(email);

        Assert.assertEquals(false, matcher.matches());
    }


    //Here we test checkRepo()
   /* @Test
   /* public void checkRepoTest(){
        when(DatabaseInitializer.getEmail(appDatabase)).thenReturn(string);
        when(ContextCompat.getColor(view.getContext(), num)).thenReturn(num);
       when(DatabaseInitializer.isEmailSet(appDatabase)).thenReturn(true);

       reportSettingsPresenter.checkRepo(button, editText);

       verify(reportSettingsPresenter, Mockito.times(1)).emailAlreadySet(button, editText);
    }


   //Here we test setAutoRepo()
   @Test
    public void setAutoRepoTest(){
        when(DatabaseInitializer.getEmail(appDatabase)).thenReturn(string);
        when(ContextCompat.getColor(view.getContext(), num)).thenReturn(num);

        when(reportSettingsPresenter.isAutoRepoEnabled()).thenReturn(true);

        reportSettingsPresenter.setAutoRepo(button, linearLayout);

        verify(reportSettingsPresenter, Mockito.times(1)).checkRepo(button, editText);
    }*/



   //Here we test emailToCheck()
    @Test
    public void emailToCheckTest(){
        when(DatabaseInitializer.getEmail(appDatabase)).thenReturn(string);
        when(ContextCompat.getColor(view.getContext(), num)).thenReturn(num);

        reportSettingsPresenter.emailtoSet(button, editText);


        verify(editText, Mockito.times(1)).setClickable(true);
        verify(editText, Mockito.times(1)).setEnabled(true);
        verify(editText, Mockito.times(1)).setTextColor(num);
        verify(editText, Mockito.times(1)).setBackgroundColor(num);

        verify(button, Mockito.times(1)).setOnClickListener(any(View.OnClickListener.class));


        verify(databaseInitializer,Mockito.times(1));
        DatabaseInitializer.setEmail(appDatabase, string);

        //TODO: try to test the if brach: the problem is that I'm not able to mock the internal call to isEmail valid,
        //from the manual: final things cannot be verified/stubbed
    }



    //Here we test emailAlreadySet()
    @Test
    public void emailAlreadySetTest(){

        when(DatabaseInitializer.getEmail(appDatabase)).thenReturn(string);
        when(ContextCompat.getColor(view.getContext(), num)).thenReturn(num);

        reportSettingsPresenter.emailAlreadySet(button, editText);

        verify(editText, Mockito.times(1)).setClickable(false);
        verify(editText, Mockito.times(1)).setEnabled(false);
        //verify(editText, Mockito.times(1)).setTextColor(num);
        verify(editText, Mockito.times(1)).setText(null);

        verify(button, Mockito.times(1)).setOnClickListener(any(View.OnClickListener.class));

        verify(databaseInitializer,Mockito.times(1));
        DatabaseInitializer.setEmail(appDatabase, string);

    }
}
