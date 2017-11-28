package com.polimi.dilapp.model;

public class Child {
    private String name;
    private int age;
    private String id;
    private int level;
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
