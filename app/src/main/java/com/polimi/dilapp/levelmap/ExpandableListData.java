package com.polimi.dilapp.levelmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Fill the Expandable List in the view to change level
 */

//TODO: take data from database
public class ExpandableListData {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> objects = new ArrayList<String>();
        objects.add("COLORI");
        objects.add("NOMI");
        objects.add("FORME");
        objects.add("COMPONI");


        List<String> numbers = new ArrayList<String>();
        numbers.add("NUMERI");
        numbers.add("ALFABETO");
        numbers.add("PAROLE");
        numbers.add("DESCRIZIONE");

        List<String> logic = new ArrayList<String>();
        logic.add("IMPARARE A CONTARE");
        logic.add("LISTA DELLA SPESA");
        logic.add("IN CUCINA CON PATTY");


        expandableListDetail.put("OGGETTI E COLORI", objects);
        expandableListDetail.put("LETTERE E NUMERI", numbers);
        expandableListDetail.put("LOGICA", logic);

        return expandableListDetail;
    }

    }
