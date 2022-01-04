package com.example.emargementnfc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExamSession {
    private int id;
    private String name;
    private String startHour;
    private String endHour;

    ExamSession(int id, String name, String startHour, String endHour) {
        this.id = id;
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getStartHour() {
        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //String s = sdf.format(this.startHour);
        return this.startHour;
    }
    public String getEndHour() {
        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //String s = sdf.format(this.endHour);
        return this.endHour;
    }

}
