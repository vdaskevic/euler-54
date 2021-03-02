package com.vd.poker.service.impl;

import com.vd.poker.model.Rank;
import com.vd.poker.model.Score;
import com.vd.poker.model.ScoreType;
import com.vd.poker.service.ScoreService;
import com.vd.poker.utils.CardsParser;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ScoreServiceImplTest {

    private final ScoreService scoreService = new ScoreServiceImpl();

    public static final String HIGH_CARD = "AD 2S 6S 4S 3C";
    public static final String ONE_PAIR = "7C 7S KC 5H 6S";
    public static final String TWO_PAIRS = "6D AS 6S 3S 3C";
    public static final String THREE_OF_KIND = "7C 7S 7H 8H 6S";
    public static final String STRAIGHT = "6D 4D 5C 8D 7D";
    public static final String FLUSH = "7D 2D TD KD 4D";
    public static final String FULL_HOUSE = "5D 5S 8D 8S 8C";
    public static final String FOUR_OF_KIND = "7C 7D 7S 7H 6S";
    public static final String STRAIGHT_FLUSH = "6D 4D 5D 2D 3D";
    public static final String ROYAL_FLUSH = "TC KC JC QC AC";

    @Test
    public void RoyalFlush() {
        assertThat(scoreService.getScore(CardsParser.parseHand(ROYAL_FLUSH)),
            is(new Score(ScoreType.ROYAL_FLUSH)));
    }

    @Test
    public void StraightFlushWithHighestCard6() {
        assertThat(scoreService.getScore(CardsParser.parseHand(STRAIGHT_FLUSH)),
            is(new Score(ScoreType.STRAIGHT_FLUSH, Rank.SIX)));
    }

    @Test
    public void FourOfKindOf7sAndHighCard6() {
        assertThat(scoreService.getScore(CardsParser.parseHand(FOUR_OF_KIND)),
            is(new Score(ScoreType.FOUR_OF_KIND, Rank.SEVEN, Rank.SIX)));
    }

    @Test
    public void FullHouseOf8sAnd5s() {
        assertThat(scoreService.getScore(CardsParser.parseHand(FULL_HOUSE)),
            is(new Score(ScoreType.FULL_HOUSE, Rank.EIGHT, Rank.FIVE)));
    }

    @Test
    public void FlushWithHighestCardKingOrderedByRanks() {
        assertThat(scoreService.getScore(CardsParser.parseHand(FLUSH)),
            is(new Score(ScoreType.FLUSH, Rank.KING, Rank.TEN, Rank.SEVEN, Rank.FOUR, Rank.TWO)));
    }

    @Test
    public void StraightWithHighestCard8() {
        assertThat(scoreService.getScore(CardsParser.parseHand(STRAIGHT)),
            is(new Score(ScoreType.STRAIGHT, Rank.EIGHT)));
    }

    @Test
    public void ThreeOfKindOf7sAndHighCardsOf8And6() {
        assertThat(scoreService.getScore(CardsParser.parseHand(THREE_OF_KIND)),
            is(new Score(ScoreType.THREE_OF_KIND, Rank.SEVEN, Rank.EIGHT, Rank.SIX)));
    }


    @Test
    public void TwoPairsOf6sAnd3sAndHighCardAce() {
        assertThat(scoreService.getScore(CardsParser.parseHand(TWO_PAIRS)),
            is(new Score(ScoreType.TWO_PAIRS, Rank.SIX, Rank.THREE, Rank.ACE)));
    }

    @Test
    public void OnePairOf7sAndHighestCardKingOrdered() {
        assertThat(scoreService.getScore(CardsParser.parseHand(ONE_PAIR)),
            is(new Score(ScoreType.ONE_PAIR, Rank.SEVEN, Rank.KING, Rank.SIX, Rank.FIVE)));
    }

    @Test
    public void HighCardOfAceOrdered() {
        assertThat(scoreService.getScore(CardsParser.parseHand(HIGH_CARD)),
            is(new Score(ScoreType.HIGH_CARD, Rank.ACE, Rank.SIX, Rank.FOUR, Rank.THREE, Rank.TWO)));
    }
}