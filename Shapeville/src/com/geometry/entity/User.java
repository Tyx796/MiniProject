package com.geometry.entity;

public class User {
    private static int scores = 0;

    private User(){}

    public static int getScores() {
        return scores;
    }


    public static void addScores(String level, int attemptsUsed) {
        scores += calScores(level, attemptsUsed);
    }

    public static int calScores(String level, int attemptsUsed) {
        int base = level.equals("Basic") ? 1 : 2;
        int bonus = attemptsUsed == 1 ? 3 : attemptsUsed == 2 ? 2 : 1;
        return base * bonus;
    }
    
    
}
