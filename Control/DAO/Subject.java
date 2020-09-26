package Control.DAO;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Subject {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty avg;
    private final SimpleStringProperty proposed;
    private final SimpleStringProperty final_;
    public Subject(String name,Double avg,String proposed,String final_){
        this.name = new SimpleStringProperty(name);
        this.avg = new SimpleDoubleProperty(avg);
        this.proposed = new SimpleStringProperty(proposed);
        this.final_ = new SimpleStringProperty(final_);
    }

    @SuppressWarnings("unused")
    public double getAvg() {
        return avg.get();
    }

    @SuppressWarnings("unused")
    public String getFinal_() {
        return final_.get();
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name.get();
    }

    @SuppressWarnings("unused")
    public String getProposed() {
        return proposed.get();
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name=" + name.get() +
                ", avg=" + avg.get() +
                ", proposed=" + proposed.get() +
                ", final_=" + final_.get() +
                '}';
    }
}
