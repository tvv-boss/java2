package ru.geekbrains.java2.dz.dz1.TymkivVitaly;

public class MyArraySizeException extends ArrayIndexOutOfBoundsException { // прописали собственный класс unchecked exception

    private String detail;

    public MyArraySizeException(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return " MyArraySizeException: details = " + detail;
    }

}