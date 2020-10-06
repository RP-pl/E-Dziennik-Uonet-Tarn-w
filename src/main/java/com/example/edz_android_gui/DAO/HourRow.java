package com.example.edz_android_gui.DAO;

public class HourRow {
    private final String lesson;
    private final String duration;
    private final String mon;
    private final String tue;
    private final String wen;
    private final String thu;
    private final String fri;
    public HourRow(String lesson, String duration, String mon, String tue, String wen, String thu, String fri){
        this.lesson = lesson;
        this.duration = duration;
        this.mon = mon;
        this.tue = tue;
        this.wen = wen;
        this.thu = thu;
        this.fri = fri;
    }

    public String getDuration() {
        return duration;
    }

    public String getLesson() {
        return lesson;
    }

    public String getMon() {
        return mon;
    }

    public String getFri() {
        return fri;
    }

    public String getWen() {
        return wen;
    }

    public String getThu() {
        return thu;
    }

    public String getTue() {
        return tue;
    }

    @Override
    public String toString() {
        return "HourRow{" +
                "lesson=" + lesson +
                ", duration=" + duration +
                ", mon=" + mon +
                ", tue=" + tue +
                ", sat=" + wen +
                ", thu=" + thu +
                ", fri=" + fri +
                '}';
    }
}
