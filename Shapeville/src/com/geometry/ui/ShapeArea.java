package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.border.TitledBorder;

import com.geometry.entity.User;
import com.geometry.service.Task3;
import com.geometry.ui.uiUtils.KidButton;

/**
 * Shape Area Calculation interface
 * Task 3: Shape Area Calculation
 * This panel provides an interactive interface for students to practice
 * calculating areas of various 2D shapes.
 */
public class ShapeArea extends JPanel {
    private MainFrame mainFrame;
    private KidButton homeButton;
    private static final String FONT_NAME = "Comic Sans MS";
    
    // Task 3 related components
    private Task3 task3Service;
    private JPanel task3ControlPanel;
    private JPanel task3DisplayPanel;
    private JTextField answerField;
    private JLabel statusLabel;
    private JLabel attemptsLabel;
    private Timer task3Timer;
    private JLabel timerLabel;
    private int secondsRemaining;
    private KidButton submitButton;
    
    /**
     * Constructor
     * @param mainFrame Reference to the main window
     */
    public ShapeArea(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        // Initialize business logic
        task3Service = new Task3();
        
        initComponents();
        setupLayout();
    }
    
    /**
     * Initialize components
     */
    private void initComponents() {
        // Create return to home button
        homeButton = new KidButton("Home");
        homeButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        homeButton.setPreferredSize(new Dimension(150, 40));
        homeButton.addActionListener(e -> {
            // Reset Task3 status
            // if (task3Timer != null && task3Timer.isRunning()) {
            //    task3Timer.stop();
            // }
            // task3Service.reset();
            
            mainFrame.showCard(MainFrame.HOME_PANEL);
        });
    }
    
    /**
     * Set up the layout
     */
    private void setupLayout() {
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 240), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        

        
        // Top navigation area
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Main content panel
        JPanel contentPanel = createMainPanel();
        
        // Add to main panel

        // color: rgb(177, 208, 239)
        contentPanel.setBackground(new Color(177, 208, 239));
        contentPanel.setOpaque(true);
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Create the main panel - Shape area calculation
     * @return The configured main panel
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 18));
        
        // Task description
        JLabel descriptionLabel = new JLabel(
                "<html><h3 style='font-family: " + FONT_NAME + "; font-size: 24px; color: white;'>" +
                "Shape Area Calculation</h3>" +
                "<p style='font-family: " + FONT_NAME + "; font-size: 18px; color: white;'>" +
                "You have 3 tries and 3 minutes for each question.</p></html>");
        panel.add(descriptionLabel, BorderLayout.NORTH);
        
        // Create shape selection area
        JPanel shapeSelectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        shapeSelectionPanel.setOpaque(false);
        String[] shapes = {Task3.RECTANGLE, Task3.TRIANGLE, Task3.PARALLELOGRAM, Task3.TRAPEZIUM};
        
        for (String shape : shapes) {
            KidButton shapeButton = new KidButton(shape);
            shapeButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
            shapeButton.setPreferredSize(new Dimension(180, 50));
            shapeButton.addActionListener(e -> {
                startTask3WithShape(shape);
                // Automatically focus answer input field after clicking shape button
                answerField.requestFocusInWindow();
            });
            shapeSelectionPanel.add(shapeButton);
        }
        
        // Create control panel
        // color: rgb(154, 156, 159)
        task3ControlPanel = new JPanel();
        task3ControlPanel.setLayout(new BoxLayout(task3ControlPanel, BoxLayout.Y_AXIS));
        task3ControlPanel.setOpaque(false);
        task3ControlPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(154, 156, 159), 2),
                "Exercise Control",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font(FONT_NAME, Font.BOLD, 20),
                new Color(154, 156, 159)
        ));
        
        // Parameter display panel
        JPanel paramsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paramsPanel.setOpaque(false);
        JLabel paramsLabel = new JLabel("Parameters:");
        paramsLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        paramsLabel.setForeground(Color.WHITE);
        paramsPanel.add(paramsLabel);
        task3ControlPanel.add(paramsPanel);
        
        // Answer input area
        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        answerPanel.setOpaque(false);
        JLabel answerLabel = new JLabel("Area:");
        answerLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        answerLabel.setForeground(Color.WHITE);
        answerPanel.add(answerLabel);
        
        answerField = new JTextField(10);
        answerField.setFont(new Font(FONT_NAME, Font.PLAIN, 22));
        answerField.setPreferredSize(new Dimension(100, 40));
        answerPanel.add(answerField);
        
        submitButton = new KidButton("OK");
        submitButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        submitButton.setPreferredSize(new Dimension(120, 40));
        submitButton.addActionListener(e -> checkTask3Answer());
        submitButton.setEnabled(false);
        answerPanel.add(submitButton);
        
        // Add Enter key listener to also trigger answer checking
        answerField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && submitButton.isEnabled()) {
                    checkTask3Answer();
                }
            }
        });
        
        task3ControlPanel.add(answerPanel);
        
        // Status and attempts panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setOpaque(false);
        statusLabel = new JLabel("Please select a shape");
        statusLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        statusLabel.setForeground(Color.WHITE);
        attemptsLabel = new JLabel("Tries: 3");
        attemptsLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        attemptsLabel.setForeground(Color.WHITE);
        statusPanel.add(statusLabel);
        statusPanel.add(Box.createHorizontalStrut(20));
        statusPanel.add(attemptsLabel);
        task3ControlPanel.add(statusPanel);
        
        // Timer panel
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timerPanel.setOpaque(false);
        timerLabel = new JLabel("Time: 3:00");
        timerLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        timerLabel.setForeground(Color.WHITE);
        timerPanel.add(timerLabel);
        task3ControlPanel.add(timerPanel);
        
        // Create display area
        task3DisplayPanel = new JPanel(new BorderLayout());
        task3DisplayPanel.setOpaque(false);
        task3DisplayPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(154, 156, 159), 2),
                "Shape Display",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font(FONT_NAME, Font.BOLD, 20),
                new Color(154, 156, 159)
        ));
        JLabel placeholderLabel = new JLabel(
                "<html><p style='font-family: " + FONT_NAME + "; font-size: 18px; color: white;'>" +
                "Select a shape to start...<br>" +
                "The shape and its parameters will be shown here.</p></html>", 
                SwingConstants.CENTER);
        task3DisplayPanel.add(placeholderLabel, BorderLayout.CENTER);
        
        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(shapeSelectionPanel, BorderLayout.NORTH);
        
        JPanel horizontalPanel = new JPanel(new BorderLayout());
        horizontalPanel.setOpaque(false);
        
        // Left control panel, fixed width 300
        task3ControlPanel.setPreferredSize(new Dimension(300, task3ControlPanel.getPreferredSize().height));
        horizontalPanel.add(task3ControlPanel, BorderLayout.WEST);
        
        // Right display panel
        horizontalPanel.add(task3DisplayPanel, BorderLayout.CENTER);
        
        centerPanel.add(horizontalPanel, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Start Task3 with the selected shape
     * @param shape Shape type
     */
    private void startTask3WithShape(String shape) {
        // Reset previous timer (if exists)
        if (task3Timer != null && task3Timer.isRunning()) {
            task3Timer.stop();
        }
        
        // Clear display panel
        task3DisplayPanel.removeAll();
        
        // Select shape and generate parameters through Task3 service
        task3Service.selectShape(shape);
        
        // Update parameter display
        updateTask3ParamsDisplay();
        
        // Clear answer field
        answerField.setText("");
        answerField.setEditable(true);
        submitButton.setEnabled(true);
        
        // Update status and attempts
        statusLabel.setText("Please calculate the area");
        attemptsLabel.setText("Tries: " + task3Service.getRemainingAttempts());
        
        // Start 3-minute timer
        secondsRemaining = 180; // 3 minutes = 180 seconds
        updateTimerDisplay();
        
        task3Timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsRemaining--;
                updateTimerDisplay();
                
                if (secondsRemaining <= 0) {
                    // Time's up, stop timer
                    task3Timer.stop();
                    timeUpForTask3();
                }
            }
        });
        task3Timer.start();
        
        // Refresh UI
        task3DisplayPanel.revalidate();
        task3DisplayPanel.repaint();
    }
    
    /**
     * Update Task3 parameter display
     */
    private void updateTask3ParamsDisplay() {
        // Get parameter names and values
        String[] paramNames = task3Service.getParameterNames();
        int[] paramValues = task3Service.getParameters();
        
        // Clear parameter panel and recreate
        Component[] components = task3ControlPanel.getComponents();
        if (components.length > 0 && components[0] instanceof JPanel) {
            JPanel paramsPanel = (JPanel) components[0];
            paramsPanel.removeAll();
            
            JLabel paramTitleLabel = new JLabel("Parameters:");
            paramTitleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
            paramTitleLabel.setForeground(Color.WHITE);
            paramsPanel.add(paramTitleLabel);
            
            // Add each parameter
            for (int i = 0; i < paramNames.length; i++) {
                JLabel paramLabel = new JLabel(paramNames[i] + ": " + paramValues[i]);
                paramLabel.setFont(new Font(FONT_NAME, Font.BOLD, 18));
                paramLabel.setForeground(Color.WHITE);
                paramLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                paramsPanel.add(paramLabel);
            }
            
            paramsPanel.revalidate();
            paramsPanel.repaint();
        }
    }
    
    /**
     * Check Task3 answer
     */
    private void checkTask3Answer() {
        try {
            double userAnswer = Double.parseDouble(answerField.getText().trim());
            boolean isCorrect = task3Service.checkAnswer(userAnswer);
            attemptsLabel.setText("Tries: " + task3Service.getRemainingAttempts());
            
            if (isCorrect) {
                answerCorrectForTask3();
            } else {
                if (task3Service.getRemainingAttempts() <= 0) {
                    noAttemptsLeftForTask3();
                } else {
                    statusLabel.setText("Incorrect answer, please try again");
                    answerField.setText("");
                }
            }
        } catch (NumberFormatException e) { 
        }
    }
    
    /**
     * Update timer display
     */
    private void updateTimerDisplay() {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        timerLabel.setText(String.format("Time: %d:%02d", minutes, seconds));
        
        // Show in red when less than 30 seconds remaining
        if (secondsRemaining <= 30) {
            timerLabel.setForeground(Color.WHITE);    
        } else {
            timerLabel.setForeground(Color.WHITE);
        }
    }
    
    /**
     * Handle correct answer for Task3
     */
    private void answerCorrectForTask3() {
        if (task3Timer != null && task3Timer.isRunning()) {
            task3Timer.stop();
        }
        mainFrame.updateScore();
        
        // Calculate points
        int points = User.calScores("Basic", 3 - task3Service.getRemainingAttempts() + 1);
        
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
        
        
        // Show score dialog
        scoreDialog.setVisible(true);
        
        showTask3Result();
        
        if (task3Service.isAllShapesCompleted()) {
            mainFrame.updateTaskStatus("Shape Area", true);
            completeTask();
        }
    }
    
    /**
     * Display task completion dialog and update UI state
     */
    public void completeTask() {
        // Clear components
        answerField.setEditable(false);
        submitButton.setEnabled(false);
        
        // Create completion dialog
        JDialog completionDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Completion", true);
        completionDialog.setLayout(new BorderLayout(10, 10));
        completionDialog.setSize(400, 220);
        completionDialog.setLocationRelativeTo(this);
        
        // Create completion panel
        JPanel completionPanel = new JPanel(new BorderLayout(10, 10));
        completionPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Add completion information
        JLabel scoreLabel = new JLabel("You got all areas!", SwingConstants.CENTER);
        scoreLabel.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        scoreLabel.setForeground(new Color(0, 150, 0));
        
        // Confirm button
        KidButton okButton = new KidButton("OK");
        okButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        okButton.addActionListener(e -> completionDialog.dispose());
        
        // Add keyboard enter listener
        completionDialog.getRootPane().setDefaultButton(okButton);
        
        // Assemble panel
        completionPanel.add(scoreLabel, BorderLayout.CENTER);
        completionPanel.add(okButton, BorderLayout.SOUTH);
        
        completionDialog.add(completionPanel);
        
        // Show completion dialog
        completionDialog.setVisible(true);
        
        // Clean up the interface
        task3DisplayPanel.removeAll();
        revalidate();
        repaint();
    }
    
    /**
     * Handle timeout for Task3
     */
    private void timeUpForTask3() {
        statusLabel.setText("Time's up!");
        statusLabel.setForeground(Color.WHITE);
        
        answerField.setEditable(false);
        submitButton.setEnabled(false);
        
        showTask3Result();

        if (task3Service.isAllShapesCompleted()) {
            mainFrame.updateTaskStatus("Shape Area", true);
            completeTask();
        }
    }
    
    /**
     * Handle when no attempts remain for Task3
     */
    private void noAttemptsLeftForTask3() {
        if (task3Timer != null && task3Timer.isRunning()) {
            task3Timer.stop();
        }
        
        statusLabel.setText("No attempts remaining");
        statusLabel.setForeground(Color.WHITE);
        
        answerField.setEditable(false);
        submitButton.setEnabled(false);
        
        showTask3Result();

        if (task3Service.isAllShapesCompleted()) {
            mainFrame.updateTaskStatus("Shape Area", true);
            completeTask();
        }
    }
    
    /**
     * Show Task3 result (with shape drawn and area formula)
     */
    private void showTask3Result() {
        task3DisplayPanel.removeAll();
        
        JPanel resultPanel = task3Service.createShapeDisplayPanel();
        submitButton.setEnabled(false);
        task3DisplayPanel.add(resultPanel, BorderLayout.CENTER);
        
        
        task3DisplayPanel.revalidate();
        task3DisplayPanel.repaint();
    }
} 