package com.polimi.dilapp.levels;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.Assert.assertEquals;


public class GamePresenterTest {

    @Parameterized.Parameter(0)
    public String element;

    @Parameterized.Parameter(1)
    public Boolean result;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { { "apple" , false }, { "_1", true}, { "_12", true }, { "_apple" , true },
                {"strawberry", false}};
        return Arrays.asList(data);
    }


    @Test
    public void isItemMultiple() throws  Exception {
        for(Object o : data()) {

        }
    }


}