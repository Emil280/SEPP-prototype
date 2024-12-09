package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class Item{

    @Id
    @Column(name = "itemid")
    protected int id;

    @Column(name = "itemtype")
    protected int type;

    @Column(name = "name")
    protected String name;

    @Column(name = "measurement")
    protected int measurement;



    public Item(int id, int type, String name, int measurement) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.measurement = measurement;

    }
    public Item(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int measurement) {
        this.measurement = measurement;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", measurement=" + measurement +
                '}';
    }
}