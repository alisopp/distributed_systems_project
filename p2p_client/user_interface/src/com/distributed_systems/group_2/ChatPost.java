package com.distributed_systems.group_2;

import javafx.beans.property.SimpleStringProperty;

public class ChatPost {
    private final SimpleStringProperty text=new SimpleStringProperty("");

    public ChatPost() {
        this("");
    }

    public ChatPost(String text) {
        setText(text);
    }

    public String getText() {
        return text.get();
    }

    public SimpleStringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }
}
