package com.vd.poker.model;

public enum ScoreType {

    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIRS(3),
    THREE_OF_KIND(5),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_KIND(8),
    STRAIGHT_FLUSH(9),
    ROYAL_FLUSH(10);

    private final int value;

    ScoreType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
