package com.polimi.dilapp;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GamePresenterTest {

    //dummy object
    private int dummy = 0;
    @Mock
    Context ActivityOneOne;

    @Test
    public void exampleTest(){
        dummy = 5;
    }
}
