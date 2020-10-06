package com.example.edz_android_gui.DAO;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;

public class Message {
    private final String from;
    private final String topic;
    private final String date;
    private final String text;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Message(String from, String topic, String date, String text){
        this.from = new String(from.getBytes(), StandardCharsets.UTF_8);
        this.topic = new String(topic.getBytes(), StandardCharsets.UTF_8);
        this.date = new String(date.getBytes(), StandardCharsets.UTF_8);
        this.text = new String(text.getBytes(), StandardCharsets.UTF_8);
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public String getTopic() {
        return topic;
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
