package org.onlineshop.service;

import java.util.Random;

public class FileService {
    public static String rndFileName() {
        int rndLetters = (int) ((Math.random() * 3) + 5);
        String numbers = "";
        for (int i = 0; i < rndLetters; i++) {
            numbers = numbers + (int) (Math.random() * 10);
        }
        return numbers;
    }
}
