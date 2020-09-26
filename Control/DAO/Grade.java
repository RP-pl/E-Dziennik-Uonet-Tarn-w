package Control.DAO;

import javafx.beans.property.SimpleStringProperty;

public class Grade {
    private final SimpleStringProperty subject;
    private final SimpleStringProperty grade;
    private final SimpleStringProperty weight;
    private final SimpleStringProperty teacher;
    private final SimpleStringProperty date;
    private final SimpleStringProperty description;
    public Grade(String subject, String grade, String weight, String teacher, String date, String description){
        this.subject = new SimpleStringProperty(subject);
        this.grade = new SimpleStringProperty(grade);
        this.weight = new SimpleStringProperty(weight);
        this.teacher = new SimpleStringProperty(teacher);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
    }

    @SuppressWarnings("unused")
    public String getGrade() {
        return grade.get();
    }

    @SuppressWarnings("unused")
    public String getSubject() {
        return subject.get();
    }

    @SuppressWarnings("unused")
    public String getDate() {
        return date.get();
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description.get();
    }

    @SuppressWarnings("unused")
    public String getTeacher() {
        return teacher.get();
    }

    @SuppressWarnings("unused")
    public String getWeight() {
        return weight.get();
    }

    @Override
    public String toString() {
        return "Grade{" +
                "subject=" + subject.get() +
                ", grade=" + grade.get() +
                ", weight=" + weight.get() +
                ", teacher=" + teacher.get() +
                ", date=" + date.get() +
                ", description=" + description.get() +
                '}';
    }
}
