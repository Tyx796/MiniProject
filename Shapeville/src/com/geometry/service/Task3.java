package com.geometry.service;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import com.geometry.resources.task3.Triangle;
import com.geometry.resources.task3.Parallelogram;
import com.geometry.resources.task3.Trapezium;
// Use fully qualified Rectangle to avoid ambiguity
import com.geometry.resources.task3.Rectangle;

/**
 * Business logic for Task3 - Area calculation for basic shapes (Rectangle, Triangle, Parallelogram, Trapezium).
 * This class manages random parameter generation, area calculation, answer checking, and progress tracking.
 */
public class Task3 {
    // Shape type constants
    public static final String RECTANGLE = "Rectangle";
    public static final String TRIANGLE = "Triangle";
    public static final String PARALLELOGRAM = "Parallelogram";
    public static final String TRAPEZIUM = "Trapezium";
    
    // Random number generator for parameter generation
    private final Random random = new Random();
    
    // Current selected shape type
    private String currentShape;
    
    // Parameters for the current shape (lengths, heights, etc.)
    private int[] parameters;
    
    // Calculated area for the current shape
    private double area;
    
    // Set of shapes that have already been processed in this session
    private final Set<String> processedShapes = new HashSet<>();
    
    // Remaining attempts for the current question
    private int remainingAttempts = 3;
    
    /**
     * Select a shape type and generate random parameters for it.
     * @param shapeType The type of shape to select
     * @return true if selection and parameter generation succeed
     */
    public boolean selectShape(String shapeType) {
        // Reset attempts for new shape
        remainingAttempts = 3;
        
        // Set current shape type
        currentShape = shapeType;
        
        // Generate random parameters for the selected shape
        generateRandomParameters();
        
        // Calculate area for the generated parameters
        calculateArea();
        
        // Mark this shape as processed
        processedShapes.add(shapeType);
        
        return true;
    }
    
    /**
     * Generate random parameters for the current shape (values in range 1-20).
     */
    private void generateRandomParameters() {
        switch (currentShape) {
            case RECTANGLE:
                // Rectangle needs length and width
                parameters = new int[2];
                parameters[0] = random.nextInt(20) + 1; // Length
                parameters[1] = random.nextInt(20) + 1; // Width
                break;
            case TRIANGLE:
                // Triangle needs base and height
                parameters = new int[2];
                parameters[0] = random.nextInt(20) + 1; // Base
                parameters[1] = random.nextInt(20) + 1; // Height
                break;
            case PARALLELOGRAM:
                // Parallelogram needs base and height
                parameters = new int[2];
                parameters[0] = random.nextInt(20) + 1; // Base
                parameters[1] = random.nextInt(20) + 1; // Height
                break;
            case TRAPEZIUM:
                // Trapezium needs top, bottom, and height
                parameters = new int[3];
                parameters[0] = random.nextInt(20) + 1; // Top length
                parameters[1] = random.nextInt(20) + 1; // Bottom length
                parameters[2] = random.nextInt(20) + 1; // Height
                break;
        }
    }
    
    /**
     * Calculate the area for the current shape and parameters.
     */
    private void calculateArea() {
        switch (currentShape) {
            case RECTANGLE:
                // Rectangle area = length × width
                area = parameters[0] * parameters[1];
                break;
            case TRIANGLE:
                // Triangle area = base × height / 2
                area = parameters[0] * parameters[1] / 2.0;
                break;
            case PARALLELOGRAM:
                // Parallelogram area = base × height
                area = parameters[0] * parameters[1];
                break;
            case TRAPEZIUM:
                // Trapezium area = (top + bottom) × height / 2
                area = (parameters[0] + parameters[1]) * parameters[2] / 2.0;
                break;
        }
    }
    
    /**
     * Check if the user's answer is correct (tolerance < 1).
     * @param userAnswer The answer provided by the user
     * @return true if correct, false otherwise
     */
    public boolean checkAnswer(double userAnswer) {
        // Allow a tolerance of 1 for floating point answers
        boolean isCorrect = Math.abs(userAnswer - area) < 1;
        
        if (isCorrect) {
            // Award score based on remaining attempts
            int attemptsUsed = 3 - remainingAttempts + 1;
            com.geometry.entity.User.addScores("Basic", attemptsUsed);
        } else {
            // Decrement attempts if incorrect
            remainingAttempts--;
        }
        
        return isCorrect;
    }
    
    /**
     * Get the current shape type.
     * @return The current shape type
     */
    public String getCurrentShape() {
        return currentShape;
    }
    
    /**
     * Get the parameters for the current shape.
     * @return Array of parameters
     */
    public int[] getParameters() {
        return parameters;
    }
    
    /**
     * Get the calculated area for the current shape.
     * @return Area value
     */
    public double getArea() {
        return area;
    }
    
    /**
     * Get the number of remaining attempts for the current question.
     * @return Remaining attempts
     */
    public int getRemainingAttempts() {
        return remainingAttempts;
    }
    
    /**
     * Check if the user has completed all shapes in this task.
     * @return true if all shapes are completed, false otherwise
     */
    public boolean isAllShapesCompleted() {
        // There are four shapes: Rectangle, Triangle, Parallelogram, Trapezium
        String[] allShapes = {RECTANGLE, TRIANGLE, PARALLELOGRAM, TRAPEZIUM};
        // Check if all shapes have been processed
        return processedShapes.containsAll(Arrays.asList(allShapes));
    }
    
    /**
     * Get the area formula description for the current shape.
     * @return Area formula as a string
     */
    public String getAreaFormula() {
        switch (currentShape) {
            case RECTANGLE:
                return "Area = Length × Width";
            case TRIANGLE:
                return "Area = (Base × Height) ÷ 2";
            case PARALLELOGRAM:
                return "Area = Base × Height";
            case TRAPEZIUM:
                return "Area = ((a + b) ÷ 2) × Height";
            default:
                return "";
        }
    }
    
    /**
     * Get the parameter names for the current shape.
     * @return Array of parameter names
     */
    public String[] getParameterNames() {
        switch (currentShape) {
            case RECTANGLE:
                return new String[]{"Length", "Width"};
            case TRIANGLE:
                return new String[]{"Base", "Height"};
            case PARALLELOGRAM:
                return new String[]{"Base", "Height"};
            case TRAPEZIUM:
                return new String[]{"Top Length", "Bottom Length", "Height"};
            default:
                return new String[]{};
        }
    }
    
    /**
     * Reset the task state, clearing progress and attempts.
     */
    public void reset() {
        processedShapes.clear();
        remainingAttempts = 3;
        currentShape = null;
    }
    
    /**
     * Create a JPanel displaying the current shape with its parameters.
     * @return JPanel containing the shape drawing
     */
    public JPanel createShapeDisplayPanel() {
        JPanel panel = new JPanel();
        switch (currentShape) {
            case RECTANGLE:
                // Create rectangle panel with length and width
                panel = new com.geometry.resources.task3.Rectangle.RectanglePanel(parameters[0], parameters[1]);
                break;
            case TRIANGLE:
                // Create triangle panel with base and height
                panel = new Triangle.TrianglePanel(parameters[0], parameters[1]);
                break;
            case PARALLELOGRAM:
                // Create parallelogram panel with base and height
                panel = new Parallelogram.ParallelogramPanel(parameters[0], parameters[1]);
                break;
            case TRAPEZIUM:
                // Create trapezium panel with top, bottom, and height
                panel = new Trapezium.TrapeziumPanel(parameters[0], parameters[1], parameters[2]);
                break;
        }
        return panel;
    }
}
