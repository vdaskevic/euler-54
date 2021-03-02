package com.vd.poker.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Score {

    private final ScoreType scoreType;
    private List<Rank> additions;

    public Score(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

    public Score(ScoreType scoreType, Rank... additions) {
        this(scoreType, Arrays.asList(additions));
    }

    public Score(ScoreType scoreType, List<Rank> additions) {
        this.scoreType = scoreType;
        this.additions = additions;
    }

    public ScoreType getScoreType() {
        return scoreType;
    }

    public List<Rank> getAdditions() {
        return additions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score score = (Score) o;
        return scoreType == score.scoreType && Objects.equals(additions, score.additions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scoreType, additions);
    }

    @Override
    public String toString() {
        return "Score{" +
            "scoreType=" + scoreType +
            ", additions=" + additions +
            '}';
    }
}
