package com.vd.poker.service;

import com.vd.poker.model.HandPair;

public interface WinnerCalculationService {

    int getWinningHand(HandPair handPair);
}
