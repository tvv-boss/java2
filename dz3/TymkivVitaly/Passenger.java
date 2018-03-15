package ru.geekbrains.java2.dz.dz3.TymkivVitaly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Passenger {

    private String name;
    private String document;
    private int flightNumber;

    public Passenger(String name, String document, int flightNumber) {
        this.name = name;
        this.document = document;
        this.flightNumber = flightNumber;
        System.out.println(this.name + "  " + this.document + "  " + this.flightNumber);
    }
    public Passenger() {

    }
    public int getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }
    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
