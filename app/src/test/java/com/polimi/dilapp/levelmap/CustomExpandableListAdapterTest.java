package com.polimi.dilapp.levelmap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareEverythingForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class CustomExpandableListAdapterTest {
    @Before
    public void test(){

        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();


        list1.add("subtitle1");
        list1.add("subtitle2");
        list1.add("subtitle3");
        list2.add("subtitle4");
        list2.add("subtitle5");
        list2.add("subtitle6");

        map.put("title1",list1);
        map.put("title2", list2);

        titles.add("title1");
        titles.add("title2");

        customExpandableListAdapter = new CustomExpandableListAdapter(context,titles,map );

    }
    @Mock
    private CustomExpandableListAdapter customExpandableListAdapter;
    @Mock
    private Context context;

    @Test
    public void getChild() throws Exception {
        Object object = customExpandableListAdapter.getChild(0,0);

        assertEquals("subtitle1", object);

        object = customExpandableListAdapter.getChild(1,0);

        assertEquals("subtitle4", object);

    }

    @Test
    public void getChildId() throws Exception {
        long object = customExpandableListAdapter.getChildId(0,2);

        assertEquals(2, object);
    }

    @Test
    public void getChildrenCount() throws Exception {
        int count = customExpandableListAdapter.getChildrenCount(0);
        assertEquals(3, count);
        count = customExpandableListAdapter.getChildrenCount(1);
        assertEquals(3, count);
    }

    @Test
    public void getGroup() throws Exception {
        Object object = customExpandableListAdapter.getGroup(0);

        assertEquals("title1", object);
    }

    @Test
    public void getGroupCount() throws Exception {
        int size = customExpandableListAdapter.getGroupCount();

        assertEquals(2, size);
    }

    @Test
    public void getGroupId() throws Exception {
        long id = customExpandableListAdapter.getGroupId(1);
        assertEquals(1, id);
    }

    @Test
    public void hasStableIds() throws Exception {
        assertFalse(customExpandableListAdapter.hasStableIds());
    }

    //TODO: Test to complete once that levels are blocked
    @Ignore
    public void isChildSelectable() throws Exception {
        assertTrue(customExpandableListAdapter.isChildSelectable(0,0));
    }

}