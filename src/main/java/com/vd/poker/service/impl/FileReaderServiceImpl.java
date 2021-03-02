package com.vd.poker.service.impl;

import com.vd.poker.model.HandPair;
import com.vd.poker.service.FileReaderService;
import com.vd.poker.utils.CardsParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileReaderServiceImpl implements FileReaderService {

    private static final String HANDS_FILE_PATH_IN_RESOURCES = "p054_poker.txt";

    @Override
    public List<HandPair> readHandsFromFile() throws URISyntaxException, IOException {
        List<HandPair> handPairList = new ArrayList<>();
        // the file is small, so can read all the lines at one time
        List<String> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(HANDS_FILE_PATH_IN_RESOURCES)).toURI()));
        lines.forEach(line -> handPairList.add(CardsParser.parseHandPair(line)));
        return handPairList;
    }

}

