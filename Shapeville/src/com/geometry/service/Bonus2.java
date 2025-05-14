package com.geometry.service;

import com.geometry.entity.SectorArea;
import javax.swing.Timer;
import java.util.*;

/**
 * Business logic for Bonus2 - Sector Area Calculation Challenge.
 * This class manages a timed challenge where students solve sector area problems
 * with varying angles and radii. Features include time limits, multiple attempts,
 * unit validation, and progressive scoring.
 */
public class Bonus2 {
    // List of remaining question numbers to be solved
    private List<Integer> remainingQuestions;
    // Current sector area question being solved
    private SectorArea currentQuestion;
    // Number of attempts made for current question
    private int attempts;
    // Timer for tracking time limit per question
    private Timer timer;
    // Remaining time in seconds
    private int timeRemaining;
    // Time limit per question (5 minutes = 300 seconds)
    private static final int TIME_LIMIT = 300;
    // Maximum attempts allowed per question
    private static final int MAX_ATTEMPTS = 3;

    /**
     * Constructor for Bonus2 task.
     * Initializes the question pool with randomized order.
     */
    public Bonus2() {
        initializeQuestions();
    }

    /**
     * Initialize the question pool with numbers 1-8.
     * Shuffles the questions to create a random sequence.
     */
    private void initializeQuestions() {
        remainingQuestions = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {  // Bonus2 has 8 questions
            remainingQuestions.add(i);
        }
        Collections.shuffle(remainingQuestions); // Randomize question order
    }

    /**
     * Set a specific question as the current challenge.
     * @param questionNumber Question number (1-8)
     * @throws IllegalArgumentException if question number is out of valid range
     */
    public void setCurrentQuestion(int questionNumber) {
        if (questionNumber < 1 || questionNumber > 8) {
            throw new IllegalArgumentException("Question number must be between 1 and 8");
        }
        currentQuestion = new SectorArea(questionNumber);
        attempts = 0;
        startTimer();
    }

    /**
     * Start a new question from the remaining pool.
     * Initializes the question and starts the timer.
     * @return true if there are questions remaining, false if all questions are completed
     */
    public boolean startNewQuestion() {
        if (remainingQuestions.isEmpty()) {
            return false;
        }

        int questionNumber = remainingQuestions.get(0);
        currentQuestion = new SectorArea(questionNumber);
        attempts = 0;
        startTimer();
        return true;
    }

    /**
     * Check the user's answer for correctness.
     * Validates both the numerical value and the unit.
     * Awards points based on remaining attempts if correct.
     * 
     * @param userAnswer User's numerical answer
     * @param unit User's unit choice
     * @return Result enum indicating CORRECT, INCORRECT, or MAX_ATTEMPTS_REACHED
     */
    public Result checkAnswer(double userAnswer, String unit) {
        attempts++;
        
        // Check if unit matches expected unit
        if (!unit.equals(currentQuestion.getUnit())) {
            if (attempts >= MAX_ATTEMPTS) {
                remainingQuestions.remove(0);
                stopTimer();
                return Result.MAX_ATTEMPTS_REACHED;
            }
            return Result.INCORRECT;
        }
        
        if (currentQuestion.checkAnswer(userAnswer)) {
            // Award points based on attempts used (Advanced level scoring)
            int attemptsUsed = MAX_ATTEMPTS - (MAX_ATTEMPTS - attempts);
            com.geometry.entity.User.addScores("Advanced", attemptsUsed);
            
            remainingQuestions.remove(0);
            stopTimer();
            return Result.CORRECT;
        }
        
        if (attempts >= MAX_ATTEMPTS) {
            remainingQuestions.remove(0);
            stopTimer();
            return Result.MAX_ATTEMPTS_REACHED;
        }
        
        return Result.INCORRECT;
    }

    /**
     * Start the countdown timer for the current question.
     * Timer runs for TIME_LIMIT seconds, updating every second.
     * Question is automatically skipped when time runs out.
     */
    private void startTimer() {
        timeRemaining = TIME_LIMIT;
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, e -> {
            timeRemaining--;
            if (timeRemaining <= 0) {
                stopTimer();
                remainingQuestions.remove(0);
            }
        });
        timer.start();
    }

    /**
     * Stop the current timer if running.
     */
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    /**
     * Get remaining time formatted as "MM:SS".
     * @return Formatted time string (e.g., "04:30" for 4 minutes 30 seconds)
     */
    public String getTimeRemainingFormatted() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Get the current sector area question.
     * @return Current SectorArea object
     */
    public SectorArea getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Get remaining time in seconds.
     * @return Seconds remaining for current question
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Get number of attempts made for current question.
     * @return Current attempt count
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Get count of remaining questions.
     * @return Number of questions left to solve
     */
    public int getRemainingQuestionsCount() {
        return remainingQuestions.size();
    }

    /**
     * Get formatted correct answer for current question.
     * @return Formatted area string with unit, or empty string if no current question
     */
    public String getCurrentQuestionFormattedArea() {
        return currentQuestion != null ? currentQuestion.getFormattedArea() : "";
    }

    /**
     * Get the expected unit for current question.
     * @return Unit string, or empty string if no current question
     */
    public String getCurrentQuestionUnit() {
        return currentQuestion != null ? currentQuestion.getUnit() : "";
    }

    /**
     * Enumeration of possible answer check results.
     * CORRECT: Answer matches expected value
     * INCORRECT: Answer does not match but attempts remain
     * MAX_ATTEMPTS_REACHED: No more attempts allowed for current question
     */
    public enum Result {
        CORRECT,
        INCORRECT,
        MAX_ATTEMPTS_REACHED
    }
}
