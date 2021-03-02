package com.vd.poker.service;

import com.vd.poker.model.Card;
import com.vd.poker.model.HandPair;
import com.vd.poker.model.Score;

import java.util.List;

public interface ScoreService {

    void fillScores(HandPair handPair);

    Score getScore(List<Card> handCards);
}
