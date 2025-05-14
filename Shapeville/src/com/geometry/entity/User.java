package com.geometry.entity;

/**
 * User entity class for tracking and managing user scores.
 * Provides methods for score calculation and updates.
 */
public class User {
    // Static score variable to track user's progress across the application
    private static int scores = 0;

    // Private constructor to prevent instantiation
    private User(){}

    /**
     * Get the current user score
     * @return Current total score
     */
    public static int getScores() {
        return scores;
    }

    /**
     * Add points to the user's score based on level and attempts used
     * @param level Difficulty level ("Basic" or "Advanced")
     * @param attemptsUsed Number of attempts used to complete the task
     */
    public static void addScores(String level, int attemptsUsed) {
        scores += calScores(level, attemptsUsed);
    }

    /**
     * Calculate score based on difficulty level and attempts used
     * @param level Difficulty level ("Basic" or "Advanced")
     * @param attemptsUsed Number of attempts used (fewer attempts = higher score)
     * @return Calculated score points
     */
    public static int calScores(String level, int attemptsUsed) {
        // Base score is higher for advanced levels
        int base = level.equals("Basic") ? 1 : 2;
        // Bonus multiplier decreases with more attempts used
        int bonus = attemptsUsed == 1 ? 3 : attemptsUsed == 2 ? 2 : 1;
        return base * bonus;
    }
}
