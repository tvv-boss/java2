package ru.geekbrains.java2.dz.dz1.TymkivVitaly;

public class MyArrayDataException extends NumberFormatException { // прописали собственный класс unchecked exception

    private String detail;

    public MyArrayDataException(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return " MyArrayDataException: details = " + detail;
    }

}