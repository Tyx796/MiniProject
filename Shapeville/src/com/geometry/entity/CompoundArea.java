package com.geometry.entity;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Entity class for compound geometric shapes.
 * Manages properties and calculations for composite shapes.
 */
public class CompoundArea {
    private int questionNumber;  // Question number (1-6)
    private Map<String, Integer> dimensions;  // Stores dimensions of the shape
    // private static List<Double> correctArea;  // Correct answer (commented out)
    private double correctArea;  // Correct area value
    private String unit;         // Area unit (cm² or m²)
    private String formula;      // Calculation formula
    private String questionImagePath;  // Path to question image
    private String answerImagePath;    // Path to answer image


    /**
     * Create a compound area question with the specified number
     * @param questionNumber Question number (1-6)
     */
    public CompoundArea(int questionNumber) {
        this.questionNumber = questionNumber;
        this.dimensions = new HashMap<>();
        this.questionImagePath = "/com/geometry/resources/bonus1/questions/" + questionNumber + ".png";
        this.answerImagePath = "/com/geometry/resources/bonus1/answers/answer" + questionNumber + ".jpg";
        // initializeDimensions();
        initializeAnswerAndUnit();
    }

    
    /**
     * Initialize correct answer and unit for each question
     */
    private void initializeAnswerAndUnit() {
        switch (questionNumber) {
            case 1:
                correctArea = 331.0;
                unit = "cm2";
                break;
            case 2:
                correctArea = 598.0;
                unit = "cm2";
                break;
            case 3:
                correctArea = 216.0;
                unit = "m2";
                break;
            case 4:
                correctArea = 3456.0;
                unit = "m2";
                break;
            case 5:
                correctArea = 18.0;
                unit = "m2";
                break;
            case 6:
                correctArea = 174.0;
                unit = "m2";
                break;
            default:
                correctArea = 0.0;
                unit = "m2";
                break;
        }
    }

    // Getters
    /**
     * @return The question number
     */
    public int getQuestionNumber() { return questionNumber; }
    
    
    /**
     * @return The correct area value
     */
    public double getCorrectArea() { return correctArea; }
    
    /**
     * @return The area unit (e.g., "cm2", "m2")
     */
    public String getUnit() { return unit; }
    
    /**
     * @return The calculation formula
     */
    public String getFormula() { return formula; }
    
    /**
     * @return Path to the question image
     */
    public String getQuestionImagePath() { return questionImagePath; }
    
    /**
     * @return Path to the answer image
     */
    public String getAnswerImagePath() { return answerImagePath; }

    /**
     * Check if the user's answer is correct (within 0.1 tolerance)
     * @param userAnswer The user's answer to check
     * @return true if the answer is correct, false otherwise
     */
    public boolean checkAnswer(double userAnswer) {
        return Math.abs(userAnswer - correctArea) < 0.01;
    }

    /**
     * Get the formatted area with unit
     * @return Formatted area string with unit
     */
    public String getFormattedArea() {
        return String.format("%.2f%s", correctArea, unit);
    }

    /**
     * Get the question image as an ImageIcon
     * @return ImageIcon for the question
     */
    public ImageIcon getQuestionImage() {
        return new ImageIcon(getClass().getResource(questionImagePath));
    }

    /**
     * Get the answer image as an ImageIcon
     * @return ImageIcon for the answer
     */
    public ImageIcon getAnswerImage() {
        return new ImageIcon(getClass().getResource(answerImagePath));
    }
}
