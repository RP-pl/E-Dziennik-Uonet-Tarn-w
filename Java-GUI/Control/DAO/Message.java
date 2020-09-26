package Control.DAO;

import javafx.beans.property.SimpleStringProperty;

import java.nio.charset.StandardCharsets;

public class Message {
    private final SimpleStringProperty from;
    private final SimpleStringProperty topic;
    private final SimpleStringProperty date;
    private final SimpleStringProperty text;
    public Message(String from,String topic,String date,String text){
        this.from = new SimpleStringProperty(new String(from.getBytes(), StandardCharsets.UTF_8));
        this.topic = new SimpleStringProperty(new String(topic.getBytes(), StandardCharsets.UTF_8));
        this.date = new SimpleStringProperty(new String(date.getBytes(), StandardCharsets.UTF_8));
        this.text = new SimpleStringProperty(new String(text.getBytes(), StandardCharsets.UTF_8));
    }

    public String getDate() {
        return date.get();
    }

    public String getFrom() {
        return from.get();
    }

    public String getText() {
        return text.get();
    }

    public String getTopic() {
        return topic.get();
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", topic=" + topic +
                ", date=" + date +
                ", text=" + text +
                '}';
    }
}
