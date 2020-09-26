package Control.DAO;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Day {
    private final SimpleStringProperty date;
    private final SimpleListProperty<String> exams;
    public Day(String date, List<String> exams){
        this.date = new SimpleStringProperty(date);
        this.exams = new SimpleListProperty<>(FXCollections.observableList(exams));
    }

    @SuppressWarnings("unused")
    public String getDate() {
        return date.get();
    }

    @SuppressWarnings("unused")
    public ObservableList<String> getExams() {
        return exams.get();
    }

    @Override
    public String toString() {
        return "Day{" +
                "date=" + date.get() +
                ", exams=" + exams.get() +
                '}';
    }
}
