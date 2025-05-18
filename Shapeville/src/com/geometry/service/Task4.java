package com.geometry.service;

import javax.swing.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import com.geometry.resources.task4.AreaByRadius;
import com.geometry.resources.task4.AreaByDiameter;
import com.geometry.resources.task4.CByRadius;
import com.geometry.resources.task4.CByDiameter;

/**
 * Business logic for Task4 - Circle Area and Circumference Calculations.
 * This class manages different types of circle calculations (area and circumference)
 * using both radius and diameter. It handles random value generation, answer validation,
 * and visual representation of the problems.
 */
public class Task4 {
    // Constants for different calculation types
    public static final String AREA_RADIUS = "Area by Radius";
    public static final String AREA_DIAMETER = "Area by Diameter";
    public static final String CIRC_RADIUS = "Circumference by Radius";
    public static final String CIRC_DIAMETER = "Circumference by Diameter";

    // Mathematical constant PI (simplified to 3.14 for elementary education)
    private static final double PI = 3.14;
    // Random number generator for parameter values
    private final Random random = new Random();
    // Current calculation type being practiced
    private String currentType;
    // Current parameter value (radius or diameter)
    private int value;
    // Expected answer for current calculation
    private double answer;
    // Number of remaining attempts for current problem
    private int remainingAttempts = 3;
    // Set to track which calculation types have been completed
    private final Set<String> completedTypes = new HashSet<>();

    /**
     * Select a calculation type and generate a new problem.
     * Generates random parameter value and calculates the expected answer.
     * @param type The type of calculation (area/circumference by radius/diameter)
     * @return true if type selection was successful
     */
    public boolean selectType(String type) {
        currentType = type;
        remainingAttempts = 3;
        // Generate random parameter (1-20)
        value = random.nextInt(20) + 1;
        // Calculate expected answer based on type
        switch (type) {
            case AREA_RADIUS:
                answer = PI * value * value;
                break;
            case AREA_DIAMETER:
                answer = PI * (value / 2.0) * (value / 2.0);
                break;
            case CIRC_RADIUS:
                answer = 2 * PI * value;
                break;
            case CIRC_DIAMETER:
                answer = PI * value;
                break;
        }
        completedTypes.add(type);
        return true;
    }

    /**
     * Check if user's answer is correct within acceptable margin of error.
     * Awards points based on remaining attempts if correct.
     * @param userAnswer The answer provided by the user
     * @return true if answer is correct (within 0.01 margin), false otherwise
     */
    public boolean checkAnswer(double userAnswer) {
        boolean correct = Math.abs(userAnswer - answer) < 0.01;
        if (correct) {
            // Award score based on attempts used
            int attemptsUsed = 3 - remainingAttempts + 1;
            com.geometry.entity.User.addScores("Basic", attemptsUsed);
        } else {
            remainingAttempts--;
        }
        return correct;
    }

    /**
     * Get the current calculation type.
     * @return Current calculation type string
     */
    public String getCurrentType() { return currentType; }

    /**
     * Get the current parameter value (radius or diameter).
     * @return Current value used in calculation
     */
    public int getValue() { return value; }

    /**
     * Get the expected answer for current calculation.
     * @return Expected answer
     */
    public double getAnswer() { return answer; }

    /**
     * Get remaining attempts for current problem.
     * @return Number of attempts remaining
     */
    public int getRemainingAttempts() { return remainingAttempts; }

    /**
     * Check if all calculation types have been completed.
     * @return true if all four types (area/circumference by radius/diameter) are completed
     */
    public boolean isAllCompleted() {
        return (completedTypes.contains(AREA_RADIUS) || completedTypes.contains(AREA_DIAMETER))
            && (completedTypes.contains(CIRC_RADIUS) || completedTypes.contains(CIRC_DIAMETER));
    }

    /**
     * Reset the task state.
     * Clears completed types, resets attempts, and current type.
     */
    public void reset() {
        completedTypes.clear();
        remainingAttempts = 3;
        currentType = null;
    }

    /**
     * Create visual panel for current calculation type.
     * Returns appropriate visualization panel based on current calculation type.
     * @return JPanel containing visual representation of the problem
     */
    public JPanel createDisplayPanel() {
        switch (currentType) {
            case AREA_RADIUS:
                return new AreaByRadius.AreaByRadiusPanel(value);
            case AREA_DIAMETER:
                return new AreaByDiameter.AreaByDiameterPanel(value);
            case CIRC_RADIUS:
                return new CByRadius.CByRadiusPanel(value);
            case CIRC_DIAMETER:
                return new CByDiameter.CByDiameterPanel(value);
            default:
                return new JPanel();
        }
    }

    /**
     * Get the formula for current calculation type.
     * @return Mathematical formula as string (e.g., "A = π × r²")
     */
    public String getFormula() {
        switch (currentType) {
            case AREA_RADIUS:
                return "A = π × r²";
            case AREA_DIAMETER:
                return "A = π × (d/2)²";
            case CIRC_RADIUS:
                return "C = 2 × π × r";
            case CIRC_DIAMETER:
                return "C = π × d";
            default:
                return "";
        }
    }

    /**
     * Get the parameter label for current calculation type.
     * @return Label indicating whether value is radius or diameter
     */
    public String getParamLabel() {
        switch (currentType) {
            case AREA_RADIUS:
            case CIRC_RADIUS:
                return "Radius: ";
            case AREA_DIAMETER:
            case CIRC_DIAMETER:
                return "Diameter: ";
            default:
                return "";
        }
    }
}
