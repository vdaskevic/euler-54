package com.vd.poker.model;

public enum Suit {

    CLUBS("C", 1),
    DIAMONDS("D", 2),
    HEARTS("H", 3),
    SPADES("S", 4);

    private final String value;
    private final int score;

    Suit(String value, int score) {
        this.value = value;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Suit fromValue(String v) {
        for (Suit suit : Suit.values()) {
            if (suit.value.equals(v)) {
                return suit;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
