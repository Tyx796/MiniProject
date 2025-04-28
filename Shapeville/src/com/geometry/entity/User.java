package com.geometry.entity;

public class User {
    private static int scores = 0;

    private User(){}

    public static int getScores() {
        return scores;
    }


    public static void addScores(String level, int attempt) {
        int base = level.equals("Basic") ? 1 : 2;
        int bonus = attempt == 1 ? 3 : attempt == 2 ? 2 : 1;
        scores += base * bonus;
    }
    
    
}
