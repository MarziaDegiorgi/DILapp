package com.polimi.dilapp.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Gson.class})
public class ConvertersTest {

    @Before
    public void test(){
        PowerMockito.mockStatic(Gson.class);

    }


    @Mock
    private Converters converters;

    @Mock
    private String string;

    @Mock
    private Type type;

    @Mock
    Gson gson;

    @Mock
    private ArrayList<String> list;

    //Here we test fromString()
    @Test
    public void fromStringTest(){
        Converters.fromString(string);
        PowerMockito.doReturn(gson.fromJson(string, type));
    }

    //Here we test fromArrayList()
    @Test
    public void fromArrayList(){

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("c");
        arrayList.add("a");

        when(gson.toJson(list)).thenReturn(string);
        converters.fromArrayList(arrayList);
        PowerMockito.doReturn(gson);
    }
}
