package com.geometry.resources.task3;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * Visual representation for calculating the area of a Trapezium
 * This class provides a graphical illustration of how to calculate the area of a trapezium
 * with labeled dimensions and formulas.
 */
public class Trapezium {
    
    /**
     * Main method for testing the drawing functionality
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Area of Trapezium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new TrapeziumPanel());
        frame.setVisible(true);
    }
    
    /**
     * Panel for displaying the trapezium area calculation visualization
     * Shows a graphical representation of a trapezium with its dimensions and area formula
     */
    public static class TrapeziumPanel extends JPanel {
        // Trapezium parameters
        private int topLength = 3;      // Top length (a)
        private int bottomLength = 4;   // Bottom length (b)
        private int height = 3;         // Height (h)
        private double area;            // Area
        private double normalizedTopLength;     // Normalized top length for drawing
        private double normalizedBottomLength;  // Normalized bottom length for drawing  
        private double normalizedHeight;        // Normalized height for drawing
                
                /**
                 * Default constructor
                 * Initializes a trapezium with default dimensions and calculates its area
                 */
                public TrapeziumPanel() {
                    this.area = (topLength + bottomLength) * height / 2.0;
                }
                
                /**
                 * Parameterized constructor
                 * Creates a trapezium with specified dimensions and calculates its area
                 * 
                 * @param topLength the length of the top side
                 * @param bottomLength the length of the bottom side
                 * @param height the height of the trapezium
                 */
                public TrapeziumPanel(int topLength, int bottomLength, int height) {
                    this.topLength = topLength;
                    this.bottomLength = bottomLength;
                    this.height = height;
                    this.area = (topLength + bottomLength) * height / 2.0;
                    
                    // Normalize values for drawing
                    // Ensures the shape size is appropriate and avoids display issues
                    // due to values that are too large or too small
                    // Normalizes values to a suitable range (e.g., 1-5)
                    double maxValue = Math.max(Math.max(topLength, bottomLength), height);
                    if (maxValue > 10) {
                        // Scale down if maximum value exceeds 10
                        double scaleFactor = 5.0 / maxValue; // Normalize to maximum of 5
                        this.normalizedTopLength = topLength * scaleFactor;
                this.normalizedBottomLength = bottomLength * scaleFactor;
                this.normalizedHeight = height * scaleFactor;
            } else if (maxValue < 5) {
                // Scale up if maximum value is too small
                double scaleFactor = 3.0 / maxValue; // Normalize to maximum of 3
                this.normalizedTopLength = topLength * scaleFactor;
                this.normalizedBottomLength = bottomLength * scaleFactor;
                this.normalizedHeight = height * scaleFactor;
            } else if (Math.min(Math.min(topLength, bottomLength), height) < 3) {
                // Scale up if minimum value is too small
                double minValue = Math.min(Math.min(topLength, bottomLength), height);
                double scaleFactor = 3.0 / minValue; // Ensure minimum value is at least 3
                this.normalizedTopLength = topLength * scaleFactor;
                this.normalizedBottomLength = bottomLength * scaleFactor;
                this.normalizedHeight = height * scaleFactor;
            } else {
                // Values are in appropriate range, keep original values
                this.normalizedTopLength = topLength;
                this.normalizedBottomLength = bottomLength;
                this.normalizedHeight = height;
            }
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // Set fonts for different elements
            Font titleFont = new Font("Comic Sans MS", Font.PLAIN, 18);
            Font formulaFont = new Font("Comic Sans MS", Font.BOLD, 20);
            Font labelFont = new Font("Comic Sans MS", Font.BOLD, 16);
            
            // Calculate drawing area dimensions
            int padding = 50;
            int diagramWidth = 200;
            int diagramHeight = 120;
            int centerX = getWidth() / 3;
            int centerY = getHeight() / 2;
            
            // Draw title
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            String title = "AREA OF TRAPEZIUM:";
            g2d.drawString(title, padding, padding);
            
            // Draw trapezium
            // Scale factor to make the shape larger
            double scale = 40;
            
            // Calculate the four vertices of the trapezium
            double topOffset = (normalizedBottomLength - normalizedTopLength) * scale / 2;
            
            int x1 = (int) (centerX - normalizedBottomLength * scale / 2 + topOffset);
            int y1 = centerY - diagramHeight / 2;
            
            int x2 = (int) (centerX + normalizedBottomLength * scale / 2 - topOffset);
            int y2 = y1;
            
            int x3 = (int) (centerX + normalizedBottomLength * scale / 2);
            int y3 = centerY + diagramHeight / 2;
            
            int x4 = (int) (centerX - normalizedBottomLength * scale / 2);
            int y4 = y3;
            
            // Create and draw trapezium path
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
            Line2D heightLine = new Line2D.Double(centerX, y1, centerX, y3);
            g2d.draw(heightLine);
            
            // Up and down arrows for height line
            int arrowSize = 7;
            Polygon upArrow = new Polygon();
            upArrow.addPoint(centerX, y1);
            upArrow.addPoint(centerX - arrowSize, y1 + arrowSize * 2);
            upArrow.addPoint(centerX + arrowSize, y1 + arrowSize * 2);
            g2d.fill(upArrow);
            
            Polygon downArrow = new Polygon();
            downArrow.addPoint(centerX, y3);
            downArrow.addPoint(centerX - arrowSize, y3 - arrowSize * 2);
            downArrow.addPoint(centerX + arrowSize, y3 - arrowSize * 2);
            g2d.fill(downArrow);
            
            // Draw top line (a)
            g2d.setColor(new Color(199, 21, 133)); // Magenta color
            g2d.setStroke(new BasicStroke(2));
            Line2D topLine = new Line2D.Double(x1, y1, x2, y2);
            g2d.draw(topLine);
            
            // Arrows for top line
            Polygon leftArrow = new Polygon();
            leftArrow.addPoint(x1, y1);
            leftArrow.addPoint(x1 + arrowSize * 2, y1 - arrowSize);
            leftArrow.addPoint(x1 + arrowSize * 2, y1 + arrowSize);
            g2d.fill(leftArrow);
            
            Polygon rightArrow = new Polygon();
            rightArrow.addPoint(x2, y2);
            rightArrow.addPoint(x2 - arrowSize * 2, y2 - arrowSize);
            rightArrow.addPoint(x2 - arrowSize * 2, y2 + arrowSize);
            g2d.fill(rightArrow);
            
            // Draw bottom line (b)
            g2d.setColor(new Color(255, 165, 0)); // Orange color
            g2d.setStroke(new BasicStroke(2));
            Line2D bottomLine = new Line2D.Double(x4, y4, x3, y3);
            g2d.draw(bottomLine);
            
            // Arrows for bottom line
            leftArrow = new Polygon();
            leftArrow.addPoint(x4, y4);
            leftArrow.addPoint(x4 + arrowSize * 2, y4 - arrowSize);
            leftArrow.addPoint(x4 + arrowSize * 2, y4 + arrowSize);
            g2d.fill(leftArrow);
            
            rightArrow = new Polygon();
            rightArrow.addPoint(x3, y3);
            rightArrow.addPoint(x3 - arrowSize * 2, y3 - arrowSize);
            rightArrow.addPoint(x3 - arrowSize * 2, y3 + arrowSize);
            g2d.fill(rightArrow);
            
            // Draw labels
            g2d.setFont(labelFont);
            
            // Top length label
            g2d.setColor(new Color(199, 21, 133)); // Magenta color
            g2d.drawString("a", (x1 + x2) / 2 - 10, y1 - 5);
            
            // Height label
            g2d.setColor(new Color(148, 0, 211)); // Purple color
            g2d.drawString("HEIGHT", centerX + 10, (y1 + y3) / 2);
            
            // Bottom length label
            g2d.setColor(new Color(255, 165, 0)); // Orange color
            g2d.drawString("b", (x3 + x4) / 2 - 5, y3 + 25);
            
            // Draw formula section
            int formulaX = centerX + 170;
            int formulaY = centerY - 50;
            
            // AREA arrow and label
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("AREA", formulaX, formulaY);
            
            // Draw down arrow
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawLine(formulaX + 20, formulaY + 10, formulaX + 20, formulaY + 25);
            g2d.drawLine(formulaX + 20, formulaY + 25, formulaX + 15, formulaY + 20);
            g2d.drawLine(formulaX + 20, formulaY + 25, formulaX + 25, formulaY + 20);
            
            // Formula A = (a + b)/2 × h
            formulaY += 40;
            formulaX -= 40;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 25, formulaY);
            
            // Left parenthesis
            g2d.drawString("(", formulaX + 45, formulaY);
            
            // a
            g2d.setColor(new Color(199, 21, 133)); // Magenta color
            g2d.drawString("a", formulaX + 55, formulaY);
            
            // +
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("+", formulaX + 70, formulaY);
            
            // b
            g2d.setColor(new Color(255, 165, 0)); // Orange color
            g2d.drawString("b", formulaX + 85, formulaY);
            
            // Right parenthesis
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString(")", formulaX + 95, formulaY);
            
            // Division by 2
            int lineY = formulaY + 5;
            g2d.drawLine(formulaX + 45, lineY, formulaX + 95, lineY);
            g2d.drawString("2", formulaX + 70, formulaY + 20);
            
            // ×
            g2d.drawString("×", formulaX + 110, formulaY);
            
            // h
            g2d.setColor(new Color(148, 0, 211)); // Purple color for height
            g2d.drawString("h", formulaX + 130, formulaY);
            
            // Underline for height
            g2d.drawLine(formulaX + 130, formulaY + 3, formulaX + 138, formulaY + 3);
            
            // Result values
            formulaY += 50;
            g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            
            // Calculate width of each digit for dynamic position adjustment
            FontMetrics fm = g2d.getFontMetrics();
            
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("A", formulaX, formulaY);
            
            int currentX = formulaX + fm.stringWidth("A") + 5;
            g2d.drawString("=", currentX, formulaY);
            
            // Left parenthesis
            currentX += fm.stringWidth("=") + 5;
            g2d.drawString("(", currentX, formulaY);
            
            // Top length value
            int leftParenX = currentX;
            currentX += fm.stringWidth("(");
            g2d.setColor(new Color(199, 21, 133)); // Magenta color
            String topLengthStr = Integer.toString(topLength);
            g2d.drawString(topLengthStr, currentX, formulaY);
            
            // +
            currentX += fm.stringWidth(topLengthStr) + 2;
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("+", currentX, formulaY);
            
            // Bottom length value
            currentX += fm.stringWidth("+") + 2;
            g2d.setColor(new Color(255, 165, 0)); // Orange color
            String bottomLengthStr = Integer.toString(bottomLength);
            g2d.drawString(bottomLengthStr, currentX, formulaY);
            
            // Right parenthesis
            currentX += fm.stringWidth(bottomLengthStr);
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString(")", currentX, formulaY);
            int rightParenX = currentX + fm.stringWidth(")");
            
            // Division by 2
            lineY = formulaY + 3;
            g2d.drawLine(leftParenX, lineY, rightParenX - 5, lineY);
            int dividerX = (leftParenX + rightParenX - 5) / 2 - fm.stringWidth("2") / 2;
            g2d.drawString("2", dividerX, formulaY + 15);
            
            // ×
            currentX = rightParenX + 5;
            g2d.drawString("×", currentX, formulaY);
            
            // Height value
            currentX += fm.stringWidth("×") + 5;
            g2d.setColor(new Color(148, 0, 211)); // Purple color for height
            String heightStr = Integer.toString(height);
            g2d.drawString(heightStr, currentX, formulaY);
            
            // =
            currentX += fm.stringWidth(heightStr) + 5;
            g2d.setColor(new Color(150, 150, 150)); // Gray color
            g2d.drawString("=", currentX, formulaY);
            
            // Area result
            currentX += fm.stringWidth("=") + 5;
            String areaStr = Double.toString(area);
            g2d.drawString(areaStr, currentX, formulaY);
        }
    }
}
