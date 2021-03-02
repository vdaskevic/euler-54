package com.vd.poker.service;

import com.vd.poker.model.HandPair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface FileReaderService {

    List<HandPair> readHandsFromFile() throws URISyntaxException, IOException;
}
