package com.geometry.ui;

import javax.swing.*;

import com.geometry.ui.uiUtils.KidButton;
import com.geometry.ui.uiUtils.TaskStatusButton;

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
    private int choiceKS1 = 0;
    private int choiceKS2 = 0;
    
    // 当前得分
    private int currentScore = 0;
    
    // 新增任务完成情况
    private JPanel taskStatusPanel;
    private java.util.Map<String, Boolean> currentTaskStatus = new java.util.LinkedHashMap<>();
    
    // 界面名称常量
    public static final String HOME_PANEL = "HOME";
    public static final String SHAPE_2D_PANEL = "SHAPE_2D";
    public static final String SHAPE_3D_PANEL = "SHAPE_3D";
    public static final String ANGLE_PANEL = "ANGLE";
    public static final String EXERCISE_PANEL = "EXERCISE";
    public static final String BONUS_PANEL = "BONUS";
    public static final String KS1_SELECTION_PANEL = "KS1_SELECTION";
    public static final String KS2_SELECTION_PANEL = "KS2_SELECTION";
    public static final String FONT_NAME = "Comic Sans MS";
    
    // 在MainFrame中保存BackgroundPanel引用
    private BackgroundPanel bgPanel;
    
    /**
     * 构造函数
     */
    public MainFrame() {
        initComponents();
        setupLayout();
        setupListeners();
        
        // 设置窗口属性
        setTitle("Shapeville");
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
        levelInfoLabel = new JLabel("<html>" +
                "<p style='font-size: 24px; font-family: \"Comic Sans MS\"; color:rgb(52, 119, 219);'>Key Stage 1 (KS1): Recognize basic shapes and angles </p>" +
                "<p style='font-size: 24px; font-family: \"Comic Sans MS\"; color:rgb(52, 119, 219);'>Key Stage 2 (KS2): Area and Perimeter</p>"
                );
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setString("Current Score: 0");
        progressBar.setPreferredSize(new Dimension(progressBar.getPreferredSize().width, 40));
        progressBar.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        
        endSessionButton = new KidButton("End");
        endSessionButton.setFont(new Font(FONT_NAME, Font.BOLD, 26));
        
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

        cardPanel.setOpaque(false);
        homePanel.setOpaque(false);
        shapePanel.setOpaque(false);
        shape3DPanel.setOpaque(false);
        anglePanel.setOpaque(false);
        exercisePanel.setOpaque(false);
        bonusPanel.setOpaque(false);
        ks1SelectionPanel.setOpaque(false);
        ks2SelectionPanel.setOpaque(false);
        
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
    
        
        // Content section
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        // 创建一个包含levelInfoLabel的面板，并添加上边距
        JPanel levelInfoPanel = new JPanel(new BorderLayout());
        levelInfoPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        levelInfoPanel.add(levelInfoLabel, BorderLayout.CENTER);
        levelInfoPanel.setOpaque(false);
        contentPanel.add(levelInfoPanel, BorderLayout.NORTH);
        
        // Create menu buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 0)); // 使用FlowLayout并设置水平间距为100
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        JButton ks1Button = createMenuButtonHome("Key Stage 1");
        ks1Button.addActionListener(e -> showCard(KS1_SELECTION_PANEL));
        
        JButton ks2Button = createMenuButtonHome("Key Stage 2");
        ks2Button.addActionListener(e -> showCard(KS2_SELECTION_PANEL));
        
        buttonPanel.add(ks1Button);
        buttonPanel.add(ks2Button);
        buttonPanel.setOpaque(false);

        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        contentPanel.setOpaque(false);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        
        return panel;
    }
    
    /**
     * 创建KS1选择面板
     */
    private JPanel createKS1SelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        
        // 内容部分 - 按钮面板
        JPanel centerPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout实现居中
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); // 减小水平间距为30
        
        JButton shapeButton = createMenuButtonKS("2D Shapes");
        shapeButton.setPreferredSize(new Dimension(200, 100));
        shapeButton.addActionListener(e -> showCard(SHAPE_2D_PANEL));
        
        JButton shape3DButton = createMenuButtonKS("3D Shapes");
        shape3DButton.setPreferredSize(new Dimension(200, 100));
        shape3DButton.addActionListener(e -> showCard(SHAPE_3D_PANEL));
        
        JButton angleButton = createMenuButtonKS("Angles");
        angleButton.setPreferredSize(new Dimension(200, 100));
        angleButton.addActionListener(e -> showCard(ANGLE_PANEL));
        
        buttonPanel.add(shapeButton);
        buttonPanel.add(shape3DButton);
        buttonPanel.add(angleButton);
        buttonPanel.setOpaque(false);
        
        // 将按钮面板添加到居中面板
        centerPanel.add(buttonPanel);
        centerPanel.setOpaque(false);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // 返回按钮
        JButton btn = new KidButton("Home");
        btn.setFont(new Font(FONT_NAME, Font.BOLD, 26));
        btn.addActionListener(e -> showCard(HOME_PANEL));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btn);
        bottomPanel.setOpaque(false);
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

        JButton task3Button = createMenuButtonKS("Task 3: Shape Area");
        task3Button.setPreferredSize(new Dimension(180, 80));
        task3Button.addActionListener(e -> showCard(EXERCISE_PANEL)); // 进入ExercisePanel并自动选中Task3

        JButton task4Button = createMenuButtonKS("Task 4: Circle");
        task4Button.setPreferredSize(new Dimension(180, 80));
        task4Button.addActionListener(e -> showCard(EXERCISE_PANEL)); // 进入ExercisePanel并自动选中Task4

        JButton bonus1Button = createMenuButtonKS("Bonus 1: Compound Area");
        bonus1Button.setPreferredSize(new Dimension(180, 80));
        bonus1Button.addActionListener(e -> showCard(BONUS_PANEL)); // 进入BonusTasksPanel并自动选中Bonus1

        JButton bonus2Button = createMenuButtonKS("Bonus 2: Sector Area");
        bonus2Button.setPreferredSize(new Dimension(180, 80));
        bonus2Button.addActionListener(e -> showCard(BONUS_PANEL)); // 进入BonusTasksPanel并自动选中Bonus2

        buttonPanel.add(task3Button);
        buttonPanel.add(task4Button);
        buttonPanel.add(bonus1Button);
        buttonPanel.add(bonus2Button);

        panel.add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Home");
        backButton.addActionListener(e -> showCard(HOME_PANEL));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    /**
     * 创建菜单按钮
     */
    private JButton createMenuButtonHome(String title) {
        JButton button = new JButton(title);
        button.setOpaque(false);
        button.setBackground(new Color(255, 255, 255, 100)); 
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        // color: rgb(64, 154, 56)
        button.setForeground(new Color(52, 119, 219));
        button.setPreferredSize(new Dimension(250, 100));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 5));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0), 5));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 5));
            }
        });
        return button;
    }
    /**
     * 创建菜单按钮
     */
    private JButton createMenuButtonKS(String title) {
        JButton button = new JButton(title);
        button.setOpaque(false);
        // color: rgb(102, 102, 98)
        button.setBackground(new Color(255, 255, 255, 100)); 
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        button.setForeground(new Color(52, 119, 219));
        button.setPreferredSize(new Dimension(250, 100));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 5));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0), 5));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 5));
            }
        });
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
        JLabel progressLabel = new JLabel("Progress ");
        progressLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        progressPanel.add(progressLabel, BorderLayout.WEST);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        
        // 新增任务完成情况面板
        taskStatusPanel = new JPanel();
        taskStatusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        updateTaskStatusPanel();
        
        JPanel progressAndTaskPanel = new JPanel();
        progressAndTaskPanel.setLayout(new BoxLayout(progressAndTaskPanel, BoxLayout.Y_AXIS));
        progressAndTaskPanel.add(progressPanel);
        progressAndTaskPanel.add(Box.createVerticalStrut(10));
        progressAndTaskPanel.add(taskStatusPanel);
        
        statusPanel.add(progressAndTaskPanel, BorderLayout.CENTER);
        statusPanel.add(endSessionButton, BorderLayout.EAST);
        
        // 主布局
        setLayout(new BorderLayout());
        bgPanel = new BackgroundPanel();
        bgPanel.add(cardPanel, BorderLayout.CENTER);
        bgPanel.add(statusPanel, BorderLayout.SOUTH);
        add(bgPanel, BorderLayout.CENTER);

        
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
        if (bgPanel != null) {
            if (HOME_PANEL.equals(cardName)) {
                bgPanel.setBackgroundImage("/com/geometry/ui/imgs/bg1.png");
            } else {
                bgPanel.setBackgroundImage("/com/geometry/ui/imgs/bg3.png");
            }
        }
        if (KS1_SELECTION_PANEL.equals(cardName) && choiceKS1 == 0) {
            choiceKS1 = 1;
            choiceKS2 = 0;
            currentTaskStatus.clear();
            updateTaskStatus("2D Shapes", false);
            updateTaskStatus("3D Shapes", false);
            updateTaskStatus("Angles", false);
            updateTaskStatusPanel();
        } else if (KS2_SELECTION_PANEL.equals(cardName) && choiceKS2 == 0) {
            choiceKS2 = 1;
            choiceKS1 = 0;
            currentTaskStatus.clear();
            updateTaskStatus("Shape Area", false);
            updateTaskStatus("Circle", false);
            updateTaskStatus("Compound Area", false);
            updateTaskStatus("Sector Area", false);
            updateTaskStatusPanel();
        }
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
        progressBar.setString("Current Score: " + progress);
    }
    
    // 新增：更新任务完成情况面板
    private void updateTaskStatusPanel() {
        taskStatusPanel.removeAll();
        if (!currentTaskStatus.isEmpty()) {
            for (java.util.Map.Entry<String, Boolean> entry : currentTaskStatus.entrySet()) {
                String taskName = entry.getKey();
                boolean done = entry.getValue();
                TaskStatusButton taskButton = new TaskStatusButton(taskName);
                
                // 设置按钮颜色
                if (done) {
                    taskButton.setNormalColor(new Color(0, 150, 0));
                } else {
                    taskButton.setNormalColor(new Color(102, 102, 98));
                }
                
                taskStatusPanel.add(taskButton);
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
    
    // 1. 新增内部类BackgroundPanel
    class BackgroundPanel extends JPanel {
        private Image bg;
        private String currentBgPath;
        public BackgroundPanel() {
            setLayout(new BorderLayout());
            setBackgroundImage("/com/geometry/ui/imgs/bg1.png"); // 默认首页
        }
        public void setBackgroundImage(String path) {
            if (path.equals(currentBgPath)) return;
            try {
                bg = new ImageIcon(getClass().getResource(path)).getImage();
                currentBgPath = path;
            } catch (Exception e) {
                bg = null;
            }
            repaint();
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bg != null) {
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
} 