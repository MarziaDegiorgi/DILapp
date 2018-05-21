package com.polimi.dilapp.levelmap;

import org.junit.Before;
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
public class ExpandableListDataTest {
    @Before
    public void test(){
        PowerMockito.mockStatic(ExpandableListData.class);
    }

    @Test
    public void getData() throws Exception {
        Map<String, List<String>> map = new HashMap<>();
        List<String> parents = new ArrayList<>();
        parents.add("OGGETTI E COLORI");
        parents.add("LETTERE E NUMERI");
        parents.add("LOGICA");

        List<String> objects = new ArrayList<>();
        objects.add("NOMI");
        objects.add("COLORI");
        objects.add("FORME");

        List<String> numbers = new ArrayList<>();
        numbers.add("NUMERI");
        numbers.add("ALFABETO");
        numbers.add("PAROLE");

        List<String> logic = new ArrayList<>();
        logic.add("CONTIAMO INSIEME");
        logic.add("CUCINA CON NOSCO");
        map.put(parents.get(0), objects);
        map.put(parents.get(1), numbers);
        map.put(parents.get(2), logic);

        Object object = ExpandableListData.getData();

        assertEquals(map, object);
    }

}