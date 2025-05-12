package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main window of the application
 */
public class MainFrame extends JFrame {
    private JProgressBar progressBar;
    private JLabel levelInfoLabel;
    private JButton endSessionButton;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    
    // 当前得分
    private int currentScore = 0;
    
    // 新增任务完成情况
    private JPanel taskStatusPanel;
    private java.util.Map<String, Boolean> currentTaskStatus = new java.util.LinkedHashMap<>() {{
        put("Task 1-2D", true);
        put("Task 1-3D", true);
        put("Task 2", true);
    }};
    
    // 界面名称常量
    public static final String HOME_PANEL = "HOME";
    public static final String SHAPE_2D_PANEL = "SHAPE_2D";
    public static final String SHAPE_3D_PANEL = "SHAPE_3D";
    public static final String ANGLE_PANEL = "ANGLE";
    public static final String EXERCISE_PANEL = "EXERCISE";
    public static final String BONUS_PANEL = "BONUS";
    public static final String KS1_SELECTION_PANEL = "KS1_SELECTION";
    public static final String KS2_SELECTION_PANEL = "KS2_SELECTION";
    
    /**
     * 构造函数
     */
    public MainFrame() {
        initComponents();
        setupLayout();
        setupListeners();
        
        // 设置窗口属性
        setTitle("Geometry Learning for Kids");
        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示
        setResizable(true);
    }
    
    /**
     * 初始化组件
     */
    private void initComponents() {
        // 创建主要组件
        levelInfoLabel = new JLabel("<html><h1>Game Level Information</h1>" +
                "<p style='font-size: 16px;'>Level 1 (KS1): Recognize basic shapes and angles </p>" +
                "<p style='font-size: 16px;'>Level 2 (KS2): Area and Perimeter</p>" +
                "<p style='font-size: 16px;'>Level 3 (Bonus): Complex shapes and sectors</p></html>");
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setString("Current Progress: 0%");
        
        endSessionButton = new JButton("End Session");
        
        // 创建卡片布局面板
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // 创建主页面
        JPanel homePanel = createHomePanel();
        
        // 创建其他功能面板
        JPanel shapePanel = new Shape2DPanel(this);
        JPanel shape3DPanel = new Shape3DPanel(this);
        JPanel anglePanel = new AnglePanel(this);
        JPanel exercisePanel = new ExercisePanel(this);
        JPanel bonusPanel = new BonusTasksPanel(this);
        JPanel ks1SelectionPanel = createKS1SelectionPanel();
        JPanel ks2SelectionPanel = createKS2SelectionPanel();
        
        // 添加到卡片布局
        cardPanel.add(homePanel, HOME_PANEL);
        cardPanel.add(shapePanel, SHAPE_2D_PANEL);
        cardPanel.add(shape3DPanel, SHAPE_3D_PANEL);
        cardPanel.add(anglePanel, ANGLE_PANEL);
        cardPanel.add(exercisePanel, EXERCISE_PANEL);
        cardPanel.add(bonusPanel, BONUS_PANEL);
        cardPanel.add(ks1SelectionPanel, KS1_SELECTION_PANEL);
        cardPanel.add(ks2SelectionPanel, KS2_SELECTION_PANEL);
    }
    
    /**
     * 创建主页面
     */
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Title section
        JLabel titleLabel = new JLabel("Welcome to Geometry Learning", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Content section
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.add(levelInfoLabel, BorderLayout.NORTH);
        
        // Create menu buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        JButton ks1Button = createMenuButton("Key Stage 1 (KS1)", "Basic shapes and angles for beginners");
        ks1Button.addActionListener(e -> showCard(KS1_SELECTION_PANEL));
        
        JButton ks2Button = createMenuButton("Key Stage 2 (KS2)", "Core and bonus tasks for advanced learners");
        ks2Button.addActionListener(e -> showCard(KS2_SELECTION_PANEL));
        
        buttonPanel.add(ks1Button);
        buttonPanel.add(ks2Button);
        
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        // Footer label
        JLabel footerLabel = new JLabel("Choose a stage to start learning!", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(footerLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * 创建KS1选择面板
     */
    private JPanel createKS1SelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 标题部分
        JLabel titleLabel = new JLabel("Please select learning content", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180)); // 钢青色
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // 内容部分 - 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JButton shapeButton = createMenuButton("2D Shapes Learning", "Learn about 2D shapes");
        shapeButton.setPreferredSize(new Dimension(150, 100));
        shapeButton.addActionListener(e -> showCard(SHAPE_2D_PANEL));
        
        JButton shape3DButton = createMenuButton("3D Shapes Learning", "Learn about 3D shapes");
        shape3DButton.setPreferredSize(new Dimension(150, 100));
        shape3DButton.addActionListener(e -> showCard(SHAPE_3D_PANEL));
        
        JButton angleButton = createMenuButton("Angles Learning", "Learn about angles");
        angleButton.setPreferredSize(new Dimension(150, 100));
        angleButton.addActionListener(e -> showCard(ANGLE_PANEL));
        
        buttonPanel.add(shapeButton);
        buttonPanel.add(shape3DButton);
        buttonPanel.add(angleButton);
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        // 返回按钮
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> showCard(HOME_PANEL));
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * 创建KS2选择面板
     */
    private JPanel createKS2SelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Please select a KS2 task", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton task3Button = createMenuButton("Task 3: Shape Area", "Calculate areas of shapes");
        task3Button.setPreferredSize(new Dimension(180, 80));
        task3Button.addActionListener(e -> showCard(EXERCISE_PANEL)); // 进入ExercisePanel并自动选中Task3

        JButton task4Button = createMenuButton("Task 4: Circle", "Circle area and circumference");
        task4Button.setPreferredSize(new Dimension(180, 80));
        task4Button.addActionListener(e -> showCard(EXERCISE_PANEL)); // 进入ExercisePanel并自动选中Task4

        JButton bonus1Button = createMenuButton("Bonus 1: Compound Area", "Advanced compound area challenge");
        bonus1Button.setPreferredSize(new Dimension(180, 80));
        bonus1Button.addActionListener(e -> showCard(BONUS_PANEL)); // 进入BonusTasksPanel并自动选中Bonus1

        JButton bonus2Button = createMenuButton("Bonus 2: Sector Area", "Advanced sector area challenge");
        bonus2Button.setPreferredSize(new Dimension(180, 80));
        bonus2Button.addActionListener(e -> showCard(BONUS_PANEL)); // 进入BonusTasksPanel并自动选中Bonus2

        buttonPanel.add(task3Button);
        buttonPanel.add(task4Button);
        buttonPanel.add(bonus1Button);
        buttonPanel.add(bonus2Button);

        panel.add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> showCard(HOME_PANEL));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    /**
     * 创建菜单按钮
     */
    private JButton createMenuButton(String title, String tooltip) {
        JButton button = new JButton(title);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(150, 80));
        button.setBackground(new Color(240, 240, 240));
        button.setFocusPainted(false);
        return button;
    }
    
    /**
     * 设置布局
     */
    private void setupLayout() {
        // 设置底部状态栏
        JPanel statusPanel = new JPanel(new BorderLayout(10, 0));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel progressPanel = new JPanel(new BorderLayout(5, 0));
        progressPanel.add(new JLabel("Learning Progress: "), BorderLayout.WEST);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        
        // 新增任务完成情况面板
        taskStatusPanel = new JPanel();
        taskStatusPanel.setLayout(new BoxLayout(taskStatusPanel, BoxLayout.X_AXIS));
        updateTaskStatusPanel();
        
        JPanel progressAndTaskPanel = new JPanel();
        progressAndTaskPanel.setLayout(new BoxLayout(progressAndTaskPanel, BoxLayout.Y_AXIS));
        progressAndTaskPanel.add(progressPanel);
        progressAndTaskPanel.add(Box.createVerticalStrut(5));
        progressAndTaskPanel.add(taskStatusPanel);
        
        statusPanel.add(progressAndTaskPanel, BorderLayout.CENTER);
        statusPanel.add(endSessionButton, BorderLayout.EAST);
        
        // 主布局
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        
        // 默认显示主页
        cardLayout.show(cardPanel, HOME_PANEL);
    }
    
    /**
     * 设置事件监听器
     */
    private void setupListeners() {
        endSessionButton.addActionListener(e -> endSession());
    }
    
    /**
     * 结束会话
     */
    private void endSession() {
        JOptionPane.showMessageDialog(this, 
                "You scored " + currentScore + " points in this session. Goodbye!", 
                "Session Ended", 
                JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    
    /**
     * 显示指定卡片
     */
    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }
    
    /**
     * 更新分数
     */
    public void updateScore(int points) {
        currentScore = points; // 直接设置分数而不是累加
        updateProgress();
    }
    
    /**
     * 更新进度条
     */
    private void updateProgress() {
        // 这里简单假设100分为满分
        int progress = Math.min(currentScore, 100);
        progressBar.setValue(progress);
        progressBar.setString("Current Progress: " + progress + "%");
    }
    
    // 新增：更新任务完成情况面板
    private void updateTaskStatusPanel() {
        taskStatusPanel.removeAll();
        if (currentTaskStatus.isEmpty()) {
            taskStatusPanel.add(new JLabel("No task status available."));
        } else {
            for (java.util.Map.Entry<String, Boolean> entry : currentTaskStatus.entrySet()) {
                String taskName = entry.getKey();
                boolean done = entry.getValue();
                JLabel label = new JLabel(taskName + ": " + (done ? "Done" : "Not Done"));
                label.setForeground(done ? new Color(0, 150, 0) : Color.GRAY);
                taskStatusPanel.add(label);
            }
        }
        taskStatusPanel.revalidate();
        taskStatusPanel.repaint();
    }
    
    // 新增：只需传入任务名和完成状态
    public void updateTaskStatus(String taskName, boolean done) {
        this.currentTaskStatus.put(taskName, done);
        updateTaskStatusPanel();
    }
    
    /**
     * 主方法
     */
    public static void main(String[] args) {
        try {
            // 设置本地系统外观
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
} 