package com.geometry.resources.task3;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * Visual representation for calculating the area of a Triangle
 * This class provides a graphical illustration of how to calculate the area of a triangle
 * with labeled dimensions and formulas.
 */
public class Triangle {
    
    /**
     * Main method for testing the drawing functionality
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Area of Triangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new TrianglePanel());
        frame.setVisible(true);
    }
    
    /**
     * Panel for displaying the triangle area calculation visualization
     * Shows a graphical representation of a triangle with its dimensions and area formula
     */
    public static class TrianglePanel extends JPanel {
        // Triangle parameters
        private int base = 5;     // Base length (b)
        private int height = 3;   // Height (h)
        private double area;      // Area
        
        /**
         * Default constructor
         * Initializes a triangle with default dimensions and calculates its area
         */
        public TrianglePanel() {
            this.area = base * height / 2.0;
        }
        
        /**
         * Parameterized constructor
         * Creates a triangle with specified dimensions and calculates its area
         * 
         * @param base the length of the base
         * @param height the height of the triangle
         */
        public TrianglePanel(int base, int height) {
            this.base = base;
            this.height = height;
            this.area = base * height / 2.0;
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
            int diagramWidth = 220;
            int diagramHeight = 160;
            int centerX = getWidth() / 3 - 30;
            int centerY = getHeight() / 2;
            
            // Draw title
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            String title = "AREA OF TRIANGLE:";
            g2d.drawString(title, padding, padding);
            
            // Calculate the three vertices of the triangle
            int x1 = centerX - diagramWidth / 2;
            int y1 = centerY + diagramHeight / 2;
            
            int x2 = centerX + diagramWidth / 2;
            int y2 = y1;
            
            int x3 = centerX;
            int y3 = centerY - diagramHeight / 2;
            
            // Create and draw triangle path
            Path2D path = new Path2D.Double();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x3, y3);
            path.closePath();
            
            g2d.setColor(new Color(230, 230, 230)); // Light gray fill
            g2d.fill(path);
            g2d.setColor(new Color(180, 180, 180)); // Gray border
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(path);
            
            // Draw height line
            g2d.setColor(new Color(148, 0, 211)); // Purple color
            g2d.setStroke(new BasicStroke(2));
            Line2D heightLine = new Line2D.Double(x3, y3, x3, y1);
            g2d.draw(heightLine);
            
            // Height line arrows
            int arrowSize = 8;
            // Upper arrow
            Polygon upArrow = new Polygon();
            upArrow.addPoint(x3, y3);
            upArrow.addPoint(x3 - arrowSize, y3 + arrowSize * 2);
            upArrow.addPoint(x3 + arrowSize, y3 + arrowSize * 2);
            g2d.fill(upArrow);
            
            // Lower arrow
            Polygon downArrow = new Polygon();
            downArrow.addPoint(x3, y1);
            downArrow.addPoint(x3 - arrowSize, y1 - arrowSize * 2);
            downArrow.addPoint(x3 + arrowSize, y1 - arrowSize * 2);
            g2d.fill(downArrow);
            
            // Draw base line
            g2d.setColor(new Color(255, 165, 0)); // Orange color
            g2d.setStroke(new BasicStroke(2));
            Line2D baseLine = new Line2D.Double(x1, y1 + 15, x2, y2 + 15);
            g2d.draw(baseLine);
            
            // Base line arrows
            // Left arrow
            Polygon leftArrow = new Polygon();
            leftArrow.addPoint(x1, y1 + 15);
            leftArrow.addPoint(x1 + arrowSize * 2, y1 + 15 - arrowSize);
            leftArrow.addPoint(x1 + arrowSize * 2, y1 + 15 + arrowSize);
            g2d.fill(leftArrow);
            
            // Right arrow
            Polygon rightArrow = new Polygon();
            rightArrow.addPoint(x2, y2 + 15);
            rightArrow.addPoint(x2 - arrowSize * 2, y2 + 15 - arrowSize);
            rightArrow.addPoint(x2 - arrowSize * 2, y2 + 15 + arrowSize);
            g2d.fill(rightArrow);
            
            // Draw labels
            g2d.setFont(labelFont);
            
            // HEIGHT label
            g2d.setColor(new Color(148, 0, 211)); // Purple color
            g2d.drawString("HEIGHT", x3 + 10, (y1 + y3) / 2);
            
            // BASE label
            g2d.setColor(new Color(255, 165, 0)); // Orange color
            g2d.drawString("BASE", centerX - 20, y1 + 40);
            
            // Draw formula section
            int formulaX = centerX + 150;
            int formulaY = centerY - 50;
            
            // AREA arrow and label
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("AREA", formulaX, formulaY);
            
            // Draw down arrow
            g2d.drawLine(formulaX + 30, formulaY + 10, formulaX + 30, formulaY + 30);
            g2d.drawLine(formulaX + 30, formulaY + 30, formulaX + 25, formulaY + 25);
            g2d.drawLine(formulaX + 30, formulaY + 30, formulaX + 35, formulaY + 25);
            
            // Formula A = 1/2 × b × h
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 20, formulaY);
            
            // Fraction 1/2
            g2d.drawString("1", formulaX + 45, formulaY - 10);
            g2d.drawLine(formulaX + 40, formulaY, formulaX + 55, formulaY);
            g2d.drawString("2", formulaX + 45, formulaY + 20);
            
            // ×
            g2d.drawString("×", formulaX + 65, formulaY);
            
            // b
            g2d.setColor(new Color(255, 165, 0)); // Orange color for base
            g2d.drawString("b", formulaX + 90, formulaY);
            
            // ×
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("×", formulaX + 115, formulaY);
            
            // h
            g2d.setColor(new Color(148, 0, 211)); // Purple color for height
            g2d.drawString("h", formulaX + 140, formulaY);
            
/*             // h向上箭头
            int hArrowX = formulaX + 145;
            int hArrowY = formulaY + 10;
            Polygon hArrow = new Polygon();
            hArrow.addPoint(hArrowX, hArrowY);
            hArrow.addPoint(hArrowX - arrowSize, hArrowY + arrowSize * 2);
            hArrow.addPoint(hArrowX + arrowSize, hArrowY + arrowSize * 2);
            g2d.setColor(new Color(148, 0, 211)); // 紫色
            g2d.fill(hArrow);
            
            // 底边向上箭头
            int bArrowX = formulaX + 100;
            int bArrowY = formulaY + 10;
            Polygon bArrow = new Polygon();
            bArrow.addPoint(bArrowX, bArrowY);
            bArrow.addPoint(bArrowX - arrowSize, bArrowY + arrowSize * 2);
            bArrow.addPoint(bArrowX + arrowSize, bArrowY + arrowSize * 2);
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.fill(bArrow); */
            
            // Result values
            formulaY += 50;
            g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 20, formulaY);
            
            // Fraction 1/2
            g2d.drawString("1", formulaX + 45, formulaY - 10);
            g2d.drawLine(formulaX + 40, formulaY, formulaX + 55, formulaY);
            g2d.drawString("2", formulaX + 45, formulaY + 20);
            
            // ×
            g2d.drawString("×", formulaX + 65, formulaY);
            
            // Base value
            g2d.setColor(new Color(255, 165, 0)); // Orange color for base
            g2d.drawString(Integer.toString(base), formulaX + 85, formulaY);
            
            // ×
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("×", formulaX + 100, formulaY);
            
            // Height value
            g2d.setColor(new Color(148, 0, 211)); // Purple color for height
            g2d.drawString(Integer.toString(height), formulaX + 120, formulaY);
            
            // =
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("=", formulaX + 140, formulaY);
            
            // Area result
            g2d.drawString(Double.toString(area), formulaX + 160, formulaY);
        }
    }
}
