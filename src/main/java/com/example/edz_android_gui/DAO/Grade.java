package com.example.edz_android_gui.DAO;

public class Grade {
    private final String subject;
    private final String grade;
    private final String weight;
    private final String teacher;
    private final String date;
    private final String description;
    public Grade(String subject, String grade, String weight, String teacher, String date, String description){
        this.subject = subject;
        this.grade = grade;
        this.weight = weight;
        this.teacher = teacher;
        this.date = date;
        this.description = description;
    }

    @SuppressWarnings("unused")
    public String getGrade() {
        return grade;
    }

    @SuppressWarnings("unused")
    public String getSubject() {
        return subject;
    }

    @SuppressWarnings("unused")
    public String getDate() {
        return date;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public String getTeacher() {
        return teacher;
    }

    @SuppressWarnings("unused")
    public String getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "subject=" + subject +
                ", grade=" + grade +
                ", weight=" + weight +
                ", teacher=" + teacher +
                ", date=" + date +
                ", description=" + description +
                '}';
    }
}
