package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Table()
public class Item{


    @Id
    protected int id;
    protected int type;
    protected String name;
    protected int measurement;
    protected float quantity;

    public Item(int id, int type, String name, int measurement, float quantity) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.measurement = measurement;
        this.quantity = quantity;
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

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Item{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", measurement=" + measurement +
                ", quantity=" + quantity +
                '}';
    }
}