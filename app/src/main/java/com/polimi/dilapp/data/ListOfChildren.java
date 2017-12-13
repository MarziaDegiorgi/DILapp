package com.polimi.dilapp.data;


import java.util.ArrayList;

public class ListOfChildren {

    private static ArrayList<Child> list = new ArrayList<>();
    /*private DatabaseReference database;
    private static DatabaseReference children;*/

    private static final ListOfChildren ourInstance = new ListOfChildren();

    public static ListOfChildren getInstance() {
        return ourInstance;
    }

    private ListOfChildren() {
        /*database= FirebaseDatabase.getInstance().getReference();
        children = database.child("children");*/

    }

    public static void addChild (Child child) {
        list.add(child);
        /*DatabaseReference account = children.child(child.getName());
        account.setValue(child);*/
    }

    public static int length () {
        return list.size();
    }

    public static Child getElement(int i){
        return list.get(i);
    }
    public static Child getElementPerId(int id){
        Child selectedChild = new Child(null, 0);
        for(Child child : list) {
            if (child.getId() == id) {
                selectedChild = child;
            }else{
                selectedChild = null;
            }
        }
        return selectedChild;
    }
    public static Child getCurrentPlayer() {
        Child currentPlayer = new Child(null, 0);
        for(Child child : list) {
            if (child.getCurrentPlayer()) {
                currentPlayer = child;
            }
        }
        return currentPlayer;
    }

    /*public static void getPhoto(int id) {

        Query child = children.equalTo(id);

        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Bitmap photo = post.child("photo").getValue(Bitmap.class);
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }*/
}
