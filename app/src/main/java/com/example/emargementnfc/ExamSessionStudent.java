package com.example.emargementnfc;

public class ExamSessionStudent {
    private int id_examsession;
    private String id_student;
    private String arriveHour;
    private String quitHour;

    ExamSessionStudent(int id_examsession, String id_student, String arriveHour, String quitHour) {
        this.id_examsession = id_examsession;
        this.id_student = id_student;
        this.arriveHour = arriveHour;
        this.quitHour = quitHour;
    }

    ExamSessionStudent(int id_examsession, String id_student, String arriveHour) {
        this(id_examsession, id_student, arriveHour, "N/A");
    }

    public int getIdExamsession() { return this.id_examsession; }
    public String getIdStudent() { return this.id_student; }
    public String getArriveHour() {
        return this.arriveHour;
    }
    public String getQuitHour() {
        return this.quitHour;
    }

}
