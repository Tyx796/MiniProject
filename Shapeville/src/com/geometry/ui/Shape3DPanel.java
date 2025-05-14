package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import com.geometry.service.Task13D;
import com.geometry.service.Task2;
import com.geometry.entity.Shapes3D;
import com.geometry.entity.User;
import com.geometry.ui.uiUtils.KidButton;

/**
 * 3D Shape Learning and Recognition Interface
 * This panel provides an interactive environment for students to learn and 
 * identify different three-dimensional geometric shapes.
 */
public class Shape3DPanel extends JPanel {
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
    private Task13D shapeTask;
    private static final String FONT_NAME = "Comic Sans MS";
    
    /**
     * Constructor
     * @param mainFrame Reference to the main window
     */
    public Shape3DPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.shapeTask = new Task13D();
        setBackground(new Color(220, 240, 255));
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
        
        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
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
            resultLabel.setForeground(Color.RED);
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
                    mainFrame.updateTaskStatus("3D Shapes", true);
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
                        mainFrame.updateTaskStatus("3D Shapes", true);
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
        shapeTask = new Task13D();
        
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
            // Get image path from Shapes3D
            String imagePath = Shapes3D.getShapeImg(shapeName);
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
                case "cube":
                    draw3DCube(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "cuboid":
                    draw3DCuboid(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "cylinder":
                    draw3DCylinder(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "sphere":
                    draw3DSphere(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "cone":
                    draw3DCone(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "tetrahedron":
                    draw3DTetrahedron(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "triangularPrism":
                    draw3DTriangularPrism(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "squareBasedPyramid":
                    draw3DSquareBasedPyramid(g2d, padding, padding, shapeWidth, shapeHeight);
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
    
    // 3D drawing methods
    
    private void draw3DCube(Graphics2D g, int x, int y, int width, int height) {
        int size = Math.min(width, height);
        int depth = size / 4;
        
        // Front face
        g.setColor(new Color(200, 200, 255));
        g.fillRect(x, y, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);
        
        // Top face
        int[] xPoints = {x, x + depth, x + size + depth, x + size};
        int[] yPoints = {y, y - depth, y - depth, y};
        g.setColor(new Color(220, 220, 255));
        g.fillPolygon(xPoints, yPoints, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 4);
        
        // Side face
        int[] xPoints2 = {x + size, x + size + depth, x + size + depth, x + size};
        int[] yPoints2 = {y, y - depth, y - depth + size, y + size};
        g.setColor(new Color(180, 180, 255));
        g.fillPolygon(xPoints2, yPoints2, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints2, yPoints2, 4);
    }

    private void draw3DCuboid(Graphics2D g, int x, int y, int width, int height) {
        int depth = width / 4;
        int cuboidHeight = height / 2;
        
        // Front face
        g.setColor(new Color(255, 200, 200));
        g.fillRect(x, y, width, cuboidHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, cuboidHeight);
        
        // Top face
        int[] xPoints = {x, x + depth, x + width + depth, x + width};
        int[] yPoints = {y, y - depth, y - depth, y};
        g.setColor(new Color(255, 220, 220));
        g.fillPolygon(xPoints, yPoints, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 4);
        
        // Side face
        int[] xPoints2 = {x + width, x + width + depth, x + width + depth, x + width};
        int[] yPoints2 = {y, y - depth, y - depth + cuboidHeight, y + cuboidHeight};
        g.setColor(new Color(255, 180, 180));
        g.fillPolygon(xPoints2, yPoints2, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints2, yPoints2, 4);
    }

    private void draw3DCylinder(Graphics2D g, int x, int y, int width, int height) {
        int cylinderWidth = width;
        int cylinderHeight = height;
        
        // Top ellipse
        g.setColor(new Color(200, 255, 200));
        g.fillOval(x, y, cylinderWidth, cylinderWidth / 3);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, cylinderWidth, cylinderWidth / 3);
        
        // Bottom ellipse
        g.setColor(new Color(180, 255, 180));
        g.fillOval(x, y + cylinderHeight - cylinderWidth / 3, cylinderWidth, cylinderWidth / 3);
        g.setColor(Color.BLACK);
        g.drawOval(x, y + cylinderHeight - cylinderWidth / 3, cylinderWidth, cylinderWidth / 3);
        
        // Connection lines
        g.drawLine(x, y + cylinderWidth / 6, x, y + cylinderHeight - cylinderWidth / 6);
        g.drawLine(x + cylinderWidth, y + cylinderWidth / 6, x + cylinderWidth, y + cylinderHeight - cylinderWidth / 6);
        
        // Rectangular body
        g.setColor(new Color(220, 255, 220));
        g.fillRect(x, y + cylinderWidth / 6, cylinderWidth, cylinderHeight - cylinderWidth / 3);
        
        // Redraw connection lines to ensure visibility
        g.setColor(Color.BLACK);
        g.drawLine(x, y + cylinderWidth / 6, x, y + cylinderHeight - cylinderWidth / 6);
        g.drawLine(x + cylinderWidth, y + cylinderWidth / 6, x + cylinderWidth, y + cylinderHeight - cylinderWidth / 6);
    }

    private void draw3DSphere(Graphics2D g, int x, int y, int width, int height) {
        int size = Math.min(width, height);
        
        // Draw circle and add shading for 3D effect
        g.setColor(new Color(255, 200, 255));
        g.fillOval(x, y, size, size);
        
        // Add highlight
        g.setColor(new Color(255, 230, 255));
        g.fillOval(x + size/4, y + size/4, size/5, size/5);
        
        g.setColor(Color.BLACK);
        g.drawOval(x, y, size, size);
    }

    private void draw3DCone(Graphics2D g, int x, int y, int width, int height) {
        int coneDiameter = width;
        int coneHeight = height;
        
        // Bottom ellipse
        g.setColor(new Color(255, 230, 200));
        g.fillOval(x, y + coneHeight - coneDiameter / 4, coneDiameter, coneDiameter / 4);
        
        // Cone body
        int[] xPoints = {x + coneDiameter / 2, x, x + coneDiameter};
        int[] yPoints = {y, y + coneHeight - coneDiameter / 8, y + coneHeight - coneDiameter / 8};
        g.setColor(new Color(255, 200, 150));
        g.fillPolygon(xPoints, yPoints, 3);
        
        // Border
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 3);
        g.drawOval(x, y + coneHeight - coneDiameter / 4, coneDiameter, coneDiameter / 4);
    }

    private void draw3DTetrahedron(Graphics2D g, int x, int y, int width, int height) {
        int size = Math.min(width, height);
        
        // Front triangle
        int[] xPoints1 = {x + size / 2, x, x + size};
        int[] yPoints1 = {y, y + size, y + size};
        g.setColor(new Color(200, 255, 255));
        g.fillPolygon(xPoints1, yPoints1, 3);
        
        // Side triangle
        int[] xPoints2 = {x + size / 2, x + size, x + size + size / 4};
        int[] yPoints2 = {y, y + size, y + size / 2};
        g.setColor(new Color(180, 255, 255));
        g.fillPolygon(xPoints2, yPoints2, 3);
        
        // Border
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints1, yPoints1, 3);
        g.drawPolygon(xPoints2, yPoints2, 3);
        g.drawLine(x + size / 2, y, x + size + size / 4, y + size / 2);
    }

    private void draw3DTriangularPrism(Graphics2D g, int x, int y, int width, int height) {
        int prismWidth = width;
        int prismHeight = height;
        int depth = prismWidth / 4;
        
        // Front triangle
        int[] xPoints1 = {x, x + prismWidth, x + prismWidth / 2};
        int[] yPoints1 = {y + prismHeight, y + prismHeight, y};
        g.setColor(new Color(255, 255, 200));
        g.fillPolygon(xPoints1, yPoints1, 3);
        
        // Back triangle
        int[] xPoints2 = {x + depth, x + prismWidth + depth, x + prismWidth / 2 + depth};
        int[] yPoints2 = {y + prismHeight - depth, y + prismHeight - depth, y - depth};
        g.setColor(new Color(255, 255, 220));
        g.fillPolygon(xPoints2, yPoints2, 3);
        
        // Upper rectangle
        int[] xPoints3 = {x + prismWidth / 2, x + prismWidth, x + prismWidth + depth, x + prismWidth / 2 + depth};
        int[] yPoints3 = {y, y + prismHeight, y + prismHeight - depth, y - depth};
        g.setColor(new Color(255, 255, 180));
        g.fillPolygon(xPoints3, yPoints3, 4);
        
        // Bottom rectangle
        int[] xPoints4 = {x, x + prismWidth, x + prismWidth + depth, x + depth};
        int[] yPoints4 = {y + prismHeight, y + prismHeight, y + prismHeight - depth, y + prismHeight - depth};
        g.setColor(new Color(255, 255, 150));
        g.fillPolygon(xPoints4, yPoints4, 4);
        
        // Border
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints1, yPoints1, 3);
        g.drawPolygon(xPoints2, yPoints2, 3);
        g.drawLine(x, y + prismHeight, x + depth, y + prismHeight - depth);
        g.drawLine(x + prismWidth, y + prismHeight, x + prismWidth + depth, y + prismHeight - depth);
        g.drawLine(x + prismWidth / 2, y, x + prismWidth / 2 + depth, y - depth);
    }

    private void draw3DSquareBasedPyramid(Graphics2D g, int x, int y, int width, int height) {
        int size = Math.min(width, height);
        int baseSize = size;
        int pyramidHeight = size;
        
        // Bottom square
        g.setColor(new Color(220, 220, 255));
        g.fillRect(x, y + pyramidHeight - baseSize/2, baseSize, baseSize/2);
        
        // Front triangle
        int[] xPoints1 = {x, x + baseSize, x + baseSize/2};
        int[] yPoints1 = {y + pyramidHeight - baseSize/2, y + pyramidHeight - baseSize/2, y};
        g.setColor(new Color(200, 200, 255));
        g.fillPolygon(xPoints1, yPoints1, 3);
        
        // Side triangle
        int[] xPoints2 = {x + baseSize, x + baseSize + baseSize/4, x + baseSize/2};
        int[] yPoints2 = {y + pyramidHeight - baseSize/2, y + pyramidHeight - baseSize/4, y};
        g.setColor(new Color(180, 180, 255));
        g.fillPolygon(xPoints2, yPoints2, 3);
        
        // Border
        g.setColor(Color.BLACK);
        g.drawRect(x, y + pyramidHeight - baseSize/2, baseSize, baseSize/2);
        g.drawPolygon(xPoints1, yPoints1, 3);
        g.drawPolygon(xPoints2, yPoints2, 3);
    }
} 