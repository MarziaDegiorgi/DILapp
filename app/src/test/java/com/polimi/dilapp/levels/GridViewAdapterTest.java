package com.polimi.dilapp.levels;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
public class GridViewAdapterTest {
    @Before
    public void setUp(){

       gridViewAdapter = new GridViewAdapter(context, 1);
    }
    @Mock
    private GridViewAdapter gridViewAdapter;
    @Mock
    private Context context;

    @Test
    public void getCount() throws Exception {
        gridViewAdapter.clearImageResources();
        gridViewAdapter.addImageResource(1);
        assertEquals(1, gridViewAdapter.getCount());
    }

    @Test
    public void getItem() throws Exception {
        assertNull(gridViewAdapter.getItem(any(Integer.class)));
    }

    @Test
    public void getItemId() throws Exception {
        assertEquals(0, gridViewAdapter.getItemId(any(Integer.class)));
    }

    @Test
    public void addImageResource() throws Exception {
        gridViewAdapter.clearImageResources();
        gridViewAdapter.addImageResource(1);
        gridViewAdapter.addImageResource(2);
        gridViewAdapter.addImageResource(3);

        assertEquals(3, gridViewAdapter.getCount());
    }

    @Test
    public void clearImageResources() throws Exception {
        gridViewAdapter.clearImageResources();
        assertEquals(0, gridViewAdapter.getCount());
    }

}