package com.distributed_systems.group_2;

import javafx.beans.property.SimpleStringProperty;

public class ClientView {
    private final SimpleStringProperty text = new SimpleStringProperty("");
    private int index;

    public ClientView() {
        this("",-1);
    }

    public ClientView(String text, int index) {
        setText(text);
        this.index = index;
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

    @Override
    public String toString() {
        return text.get();
    }

    public int getIndex() {
        return index;
    }
}
