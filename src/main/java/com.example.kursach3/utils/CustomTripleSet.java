package com.example.kursach3.utils;

public class CustomTripleSet<T, V, S> {
    private final T first;
    private final V second;
    private final S third;

    public CustomTripleSet(T first, V second, S third) {
        this.first = first;
        this.second = second;
        this.third = third;
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
}
