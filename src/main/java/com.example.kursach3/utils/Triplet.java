package com.example.kursach3.utils;

public class Triplet {

    private final String first;
    private final String second;
    private final int third;

    public Triplet(String first, String second, int third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public String getFirst() { return first; }
    public String getSecond() { return second; }
    public int getThird() { return third; }
}