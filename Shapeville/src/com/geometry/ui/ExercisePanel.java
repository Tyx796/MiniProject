package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.border.TitledBorder;

import com.geometry.entity.User;
import com.geometry.service.Task3;
import com.geometry.service.Task4;

/**
 * Exercise interface - Contains KS2 core tasks
 * Task 3: Shape Area Calculation
 * Task 4: Circle Area and Circumference Calculation
 */
public class ExercisePanel extends JPanel {
    private MainFrame mainFrame;
    private JButton homeButton;
    private JTabbedPane tabbedPane;  // 使用选项卡切换不同任务
    
    // Task 3相关组件
    private Task3 task3Service;
    private JPanel task3ControlPanel;
    private JPanel task3DisplayPanel;
    private JTextField answerField;
    private JLabel statusLabel;
    private JLabel attemptsLabel;
    private Timer task3Timer;
    private JLabel timerLabel;
    private int secondsRemaining;
    private JButton submitButton;
    
    // Task 4相关组件
    private Task4 task4Service;
    private JPanel task4ControlPanel;
    private JPanel task4DisplayPanel;
    private JTextField answerField4;
    private JLabel statusLabel4;
    private JLabel attemptsLabel4;
    private Timer task4Timer;
    private JLabel timerLabel4;
    private int secondsRemaining4;
    private JButton submitButton4;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口引用
     */
    public ExercisePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        // 初始化业务逻辑
        task3Service = new Task3();
        task4Service = new Task4();
        
        initComponents();
        setupLayout();
    }
    
    /**
     * 初始化组件
     */
    private void initComponents() {
        // Create return to home button
        homeButton = new JButton("Back to Home");
        homeButton.addActionListener(e -> {
            // Reset Task3 status
            if (task3Timer != null && task3Timer.isRunning()) {
                task3Timer.stop();
            }
            task3Service.reset();
            
            mainFrame.showCard(MainFrame.HOME_PANEL);
        });
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add Task 3 tab - Shape Area Calculation
        JPanel task3Panel = createTask3Panel();
        tabbedPane.addTab("Task 3: Shape Area Calculation", task3Panel);
        
        // Add Task 4 tab - Circle Area and Circumference
        JPanel task4Panel = createTask4Panel();
        tabbedPane.addTab("Task 4: Circle Calculations", task4Panel);
    }
    
    /**
     * 设置布局
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top navigation area
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("KS2 Core Tasks", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Add to main panel
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    /**
     * 创建任务3面板 - 图形面积计算
     */
    private JPanel createTask3Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Task description
        JLabel descriptionLabel = new JLabel(
                "<html><h3>Shape Area Calculation</h3>" +
                "<p>In this task, you will learn how to calculate the areas of different geometric shapes.</p>" +
                "<p>The system will generate random numbers between 1-20 as shape parameters.</p>" +
                "<p>You have 3 attempts and a 3-minute time limit for each question.</p></html>");
        panel.add(descriptionLabel, BorderLayout.NORTH);
        
        // Create shape selection area
        JPanel shapeSelectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        String[] shapes = {Task3.RECTANGLE, Task3.TRIANGLE, Task3.PARALLELOGRAM, Task3.TRAPEZIUM};
        
        for (String shape : shapes) {
            JButton shapeButton = new JButton(shape);
            shapeButton.setPreferredSize(new Dimension(120, 40));
            shapeButton.addActionListener(e -> startTask3WithShape(shape));
            shapeSelectionPanel.add(shapeButton);
        }
        
        // Create control panel
        task3ControlPanel = new JPanel();
        task3ControlPanel.setLayout(new BoxLayout(task3ControlPanel, BoxLayout.Y_AXIS));
        task3ControlPanel.setBorder(BorderFactory.createTitledBorder("Exercise Control"));
        
        // Parameter display panel
        JPanel paramsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paramsPanel.add(new JLabel("Shape Parameters:"));
        task3ControlPanel.add(paramsPanel);
        
        // Answer input area
        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        answerPanel.add(new JLabel("Enter Area:"));
        answerField = new JTextField(10);
        answerPanel.add(answerField);
        
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> checkTask3Answer());
        submitButton.setEnabled(false);
        answerPanel.add(submitButton);
        task3ControlPanel.add(answerPanel);
        
        // Status and attempts panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Please select a shape to start");
        attemptsLabel = new JLabel("Remaining attempts: 3");
        statusPanel.add(statusLabel);
        statusPanel.add(Box.createHorizontalStrut(20));
        statusPanel.add(attemptsLabel);
        task3ControlPanel.add(statusPanel);
        
        // Timer panel
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timerLabel = new JLabel("Time remaining: 3:00");
        timerPanel.add(timerLabel);
        task3ControlPanel.add(timerPanel);
        
        // Create display area
        task3DisplayPanel = new JPanel(new BorderLayout());
        task3DisplayPanel.setBorder(BorderFactory.createTitledBorder("Shape Display"));
        JLabel placeholderLabel = new JLabel(
                "<html>Select a shape to start...<br>" +
                "The randomly generated shape and its parameters will be shown here.</html>", 
                JLabel.CENTER);
        task3DisplayPanel.add(placeholderLabel, BorderLayout.CENTER);
        
        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(shapeSelectionPanel, BorderLayout.NORTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                task3ControlPanel, task3DisplayPanel);
        splitPane.setDividerLocation(300);
        centerPanel.add(splitPane, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * 使用选定的图形启动任务3
     * @param shape 图形类型
     */
    private void startTask3WithShape(String shape) {
        // 重置之前的计时器（如果存在）
        if (task3Timer != null && task3Timer.isRunning()) {
            task3Timer.stop();
        }
        
        // 清空显示面板
        task3DisplayPanel.removeAll();
        
        // 通过Task3服务选择图形并生成参数
        task3Service.selectShape(shape);
        
        // 更新参数显示
        updateTask3ParamsDisplay();
        
        // 清空答案字段
        answerField.setText("");
        answerField.setEditable(true);
        submitButton.setEnabled(true);
        
        // 更新状态和尝试次数
        statusLabel.setText("Please calculate the area of " + shape);
        attemptsLabel.setText("Remaining attempts: " + task3Service.getRemainingAttempts());
        
        // 启动3分钟计时器
        secondsRemaining = 180; // 3分钟 = 180秒
        updateTimerDisplay();
        
        task3Timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsRemaining--;
                updateTimerDisplay();
                
                if (secondsRemaining <= 0) {
                    // 时间到，停止计时器
                    task3Timer.stop();
                    timeUpForTask3();
                }
            }
        });
        task3Timer.start();
        
        // 刷新UI
        task3DisplayPanel.revalidate();
        task3DisplayPanel.repaint();
    }
    
    /**
     * 更新任务3的参数显示
     */
    private void updateTask3ParamsDisplay() {
        // 获取参数名称和值
        String[] paramNames = task3Service.getParameterNames();
        int[] paramValues = task3Service.getParameters();
        
        // 清空参数面板并重新创建
        Component[] components = task3ControlPanel.getComponents();
        if (components.length > 0 && components[0] instanceof JPanel) {
            JPanel paramsPanel = (JPanel) components[0];
            paramsPanel.removeAll();
            paramsPanel.add(new JLabel("Shape Parameters:"));
            
            // 添加每个参数
            for (int i = 0; i < paramNames.length; i++) {
                JLabel paramLabel = new JLabel(paramNames[i] + ": " + paramValues[i]);
                paramLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                paramsPanel.add(paramLabel);
            }
            
            paramsPanel.revalidate();
            paramsPanel.repaint();
        }
    }
    
    /**
     * 检查任务3的答案
     */
    private void checkTask3Answer() {
        try {
            double userAnswer = Double.parseDouble(answerField.getText().trim());
            boolean isCorrect = task3Service.checkAnswer(userAnswer);
            attemptsLabel.setText("Remaining attempts: " + task3Service.getRemainingAttempts());
            
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
            JOptionPane.showMessageDialog(this, 
                    "Please enter a valid number", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 更新计时器显示
     */
    private void updateTimerDisplay() {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        timerLabel.setText(String.format("Time remaining: %d:%02d", minutes, seconds));
        
        // 当时间不足30秒时，显示红色
        if (secondsRemaining <= 30) {
            timerLabel.setForeground(Color.RED);
        } else {
            timerLabel.setForeground(Color.BLACK);
        }
    }
    
    /**
     * 任务3答案正确时的处理
     */
    private void answerCorrectForTask3() {
        if (task3Timer != null && task3Timer.isRunning()) {
            task3Timer.stop();
        }
        
        int attemptsUsed = 3 - task3Service.getRemainingAttempts() + 1;
        
        JDialog scoreDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Score Alert", true);
        scoreDialog.setLayout(new BorderLayout(10, 10));
        scoreDialog.setSize(300, 150);
        scoreDialog.setLocationRelativeTo(this);
        
        JPanel scorePanel = new JPanel(new BorderLayout(10, 10));
        scorePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel scoreLabel = new JLabel("Congratulations! You earned " + User.calScores("Basic", attemptsUsed) + " points!", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(new Color(0, 150, 0));
        
        JButton okButton = new JButton("Continue");
        okButton.addActionListener(e -> scoreDialog.dispose());
        
        scorePanel.add(scoreLabel, BorderLayout.NORTH);
        scorePanel.add(okButton, BorderLayout.SOUTH);
        
        scoreDialog.add(scorePanel);
        
        statusLabel.setText("Correct answer!");
        statusLabel.setForeground(new Color(0, 150, 0));
        
        answerField.setEditable(false);
        submitButton.setEnabled(false);
        
        showTask3Result();
        
        scoreDialog.setVisible(true);
        
        if (task3Service.isAllShapesCompleted()) {
            mainFrame.updateTaskStatus("Shape Area", true);
            JOptionPane.showMessageDialog(this, 
                    "Congratulations! You have completed all shape area calculations.", 
                    "Exercise Complete", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * 任务3超时时的处理
     */
    private void timeUpForTask3() {
        statusLabel.setText("Time's up!");
        statusLabel.setForeground(Color.RED);
        
        answerField.setEditable(false);
        submitButton.setEnabled(false);
        
        showTask3Result();
    }
    
    /**
     * 任务3尝试次数用完时的处理
     */
    private void noAttemptsLeftForTask3() {
        if (task3Timer != null && task3Timer.isRunning()) {
            task3Timer.stop();
        }
        
        statusLabel.setText("No attempts remaining");
        statusLabel.setForeground(Color.RED);
        
        answerField.setEditable(false);
        submitButton.setEnabled(false);
        
        showTask3Result();
    }
    
    /**
     * 显示任务3的结果（带有面积公式和计算的图形）
     */
    private void showTask3Result() {
        task3DisplayPanel.removeAll();
        
        JPanel resultPanel = task3Service.createShapeDisplayPanel();
        task3DisplayPanel.add(resultPanel, BorderLayout.CENTER);
        
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        
        JLabel formulaLabel = new JLabel("Formula: " + task3Service.getAreaFormula());
        JLabel areaLabel = new JLabel("Correct area: " + new DecimalFormat("0.##").format(task3Service.getArea()));
        
        answerPanel.add(formulaLabel);
        answerPanel.add(Box.createVerticalStrut(5));
        answerPanel.add(areaLabel);
        
        task3DisplayPanel.add(answerPanel, BorderLayout.SOUTH);
        
        task3DisplayPanel.revalidate();
        task3DisplayPanel.repaint();
    }
    
    /**
     * 创建任务4面板 - 圆的面积和周长计算
     */
    private JPanel createTask4Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel descriptionLabel = new JLabel(
                "<html><h3>Circle Area and Circumference Calculation</h3>" +
                "<p>Please select a question type. The system will generate random radius or diameter values.</p>" +
                "<p>You have 3 attempts and a 3-minute time limit for each question.</p></html>");
        panel.add(descriptionLabel, BorderLayout.NORTH);
        
        // Question type selection
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        String[] types = {Task4.AREA_RADIUS, Task4.AREA_DIAMETER, Task4.CIRC_RADIUS, Task4.CIRC_DIAMETER};
        for (String type : types) {
            JButton btn = new JButton(type);
            btn.setPreferredSize(new Dimension(180, 40));
            btn.addActionListener(e -> startTask4WithType(type));
            typePanel.add(btn);
        }
        
        // Control panel
        task4ControlPanel = new JPanel();
        task4ControlPanel.setLayout(new BoxLayout(task4ControlPanel, BoxLayout.Y_AXIS));
        task4ControlPanel.setBorder(BorderFactory.createTitledBorder("Exercise Control"));
        
        // Parameter display
        JPanel paramsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paramsPanel.add(new JLabel("Parameter:"));
        task4ControlPanel.add(paramsPanel);
        
        // Answer input
        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        answerPanel.add(new JLabel("Enter answer:"));
        answerField4 = new JTextField(10);
        answerPanel.add(answerField4);
        
        submitButton4 = new JButton("Submit");
        submitButton4.addActionListener(e -> checkTask4Answer());
        submitButton4.setEnabled(false);
        answerPanel.add(submitButton4);
        task4ControlPanel.add(answerPanel);
        
        // Status and attempts
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel4 = new JLabel("Please select a question type to start");
        attemptsLabel4 = new JLabel("Remaining attempts: 3");
        statusPanel.add(statusLabel4);
        statusPanel.add(Box.createHorizontalStrut(20));
        statusPanel.add(attemptsLabel4);
        task4ControlPanel.add(statusPanel);
        
        // Timer
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timerLabel4 = new JLabel("Time remaining: 3:00");
        timerPanel.add(timerLabel4);
        task4ControlPanel.add(timerPanel);
        
        // Display area
        task4DisplayPanel = new JPanel(new BorderLayout());
        task4DisplayPanel.setBorder(BorderFactory.createTitledBorder("Circle Display"));
        JLabel placeholderLabel = new JLabel(
                "<html>Select a question type to start...<br>The randomly generated circle and its parameters will be shown here.</html>",
                JLabel.CENTER);
        task4DisplayPanel.add(placeholderLabel, BorderLayout.CENTER);
        
        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(typePanel, BorderLayout.NORTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                task4ControlPanel, task4DisplayPanel);
        splitPane.setDividerLocation(300);
        centerPanel.add(splitPane, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private void startTask4WithType(String type) {
        if (task4Timer != null && task4Timer.isRunning()) {
            task4Timer.stop();
        }
        task4DisplayPanel.removeAll();
        task4Service.selectType(type);
        updateTask4ParamsDisplay();
        answerField4.setText("");
        answerField4.setEditable(true);
        submitButton4.setEnabled(true);
        statusLabel4.setText("Please calculate " + type);
        statusLabel4.setForeground(Color.BLACK);
        attemptsLabel4.setText("Remaining attempts: " + task4Service.getRemainingAttempts());
        secondsRemaining4 = 180;
        updateTimerDisplay4();
        task4Timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsRemaining4--;
                updateTimerDisplay4();
                if (secondsRemaining4 <= 0) {
                    task4Timer.stop();
                    timeUpForTask4();
                }
            }
        });
        task4Timer.start();
        task4DisplayPanel.revalidate();
        task4DisplayPanel.repaint();
    }
    
    private void updateTask4ParamsDisplay() {
        Component[] components = task4ControlPanel.getComponents();
        if (components.length > 0 && components[0] instanceof JPanel) {
            JPanel paramsPanel = (JPanel) components[0];
            paramsPanel.removeAll();
            paramsPanel.add(new JLabel("Parameter:"));
            JLabel paramLabel = new JLabel(task4Service.getParamLabel() + task4Service.getValue());
            paramLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            paramsPanel.add(paramLabel);
            
            JLabel piTip = new JLabel("(Please use π = 3.14 for calculations)");
            piTip.setForeground(new Color(70, 130, 180));
            piTip.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            paramsPanel.add(piTip);
            
            paramsPanel.revalidate();
            paramsPanel.repaint();
        }
    }
    
    private void checkTask4Answer() {
        try {
            double userAnswer = Double.parseDouble(answerField4.getText().trim());
            boolean isCorrect = task4Service.checkAnswer(userAnswer);
            attemptsLabel4.setText("Remaining attempts: " + task4Service.getRemainingAttempts());
            
            if (isCorrect) {
                answerCorrectForTask4();
            } else {
                if (task4Service.getRemainingAttempts() <= 0) {
                    noAttemptsLeftForTask4();
                } else {
                    statusLabel4.setText("Incorrect answer, please try again");
                    statusLabel4.setForeground(Color.RED);
                    answerField4.setText("");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateTimerDisplay4() {
        int minutes = secondsRemaining4 / 60;
        int seconds = secondsRemaining4 % 60;
        timerLabel4.setText(String.format("Time remaining: %d:%02d", minutes, seconds));
        if (secondsRemaining4 <= 30) {
            timerLabel4.setForeground(Color.RED);
        } else {
            timerLabel4.setForeground(Color.BLACK);
        }
    }
    
    private void answerCorrectForTask4() {
        if (task4Timer != null && task4Timer.isRunning()) {
            task4Timer.stop();
        }
        
        int points = User.calScores("Basic", 3 - task4Service.getRemainingAttempts() + 1);
        
        JDialog scoreDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Score Alert", true);
        scoreDialog.setLayout(new BorderLayout(10, 10));
        scoreDialog.setSize(300, 150);
        scoreDialog.setLocationRelativeTo(this);
        
        JPanel scorePanel = new JPanel(new BorderLayout(10, 10));
        scorePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel scoreLabel = new JLabel("Congratulations! You earned " + points + " points!", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(new Color(0, 150, 0));
        
        JButton okButton = new JButton("Continue");
        okButton.addActionListener(e -> scoreDialog.dispose());
        
        scorePanel.add(scoreLabel, BorderLayout.NORTH);
        scorePanel.add(okButton, BorderLayout.SOUTH);
        
        scoreDialog.add(scorePanel);
        
        statusLabel4.setText("Correct answer!");
        statusLabel4.setForeground(new Color(0, 150, 0));
        answerField4.setEditable(false);
        submitButton4.setEnabled(false);
        
        showTask4Result();
        
        scoreDialog.setVisible(true);
        
        if (task4Service.isAllCompleted()) {
            mainFrame.updateTaskStatus("Circle", true);
            JOptionPane.showMessageDialog(this,
                    "Congratulations! You have completed all circle calculations.",
                    "Exercise Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void timeUpForTask4() {
        statusLabel4.setText("Time's up!");
        statusLabel4.setForeground(Color.RED);
        answerField4.setEditable(false);
        submitButton4.setEnabled(false);
        showTask4Result();
    }
    
    private void noAttemptsLeftForTask4() {
        if (task4Timer != null && task4Timer.isRunning()) {
            task4Timer.stop();
        }
        statusLabel4.setText("No attempts remaining");
        statusLabel4.setForeground(Color.RED);
        answerField4.setEditable(false);
        submitButton4.setEnabled(false);
        showTask4Result();
    }
    
    private void showTask4Result() {
        task4DisplayPanel.removeAll();
        JPanel resultPanel = task4Service.createDisplayPanel();
        task4DisplayPanel.add(resultPanel, BorderLayout.CENTER);
        
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        
        JLabel formulaLabel = new JLabel("Formula: " + task4Service.getFormula());
        JLabel valueLabel = new JLabel("Correct answer: " + new DecimalFormat("0.##").format(task4Service.getAnswer()));
        
        answerPanel.add(formulaLabel);
        answerPanel.add(Box.createVerticalStrut(5));
        answerPanel.add(valueLabel);
        
        task4DisplayPanel.add(answerPanel, BorderLayout.SOUTH);
        task4DisplayPanel.revalidate();
        task4DisplayPanel.repaint();
    }
} 