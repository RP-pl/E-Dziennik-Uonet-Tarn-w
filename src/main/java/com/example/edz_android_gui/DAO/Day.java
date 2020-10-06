package com.example.edz_android_gui.DAO;

import java.util.List;

public class Day {
    private final String date;
    private final List<String> exams;
    public Day(String date, List<String> exams){
        this.date = date;
        this.exams = exams;
    }

    @SuppressWarnings("unused")
    public String getDate() {
        return date;
    }

    @SuppressWarnings("unused")
    public List<String> getExams() {
        return exams;
    }

    @Override
    public String toString() {
        return "Day{" +
                "date=" + date +
                ", exams=" + exams +
                '}';
    }
}
