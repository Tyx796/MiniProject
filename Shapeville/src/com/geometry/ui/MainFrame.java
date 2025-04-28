package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 应用程序主界面
 * 显示可用游戏级别、进度条和结束会话按钮
 */
public class MainFrame extends JFrame {
    private JProgressBar progressBar;
    private JLabel levelInfoLabel;
    private JButton endSessionButton;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    
    // 当前得分
    private int currentScore = 0;
    
    // 界面名称常量
    public static final String HOME_PANEL = "HOME";
    public static final String SHAPE2D_PANEL = "SHAPE2D";
    public static final String SHAPE3D_PANEL = "SHAPE3D";
    public static final String ANGLE_PANEL = "ANGLE";
    public static final String EXERCISE_PANEL = "EXERCISE";
    public static final String BONUS_PANEL = "BONUS";
    public static final String KS1_SELECTION_PANEL = "KS1_SELECTION";
    
    /**
     * 构造函数
     */
    public MainFrame() {
        initComponents();
        setupLayout();
        setupListeners();
        
        // 设置窗口属性
        setTitle("几何学习乐园");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示
        setResizable(true);
    }
    
    /**
     * 初始化组件
     */
    private void initComponents() {
        // 创建主要组件
        levelInfoLabel = new JLabel("<html><h1>游戏级别信息</h1>" +
                "<p style='font-size: 16px;'>级别1 (KS1): 认识基本图形和角度 </p>" +
                "<p style='font-size: 16px;'>级别2 (KS2): 图形面积计算</p>" +
                "<p style='font-size: 16px;'>级别3 (Bonus): 复合图形和扇形</p></html>");
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setString("当前进度: 0%");
        
        endSessionButton = new JButton("结束会话");
        
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
        
        // 添加到卡片布局
        cardPanel.add(homePanel, HOME_PANEL);
        cardPanel.add(shapePanel, SHAPE_PANEL);
        cardPanel.add(shape3DPanel, SHAPE3D_PANEL);
        cardPanel.add(anglePanel, ANGLE_PANEL);
        cardPanel.add(exercisePanel, EXERCISE_PANEL);
        cardPanel.add(bonusPanel, BONUS_PANEL);
        cardPanel.add(ks1SelectionPanel, KS1_SELECTION_PANEL);
    }
    
    /**
     * 创建主页面
     */
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 标题部分
        JLabel titleLabel = new JLabel("欢迎来到几何学习乐园！", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180)); // 钢青色
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // 内容部分
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.add(levelInfoLabel, BorderLayout.NORTH);
        
        // 创建功能按钮
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        
        JButton ks1Button = createMenuButton("基础图形与角度");
        ks1Button.addActionListener(e -> showCard(KS1_SELECTION_PANEL));
        
        JButton ks2Button = createMenuButton("图形面积计算");
        ks2Button.addActionListener(e -> showCard(EXERCISE_PANEL));
        
        JButton bonusButton = createMenuButton("高级任务");
        bonusButton.addActionListener(e -> showCard(BONUS_PANEL));
        
        buttonPanel.add(ks1Button);
        buttonPanel.add(ks2Button);
        buttonPanel.add(bonusButton);
        
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * 创建KS1选择面板
     */
    private JPanel createKS1SelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 标题部分
        JLabel titleLabel = new JLabel("请选择学习内容", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180)); // 钢青色
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // 内容部分 - 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JButton shapeButton = createMenuButton("2D图形学习");
        shapeButton.setPreferredSize(new Dimension(150, 100));
        shapeButton.addActionListener(e -> showCard(SHAPE_PANEL));
        
        JButton shape3DButton = createMenuButton("3D图形学习");
        shape3DButton.setPreferredSize(new Dimension(150, 100));
        shape3DButton.addActionListener(e -> showCard(SHAPE3D_PANEL));
        
        JButton angleButton = createMenuButton("角度学习");
        angleButton.setPreferredSize(new Dimension(150, 100));
        angleButton.addActionListener(e -> showCard(ANGLE_PANEL));
        
        buttonPanel.add(shapeButton);
        buttonPanel.add(shape3DButton);
        buttonPanel.add(angleButton);
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        // 返回按钮
        JButton backButton = new JButton("返回主页");
        backButton.addActionListener(e -> showCard(HOME_PANEL));
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * 创建菜单按钮
     */
    private JButton createMenuButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("宋体", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(120, 80));
        button.setBackground(new Color(240, 240, 255)); // 淡蓝色
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
        progressPanel.add(new JLabel("学习进度: "), BorderLayout.WEST);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        
        statusPanel.add(progressPanel, BorderLayout.CENTER);
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
                "你在本次会话中获得了 " + currentScore + " 分。再见！", 
                "会话结束", 
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
        progressBar.setString("当前进度: " + progress + "%");
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