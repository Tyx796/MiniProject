package com.geometry.service;

import com.geometry.entity.Shapes3D;
import com.geometry.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Business logic for Task13D - 3D shape recognition task.
 * This class manages the sequence of 3D shapes, answer checking, attempts tracking,
 * and scoring for the advanced shape recognition exercise.
 */
public class Task13D {
    // List of 3D shape names to be recognized
    private List<String> shapes;
    // Index of the current shape in the list
    private int currentShapeIndex;
    // Index of the previous shape (for showing correct answers)
    private int previousShapeIndex;
    // Number of attempts for the current shape
    private int attempts;
    // Number of correct answers
    private int correctCount;
    // Maximum allowed attempts per shape
    private int maxAttempts = 3;
    // Number of required shapes to complete the task
    private int requiredShapes = 4;
    
    /**
     * Initialize the task with available 3D shapes.
     * Creates a randomized sequence of shapes from the Shapes3D collection
     * and sets up initial state for task progression tracking.
     */
    public Task13D() {
        // Initialize with available shapes from Shapes3D
        shapes = new ArrayList<>(Shapes3D.getAvailableShapes());
        
        // Shuffle the shapes to randomize order
        Collections.shuffle(shapes);
        
        // Start with the first shape
        currentShapeIndex = 0;
        previousShapeIndex = -1;
        attempts = 0;
        correctCount = 0;
    }
    
    /**
     * Get the current shape name that needs to be identified.
     * @return current shape name, or null if no more shapes are available
     */
    public String getCurrentShape() {
        if (currentShapeIndex < shapes.size()) {
            return shapes.get(currentShapeIndex);
        }
        return null;
    }
    
    /**
     * Get the previous shape name (used for showing correct answer after failed attempts).
     * @return previous shape name, or null if no previous shape exists
     */
    public String getPreviousShape() {
        if (previousShapeIndex >= 0 && previousShapeIndex < shapes.size()) {
            return shapes.get(previousShapeIndex);
        }
        return null;
    }
    
    /**
     * Get the number of attempts used for the current shape.
     * @return Number of attempts made for the current shape
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Check if the user's answer is correct for the current shape.
     * Updates score, attempt count, and progresses to next shape when appropriate.
     * @param answer User's answer (shape name)
     * @return true if correct, false otherwise
     */
    public boolean checkAnswer(String answer) {
        boolean isCorrect = false;
        
        if (currentShapeIndex < shapes.size()) {
            String currentShape = shapes.get(currentShapeIndex);
            isCorrect = currentShape.equalsIgnoreCase(answer);
            
            if (isCorrect) {
                // Award score based on remaining attempts (Advanced level scoring)
                int attemptsUsed = maxAttempts - getRemainingAttempts() + 1;
                User.addScores("Advanced", attemptsUsed);
                
                correctCount++;
                previousShapeIndex = currentShapeIndex;
                nextShape();
            } else {
                attempts++;
                if (attempts >= maxAttempts) {
                    // Move to next shape after max attempts exhausted
                    previousShapeIndex = currentShapeIndex;
                    nextShape();
                }
            }
        }
        
        return isCorrect;
    }
    
    /**
     * Move to the next shape in the sequence and reset attempts.
     * This is a private helper method called after correct answer or max attempts reached.
     */
    private void nextShape() {
        currentShapeIndex++;
        attempts = 0;
    }
    
    /**
     * Check if the task is completed (all required shapes are shown or identified).
     * Task completion occurs when either the required number of shapes is reached
     * or all available shapes have been shown.
     * @return true if task is completed, false otherwise
     */
    public boolean isTaskCompleted() {
        return currentShapeIndex >= requiredShapes || currentShapeIndex >= shapes.size();
    }
    
    /**
     * Get the number of remaining attempts for the current shape.
     * @return Number of attempts remaining before moving to next shape
     */
    public int getRemainingAttempts() {
        return maxAttempts - attempts;
    }
    
    /**
     * Get the total number of correct answers achieved so far.
     * @return Count of correctly identified shapes
     */
    public int getCorrectCount() {
        return correctCount;
    }
    
    /**
     * Get the index of the current shape in the sequence.
     * @return Current shape index (0-based)
     */
    public int getCurrentShapeIndex() {
        return currentShapeIndex;
    }
    
    /**
     * Get the total number of shapes to be shown in this task.
     * @return Total shape count (limited to requiredShapes or available shapes)
     */
    public int getTotalShapes() {
        return Math.min(requiredShapes, shapes.size());
    }
    
    /**
     * Get the current user's accumulated score.
     * @return Current total score
     */
    public int getCurrentScore() {
        return User.getScores();
    }
} 