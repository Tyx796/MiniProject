package com.geometry.resources.task3;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * Visual representation for calculating the area of a Parallelogram
 * This class provides a graphical illustration of how to calculate the area of a parallelogram
 * with labeled dimensions and formulas.
 */
public class Parallelogram {
    
    /**
     * Main method for testing the drawing functionality
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Area of Parallelogram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(new ParallelogramPanel());
        frame.setVisible(true);
    }
    
    /**
     * Panel for displaying the parallelogram area calculation visualization
     * Shows a graphical representation of a parallelogram with its dimensions and area formula
     */
    public static class ParallelogramPanel extends JPanel {
        // Parallelogram parameters
        private int base = 4; // Base length
        private int height = 3; // Height
        private int area; // Area
        
        /**
         * Default constructor
         * Initializes a parallelogram with default dimensions and calculates its area
         */
        public ParallelogramPanel() {
            this.area = base * height;
        }
        
        /**
         * Parameterized constructor
         * Creates a parallelogram with specified dimensions and calculates its area
         * 
         * @param base the length of the base
         * @param height the height of the parallelogram
         */
        public ParallelogramPanel(int base, int height) {
            this.base = base;
            this.height = height;
            this.area = base * height;
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
            int diagramHeight = 150;
            int centerX = getWidth() / 2 - 70;
            int centerY = getHeight() / 2;
            
            // Draw title
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            String title = "AREA OF PARALLELOGRAM:";
            g2d.drawString(title, padding, padding);
            
            // Draw parallelogram
            int shearFactor = 40; // Shear factor determining the slant of the parallelogram
            
            // Calculate the four vertices of the parallelogram
            int scale = 40; // Scale factor to make the shape larger
            
            int x1 = centerX - diagramWidth/2 + shearFactor;
            int y1 = centerY - diagramHeight/2;
            
            int x2 = centerX + diagramWidth/2 + shearFactor;
            int y2 = y1;
            
            int x3 = centerX + diagramWidth/2;
            int y3 = centerY + diagramHeight/2;
            
            int x4 = centerX - diagramWidth/2;
            int y4 = y3;
            
            // Create and draw parallelogram path
            Path2D path = new Path2D.Double();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x3, y3);
            path.lineTo(x4, y4);
            path.closePath();
            
            g2d.setColor(new Color(230, 230, 230)); // Light gray fill
            g2d.fill(path);
            g2d.setColor(new Color(180, 180, 180)); // Gray border
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(path);
            
            // Draw height line
            g2d.setColor(new Color(148, 0, 211)); // Purple color
            g2d.setStroke(new BasicStroke(2));
            Line2D heightLine = new Line2D.Double(x4 + 20, y4, x4 + 20, y1);
            g2d.draw(heightLine);
            
            // Height line T-markers at top and bottom
            int tLength = 10;
            g2d.drawLine(x4 + 20 - tLength, y1, x4 + 20 + tLength, y1);
            g2d.drawLine(x4 + 20 - tLength, y4, x4 + 20 + tLength, y4);
            
            // Draw base line
            g2d.setColor(new Color(255, 165, 0)); // Orange color
            g2d.setStroke(new BasicStroke(2));
            Line2D baseLine = new Line2D.Double(x4, y4 + 15, x3, y3 + 15);
            g2d.draw(baseLine);
            
            // Base line T-markers at left and right
            g2d.drawLine(x4, y4 + 15 - tLength, x4, y4 + 15 + tLength);
            g2d.drawLine(x3, y3 + 15 - tLength, x3, y3 + 15 + tLength);
            
            // Draw labels
            g2d.setFont(labelFont);
            
            // HEIGHT label
            g2d.setColor(new Color(148, 0, 211)); // Purple color
            g2d.drawString("HEIGHT", x4 - 80, (y1 + y4) / 2);
            
            // BASE label
            g2d.setColor(new Color(255, 165, 0)); // Orange color
            g2d.drawString("BASE", (x3 + x4) / 2 - 30, y4 + 40);
            
            // Draw formula section
            int formulaX = x3 + 50;
            int formulaY = centerY - 20;
            
            // AREA arrow and label
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("AREA", formulaX, formulaY);
            
            // Draw down arrow
            g2d.drawLine(formulaX + 30, formulaY + 10, formulaX + 30, formulaY + 30);
            g2d.drawLine(formulaX + 30, formulaY + 30, formulaX + 25, formulaY + 25);
            g2d.drawLine(formulaX + 30, formulaY + 30, formulaX + 35, formulaY + 25);
            
            // Formula A = b × h
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 25, formulaY);
            
            g2d.setColor(new Color(255, 165, 0)); // Orange color for base
            g2d.drawString("b", formulaX + 50, formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("×", formulaX + 75, formulaY);
            
            g2d.setColor(new Color(148, 0, 211)); // Purple color for height
            g2d.drawString("h", formulaX + 100, formulaY);
            
            // Result values
            formulaY += 40;
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 25, formulaY);
            
            g2d.setColor(new Color(255, 165, 0)); // Orange color for base
            g2d.drawString(Integer.toString(base), formulaX + 50, formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("×", formulaX + 75, formulaY);
            
            g2d.setColor(new Color(148, 0, 211)); // Purple color for height
            g2d.drawString(Integer.toString(height), formulaX + 100, formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("=", formulaX + 125, formulaY);
            
            g2d.drawString(Integer.toString(area), formulaX + 150, formulaY);
            
/*             // Base underline
            g2d.setColor(new Color(255, 165, 0)); // Orange color
            g2d.drawLine(formulaX + 50, formulaY + 5, formulaX + 60, formulaY + 5);
            
            // Height underline
            g2d.setColor(new Color(148, 0, 211)); // Purple color
            g2d.drawLine(formulaX + 100, formulaY + 5, formulaX + 110, formulaY + 5); */
        }
    }
}
