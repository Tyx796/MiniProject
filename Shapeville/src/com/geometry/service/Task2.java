package com.geometry.service;

import com.geometry.entity.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Business logic for Task2 - Angle recognition task.
 * This class manages angle type recognition, answer checking, and progress tracking for angle learning.
 */
public class Task2 {
    // Angle type constants
    public static final String ACUTE_ANGLE = "acute";
    public static final String RIGHT_ANGLE = "right";
    public static final String OBTUSE_ANGLE = "obtuse";
    public static final String STRAIGHT_ANGLE = "straight";
    public static final String REFLEX_ANGLE = "reflex";
    
    // List of all angle types
    private final List<String> angleTypes = Arrays.asList(
        ACUTE_ANGLE, RIGHT_ANGLE, OBTUSE_ANGLE, STRAIGHT_ANGLE, REFLEX_ANGLE
    );
    
    // Set of completed angle types
    private Set<String> completedTypes = new HashSet<>();
    
    // Current angle value (degrees)
    private int currentAngle = 0;
    
    // Number of attempts for current angle
    private int attempts = 0;
    
    // Maximum allowed attempts per angle
    private int maxAttempts = 3;
    
    // Current angle type (string)
    private String currentAngleType = "";
    
    // Number of correct answers
    private int correctCount = 0;
    
    // Number of required angle types to complete the task
    private int requiredTypes = 4;
    
    /**
     * Set the current angle (user input).
     * @param angle The angle value input by the user
     */
    public void setCurrentAngle(int angle) {
        this.currentAngle = angle;
        this.attempts = 0;
        this.currentAngleType = getAngleType(angle);
    }
    
    /**
     * Get the angle type string for a given angle value.
     * @param angle Angle value in degrees
     * @return Angle type string
     */
    public String getAngleType(int angle) {
        if (angle == 0) return ACUTE_ANGLE; // Special case: treat 0° as acute
        if (angle > 0 && angle < 90) return ACUTE_ANGLE;
        if (angle == 90) return RIGHT_ANGLE;
        if (angle > 90 && angle < 180) return OBTUSE_ANGLE;
        if (angle == 180) return STRAIGHT_ANGLE;
        if (angle > 180 && angle < 360) return REFLEX_ANGLE;
        if (angle == 360) return STRAIGHT_ANGLE; // Special case: treat 360° as straight
        return "";
    }
    
    /**
     * Check if the user's answer for angle type is correct.
     * @param answer User's answer (angle type string)
     * @return true if correct, false otherwise
     */
    public boolean checkAnswer(String answer) {
        boolean isCorrect = answer.equalsIgnoreCase(currentAngleType);
        
        if (isCorrect) {
            // Award score based on remaining attempts
            int attemptsUsed = maxAttempts - getRemainingAttempts() + 1;
            User.addScores("Basic", attemptsUsed);
            // Mark this angle type as completed
            completedTypes.add(currentAngleType);
            correctCount++;
            return true;
        } else {
            attempts++;
            return false;
        }
    }
    
    /**
     * Get the current angle value.
     * @return Current angle in degrees
     */
    public int getCurrentAngle() {
        return currentAngle;
    }
    
    /**
     * Get the current angle type string.
     * @return Current angle type
     */
    public String getCurrentAngleType() {
        return currentAngleType;
    }
    
    /**
     * Get the number of attempts used for the current angle.
     * @return Number of attempts
     */
    public int getAttempts() {
        return attempts;
    }
    
    /**
     * Get the number of remaining attempts for the current angle.
     * @return Remaining attempts
     */
    public int getRemainingAttempts() {
        return maxAttempts - attempts;
    }
    
    /**
     * Get the number of correct answers so far.
     * @return Correct answer count
     */
    public int getCorrectCount() {
        return correctCount;
    }
    
    /**
     * Check if the angle recognition task is completed.
     * @return true if completed, false otherwise
     */
    public boolean isTaskCompleted() {
        return completedTypes.size() >= requiredTypes || completedTypes.size() >= angleTypes.size();
    }
    
    /**
     * Get the current user score.
     * @return Current score
     */
    public int getCurrentScore() {
        return User.getScores();
    }
    
    /**
     * Get all available angle types.
     * @return List of angle type strings
     */
    public List<String> getAngleTypes() {
        return new ArrayList<>(angleTypes);
    }
    
    /**
     * Get the display name for an angle type.
     * @param type Angle type string
     * @return Display name
     */
    public String getAngleTypeName(String type) {
        switch (type) {
            case ACUTE_ANGLE:
                return "Acute Angle";
            case RIGHT_ANGLE:
                return "Right Angle";
            case OBTUSE_ANGLE:
                return "Obtuse Angle";
            case STRAIGHT_ANGLE:
                return "Straight Angle";
            case REFLEX_ANGLE:
                return "Reflex Angle";
            default:
                return "Unknown";
        }
    }
    
    /**
     * Get the description for an angle type.
     * @param type Angle type string
     * @return Description string
     */
    public String getAngleTypeDescription(String type) {
        switch (type) {
            case ACUTE_ANGLE:
                return "less than 90° and greater than 0°";
            case RIGHT_ANGLE:
                return "equal to 90°";
            case OBTUSE_ANGLE:
                return "less than 180° and greater than 90°";
            case STRAIGHT_ANGLE:
                return "equal to 180°";
            case REFLEX_ANGLE:
                return "greater than 180° and less than 360°";
            default:
                return "";
        }
    }
    
    /**
     * Get the image icon for a given angle value.
     * @param degrees Angle value in degrees
     * @return ImageIcon for the angle
     */
    public javax.swing.ImageIcon getAngleImage(int degrees) {
        return com.geometry.entity.Angles.getAngleIcon(degrees);
    }
}