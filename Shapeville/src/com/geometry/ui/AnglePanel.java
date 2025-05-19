package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import com.geometry.service.Task2;
import com.geometry.entity.Angles;
import com.geometry.entity.User;
import com.geometry.ui.uiUtils.KidButton;

/**
 * Angle Learning and Recognition Panel
 * Interface for students to learn about different types of angles and practice identifying them.
 */
public class AnglePanel extends JPanel {
    private MainFrame mainFrame;
    private KidButton homeButton;
    private Task2 angleTask;
    private static final String FONT_NAME = "Comic Sans MS";
    
    // Main panels
    private JPanel inputPanel;    // Panel for angle input
    private JPanel quizPanel;     // Panel for angle type selection
    private JPanel resultPanel;   // Panel for displaying results
    
    // Angle input panel components
    private JTextField angleInput;
    private KidButton submitAngleButton;
    private JLabel angleImageLabel;
    private JLabel instructionLabel;
    private JLabel errorLabel;
    
    // Angle type selection panel components
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private JLabel attemptsLabel;
    private JLabel resultLabel;
    
    // Result panel components
    private JLabel completionLabel;
    private JLabel scoreLabel;
    private KidButton restartButton;
    
    /**
     * Constructor for the AnglePanel
     * @param mainFrame Reference to the main application frame
     */
    public AnglePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.angleTask = new Task2();
        setBackground(new Color(220, 240, 255));
        initComponents();
        setupLayout();
        
        // Default to show the angle input panel
        showInputPanel();
    }
    
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        // Create home button
        homeButton = new KidButton("Home");
        homeButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        homeButton.setPreferredSize(new Dimension(150, 40));
        homeButton.addActionListener(e -> mainFrame.showCard(MainFrame.HOME_PANEL));
        
        // Initialize all panels
        initInputPanel();
        initQuizPanel();
        initResultPanel();
    }
    
    /**
     * Initialize the angle input panel
     */
    private void initInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);
        
        // Angle input instruction label
        instructionLabel = new JLabel("Type angle (0-360, x10)", SwingConstants.CENTER);
        instructionLabel.setFont(new Font(FONT_NAME, Font.BOLD, 22));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Angle input field and submit button
        JPanel inputControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputControlPanel.setOpaque(false);
        angleInput = new JTextField(5);
        angleInput.setFont(new Font(FONT_NAME, Font.PLAIN, 22));
        angleInput.setPreferredSize(new Dimension(100, 40));
        angleInput.addActionListener(e -> processAngleInput());
        // Add focus request to automatically focus the input field when panel is shown
        angleInput.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                // Use SwingUtilities.invokeLater to ensure component is fully displayed before requesting focus
                SwingUtilities.invokeLater(() -> angleInput.requestFocusInWindow());
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {}
            public void ancestorMoved(javax.swing.event.AncestorEvent event) {}
        });
        submitAngleButton = new KidButton("Show");
        submitAngleButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        submitAngleButton.setPreferredSize(new Dimension(120, 40));
        submitAngleButton.addActionListener(e -> processAngleInput());
        
        inputControlPanel.add(angleInput);
        inputControlPanel.add(submitAngleButton);
        
        // Error message label
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        errorLabel.setForeground(Color.WHITE);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Angle image label
        angleImageLabel = new JLabel();
        angleImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components to panel
        inputPanel.add(Box.createVerticalGlue());
        inputPanel.add(instructionLabel);
        inputPanel.add(Box.createVerticalStrut(20));
        inputPanel.add(inputControlPanel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(errorLabel);
        inputPanel.add(Box.createVerticalStrut(20));
        inputPanel.add(angleImageLabel);
        inputPanel.add(Box.createVerticalGlue());
    }
    
    /**
     * Initialize the angle type selection panel
     */
    private void initQuizPanel() {
        quizPanel = new JPanel();
        quizPanel.setLayout(new BorderLayout(20, 20));
        quizPanel.setOpaque(false);
        
        // Question label
        questionLabel = new JLabel("Type?", SwingConstants.CENTER);
        questionLabel.setFont(new Font(FONT_NAME, Font.BOLD, 32));
        questionLabel.setForeground(Color.WHITE);
        
        // Options panel - changed to vertical layout on the right side
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(80, 40, 10, 80)); // Increased right padding
        optionsPanel.setOpaque(false);
        
        // Attempts remaining label
        attemptsLabel = new JLabel("Tries: 3", SwingConstants.CENTER);
        attemptsLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        attemptsLabel.setForeground(Color.WHITE);
        
        // Result label
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create left image panel
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 10)); // Increased left padding
        leftPanel.setOpaque(false);
        leftPanel.add(angleImageLabel, BorderLayout.CENTER);
        
        // Create bottom status panel
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setOpaque(false);
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.add(attemptsLabel);
        statusPanel.add(Box.createVerticalStrut(10));
        statusPanel.add(resultLabel);
        
        // Add components to panel
        quizPanel.add(questionLabel, BorderLayout.NORTH);
        quizPanel.add(leftPanel, BorderLayout.CENTER);
        quizPanel.add(optionsPanel, BorderLayout.EAST);
        quizPanel.add(statusPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Initialize the result panel
     */
    private void initResultPanel() {
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setOpaque(false);
        
        // Completion label
        completionLabel = new JLabel("All done!", SwingConstants.CENTER);
        completionLabel.setFont(new Font(FONT_NAME, Font.BOLD, 38));
        completionLabel.setForeground(Color.WHITE);
        completionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Score label
        scoreLabel = new JLabel("You got X / X!", SwingConstants.CENTER);
        scoreLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 34));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Restart button
        restartButton = new KidButton("Again");
        restartButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        restartButton.setPreferredSize(new Dimension(150, 40));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> restartTask());
        
        // Add components to panel
        resultPanel.add(Box.createVerticalGlue());
        resultPanel.add(completionLabel);
        resultPanel.add(Box.createVerticalStrut(20));
        resultPanel.add(scoreLabel);
        resultPanel.add(Box.createVerticalStrut(20));
        resultPanel.add(restartButton);
        resultPanel.add(Box.createVerticalGlue());
    }
    
    /**
     * Set up the panel layout
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top navigation area
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        // JLabel titleLabel = new JLabel("Angle Learning and Recognition", SwingConstants.CENTER);
        // titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        // topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Add top panel
        add(topPanel, BorderLayout.NORTH);
        
        // Main content area uses card layout, dynamically switched in methods
    }
    
    /**
     * Show the angle input panel
     */
    private void showInputPanel() {
        // Remove current center component
        Component centerComponent = ((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null) {
            remove(centerComponent);
        }
        
        // Add the angle input panel
        add(inputPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    /**
     * Show the angle type selection panel
     */
    private void showQuizPanel() {
        // Remove current center component
        Component centerComponent = ((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null) {
            remove(centerComponent);
        }
        
        // Remove image label to avoid duplicate addition
        Container parent = angleImageLabel.getParent();
        if (parent != null) {
            parent.remove(angleImageLabel);
        }
        
        // Re-add image label to quiz panel's left side
        JPanel leftPanel = (JPanel) quizPanel.getComponent(1); // Get left panel
        leftPanel.add(angleImageLabel, BorderLayout.CENTER);
        
        // Add the angle type selection panel
        add(quizPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    /**
     * Show the result panel
     */
    private void showResultPanel() {
        // Remove current center component
        Component centerComponent = ((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null) {
            remove(centerComponent);
        }
        
        // Update result label
        int correctCount = angleTask.getCorrectCount();
        int requiredTypes = 4;
        scoreLabel.setText("You got " + correctCount + " / " + requiredTypes + " angles right!");
        
        // Add result panel
        add(resultPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    /**
     * Process the angle input from the user
     */
    private void processAngleInput() {
        try {
            int angle = Integer.parseInt(angleInput.getText().trim());
            
            // Check if angle is within valid range
            if (angle <= 0 || angle >= 360) {
                errorLabel.setText("Angle must be between 0 and 360 degrees (not included)");
                return;
            }
            
            // Check if angle is a multiple of 10
            if (angle % 10 != 0) {
                errorLabel.setText("Angle must be a multiple of 10");
                return;
            }
            
            // Clear error message
            errorLabel.setText("");
            
            // Set current angle
            angleTask.setCurrentAngle(angle);
            
            // Display angle image - use business logic layer to get image
            ImageIcon icon = angleTask.getAngleImage(angle);
            if (icon != null) {
                // Resize image to fit display area
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                angleImageLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                // If image not found, use original drawing method
                icon = createAngleIcon(angle, 300, 300);
                angleImageLabel.setIcon(icon);
                System.err.println("Warning: Falling back to dynamic angle drawing for angle " + angle);
            }
            
            // Switch to quiz panel
            setupQuizOptions();
            updateAttemptsLabel();
            resultLabel.setText("");
            showQuizPanel();
            
        } catch (NumberFormatException e) {
            // Error handling for non-numeric input
        }
    }
    
    /**
     * Set up angle type selection options
     */
    private void setupQuizOptions() {
        // Clear options panel
        optionsPanel.removeAll();
        
        // Get all angle types
        java.util.List<String> angleTypes = angleTask.getAngleTypes();
        
        // Create button for each angle type
        for (String type : angleTypes) {
            String typeName = angleTask.getAngleTypeName(type);
            KidButton optionButton = new KidButton(typeName);
            optionButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
            optionButton.setPreferredSize(new Dimension(220, 50));
            optionButton.setMaximumSize(new Dimension(220, 50));
            optionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            optionButton.addActionListener(e -> checkAngleType(type));
            optionsPanel.add(optionButton);
            optionsPanel.add(Box.createVerticalStrut(15));
        }
        
        // Refresh options panel layout
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }
    
    /**
     * Check the user's answer
     * @param selectedType The angle type selected by the user
     */
    private void checkAngleType(String selectedType) {
        boolean isCorrect = angleTask.checkAnswer(selectedType);
        
        if (isCorrect) {
            // Calculate score
            int points = User.calScores("Basic", 3 - angleTask.getRemainingAttempts() + 1);
            
            // Create score dialog
            JDialog scoreDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Score", true);
            scoreDialog.setLayout(new BorderLayout(10, 10));
            scoreDialog.setSize(400, 220);
            scoreDialog.setLocationRelativeTo(this);
            
            // Create score panel
            JPanel scorePanel = new JPanel(new BorderLayout(10, 10));
            scorePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            // Add score information
            JLabel scoreLabel = new JLabel("Score + " + points + " !", SwingConstants.CENTER);
            scoreLabel.setFont(new Font(FONT_NAME, Font.BOLD, 28));
            scoreLabel.setForeground(new Color(0, 150, 0));
            
            // Confirm button
            KidButton okButton = new KidButton("OK");
            okButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
            okButton.addActionListener(e -> scoreDialog.dispose());
            scoreDialog.getRootPane().setDefaultButton(okButton);
            
            // Assemble panel
            scorePanel.add(scoreLabel, BorderLayout.CENTER);
            scorePanel.add(okButton, BorderLayout.SOUTH);
            
            scoreDialog.add(scorePanel);

            mainFrame.updateScore();
            
            // Update result label
            resultLabel.setText("Great!");
            resultLabel.setForeground(new Color(0, 150, 0));
            
            // Show score dialog
            scoreDialog.setVisible(true);
            
            // Delay and show next question
            Timer timer = new Timer(500, e -> {
                if (angleTask.isTaskCompleted()) {
                    mainFrame.updateTaskStatus("Angles", true);
                    showResultPanel();
                } else {
                    showInputPanel();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            resultLabel.setText("Try again!");
            resultLabel.setForeground(Color.WHITE);
            
            if (angleTask.getRemainingAttempts() <= 0) {
                // Show correct answer
                resultLabel.setText("Answer: " + angleTask.getCurrentAngleType());
                Timer timer = new Timer(2000, e -> {
                    if (angleTask.isTaskCompleted()) {
                        mainFrame.updateTaskStatus("Angles", true);
                        showResultPanel();
                    } else {
                        showInputPanel();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
        
        // Update attempts display
        updateAttemptsLabel();
    }
    
    /**
     * Update the attempts remaining label
     */
    private void updateAttemptsLabel() {
        int remaining = angleTask.getRemainingAttempts();
        attemptsLabel.setText("Tries: " + remaining);
    }
    
    /**
     * Restart the task
     */
    private void restartTask() {
        // Reinitialize the task
        angleTask = new Task2();
        
        // Clear angle input field
        angleInput.setText("");
        errorLabel.setText("");
        
        // Reset image label
        angleImageLabel.setIcon(null);
        
        // Show angle input panel
        showInputPanel();
    }
    
    /**
     * Create an angle icon
     * @param degrees The angle in degrees
     * @param width The width of the icon
     * @param height The height of the icon
     * @return ImageIcon representing the specified angle
     */
    private ImageIcon createAngleIcon(int degrees, int width, int height) {
        // Create a buffered image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Set anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Set background to white
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        // Calculate center point and radius
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = (int) (Math.min(width, height) / 1.5);
        
        // Set line color and width
        g2d.setColor(new Color(70, 130, 180)); // Deep blue for arrows
        g2d.setStroke(new BasicStroke(3));
        
        String angleType = angleTask.getAngleType(degrees);
        
        // Calculate second line angle - note: in Java graphics coordinate system, 0 degrees is east, 90 degrees is south
        // So we need to use -degrees to correctly draw the angle
        double radians = Math.toRadians(-degrees);
        
        if (angleType.equals(Task2.ACUTE_ANGLE)) {
            // Acute angle (< 90°)
            // Draw first line (horizontal right)
            g2d.drawLine(centerX - radius / 2, centerY, centerX + radius, centerY);
            
            // Draw second line (upward sloped)
            int startX = centerX - radius / 2;
            int startY = centerY;
            int endX = centerX - radius / 2 + (int)(radius * Math.cos(radians));
            int endY = centerY + (int)(radius * Math.sin(radians));
            g2d.drawLine(startX, startY, endX, endY);
            
            // Draw arrows
            drawArrow(g2d, centerX + radius, centerY, 0);
            drawArrow(g2d, endX, endY, radians);
            
            // Draw angle marker
            g2d.setColor(new Color(200, 220, 250, 150)); // Light blue fill, semi-transparent
            g2d.fillArc(centerX - radius / 2 - radius / 4, centerY - radius / 4, 
                        radius / 2, radius / 2, 0, degrees);
            
        } else if (angleType.equals(Task2.RIGHT_ANGLE)) {
            // Right angle (90°)
            // Draw horizontal line
            g2d.drawLine(centerX - radius / 2, centerY, centerX + radius, centerY);
            
            // Draw vertical line
            g2d.drawLine(centerX - radius / 2, centerY, centerX - radius / 2, centerY + radius);
            
            // Draw arrows
            drawArrow(g2d, centerX + radius, centerY, 0);
            drawArrow(g2d, centerX - radius / 2, centerY + radius, -Math.PI/2);
            
            // Draw right angle marker (small square)
            g2d.setColor(new Color(200, 220, 250, 150)); // Light blue fill, semi-transparent
            int squareSize = radius / 6;
            g2d.fillRect(centerX - radius / 2, centerY, squareSize, squareSize);
            
        } else if (angleType.equals(Task2.OBTUSE_ANGLE)) {
            // Obtuse angle (> 90° and < 180°)
            // Draw first line (horizontal right)
            g2d.drawLine(centerX, centerY, centerX + radius, centerY);
            
            // Draw second line (downward sloped)
            int endX = centerX + (int)(radius * Math.cos(radians));
            int endY = centerY + (int)(radius * Math.sin(radians));
            g2d.drawLine(centerX, centerY, endX, endY);
            
            // Draw arrows
            drawArrow(g2d, centerX + radius, centerY, 0);
            drawArrow(g2d, endX, endY, radians);
            
            // Draw angle arc
            g2d.setColor(new Color(200, 220, 250, 150)); // Light blue fill, semi-transparent
            g2d.fillArc(centerX - radius / 4, centerY - radius / 4, 
                       radius / 2, radius / 2, 0, degrees);
            
        } else if (angleType.equals(Task2.STRAIGHT_ANGLE)) {
            // Straight angle (180°)
            // Draw horizontal line
            g2d.drawLine(centerX - radius, centerY, centerX + radius, centerY);
            
            // Draw arrows
            drawArrow(g2d, centerX - radius, centerY, Math.PI);
            drawArrow(g2d, centerX + radius, centerY, 0);
            
            // Draw center point marker
            g2d.setColor(new Color(200, 220, 250)); // Light blue fill
            int dotSize = 6;
            g2d.fillOval(centerX - dotSize/2, centerY - dotSize/2, dotSize, dotSize);
            
            // Draw angle arc
            g2d.setColor(new Color(200, 220, 250, 150));
            g2d.drawArc(centerX - radius / 4, centerY - radius / 4, radius / 2, radius / 2, 0, 180);
            
        } else if (angleType.equals(Task2.REFLEX_ANGLE)) {
            // Reflex angle (> 180° and < 360°)
            // Draw first line (horizontal right)
            g2d.drawLine(centerX, centerY, centerX + radius * 3/4, centerY);
            
            // Draw second line (clockwise direction)
            int endX = centerX + (int)(radius * 3/4 * Math.cos(radians));
            int endY = centerY + (int)(radius * 3/4 * Math.sin(radians));
            g2d.drawLine(centerX, centerY, endX, endY);
            
            // Draw arrows
            drawArrow(g2d, centerX + radius * 3/4, centerY, 0);
            drawArrow(g2d, endX, endY, radians);
            
            // Draw angle arc
            g2d.setColor(new Color(200, 220, 250, 150)); // Light blue fill, semi-transparent
            g2d.fillArc(centerX - radius / 4, centerY - radius / 4, 
                       radius / 2, radius / 2, 0, degrees);
        }
        
        // Clean up resources
        g2d.dispose();
        
        return new ImageIcon(image);
    }
    
    /**
     * Draw an arrow at the end of a line
     * @param g2d Graphics2D context
     * @param x X coordinate of arrow tip
     * @param y Y coordinate of arrow tip
     * @param angle Angle of the line in radians
     */
    private void drawArrow(Graphics2D g2d, int x, int y, double angle) {
        int arrowSize = 10;
        
        // Save current color
        Color originalColor = g2d.getColor();
        
        // Calculate the two points of the arrow
        int x1 = (int) (x - arrowSize * Math.cos(angle - Math.PI/6));
        int y1 = (int) (y + arrowSize * Math.sin(angle - Math.PI/6));
        int x2 = (int) (x - arrowSize * Math.cos(angle + Math.PI/6));
        int y2 = (int) (y + arrowSize * Math.sin(angle + Math.PI/6));
        
        // Draw arrow
        g2d.setColor(originalColor);
        int[] xPoints = {x, x1, x2};
        int[] yPoints = {y, y1, y2};
        g2d.fillPolygon(xPoints, yPoints, 3);
        
        // Restore original color
        g2d.setColor(originalColor);
    }
}