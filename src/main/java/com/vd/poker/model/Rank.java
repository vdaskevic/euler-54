package com.vd.poker.model;

public enum Rank {

    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("T", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
    ACE("A", 14);

    private final String value;
    private final int score;

    Rank(String value, int score) {
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

    public static Rank fromValue(String v) {
        for (Rank rank : Rank.values()) {
            if (rank.value.equals(v)) {
                return rank;
            }
        }
        throw new IllegalArgumentException(v);
    }


}
