package com.example.kursach3.utils;

public class CustomSixfoldSet <T, V, S, M, W, O> {
    private final T first;
    private final V second;
    private final S third;
    private final M fourth;
    private final W fifth;
    private final O sixth;

    public CustomSixfoldSet(T first, V second, S third, M fourth, W fifth, O sixth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
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

    public O getSixth() {
        return sixth;
    }
}
