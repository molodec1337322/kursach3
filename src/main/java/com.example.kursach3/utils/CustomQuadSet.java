package com.example.kursach3.utils;

public class CustomQuadSet <T, V, S, M> {

    private final T first;
    private final V second;
    private final S third;
    private final M fourth;

    public CustomQuadSet(T first, V second, S third, M fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public T getFirst() { return first; }
    public V getSecond() { return second; }
    public S getThird() { return third; }
    public M getFourth() {
        return fourth;
    }
}