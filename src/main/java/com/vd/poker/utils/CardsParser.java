package com.vd.poker.utils;

import com.vd.poker.model.Card;
import com.vd.poker.model.Hand;
import com.vd.poker.model.HandPair;
import com.vd.poker.model.Rank;
import com.vd.poker.model.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardsParser {

    private static final String CARDS_DELIMITER = " ";

    public static HandPair parseHandPair(String handPairString) {
        String[] cardStrings = handPairString.split(CARDS_DELIMITER);

        List<Card> hand1Cards = new ArrayList<>();
        hand1Cards.add(parseCard(cardStrings[0]));
        hand1Cards.add(parseCard(cardStrings[1]));
        hand1Cards.add(parseCard(cardStrings[2]));
        hand1Cards.add(parseCard(cardStrings[3]));
        hand1Cards.add(parseCard(cardStrings[4]));

        List<Card> hand2Cards = new ArrayList<>();
        hand2Cards.add(parseCard(cardStrings[5]));
        hand2Cards.add(parseCard(cardStrings[6]));
        hand2Cards.add(parseCard(cardStrings[7]));
        hand2Cards.add(parseCard(cardStrings[8]));
        hand2Cards.add(parseCard(cardStrings[9]));

        return new HandPair(new Hand(hand1Cards), new Hand(hand2Cards));
    }

    public static List<Card> parseHand(String handString) {
        List<Card> result = new ArrayList<>();
        Arrays.asList(handString.split(CARDS_DELIMITER)).forEach(cardStr -> result.add(parseCard(cardStr)));
        return result;
    }

    private static Card parseCard(String chunk) {
        return new Card(
            Rank.fromValue(chunk.substring(0, 1)),
            Suit.fromValue(chunk.substring(1, 2)));
    }
}
