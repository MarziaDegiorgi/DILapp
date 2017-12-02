package com.polimi.dilapp.data;


import java.util.ArrayList;

public class ListOfChildren {

    private static ArrayList<Child> list = new ArrayList<>();

    private static final ListOfChildren ourInstance = new ListOfChildren();

    public static ListOfChildren getInstance() {
        return ourInstance;
    }

    private ListOfChildren() {
    }



    public static void addChild (Child child) {
        list.add(child);
    }
    public static int length () {
        return list.size();
    }

}
