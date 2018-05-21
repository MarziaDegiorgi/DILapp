package com.polimi.dilapp.main;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class SlideAdapterTest {
    @Before
    public void setUp(){
        slideAdapter = new SlideAdapter(context);

    }
    @Mock
    private SlideAdapter slideAdapter;
    @Mock
    private Context context;

    @Test
    public void getCount() throws Exception {
        assertEquals(4, slideAdapter.getCount());
    }
}