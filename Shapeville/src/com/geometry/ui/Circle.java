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
import com.geometry.service.Task4;
import com.geometry.ui.uiUtils.KidButton;

/**
 * Circle Area and Circumference Calculation interface
 * Task 4: Circle Area and Circumference Calculation
 */
public class Circle extends JPanel {
    private MainFrame mainFrame;
    private KidButton homeButton;
    private static final String FONT_NAME = "Comic Sans MS";
    
    // Task 4相关组件
    private Task4 task4Service;
    private JPanel task4ControlPanel;
    private JPanel task4DisplayPanel;
    private JTextField answerField;
    private JLabel statusLabel;
    private JLabel attemptsLabel;
    private Timer task4Timer;
    private JLabel timerLabel;
    private int secondsRemaining;
    private KidButton submitButton;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口引用
     */
    public Circle(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        // 初始化业务逻辑
        task4Service = new Task4();
        
        initComponents();
        setupLayout();
    }
    
    /**
     * 初始化组件
     */
    private void initComponents() {
        // Create return to home button
        homeButton = new KidButton("Home");
        homeButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        homeButton.setPreferredSize(new Dimension(150, 40));
        homeButton.addActionListener(e -> {
            // Reset Timer status
            if (task4Timer != null && task4Timer.isRunning()) {
                task4Timer.stop();
            }
            
            mainFrame.showCard(MainFrame.HOME_PANEL);
        });
    }
    
    /**
     * 设置布局
     */
    private void setupLayout() {
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 240), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        
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
        
        // color: rgb(177, 208, 239)
        contentPanel.setBackground(new Color(177, 208, 239));
        contentPanel.setOpaque(true);
        
        // Add to main panel
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * 创建主面板 - 圆的面积和周长计算
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 18));
        
        JLabel descriptionLabel = new JLabel(
                "<html><h3 style='font-family: " + FONT_NAME + "; font-size: 24px; color: rgb(52, 119, 219);'>" +
                "Circle Area and Circumference</h3>" +
                "<p style='font-family: " + FONT_NAME + "; font-size: 18px; color: white;'>" +
                "You have 3 tries and 3 minutes for each question.</p></html>");
        panel.add(descriptionLabel, BorderLayout.NORTH);
        
        // Question type selection
        JPanel typePanel = new JPanel(new GridLayout(2, 2, 15, 15));
        typePanel.setBorder(BorderFactory.createEmptyBorder(0, 110, 10, 110));
        typePanel.setOpaque(false);
        String[] types = {Task4.AREA_RADIUS, Task4.AREA_DIAMETER, Task4.CIRC_RADIUS, Task4.CIRC_DIAMETER};
        for (String type : types) {
            KidButton btn = new KidButton(type);
            btn.setFont(new Font(FONT_NAME, Font.BOLD, 20));
            btn.setPreferredSize(new Dimension(200, 50));
            btn.addActionListener(e -> {
                startTask4WithType(type);
                // 点击类型按钮后，让答案输入框自动获取焦点
                answerField.requestFocusInWindow();
            });
            typePanel.add(btn);
        }
        
        // Control panel
        task4ControlPanel = new JPanel();
        task4ControlPanel.setLayout(new BoxLayout(task4ControlPanel, BoxLayout.Y_AXIS));
        task4ControlPanel.setOpaque(false);
        task4ControlPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 119, 219), 2),
                "Exercise Control",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font(FONT_NAME, Font.BOLD, 20),
                new Color(52, 119, 219)
        ));
        
        // Parameter display
        JPanel paramsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paramsPanel.setOpaque(false);
        JLabel paramsLabel = new JLabel("Parameters:");
        paramsLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        paramsLabel.setForeground(Color.WHITE);
        paramsPanel.add(paramsLabel);
        task4ControlPanel.add(paramsPanel);
        
        // Answer input
        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        answerPanel.setOpaque(false);
        JLabel answerLabel = new JLabel("Answer:");
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
        submitButton.addActionListener(e -> checkAnswer());
        submitButton.setEnabled(false);
        answerPanel.add(submitButton);
        
        // 添加回车键监听器，使回车键也能触发答案检查
        answerField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && submitButton.isEnabled()) {
                    checkAnswer();
                }
            }
        });
        
        task4ControlPanel.add(answerPanel);



        
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
        task4ControlPanel.add(statusPanel);
        
        // Timer
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timerPanel.setOpaque(false);
        timerLabel = new JLabel("Time: 3:00");
        timerLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        timerLabel.setForeground(Color.WHITE);
        timerPanel.add(timerLabel);
        task4ControlPanel.add(timerPanel);
        
        // Display area
        task4DisplayPanel = new JPanel(new BorderLayout());
        task4DisplayPanel.setOpaque(false);
        task4DisplayPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 119, 219), 2),
                "Circle Display",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font(FONT_NAME, Font.BOLD, 20),
                new Color(52, 119, 219)
        ));
        
        JLabel placeholderLabel = new JLabel(
                "<html><p style='font-family: " + FONT_NAME + "; font-size: 18px; color: white;'>" +
                "Select a question type to start...<br>The circle and its parameters will be shown here.</p></html>",
                SwingConstants.CENTER);
        task4DisplayPanel.add(placeholderLabel, BorderLayout.CENTER);
        
        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(typePanel, BorderLayout.NORTH);
        
        JPanel horizontalPanel = new JPanel(new BorderLayout());
        horizontalPanel.setOpaque(false);
        
        // 左侧控制面板，固定宽度300
        task4ControlPanel.setPreferredSize(new Dimension(300, task4ControlPanel.getPreferredSize().height));
        horizontalPanel.add(task4ControlPanel, BorderLayout.WEST);
        
        // 右侧显示面板
        horizontalPanel.add(task4DisplayPanel, BorderLayout.CENTER);
        
        centerPanel.add(horizontalPanel, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private void startTask4WithType(String type) {
        if (task4Timer != null && task4Timer.isRunning()) {
            task4Timer.stop();
        }
        task4DisplayPanel.removeAll();
        task4Service.selectType(type);
        updateParamsDisplay();
        answerField.setText("");
        answerField.setEditable(true);
        submitButton.setEnabled(true);
        attemptsLabel.setText("Tries: " + task4Service.getRemainingAttempts());
        secondsRemaining = 180;
        updateTimerDisplay();
        task4Timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsRemaining--;
                updateTimerDisplay();
                if (secondsRemaining <= 0) {
                    task4Timer.stop();
                    timeUp();
                }
            }
        });
        task4Timer.start();
        task4DisplayPanel.revalidate();
        task4DisplayPanel.repaint();
    }
    
    private void updateParamsDisplay() {
        Component[] components = task4ControlPanel.getComponents();
        if (components.length > 0 && components[0] instanceof JPanel) {
            JPanel paramsPanel = (JPanel) components[0];
            paramsPanel.removeAll();
            
            JLabel paramTitleLabel = new JLabel("Parameters:");
            paramTitleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
            paramTitleLabel.setForeground(Color.WHITE);
            paramsPanel.add(paramTitleLabel);
            
            JLabel paramLabel = new JLabel(task4Service.getParamLabel() + task4Service.getValue());
            paramLabel.setFont(new Font(FONT_NAME, Font.BOLD, 18));
            paramLabel.setForeground(Color.WHITE);
            paramLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            paramsPanel.add(paramLabel);
            
            JLabel piTip = new JLabel("(Use π = 3.14 for calculations)");
            piTip.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
            piTip.setForeground(new Color(70, 130, 180));
            piTip.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            paramsPanel.add(piTip);
            
            paramsPanel.revalidate();
            paramsPanel.repaint();
        }
    }
    
    private void checkAnswer() {
        try {
            double userAnswer = Double.parseDouble(answerField.getText().trim());
            boolean isCorrect = task4Service.checkAnswer(userAnswer);
            attemptsLabel.setText("Tries: " + task4Service.getRemainingAttempts());
            
            if (isCorrect) {
                answerCorrect();
            } else {
                if (task4Service.getRemainingAttempts() <= 0) {
                    noAttemptsLeft();
                } else {
                    statusLabel.setText("Incorrect answer, please try again");
                    statusLabel.setForeground(Color.RED);
                    answerField.setText("");
                }
            }
        } catch (NumberFormatException e) {
        }
    }
    
    private void updateTimerDisplay() {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        timerLabel.setText(String.format("Time: %d:%02d", minutes, seconds));
        if (secondsRemaining <= 30) {
            timerLabel.setForeground(Color.RED);
        } else {
            timerLabel.setForeground(Color.WHITE);
        }
    }
    
    private void answerCorrect() {
        if (task4Timer != null && task4Timer.isRunning()) {
            task4Timer.stop();
        }
        
        mainFrame.updateScore();
        
        // 计算得分
        int points = User.calScores("Basic", 3 - task4Service.getRemainingAttempts() + 1);
        
        // 创建得分提示弹窗
        JDialog scoreDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Score", true);
        scoreDialog.setLayout(new BorderLayout(10, 10));
        scoreDialog.setSize(400, 220);
        scoreDialog.setLocationRelativeTo(this);
        
        // 创建得分面板
        JPanel scorePanel = new JPanel(new BorderLayout(10, 10));
        scorePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // 添加得分信息
        JLabel scoreLabel = new JLabel("Score + " + points + " !", SwingConstants.CENTER);
        scoreLabel.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        scoreLabel.setForeground(new Color(0, 150, 0));
        
        // 确认按钮
        KidButton okButton = new KidButton("OK");
        okButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        okButton.addActionListener(e -> scoreDialog.dispose());
        
        // 添加键盘回车监听
        scoreDialog.getRootPane().setDefaultButton(okButton);
        
        // 组装面板
        scorePanel.add(scoreLabel, BorderLayout.CENTER);
        scorePanel.add(okButton, BorderLayout.SOUTH);
        
        scoreDialog.add(scorePanel);
        
        // 更新主窗口分数显示
        mainFrame.updateScore();
        
        // 显示得分弹窗
        scoreDialog.setVisible(true);
        
        showResult();
        
        if (task4Service.isAllCompleted()) {
            mainFrame.updateTaskStatus("Circle", true);
            completeTask();
        }
    }
    
    public void completeTask() {
        // 清除组件
        answerField.setEditable(false);
        submitButton.setEnabled(false);
        
        // 创建完成任务弹窗
        JDialog completionDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Completion", true);
        completionDialog.setLayout(new BorderLayout(10, 10));
        completionDialog.setSize(400, 220);
        completionDialog.setLocationRelativeTo(this);
        
        // 创建完成面板
        JPanel completionPanel = new JPanel(new BorderLayout(10, 10));
        completionPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // 添加完成信息
        JLabel scoreLabel = new JLabel("You got all circles!", SwingConstants.CENTER);
        scoreLabel.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        scoreLabel.setForeground(new Color(0, 150, 0));
        
        // 确认按钮
        KidButton okButton = new KidButton("OK");
        okButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        okButton.addActionListener(e -> completionDialog.dispose());
        
        // 添加键盘回车监听
        completionDialog.getRootPane().setDefaultButton(okButton);
        
        // 组装面板
        completionPanel.add(scoreLabel, BorderLayout.CENTER);
        completionPanel.add(okButton, BorderLayout.SOUTH);
        
        completionDialog.add(completionPanel);
        
        // 显示完成弹窗
        completionDialog.setVisible(true);
        
        // 清理界面
        task4DisplayPanel.removeAll();
        revalidate();
        repaint();
    }
    
    private void timeUp() {
        statusLabel.setText("Time's up!");
        statusLabel.setForeground(Color.RED);
        answerField.setEditable(false);
        submitButton.setEnabled(false);
        showResult();
    }
    
    private void noAttemptsLeft() {
        if (task4Timer != null && task4Timer.isRunning()) {
            task4Timer.stop();
        }
        statusLabel.setText("No attempts remaining");
        statusLabel.setForeground(Color.RED);
        answerField.setEditable(false);
        submitButton.setEnabled(false);
        showResult();
    }
    
    private void showResult() {
        task4DisplayPanel.removeAll();
        JPanel resultPanel = task4Service.createDisplayPanel();
        task4DisplayPanel.add(resultPanel, BorderLayout.CENTER);
        
        task4DisplayPanel.revalidate();
        task4DisplayPanel.repaint();
    }
} 