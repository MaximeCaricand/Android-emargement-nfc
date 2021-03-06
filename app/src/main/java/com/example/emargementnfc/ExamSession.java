package com.example.emargementnfc;

public class ExamSession {
    private int id;
    private String name;
    private String date;
    private String startHour;
    private String endHour;

    ExamSession(String name, String date, String startHour, String endHour) {
        this(-1, name, date, startHour, endHour);
    }

    ExamSession(int id, String name, String date, String startHour, String endHour) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getDate() { return this.date; }
    public String getStartHour() { return this.startHour; }
    public String getEndHour() { return this.endHour; }
}
