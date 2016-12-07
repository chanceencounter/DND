package com.ericarao.dnd.model;

public class PlayerComboBoxItem {
    //Variable/Data Fields
    private final int id;
    private final String name;

    public PlayerComboBoxItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
