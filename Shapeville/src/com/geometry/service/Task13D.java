package com.geometry.service;

import com.geometry.entity.Shapes3D;
import com.geometry.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 3D Shape recognition task business logic
 */
public class Task13D {
    private List<String> shapes;
    private int currentShapeIndex;
    private int previousShapeIndex;
    private int attempts;
    private int correctCount;
    private int maxAttempts = 3;
    private int requiredShapes = 4;
    
    /**
     * Initialize the task with available 3D shapes
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
     * Get the current shape name
     * @return current shape name
     */
    public String getCurrentShape() {
        if (currentShapeIndex < shapes.size()) {
            return shapes.get(currentShapeIndex);
        }
        return null;
    }
    
    /**
     * Get the previous shape name (used for showing correct answer)
     * @return previous shape name
     */
    public String getPreviousShape() {
        if (previousShapeIndex >= 0 && previousShapeIndex < shapes.size()) {
            return shapes.get(previousShapeIndex);
        }
        return null;
    }
    
    /**
     * Get current attempt count
     * @return attempt count
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Check if answer is correct for current shape
     * @param answer User's answer
     * @return true if correct, false otherwise
     */
    public boolean checkAnswer(String answer) {
        boolean isCorrect = false;
        
        if (currentShapeIndex < shapes.size()) {
            String currentShape = shapes.get(currentShapeIndex);
            isCorrect = currentShape.equalsIgnoreCase(answer);
            
            if (isCorrect) {
                // Add score based on attempts remaining
                int attemptsUsed = maxAttempts - getRemainingAttempts() + 1;
                User.addScores("Advanced", attemptsUsed);
                
                correctCount++;
                previousShapeIndex = currentShapeIndex;
                nextShape();
            } else {
                attempts++;
                if (attempts >= maxAttempts) {
                    // Move to next shape after max attempts
                    previousShapeIndex = currentShapeIndex;
                    nextShape();
                }
            }
        }
        
        return isCorrect;
    }
    
    /**
     * Move to the next shape
     */
    private void nextShape() {
        currentShapeIndex++;
        attempts = 0;
    }
    
    /**
     * Check if the task is completed
     * @return true if all required shapes are identified or shown
     */
    public boolean isTaskCompleted() {
        return currentShapeIndex >= requiredShapes || currentShapeIndex >= shapes.size();
    }
    
    /**
     * Get the number of remaining attempts for current shape
     * @return remaining attempts
     */
    public int getRemainingAttempts() {
        return maxAttempts - attempts;
    }
    
    /**
     * Get the total number of correct answers
     * @return correct count
     */
    public int getCorrectCount() {
        return correctCount;
    }
    
    /**
     * Get the index of current shape
     * @return current shape index
     */
    public int getCurrentShapeIndex() {
        return currentShapeIndex;
    }
    
    /**
     * Get the total number of shapes to be shown
     * @return total shape count (limited to required shapes)
     */
    public int getTotalShapes() {
        return Math.min(requiredShapes, shapes.size());
    }
    
    /**
     * Get current user score
     * @return current score
     */
    public int getCurrentScore() {
        return User.getScores();
    }
} 