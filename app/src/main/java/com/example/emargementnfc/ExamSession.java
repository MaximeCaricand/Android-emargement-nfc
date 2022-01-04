package com.example.emargementnfc;

public class ExamSession {
    private int id;
    private String name;
    private String date;
    private String startHour;
    private String endHour;

    ExamSession(int id, String name, String date, String startHour, String endHour) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getDate() {
        //SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
        //String s = sdf.format(this.date);
        return this.date;
    }
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
