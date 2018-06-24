package com.distributed_systems.group_2;

import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleStringProperty name=new SimpleStringProperty("");
    private final SimpleStringProperty ip=new SimpleStringProperty("");

    public User() {
        this("","");
    }
    public User(String name,String ip) {
        setName(name);
        setIp(ip);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getIp() {
        return ip.get();
    }

    public SimpleStringProperty ipProperty() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }
}
