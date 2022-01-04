package com.example.emargementnfc;

public class Student {

    private String id;
    private String name;

    Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
}
