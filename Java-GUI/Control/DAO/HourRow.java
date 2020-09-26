package Control.DAO;

import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings("unused")
public class HourRow {
    private final SimpleStringProperty lesson;
    private final SimpleStringProperty duration;
    private final SimpleStringProperty mon;
    private final SimpleStringProperty tue;
    private final SimpleStringProperty wen;
    private final SimpleStringProperty thu;
    private final SimpleStringProperty fri;
    public HourRow(String lesson, String duration, String mon, String tue, String wen, String thu, String fri){
        this.lesson = new SimpleStringProperty(lesson);
        this.duration = new SimpleStringProperty(duration);
        this.mon = new SimpleStringProperty(mon);
        this.tue = new SimpleStringProperty(tue);
        this.wen = new SimpleStringProperty(wen);
        this.thu = new SimpleStringProperty(thu);
        this.fri = new SimpleStringProperty(fri);
    }

    public String getDuration() {
        return duration.get();
    }

    public String getLesson() {
        return lesson.get();
    }

    public String getMon() {
        return mon.get();
    }

    public String getFri() {
        return fri.get();
    }

    public String getWen() {
        return wen.get();
    }

    public String getThu() {
        return thu.get();
    }

    public String getTue() {
        return tue.get();
    }

    @Override
    public String toString() {
        return "HourRow{" +
                "lesson=" + lesson.get() +
                ", duration=" + duration.get() +
                ", mon=" + mon.get() +
                ", tue=" + tue.get() +
                ", sat=" + wen.get() +
                ", thu=" + thu.get() +
                ", fri=" + fri.get() +
                '}';
    }
}
