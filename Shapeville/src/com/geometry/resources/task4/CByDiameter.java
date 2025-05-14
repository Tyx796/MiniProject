package com.geometry.resources.task4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * Visual representation for calculating the circumference of a circle using its diameter
 * This class provides a graphical illustration of how to calculate the circumference
 * of a circle when the diameter is known, with labeled dimensions and formulas.
 */
public class CByDiameter {
    /**
     * Main method for testing the drawing functionality
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Circumference by Diameter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new CByDiameterPanel());
        frame.setVisible(true);
    }

    /**
     * Panel for displaying the circle circumference calculation visualization (using diameter)
     * Shows a graphical representation of a circle with its diameter and circumference formula
     */
    public static class CByDiameterPanel extends JPanel {
        // Circle parameters
        private int diameter = 10; // Diameter
        private double circumference; // Circumference
        private static final String FONT_NAME = "Comic Sans MS"; // Common font name for consistency


        /**
         * Default constructor
         * Initializes a circle with default diameter and calculates its circumference
         */
        public CByDiameterPanel() {
            this.circumference = 3.14 * diameter;
        }

        /**
         * Parameterized constructor
         * Creates a circle with specified diameter and calculates its circumference
         * 
         * @param diameter the diameter of the circle
         */
        public CByDiameterPanel(int diameter) {
            this.diameter = diameter;
            this.circumference = 3.14 * diameter;
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
            String title = "CIRCUMFERENCE OF CIRCLE:";
            g2d.drawString(title, padding, padding);

            // Draw circle
            int circleX = centerX - diagramRadius;
            int circleY = centerY - diagramRadius;
            int d = diagramRadius * 2; // Diameter for display
            g2d.setColor(new Color(230, 230, 230)); // Light gray fill
            g2d.fill(new Ellipse2D.Double(circleX, circleY, d, d));
            g2d.setColor(new Color(180, 180, 180)); // Gray border
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new Ellipse2D.Double(circleX, circleY, d, d));

            // Draw diameter line
            g2d.setColor(new Color(255, 99, 71)); // Tomato red color
            g2d.setStroke(new BasicStroke(2));
            Line2D diameterLine = new Line2D.Double(centerX - diagramRadius, centerY, centerX + diagramRadius, centerY);
            g2d.draw(diameterLine);

            // Arrows at both ends of diameter line
            int arrowSize = 8;
            Polygon leftArrow = new Polygon();
            leftArrow.addPoint(centerX - diagramRadius, centerY);
            leftArrow.addPoint(centerX - diagramRadius + arrowSize, centerY - arrowSize);
            leftArrow.addPoint(centerX - diagramRadius + arrowSize, centerY + arrowSize);
            g2d.fill(leftArrow);
            Polygon rightArrow = new Polygon();
            rightArrow.addPoint(centerX + diagramRadius, centerY);
            rightArrow.addPoint(centerX + diagramRadius - arrowSize, centerY - arrowSize);
            rightArrow.addPoint(centerX + diagramRadius - arrowSize, centerY + arrowSize);
            g2d.fill(rightArrow);

            // Draw diameter labels
            g2d.setFont(labelFont);
            g2d.setColor(new Color(255, 99, 71)); // Tomato red color
            g2d.drawString("DIAMETER", centerX - 40, centerY - 15);
            g2d.drawString("d", centerX - 10, centerY + 30);

            // Draw formula section
            int formulaX = centerX + 100;
            int formulaY = centerY - 40;

            // Circumference label and arrow
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("CIRCUMFERENCE", formulaX, formulaY);

            // Draw down arrow
            g2d.drawLine(formulaX + 100, formulaY + 10, formulaX + 100, formulaY + 30);
            g2d.drawLine(formulaX + 100, formulaY + 30, formulaX + 95, formulaY + 25);
            g2d.drawLine(formulaX + 100, formulaY + 30, formulaX + 105, formulaY + 25);

            // Formula C = π × d
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("C", formulaX, formulaY);
            g2d.drawString("=", formulaX + 30, formulaY);
            g2d.drawString("π", formulaX + 60, formulaY);
            g2d.drawString("×", formulaX + 90, formulaY);
            g2d.setColor(new Color(255, 99, 71)); // Tomato red color for diameter
            g2d.drawString("d", formulaX + 120, formulaY);

            // Result values
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("C", formulaX, formulaY);
            g2d.drawString("=", formulaX + 30, formulaY);
            g2d.drawString("π", formulaX + 60, formulaY);
            g2d.drawString("×", formulaX + 90, formulaY);
            g2d.setColor(new Color(255, 99, 71)); // Tomato red color for diameter
            g2d.drawString(Integer.toString(diameter), formulaX + 120, formulaY);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("=", formulaX + 155, formulaY);
            g2d.drawString(String.format("%.2f", circumference), formulaX + 170, formulaY);
        }
    }
}
