package com.example.kursach3.utils;

public class CustomSetStringStringIntString {

    private final String first;
    private final String second;
    private final int third;
    private final String fourth;

    public CustomSetStringStringIntString(String first, String second, int third, String fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public String getFirst() { return first; }
    public String getSecond() { return second; }
    public int getThird() { return third; }
    public String getFourth() {
        return fourth;
    }
}