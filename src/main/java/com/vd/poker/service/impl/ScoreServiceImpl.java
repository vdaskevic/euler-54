package com.vd.poker.service.impl;

import com.vd.poker.model.Card;
import com.vd.poker.model.Hand;
import com.vd.poker.model.HandPair;
import com.vd.poker.model.Rank;
import com.vd.poker.model.Score;
import com.vd.poker.model.ScoreType;
import com.vd.poker.service.ScoreService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Override
    public void fillScores(HandPair handPair) {
        fillHandScore(handPair.getHand1());
        fillHandScore(handPair.getHand2());
    }

    private void fillHandScore(Hand hand) {
        hand.setScore(getScore(hand.getCards()));
    }

    @Override
    public Score getScore(List<Card> handCards) {
        List<Card> sortedByRankCards = new ArrayList<>(handCards);
        sortedByRankCards.sort((c1, c2) -> Integer.compare(c1.getRank().getScore(), c2.getRank().getScore()) * -1);
        List<Card> sortedBySuitCards = new ArrayList<>(handCards);
        sortedBySuitCards.sort(Comparator.comparingInt(c -> c.getSuit().getScore()));
        Set<Map.Entry<Rank, Integer>> rankFrequencySet = getRankFrequencyMap(handCards);

        if (isFlush(sortedBySuitCards) && isStraight(sortedByRankCards, rankFrequencySet)) {
            if (sortedByRankCards.get(0).getRank() == Rank.ACE) {
                return new Score(ScoreType.ROYAL_FLUSH);
            }
            return new Score(ScoreType.STRAIGHT_FLUSH,
                Collections.singletonList(sortedByRankCards.get(0).getRank()));
        }
        if (isFlush(sortedBySuitCards)) {
            return new Score(ScoreType.FLUSH, getAllAdditions(sortedByRankCards));
        }
        if (isStraight(sortedByRankCards, rankFrequencySet)) {
            return new Score(ScoreType.STRAIGHT,
                Collections.singletonList(sortedByRankCards.get(0).getRank()));
        }
        if (rankFrequencySet.size() == 2) {
            return getFourOfKindOrFullHouseScore(rankFrequencySet);
        }
        if (rankFrequencySet.size() == 3) {
            return getThreeOfKindOrTwoPairsScore(rankFrequencySet);
        }
        if (rankFrequencySet.size() == 4) {
            return getOnePairScore(rankFrequencySet);
        }
        return new Score(ScoreType.HIGH_CARD, getAllAdditions(sortedByRankCards));
    }

    private Set<Map.Entry<Rank, Integer>> getRankFrequencyMap(List<Card> handCards) {
        Map<Rank, Integer> rankFrequencyMap = new EnumMap<>(Rank.class);
        handCards.forEach(card -> rankFrequencyMap.merge(card.getRank(), 1, Integer::sum));
        return rankFrequencyMap.entrySet();
    }

    private boolean isFlush(List<Card> sortedBySuitCards) {
        return sortedBySuitCards.get(0).getSuit() == sortedBySuitCards.get(4).getSuit();
    }

    private boolean isStraight(List<Card> sortedByRankCards,
                               Set<Map.Entry<Rank, Integer>> rankFrequencySet) {
        return rankFrequencySet.size() == 5
            && sortedByRankCards.get(0).getRank().getScore()
            - sortedByRankCards.get(4).getRank().getScore() == 4;
    }

    private Score getFourOfKindOrFullHouseScore(Set<Map.Entry<Rank, Integer>> rankFrequencySet) {
        ScoreType scoreType = null;
        Rank higherRank = null;
        Rank lowerRank = null;
        for (Map.Entry<Rank, Integer> entry : rankFrequencySet) {
            switch (entry.getValue()) {
                case 1:
                    scoreType = ScoreType.FOUR_OF_KIND;
                    lowerRank = entry.getKey();
                    break;
                case 2:
                    scoreType = ScoreType.FULL_HOUSE;
                    lowerRank = entry.getKey();
                    break;
                case 3:
                    scoreType = ScoreType.FULL_HOUSE;
                    higherRank = entry.getKey();
                    break;
                case 4:
                    scoreType = ScoreType.FOUR_OF_KIND;
                    higherRank = entry.getKey();
                    break;
                default:
                    break;
            }
        }
        return new Score(scoreType, Arrays.asList(higherRank, lowerRank));
    }

    private Score getThreeOfKindOrTwoPairsScore(Set<Map.Entry<Rank, Integer>> rankFrequencySet) {
        // three of a kind and two high cards
        // or two pairs and one high card
        List<Rank> highCardsSortedList = new ArrayList<>();
        Rank higherPair = null;
        Rank lowerPair = null;
        Rank threeOfKindRank = null;
        boolean isThreeOfKind = false;
        for (Map.Entry<Rank, Integer> entry : rankFrequencySet) {
            if (entry.getValue().equals(1)) {
                highCardsSortedList.add(entry.getKey());
            } else if (entry.getValue().equals(2)) {
                if (higherPair != null && higherPair.getScore() > entry.getKey().getScore()) {
                    lowerPair = entry.getKey();
                } else if (higherPair != null) {
                    lowerPair = higherPair;
                    higherPair = entry.getKey();
                } else {
                    higherPair = entry.getKey();
                }
            } else if (entry.getValue().equals(3)) {
                isThreeOfKind = true;
                threeOfKindRank = entry.getKey();
            }
        }

        if (isThreeOfKind) {
            List<Rank> additions = new ArrayList<>();
            additions.add(threeOfKindRank);
            highCardsSortedList.sort((r1, r2) -> Integer.compare(r1.getScore(), r2.getScore()) * -1);
            additions.addAll(highCardsSortedList);
            return new Score(ScoreType.THREE_OF_KIND, additions);
        } else {
            return new Score(ScoreType.TWO_PAIRS,
                Arrays.asList(higherPair, lowerPair, highCardsSortedList.get(0)));
        }
    }

    private Score getOnePairScore(Set<Map.Entry<Rank, Integer>> rankFrequencySet) {
        // one pair and 3 high cards
        List<Rank> additions = new ArrayList<>();
        List<Rank> highCardsSortedList = new ArrayList<>();
        for (Map.Entry<Rank, Integer> entry : rankFrequencySet) {
            if (entry.getValue().equals(2)) {
                // pair rank goes first
                additions.add(entry.getKey());
            } else if (entry.getValue().equals(1)) {
                highCardsSortedList.add(entry.getKey());
            }
        }
        highCardsSortedList.sort((r1, r2) -> Integer.compare(r1.getScore(), r2.getScore()) * -1);
        additions.addAll(highCardsSortedList);
        return new Score(ScoreType.ONE_PAIR, additions);
    }

    private List<Rank> getAllAdditions(List<Card> sortedByRankCards) {
        return sortedByRankCards.stream()
            .map(Card::getRank)
            .collect(Collectors.toList());
    }

}
