package com.vd.poker.model;

import java.util.List;

public class Hand {

    private final List<Card> cards;
    private Score score;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }


    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Hand{" +
            "cards=" + cards +
            ", score=" + score +
            '}';
    }
}
