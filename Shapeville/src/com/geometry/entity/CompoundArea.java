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
    private int questionNumber;  // Question number (1-9)
    private Map<String, Integer> dimensions;  // Stores dimensions of the shape
    // private static List<Double> correctArea;  // Correct answer (commented out)
    private double correctArea;  // Correct area value
    private String unit;         // Area unit (cm² or m²)
    private String formula;      // Calculation formula
    private String questionImagePath;  // Path to question image
    private String answerImagePath;    // Path to answer image


    /**
     * Create a compound area question with the specified number
     * @param questionNumber Question number (1-9)
     */
    public CompoundArea(int questionNumber) {
        this.questionNumber = questionNumber;
        this.dimensions = new HashMap<>();
        this.questionImagePath = "/com/geometry/resources/bonus1/questions/" + questionNumber + ".png";
        this.answerImagePath = "/com/geometry/resources/bonus1/answers/answer" + questionNumber + ".jpg";
        initializeDimensions();
        initializeAnswerAndUnit();
    }

    /**
     * Initialize dimensions for the shape based on question number
     */
    private void initializeDimensions() {
        switch (questionNumber) {
            case 1:
                dimensions.put("length", 8);
                dimensions.put("width", 4);
                dimensions.put("height", 6);
                formula = "Area = (length × width) + (width × height)";
                break;
            case 2:
                dimensions.put("base", 10);
                dimensions.put("height", 6);
                dimensions.put("top", 4);
                formula = "Area = base × height - (base - top) × height/2";
                break;
            case 3:
                dimensions.put("length", 8);
                dimensions.put("width", 4);
                dimensions.put("small_width", 2);
                dimensions.put("small_height", 2);
                formula = "Area = length × width - small_width × small_height";
                break;
            case 4:
                dimensions.put("length", 10);
                dimensions.put("width", 6);
                dimensions.put("small_length", 4);
                formula = "Area = length × width - small_length × width";
                break;
            case 5:
                dimensions.put("base", 8);
                dimensions.put("height", 6);
                dimensions.put("small_base", 4);
                formula = "Area = (base × height) - (small_base × height/2)";
                break;
            case 6:
                dimensions.put("length", 8);
                dimensions.put("width", 6);
                dimensions.put("small_width", 2);
                formula = "Area = length × width + (width × small_width)";
                break;
            case 7:
                dimensions.put("length", 10);
                dimensions.put("width", 6);
                dimensions.put("small_length", 4);
                dimensions.put("small_width", 2);
                formula = "Area = (length × width) + (small_length × small_width)";
                break;
            case 8:
                dimensions.put("length", 8);
                dimensions.put("width", 6);
                dimensions.put("small_length", 4);
                formula = "Area = (length × width) + (small_length × width)";
                break;
            case 9:
                dimensions.put("outer_length", 10);
                dimensions.put("inner_length", 6);
                dimensions.put("width", 4);
                formula = "Area = (outer_length × width) - (inner_length × width)";
                break;
        }
    }

    /**
     * Initialize correct answer and unit for each question
     */
    private void initializeAnswerAndUnit() {
        switch (questionNumber) {
            case 1:
                correctArea = 213.5;
                unit = "cm2";
                break;
            case 2:
                correctArea = 331.0;
                unit = "cm2";
                break;
            case 3:
                correctArea = 216.0;
                unit = "m2";
                break;
            case 4:
                correctArea = 216.0;
                unit = "m2";
                break;
            case 5:
                correctArea = 216.0;
                unit = "m2";
                break;
            case 6:
                correctArea = 216.0;
                unit = "m2";
                break;
            case 7:
                correctArea = 3456.0;
                unit = "m2";
                break;
            case 8:
                correctArea = 174.0;
                unit = "m2";
                break;
            case 9:
                correctArea = 174.0;
                unit = "m2";
                break;
        }
    }

    /**
     * Calculate area based on dimensions and question type
     */
    private void calculateArea() {
        switch (questionNumber) {
            case 1:
                correctArea = (dimensions.get("length") * dimensions.get("width")) +
                            (dimensions.get("width") * dimensions.get("height"));
                break;
            case 2:
                double trapezoidArea = dimensions.get("base") * dimensions.get("height") -
                                     (dimensions.get("base") - dimensions.get("top")) * dimensions.get("height") / 2.0;
                correctArea = trapezoidArea;
                break;
            case 3:
                correctArea = dimensions.get("length") * dimensions.get("width") -
                            dimensions.get("small_width") * dimensions.get("small_height");
                break;
            case 4:
                correctArea = dimensions.get("length") * dimensions.get("width") -
                            dimensions.get("small_length") * dimensions.get("width");
                break;
            case 5:
                correctArea = (dimensions.get("base") * dimensions.get("height")) -
                            (dimensions.get("small_base") * dimensions.get("height") / 2.0);
                break;
            case 6:
                correctArea = dimensions.get("length") * dimensions.get("width") +
                            (dimensions.get("width") * dimensions.get("small_width"));
                break;
            case 7:
                correctArea = (dimensions.get("length") * dimensions.get("width")) +
                            (dimensions.get("small_length") * dimensions.get("small_width"));
                break;
            case 8:
                correctArea = (dimensions.get("length") * dimensions.get("width")) +
                            (dimensions.get("small_length") * dimensions.get("width"));
                break;
            case 9:
                correctArea = (dimensions.get("outer_length") * dimensions.get("width")) -
                            (dimensions.get("inner_length") * dimensions.get("width"));
                break;
        }
    }

    // Getters
    /**
     * @return The question number
     */
    public int getQuestionNumber() { return questionNumber; }
    
    /**
     * @return Map of shape dimensions
     */
    public Map<String, Integer> getDimensions() { return dimensions; }
    
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
        return Math.abs(userAnswer - correctArea) < 0.1;
    }

    /**
     * Get the formatted area with unit
     * @return Formatted area string with unit
     */
    public String getFormattedArea() {
        return String.format("%.1f%s", correctArea, unit);
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
