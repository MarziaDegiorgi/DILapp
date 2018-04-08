package com.polimi.dilapp.levelmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fill the Expandable List in the view to change level
 */
public class ExpandableListData {

    public static Map<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
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

        expandableListDetail.put(parents.get(0), objects);
        expandableListDetail.put(parents.get(1), numbers);
        expandableListDetail.put(parents.get(2), logic);

        return expandableListDetail;
    }
    }
