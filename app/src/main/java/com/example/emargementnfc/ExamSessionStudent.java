package com.example.emargementnfc;

public class ExamSessionStudent {
    private int id_examsession;
    private int id_student;
    private String arriveHour;
    private String quitHour;

    ExamSessionStudent(int id_examsession, int id_student, String arriveHour, String quitHour) {
        this.id_examsession = id_examsession;
        this.id_student = id_student;
        this.arriveHour = arriveHour;
        this.quitHour = quitHour;
    }

    public int getIdExamsession() { return this.id_examsession; }
    public int getIdStudent() { return this.id_student; }
    public String getArriveHour() {
        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //String s = sdf.format(this.arriveHour);
        return this.arriveHour;
    }
    public String getQuitHour() {
        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //String s = sdf.format(this.quitHour);
        return this.quitHour;
    }

}
