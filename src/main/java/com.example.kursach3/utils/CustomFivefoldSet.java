package com.example.kursach3.utils;

public class CustomFivefoldSet <T, V, S, M, W> {
    private final T first;
    private final V second;
    private final S third;
    private final M fourth;
    private final W fifth;

    public CustomFivefoldSet(T first, V second, S third, M fourth, W fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
    }

    public T getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public S getThird() {
        return third;
    }

    public M getFourth() {
        return fourth;
    }

    public W getFifth() {
        return fifth;
    }
}
