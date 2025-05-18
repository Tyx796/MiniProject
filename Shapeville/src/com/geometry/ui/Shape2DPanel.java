package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import com.geometry.service.Task12D;
import com.geometry.ui.uiUtils.KidButton;
import com.geometry.entity.Shapes2D;
import com.geometry.entity.User;

/**
 * 2D Shape Learning and Recognition Interface
 * This panel provides an interactive interface for students to learn and identify
 * different 2D geometric shapes.
 */
public class Shape2DPanel extends JPanel {
    private MainFrame mainFrame;
    private KidButton homeButton;
    private KidButton submitButton;
    private JLabel progressLabel;
    
    // Shape recognition exercise components
    private JPanel recognitionPanel;
    private JLabel shapeImageLabel;
    private JLabel attemptsLabel;
    private JLabel resultLabel;
    private JTextField answerField;
    private Task12D shapeTask;
    private static final String FONT_NAME = "Comic Sans MS";
    
    /**
     * Constructor
     * @param mainFrame Reference to the main window
     */
    public Shape2DPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.shapeTask = new Task12D();
        setBackground(new Color(220, 240, 255)); // Soft background color
        initComponents();
        setupLayout();
        
        // Display initial shape
        showCurrentShape();
    }
    
    /**
     * Initialize components
     */
    private void initComponents() {
        // Create return to home button
        homeButton = new KidButton("Home");
        homeButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        homeButton.setPreferredSize(new Dimension(150, 40));
        homeButton.addActionListener(e -> mainFrame.showCard(MainFrame.HOME_PANEL));
        
        // Create submit button
        submitButton = new KidButton("OK");
        // color: rgb(236, 243, 232)
        submitButton.setNormalColor(Color.WHITE);
        submitButton.setForeground(Color.BLACK);
        submitButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        submitButton.setPreferredSize(new Dimension(120, 40));
        submitButton.addActionListener(e -> checkAnswer());
        
        // Create progress label
        progressLabel = new JLabel("");
        progressLabel.setFont(new Font(FONT_NAME, Font.BOLD, 22));
        updateProgressLabel();
        
        // Initialize recognition exercise components
        recognitionPanel = new JPanel(new BorderLayout(15, 15));
        recognitionPanel.setOpaque(false);
        shapeImageLabel = new JLabel();
        shapeImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        attemptsLabel = new JLabel("", SwingConstants.CENTER);
        attemptsLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        attemptsLabel.setForeground(Color.WHITE);
        updateAttemptsLabel();
        
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        
        answerField = new JTextField(10);
        answerField.setFont(new Font(FONT_NAME, Font.PLAIN, 22));
        answerField.setPreferredSize(new Dimension(100, 40));
        answerField.addActionListener(e -> checkAnswer());
        // Add focus request to automatically focus the input field when panel is shown
        answerField.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                // Use SwingUtilities.invokeLater to ensure component is fully displayed before requesting focus
                SwingUtilities.invokeLater(() -> answerField.requestFocusInWindow());
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {}
            public void ancestorMoved(javax.swing.event.AncestorEvent event) {}
        });
    }
    
    /**
     * Set up the layout
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top navigation area
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        // topPanel.add(progressLabel, BorderLayout.WEST);
        
        // Set up recognition exercise panel
        JPanel imagePanel = new JPanel(new BorderLayout(10, 10));
        imagePanel.setOpaque(false);
        imagePanel.add(shapeImageLabel, BorderLayout.CENTER);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        
        
        JPanel attemptsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        attemptsPanel.setOpaque(false);
        attemptsPanel.add(attemptsLabel);
        inputPanel.add(attemptsPanel);
        
        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,0));
        answerPanel.setOpaque(false);
        JLabel answerLabel = new JLabel("Name: ", SwingConstants.CENTER);
        answerLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        answerLabel.setForeground(Color.WHITE);
        answerPanel.add(answerLabel);
        answerPanel.add(answerField);
        answerPanel.add(submitButton);
        inputPanel.add(answerPanel);
        
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultPanel.setOpaque(false);
        resultPanel.add(resultLabel);
        inputPanel.add(resultPanel);
        
        // Add to main panel
        add(topPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Display the current shape
     */
    private void showCurrentShape() {
        String shapeName = shapeTask.getCurrentShape();
        
        if (shapeName != null) {
            // Set shape image
            ImageIcon icon = createShapeIcon(shapeName, 300, 300);
            if (icon != null) {
                shapeImageLabel.setIcon(icon);
                shapeImageLabel.setText("");
            } else {
                shapeImageLabel.setText("No image");
            }
            
            // Update labels
            updateProgressLabel();
            updateAttemptsLabel();
            
            // Clear result and input
            resultLabel.setText("");
            answerField.setText("");
            answerField.requestFocus();
        } else {
            // Task completed
            completeTask();
        }
    }
    
    /**
     * Check the answer
     */
    private void checkAnswer() {
        String answer = answerField.getText().trim();
        
        if (answer.isEmpty()) {
            resultLabel.setText("Type answer!");
            resultLabel.setForeground(Color.WHITE);
            return;
        }
        
        boolean isCorrect = shapeTask.checkAnswer(answer);
        
        if (isCorrect) {
            updateAttemptsLabel();
            resultLabel.setText("Great!");
            resultLabel.setForeground(new Color(0, 128, 0));
            
            // Calculate score
            int points = User.calScores("Basic", 3 - shapeTask.getRemainingAttempts() + 1);
            
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
            
            // Add keyboard enter listener
            scoreDialog.getRootPane().setDefaultButton(okButton);
            
            // Assemble panel
            scorePanel.add(scoreLabel, BorderLayout.CENTER);
            scorePanel.add(okButton, BorderLayout.SOUTH);
            
            scoreDialog.add(scorePanel);
            
            // Update main window score display
            mainFrame.updateScore();
            
            // Show score dialog
            scoreDialog.setVisible(true);
            
            // Show next shape (delayed by 0.5 seconds)
            Timer timer = new Timer(500, e -> {
                if (shapeTask.isTaskCompleted()) {
                    mainFrame.updateTaskStatus("2D Shapes", true);
                    completeTask();
                } else {
                    showCurrentShape();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            resultLabel.setText("Try again!");
            resultLabel.setForeground(Color.WHITE);
            answerField.setText("");
            answerField.requestFocus();
            
            if (shapeTask.getAttempts() == 0) {
                attemptsLabel.setText("Tries: 0");
                // Show the correct answer
                String correctAnswer = shapeTask.getPreviousShape();
                resultLabel.setText("Answer: " + correctAnswer);
                
                // Show next shape (delayed by 2 seconds)
                Timer timer = new Timer(2000, e -> {
                    if (shapeTask.isTaskCompleted()) {
                        mainFrame.updateTaskStatus("2D Shapes", true);
                        completeTask();
                    } else {
                        showCurrentShape();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                updateAttemptsLabel();
            }
        }
    }
    
    /**
     * Complete the task
     */
    private void completeTask() {
        // Clear components
        shapeImageLabel.setIcon(null);
        answerField.setEnabled(false);
        submitButton.setEnabled(false);
        
        // Display final results
        int correctCount = shapeTask.getCorrectCount();
        int totalShapes = shapeTask.getTotalShapes();
        
        JPanel completionPanel = new JPanel();
        completionPanel.setOpaque(false);
        completionPanel.setLayout(new BoxLayout(completionPanel, BoxLayout.Y_AXIS));
        
        JLabel completionLabel = new JLabel("All done!", SwingConstants.CENTER);
        completionLabel.setFont(new Font(FONT_NAME, Font.BOLD, 38));
        completionLabel.setForeground(Color.WHITE);
        completionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel scoreLabel = new JLabel("You got " + correctCount + " / " + totalShapes + "!", SwingConstants.CENTER);
        scoreLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 34));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        KidButton restartButton = new KidButton("Again");
        restartButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        restartButton.setPreferredSize(new Dimension(150, 40));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> restartTask());
        
        completionPanel.add(Box.createVerticalGlue());
        completionPanel.add(completionLabel);
        completionPanel.add(Box.createVerticalStrut(20));
        completionPanel.add(scoreLabel);
        completionPanel.add(Box.createVerticalStrut(20));
        completionPanel.add(restartButton);
        completionPanel.add(Box.createVerticalGlue());
        
        // Replace center component
        remove(shapeImageLabel.getParent());
        add(completionPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    /**
     * Restart the task
     */
    private void restartTask() {
        // Reinitialize the task
        shapeTask = new Task12D();
        
        // Reset UI components
        answerField.setEnabled(true);
        submitButton.setEnabled(true);
        
        // Remove completion panel
        Component centerComponent = ((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != shapeImageLabel.getParent()) {
            remove(centerComponent);
            
            JPanel imagePanel = new JPanel(new BorderLayout(10, 10));
            imagePanel.setOpaque(false);
            imagePanel.add(shapeImageLabel, BorderLayout.CENTER);
            add(imagePanel, BorderLayout.CENTER);
        }
        
        // Show initial shape
        showCurrentShape();
        revalidate();
        repaint();
    }
    
    /**
     * Update the progress label
     */
    private void updateProgressLabel() {
        int current = shapeTask.getCurrentShapeIndex() + 1;
        int total = shapeTask.getTotalShapes();
        progressLabel.setText(current + " / " + total);
    }
    
    /**
     * Update the attempts label
     */
    private void updateAttemptsLabel() {
        int remaining = shapeTask.getRemainingAttempts();
        attemptsLabel.setText("Tries: " + remaining);
    }
    
    /**
     * Create shape icon
     */
    private ImageIcon createShapeIcon(String shapeName, int width, int height) {
        ImageIcon icon = null;
        
        try {
            // Get image path from Shapes2D
            String imagePath = Shapes2D.getShapeImg(shapeName);
            if (imagePath == null) {
                return null;
            }
            
            // Load image resource
            java.net.URL imageURL = getClass().getClassLoader().getResource(imagePath);
            if (imageURL != null) {
                // Read image
                Image originalImage = new ImageIcon(imageURL).getImage();
                
                // Resize image
                Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImage);
            } else {
                System.err.println("Cannot find image: " + imagePath);
                
                // If image not found, use default drawn shape as a fallback
                return createDefaultShapeIcon(shapeName, width, height);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        return icon;
    }
    
    /**
     * Create a default shape icon (when image cannot be loaded)
     */
    private ImageIcon createDefaultShapeIcon(String shapeName, int width, int height) {
        // Simple drawing of basic shapes as a fallback
        ImageIcon icon = null;
        
        try {
            // Create image
            Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) image.getGraphics();
            
            // Set anti-aliasing
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Set border and fill colors
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            
            // Draw shape
            int padding = 40;
            int shapeWidth = width - 2 * padding;
            int shapeHeight = height - 2 * padding;
            
            switch (shapeName) {
                case "circle":
                    g2d.setColor(new Color(255, 200, 200)); // Light red fill
                    g2d.fillOval(padding, padding, shapeWidth, shapeHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(padding, padding, shapeWidth, shapeHeight);
                    break;
                    
                case "triangle":
                    int[] xPoints = {width / 2, padding, width - padding};
                    int[] yPoints = {padding, height - padding, height - padding};
                    g2d.setColor(new Color(200, 255, 200)); // Light green fill
                    g2d.fillPolygon(xPoints, yPoints, 3);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(xPoints, yPoints, 3);
                    break;
                    
                case "square":
                    g2d.setColor(new Color(200, 200, 255)); // Light blue fill
                    g2d.fillRect(padding, padding, shapeWidth, shapeWidth);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(padding, padding, shapeWidth, shapeWidth);
                    break;
                    
                case "rectangle":
                    g2d.setColor(new Color(255, 255, 200)); // Light yellow fill
                    g2d.fillRect(padding, padding + shapeHeight/4, shapeWidth, shapeHeight/2);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(padding, padding + shapeHeight/4, shapeWidth, shapeHeight/2);
                    break;
                    
                case "pentagon":
                    int[] pentX = new int[5];
                    int[] pentY = new int[5];
                    int radius = shapeWidth / 2;
                    int centerX = width / 2;
                    int centerY = height / 2;
                    for (int i = 0; i < 5; i++) {
                        double angle = 2 * Math.PI * i / 5 - Math.PI / 2;
                        pentX[i] = centerX + (int)(radius * Math.cos(angle));
                        pentY[i] = centerY + (int)(radius * Math.sin(angle));
                    }
                    g2d.setColor(new Color(255, 200, 255)); // Light purple fill
                    g2d.fillPolygon(pentX, pentY, 5);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(pentX, pentY, 5);
                    break;
                    
                case "hexagon":
                    int[] hexX = new int[6];
                    int[] hexY = new int[6];
                    radius = shapeWidth / 2;
                    centerX = width / 2;
                    centerY = height / 2;
                    for (int i = 0; i < 6; i++) {
                        double angle = 2 * Math.PI * i / 6 - Math.PI / 2;
                        hexX[i] = centerX + (int)(radius * Math.cos(angle));
                        hexY[i] = centerY + (int)(radius * Math.sin(angle));
                    }
                    g2d.setColor(new Color(200, 255, 255)); // Light cyan fill
                    g2d.fillPolygon(hexX, hexY, 6);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(hexX, hexY, 6);
                    break;
                    
                case "heptagon":
                    int[] heptX = new int[7];
                    int[] heptY = new int[7];
                    radius = shapeWidth / 2;
                    centerX = width / 2;
                    centerY = height / 2;
                    for (int i = 0; i < 7; i++) {
                        double angle = 2 * Math.PI * i / 7 - Math.PI / 2;
                        heptX[i] = centerX + (int)(radius * Math.cos(angle));
                        heptY[i] = centerY + (int)(radius * Math.sin(angle));
                    }
                    g2d.setColor(new Color(255, 230, 200)); // Light orange fill
                    g2d.fillPolygon(heptX, heptY, 7);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(heptX, heptY, 7);
                    break;
                    
                case "octagon":
                    int[] octX = new int[8];
                    int[] octY = new int[8];
                    radius = shapeWidth / 2;
                    centerX = width / 2;
                    centerY = height / 2;
                    for (int i = 0; i < 8; i++) {
                        double angle = 2 * Math.PI * i / 8 - Math.PI / 2;
                        octX[i] = centerX + (int)(radius * Math.cos(angle));
                        octY[i] = centerY + (int)(radius * Math.sin(angle));
                    }
                    g2d.setColor(new Color(220, 220, 255)); // Light blue-purple fill
                    g2d.fillPolygon(octX, octY, 8);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(octX, octY, 8);
                    break;
                    
                case "oval":
                    g2d.setColor(new Color(255, 220, 220)); // Light pink fill
                    g2d.fillOval(padding, padding + shapeHeight/4, shapeWidth, shapeHeight/2);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(padding, padding + shapeHeight/4, shapeWidth, shapeHeight/2);
                    break;
                    
                case "rhombus":
                    int[] rhombX = {width/2, padding, width/2, width - padding};
                    int[] rhombY = {padding, height/2, height - padding, height/2};
                    g2d.setColor(new Color(220, 255, 220)); // Light green fill
                    g2d.fillPolygon(rhombX, rhombY, 4);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(rhombX, rhombY, 4);
                    break;
                    
                case "kite":
                    int[] kiteX = {width/2, width/2 - shapeWidth/4, width/2, width/2 + shapeWidth/4};
                    int[] kiteY = {padding, height/2, height - padding, height/2};
                    g2d.setColor(new Color(255, 255, 220)); // Light yellow fill
                    g2d.fillPolygon(kiteX, kiteY, 4);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(kiteX, kiteY, 4);
                    break;
            }
            
            g2d.dispose();
            icon = new ImageIcon(image);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        return icon;
    }
} 