package com.geometry.service;

import com.geometry.entity.Shapes2D;
import com.geometry.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Business logic for Task12D - 2D shape recognition task.
 * This class manages the sequence of 2D shapes, answer checking, attempts, and scoring.
 */
public class Task12D {
    // List of shape names to be recognized
    private List<String> shapes;
    // Index of the current shape in the list
    private int currentShapeIndex;
    // Number of attempts for the current shape
    private int attempts;
    // Number of correct answers
    private int correctCount;
    // Maximum allowed attempts per shape
    private int maxAttempts = 3;
    // Number of required shapes to complete the task
    private int requiredShapes = 4;
    
    /**
     * Initialize the task with available shapes
     */
    public Task12D() {
        // Initialize with available shapes from Shapes2D
        shapes = new ArrayList<>(Shapes2D.getAvailableShapes());
        // Shuffle the shapes to randomize order
        Collections.shuffle(shapes);
        // Start with the first shape
        currentShapeIndex = 0;
        attempts = 0;
        correctCount = 0;
    }
    
    /**
     * Get the current shape name.
     * @return current shape name
     */
    public String getCurrentShape() {
        if (currentShapeIndex < shapes.size()) {
            return shapes.get(currentShapeIndex);
        }
        return null;
    }
    
    /**
     * Get the previous shape name (used for showing correct answer).
     * @return previous shape name
     */
    public String getPreviousShape() {
        if (currentShapeIndex > 0 && currentShapeIndex <= shapes.size()) {
            return shapes.get(currentShapeIndex - 1);
        }
        return null;
    }
    
    /**
     * Get the number of attempts used for the current shape.
     * @return Number of attempts
     */
    public int getAttempts() {
        return attempts;
    }
    
    /**
     * Check if the user's answer is correct for the current shape.
     * @param answer User's answer (shape name)
     * @return true if correct, false otherwise
     */
    public boolean checkAnswer(String answer) {
        boolean isCorrect = false;
        if (currentShapeIndex < shapes.size()) {
            String currentShape = shapes.get(currentShapeIndex);
            isCorrect = currentShape.equalsIgnoreCase(answer);
            if (isCorrect) {
                // Award score based on remaining attempts
                int attemptsUsed = maxAttempts - getRemainingAttempts() + 1;
                User.addScores("Basic", attemptsUsed);
                correctCount++;
                nextShape();
            } else {
                attempts++;
                if (attempts >= maxAttempts) {
                    // Move to next shape after max attempts
                    nextShape();
                }
            }
        }
        return isCorrect;
    }
    
    /**
     * Move to the next shape in the list.
     */
    public void nextShape() {
        currentShapeIndex++;
        attempts = 0;
    }
    
    /**
     * Check if the task is completed (all required shapes are shown or identified).
     * @return true if completed, false otherwise
     */
    public boolean isTaskCompleted() {
        return currentShapeIndex >= requiredShapes || currentShapeIndex >= shapes.size();
    }
    
    /**
     * Get the number of remaining attempts for the current shape.
     * @return Remaining attempts
     */
    public int getRemainingAttempts() {
        return maxAttempts - attempts;
    }
    
    /**
     * Get the total number of correct answers so far.
     * @return Correct answer count
     */
    public int getCorrectCount() {
        return correctCount;
    }
    
    /**
     * Get the index of the current shape.
     * @return Current shape index
     */
    public int getCurrentShapeIndex() {
        return currentShapeIndex;
    }
    
    /**
     * Get the total number of shapes to be shown (limited to requiredShapes).
     * @return Total shape count
     */
    public int getTotalShapes() {
        return Math.min(requiredShapes, shapes.size());
    }
    
    /**
     * Get the current user score.
     * @return Current score
     */
    public int getCurrentScore() {
        return User.getScores();
    }
}
