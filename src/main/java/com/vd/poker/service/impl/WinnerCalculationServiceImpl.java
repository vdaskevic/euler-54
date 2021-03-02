package com.vd.poker.service.impl;

import com.vd.poker.model.HandPair;
import com.vd.poker.model.Rank;
import com.vd.poker.model.Score;
import com.vd.poker.service.WinnerCalculationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WinnerCalculationServiceImpl implements WinnerCalculationService {

    @Override
    public int getWinningHand(HandPair handPair) {
        Score score1 = handPair.getHand1().getScore();
        Score score2 = handPair.getHand2().getScore();

        // first check score type
        int scoreTypeCompareResult = Integer.compare(score1.getScoreType().getValue(), score2.getScoreType().getValue());
        if (scoreTypeCompareResult == 1) {
            return 1;
        }
        if (scoreTypeCompareResult == -1) {
            return 2;
        }

        // if score type is the same, we compare additions ranks of the same index
        // until we find differences and get the winner
        List<Rank> ranks1 = score1.getAdditions();
        List<Rank> ranks2 = score2.getAdditions();
        for (int i = 0; i < ranks1.size(); i++) {
            int additionsResult = Integer.compare(ranks1.get(i).getScore(), ranks2.get(i).getScore());
            if (additionsResult == 1) {
                return 1;
            } else if (additionsResult == -1) {
                return 2;
            }
        }
        // draw
        return 0;
    }
}
