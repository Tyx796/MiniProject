package com.geometry.resources.task3;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Visual representation for calculating the area of a Rectangle
 * This class provides a graphical illustration of how to calculate the area of a rectangle
 * with labeled dimensions and formulas.
 */
public class Rectangle {
    
    /**
     * Main method for testing the drawing functionality
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Area of Rectangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new RectanglePanel());
        frame.setVisible(true);
    }
    
    /**
     * Panel for displaying the rectangle area calculation visualization
     * Shows a graphical representation of a rectangle with its dimensions and area formula
     */
    public static class RectanglePanel extends JPanel {
        // Rectangle parameters
        private int length = 5;    // Length (l)
        private int width = 4;     // Width (w)
        private int area;          // Area
        
        /**
         * Default constructor
         * Initializes a rectangle with default dimensions and calculates its area
         */
        public RectanglePanel() {
            this.area = length * width;
        }
        
        /**
         * Parameterized constructor
         * Creates a rectangle with specified dimensions and calculates its area
         * 
         * @param length the length of the rectangle
         * @param width the width of the rectangle
         */
        public RectanglePanel(int length, int width) {
            this.length = length;
            this.width = width;
            this.area = length * width;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // Set fonts for different elements
            Font titleFont = new Font("Comic Sans MS", Font.PLAIN, 18);
            Font formulaFont = new Font("Comic Sans MS", Font.BOLD, 22);
            Font labelFont = new Font("Comic Sans MS", Font.BOLD, 18);
            
            // Calculate drawing area dimensions
            int padding = 50;
            int diagramWidth = 200;
            int diagramHeight = 160;
            int centerX = getWidth() / 3;
            int centerY = getHeight() / 2;
            
            // Draw title
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            String title = "AREA OF RECTANGLE:";
            g2d.drawString(title, padding, padding);
            
            // Draw rectangle
            int rectX = centerX - diagramWidth / 2;
            int rectY = centerY - diagramHeight / 2;
            
            // Create and draw rectangle
            g2d.setColor(new Color(230, 230, 230)); // Light gray fill
            g2d.fillRect(rectX, rectY, diagramWidth, diagramHeight);
            g2d.setColor(new Color(180, 180, 180)); // Gray border
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(rectX, rectY, diagramWidth, diagramHeight);
            
            // Draw width line
            g2d.setColor(new Color(56, 176, 170)); // Teal color
            g2d.setStroke(new BasicStroke(2));
            Line2D widthLine = new Line2D.Double(rectX - 20, rectY, rectX - 20, rectY + diagramHeight);
            g2d.draw(widthLine);
            
            // Width line arrows
            int arrowSize = 8;
            // Upper arrow
            Polygon upArrow = new Polygon();
            upArrow.addPoint(rectX - 20, rectY);
            upArrow.addPoint(rectX - 20 - arrowSize, rectY + arrowSize * 2);
            upArrow.addPoint(rectX - 20 + arrowSize, rectY + arrowSize * 2);
            g2d.fill(upArrow);
            
            // Lower arrow
            Polygon downArrow = new Polygon();
            downArrow.addPoint(rectX - 20, rectY + diagramHeight);
            downArrow.addPoint(rectX - 20 - arrowSize, rectY + diagramHeight - arrowSize * 2);
            downArrow.addPoint(rectX - 20 + arrowSize, rectY + diagramHeight - arrowSize * 2);
            g2d.fill(downArrow);
            
            // Draw length line
            g2d.setColor(new Color(144, 238, 144)); // Light green color
            g2d.setStroke(new BasicStroke(2));
            Line2D lengthLine = new Line2D.Double(rectX, rectY + diagramHeight + 20, 
                                                 rectX + diagramWidth, rectY + diagramHeight + 20);
            g2d.draw(lengthLine);
            
            // Length line arrows
            // Left arrow
            Polygon leftArrow = new Polygon();
            leftArrow.addPoint(rectX, rectY + diagramHeight + 20);
            leftArrow.addPoint(rectX + arrowSize * 2, rectY + diagramHeight + 20 - arrowSize);
            leftArrow.addPoint(rectX + arrowSize * 2, rectY + diagramHeight + 20 + arrowSize);
            g2d.fill(leftArrow);
            
            // Right arrow
            Polygon rightArrow = new Polygon();
            rightArrow.addPoint(rectX + diagramWidth, rectY + diagramHeight + 20);
            rightArrow.addPoint(rectX + diagramWidth - arrowSize * 2, rectY + diagramHeight + 20 - arrowSize);
            rightArrow.addPoint(rectX + diagramWidth - arrowSize * 2, rectY + diagramHeight + 20 + arrowSize);
            g2d.fill(rightArrow);
            
            // Draw labels
            g2d.setFont(labelFont);
            
            // WIDTH label
            g2d.setColor(new Color(56, 176, 170)); // Teal color
            g2d.drawString("WIDTH", rectX - 80, centerY);
            
            // LENGTH label
            g2d.setColor(new Color(144, 238, 144)); // Light green color
            g2d.drawString("LENGTH", centerX - 40, rectY + diagramHeight + 50);
            
            // Draw formula section
            int formulaX = centerX + 220;
            int formulaY = centerY - 50;
            
            // AREA arrow and label
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("AREA", formulaX - 20, formulaY);
            
            // Draw down arrow
            g2d.drawLine(formulaX, formulaY + 10, formulaX, formulaY + 30);
            g2d.drawLine(formulaX, formulaY + 30, formulaX - 5, formulaY + 25);
            g2d.drawLine(formulaX, formulaY + 30, formulaX + 5, formulaY + 25);
            
            // Formula A = L × W
            formulaY += 50;
            formulaX -= 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("A", formulaX - 20, formulaY);
            
            g2d.drawString("=", formulaX, formulaY);
            
            g2d.setColor(new Color(144, 238, 144)); // Light green color for length
            g2d.drawString("L", formulaX + 30, formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("×", formulaX + 50, formulaY);
            
            g2d.setColor(new Color(56, 176, 170)); // Teal color for width
            g2d.drawString("W", formulaX + 75, formulaY);
            
           /*  // Length up arrow
            Polygon lArrow = new Polygon();
            int lArrowX = formulaX + 38;
            int lArrowY = formulaY + 13;
            lArrow.addPoint(lArrowX, lArrowY);
            lArrow.addPoint(lArrowX - arrowSize, lArrowY + arrowSize * 2);
            lArrow.addPoint(lArrowX + arrowSize, lArrowY + arrowSize * 2);
            g2d.setColor(new Color(144, 238, 144)); // Light green color
            g2d.fill(lArrow);
            
            // Width up arrow
            Polygon wArrow = new Polygon();
            int wArrowX = formulaX + 85;
            int wArrowY = formulaY + 13;
            wArrow.addPoint(wArrowX, wArrowY);
            wArrow.addPoint(wArrowX - arrowSize, wArrowY + arrowSize * 2);
            wArrow.addPoint(wArrowX + arrowSize, wArrowY + arrowSize * 2);
            g2d.setColor(new Color(56, 176, 170)); // Teal color
            g2d.fill(wArrow); */
            
            // Result values
            formulaY += 50;
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("A", formulaX - 20, formulaY);
            
            g2d.drawString("=", formulaX, formulaY);
            
            g2d.setColor(new Color(144, 238, 144)); // Light green color for length
            String lengthStr = Integer.toString(length);
            int lengthWidth = g2d.getFontMetrics().stringWidth(lengthStr);
            g2d.drawString(lengthStr, formulaX + 25 + (15 - lengthWidth/2), formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("×", formulaX + 50, formulaY);
            
            g2d.setColor(new Color(56, 176, 170)); // Teal color for width
            g2d.drawString(Integer.toString(width), formulaX + 75, formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("=", formulaX + 100, formulaY);
            
            g2d.drawString(Integer.toString(area), formulaX + 130, formulaY);
        }
    }
}
