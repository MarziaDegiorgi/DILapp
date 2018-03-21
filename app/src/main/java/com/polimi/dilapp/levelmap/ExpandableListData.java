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
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> objects = new ArrayList<>();
        objects.add("NOMI");
        objects.add("COLORI");
        objects.add("FORME");
        objects.add("COMPONI");

        List<String> numbers = new ArrayList<>();
        numbers.add("NUMERI");
        numbers.add("ALFABETO");
        numbers.add("PAROLE");
        numbers.add("DESCRIZIONE");

        expandableListDetail.put("OGGETTI E COLORI", objects);
        expandableListDetail.put("LETTERE E NUMERI", numbers);

        return expandableListDetail;
    }
    }
