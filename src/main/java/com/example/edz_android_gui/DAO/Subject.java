package com.example.edz_android_gui.DAO;

public class Subject {
    private final String name;
    private final Double avg;
    private final String proposed;
    private final String final_;
    public Subject(String name,Double avg,String proposed,String final_){
        this.name = name;
        this.avg = avg;
        this.proposed = proposed;
        this.final_ = final_;
    }

    @SuppressWarnings("unused")
    public double getAvg() {
        return avg;
    }

    @SuppressWarnings("unused")
    public String getFinal_() {
        return final_;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public String getProposed() {
        return proposed;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name=" + name +
                ", avg=" + avg +
                ", proposed=" + proposed +
                ", final_=" + final_ +
                '}';
    }
}