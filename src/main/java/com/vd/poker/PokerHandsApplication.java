package com.vd.poker;

import com.vd.poker.model.HandPair;
import com.vd.poker.service.FileReaderService;
import com.vd.poker.service.ScoreService;
import com.vd.poker.service.WinnerCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
public class PokerHandsApplication implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(PokerHandsApplication.class);

    @Autowired
    private FileReaderService fileReaderService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private WinnerCalculationService winnerCalculationService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PokerHandsApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... arg0) throws IOException, URISyntaxException {
        LocalDateTime startTime = LocalDateTime.now();

        List<HandPair> handPairList = fileReaderService.readHandsFromFile();

        int player1Wins = 0;
        int player2Wins = 0;
        int draws = 0;
        log.info("=========================================================");
        for (HandPair handPair : handPairList) {
            scoreService.fillScores(handPair);
            int winner = winnerCalculationService.getWinningHand(handPair);
            if (winner == 1) {
                player1Wins++;
            } else if (winner == 2) {
                player2Wins++;
            } else {
                draws++;
            }
            log.info("Player " + winner + " won. 1: " + handPair.getHand1() + ", 2: " + handPair.getHand2());
        }

        LocalDateTime finishTime = LocalDateTime.now();
        log.info("=========================================================");
        log.info("Player 1 won: " + player1Wins + " times.");
        log.info("Player 2 won: " + player2Wins + " times.");
        log.info("Draws: " + draws + " times.");
        log.info("Execution time: " + ChronoUnit.MILLIS.between(startTime, finishTime) + " ms.");
    }

}
