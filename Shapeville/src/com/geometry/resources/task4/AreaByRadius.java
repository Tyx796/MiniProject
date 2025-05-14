package com.geometry.resources.task4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * Visual representation for calculating the area of a circle using its radius
 * This class provides a graphical illustration of how to calculate the area of a circle
 * when the radius is known, with labeled dimensions and formulas.
 */
public class AreaByRadius {
    /**
     * Main method for testing the drawing functionality
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Area of Circle by Radius");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new AreaByRadiusPanel());
        frame.setVisible(true);
    }

    /**
     * Panel for displaying the circle area calculation visualization (using radius)
     * Shows a graphical representation of a circle with its radius and area formula
     */
    public static class AreaByRadiusPanel extends JPanel {
        // Circle parameters
        private int radius = 5; // Radius
        private double area; // Area
        private static final String FONT_NAME = "Comic Sans MS"; // Common font name for consistency


        /**
         * Default constructor
         * Initializes a circle with default radius and calculates its area
         */
        public AreaByRadiusPanel() {
            this.area = 3.14 * radius * radius;
        }

        /**
         * Parameterized constructor
         * Creates a circle with specified radius and calculates its area
         * 
         * @param radius the radius of the circle
         */
        public AreaByRadiusPanel(int radius) {
            this.radius = radius;
            this.area = 3.14 * radius * radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Set fonts for different elements
            Font titleFont = new Font(FONT_NAME, Font.PLAIN, 18);
            Font formulaFont = new Font(FONT_NAME, Font.BOLD, 22);
            Font labelFont = new Font(FONT_NAME, Font.BOLD, 18);

            // Calculate drawing area dimensions
            int padding = 50;
            int diagramRadius = 80; // Circle radius for display
            int centerX = getWidth() / 3 - 50;
            int centerY = getHeight() / 2;

            // Draw title
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            String title = "AREA OF CIRCLE:";
            g2d.drawString(title, padding, padding);

            // Draw circle
            int circleX = centerX - diagramRadius;
            int circleY = centerY - diagramRadius;
            int diameter = diagramRadius * 2; // Diameter for display
            g2d.setColor(new Color(230, 230, 230)); // Light gray fill
            g2d.fill(new Ellipse2D.Double(circleX, circleY, diameter, diameter));
            g2d.setColor(new Color(180, 180, 180)); // Gray border
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new Ellipse2D.Double(circleX, circleY, diameter, diameter));

            // Draw radius line
            g2d.setColor(new Color(70, 130, 180)); // Steel blue color
            g2d.setStroke(new BasicStroke(2));
            Line2D radiusLine = new Line2D.Double(centerX, centerY, centerX + diagramRadius, centerY);
            g2d.draw(radiusLine);

            // Arrow at end of radius line
            int arrowSize = 8;
            Polygon arrow = new Polygon();
            arrow.addPoint(centerX + diagramRadius, centerY);
            arrow.addPoint(centerX + diagramRadius - arrowSize, centerY - arrowSize);
            arrow.addPoint(centerX + diagramRadius - arrowSize, centerY + arrowSize);
            g2d.fill(arrow);

            // Draw radius labels
            g2d.setFont(labelFont);
            g2d.setColor(new Color(70, 130, 180)); // Steel blue color
            g2d.drawString("RADIUS", centerX + diagramRadius / 2 - 20, centerY - 10);
            g2d.drawString("r", centerX + diagramRadius / 2 + 10, centerY + 25);

            // Draw formula section
            int formulaX = centerX + 100;
            int formulaY = centerY - 40;

            // AREA label and arrow
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("AREA", formulaX, formulaY);

            // Draw down arrow
            g2d.drawLine(formulaX + 40, formulaY + 10, formulaX + 40, formulaY + 30);
            g2d.drawLine(formulaX + 40, formulaY + 30, formulaX + 35, formulaY + 25);
            g2d.drawLine(formulaX + 40, formulaY + 30, formulaX + 45, formulaY + 25);

            // Formula A = π × r²
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("A", formulaX, formulaY);
            g2d.drawString("=", formulaX + 30, formulaY);
            g2d.drawString("π", formulaX + 60, formulaY);
            g2d.drawString("×", formulaX + 90, formulaY);
            g2d.setColor(new Color(70, 130, 180)); // Steel blue color for radius
            g2d.drawString("r", formulaX + 120, formulaY);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("²", formulaX + 135, formulaY - 10); // Squared notation

            // Result values
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("A", formulaX, formulaY);
            g2d.drawString("=", formulaX + 30, formulaY);
            g2d.drawString("π", formulaX + 60, formulaY);
            g2d.drawString("×", formulaX + 90, formulaY);
            g2d.setColor(new Color(70, 130, 180)); // Steel blue color for radius
            g2d.drawString(Integer.toString(radius), formulaX + 120, formulaY);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("²", formulaX + 135, formulaY - 10); // Squared notation
            g2d.drawString("=", formulaX + 150, formulaY);
            g2d.drawString(String.format("%.2f", area), formulaX + 180, formulaY);
        }
    }
}
