package com.geometry.entity;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity class for circular sector area calculations.
 * Manages sector area questions and their properties.
 */
public class SectorArea {
    private int questionNumber;      // Question number (1-8)
    private double correctArea;      // Correct area value
    private String unit;             // Area unit (cm², ft², m², etc.)
    private String questionImagePath;  // Path to question image
    private String answerImagePath;    // Path to answer image

    /**
     * Create a sector area question with the specified number
     * @param questionNumber Question number (1-8)
     */
    public SectorArea(int questionNumber) {
        this.questionNumber = questionNumber;
        this.questionImagePath = "/com/geometry/resources/bonus2/questions/" + questionNumber + ".png";
        this.answerImagePath = "/com/geometry/resources/bonus2/answers/" + questionNumber + ".jpg";
        initializeAnswerAndUnit();
    }

    /**
     * Initialize correct answer and unit for each question
     */
    private void initializeAnswerAndUnit() {
        switch (questionNumber) {
            case 1:
                correctArea = 50.24;
                unit = "cm2";
                break;
            case 2:
                correctArea = 367.38;
                unit = "ft2";
                break;
            case 3:
                correctArea = 755.69;
                unit = "cm2";
                break;
            case 4:
                correctArea = 464.37;
                unit = "ft2";
                break;
            case 5:
                correctArea = 10.68;
                unit = "m2";
                break;
            case 6:
                correctArea = 150.72;
                unit = "in2";
                break;
            case 7:
                correctArea = 351.68;
                unit = "yd2";
                break;
            case 8:
                correctArea = 490.63;
                unit = "mm2";
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
     * @return The area unit (e.g., "cm2", "ft2")
     */
    public String getUnit() { return unit; }
    
    /**
     * @return Path to the question image
     */
    public String getQuestionImagePath() { return questionImagePath; }
    
    /**
     * @return Path to the answer image
     */
    public String getAnswerImagePath() { return answerImagePath; }

    /**
     * Check if the user's answer is correct (within 0.01 tolerance)
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
