package com.polimi.dilapp.data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.ContactsContract;

public class Child {
    private String name;
    private int age;
    private String id;
    private int level;
    private Bitmap photo;
    //TODO: Decide if add also an avatar or other information

    public Child(String name, int age){
        this.name=name;
        this.age= age;
        this.level = 0;
        //TODO: Generate a unique id to associate to the name
    }

    public void setName( String name) {
        this.name=name;
    }
    public void setPhoto (Bitmap photo) {this.photo=photo;}

    public String getName() {
        return this.name;
    }
    public void setAge( int age) {
        this.age=age;
    }

    public int getAge() {
        return this.age;
    }

    public String getId() {
       return this.id;
    }

    public int getLevel() {
        return this.level;
    }




}
