package com.polimi.dilapp.model;

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
        objects.add("Colori");
        objects.add("Nomi");
        objects.add("Forme");
        objects.add("Componi");


        List<String> numbers = new ArrayList<String>();
        numbers.add("Numeri");
        numbers.add("Alfabeto");
        numbers.add("Parole");
        numbers.add("Descrizione");

        List<String> logic = new ArrayList<String>();
        logic.add("Imparare a contare");
        logic.add("Lista della spesa");
        logic.add(" In cucina con Patty");



        expandableListDetail.put("LOGICA", logic);
        expandableListDetail.put("LETTERE E NUMERI", numbers);
        expandableListDetail.put("OGGETTI E COLORI", objects);

        return expandableListDetail;
    }

    }
