package com.geometry.ui;

import javax.swing.*;
import java.awt.*;

/**
 * 附加任务界面
 * Bonus 1: 复合图形面积计算
 * Bonus 2: 扇形面积和弧长计算
 */
public class BonusTasksPanel extends JPanel {
    private MainFrame mainFrame;
    private JButton homeButton;
    private JButton exerciseButton; // 返回主要练习的按钮
    private JTabbedPane tabbedPane;  // 使用选项卡切换不同任务
    
    /**
     * 构造函数
     * @param mainFrame 主窗口引用
     */
    public BonusTasksPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        setupLayout();
    }
    
    /**
     * 初始化组件
     */
    private void initComponents() {
        // 创建返回主页按钮
        homeButton = new JButton("返回主页");
        homeButton.addActionListener(e -> mainFrame.showCard(MainFrame.HOME_PANEL));
        
        // 创建返回核心任务按钮
        exerciseButton = new JButton("返回核心任务");
        exerciseButton.addActionListener(e -> mainFrame.showCard(MainFrame.EXERCISE_PANEL));
        
        // 创建选项卡面板
        tabbedPane = new JTabbedPane();
        
        // 添加附加任务1选项卡 - 复合图形面积计算
        JPanel bonus1Panel = createBonus1Panel();
        tabbedPane.addTab("附加1: 复合图形面积", bonus1Panel);
        
        // 添加附加任务2选项卡 - 扇形面积和弧长计算
        JPanel bonus2Panel = createBonus2Panel();
        tabbedPane.addTab("附加2: 扇形和弧长", bonus2Panel);
    }
    
    /**
     * 设置布局
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 顶部导航区域
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("附加挑战任务", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(exerciseButton);
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // 添加到主面板
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    /**
     * 创建附加任务1面板 - 复合图形面积计算
     */
    private JPanel createBonus1Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // 任务描述
        JLabel descriptionLabel = new JLabel(
                "<html><h3>复合图形面积计算</h3>" +
                "<p>在这个任务中，你将学习如何计算由多个基本图形组成的复合图形的面积。</p>" +
                "<p>例如：L形、T形等由矩形组合而成的图形。</p></html>");
        panel.add(descriptionLabel, BorderLayout.NORTH);
        
        // 创建图形选择区域
        JPanel shapeSelectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        String[] shapes = {"L形", "T形", "十字形", "自定义组合"};
        
        for (String shape : shapes) {
            JButton shapeButton = new JButton(shape);
            shapeButton.setPreferredSize(new Dimension(120, 40));
            shapeSelectionPanel.add(shapeButton);
        }
        
        // 创建计算区域（作为占位符）
        JPanel calculationPanel = new JPanel(new BorderLayout());
        calculationPanel.setBorder(BorderFactory.createTitledBorder("复合图形面积计算"));
        JLabel placeholderLabel = new JLabel(
                "<html>选择一个复合图形开始计算...<br>" +
                "你可以通过分解复合图形为基本图形，然后分别计算并相加得到总面积。</html>", 
                JLabel.CENTER);
        calculationPanel.add(placeholderLabel, BorderLayout.CENTER);
        
        // 组合面板
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(shapeSelectionPanel, BorderLayout.NORTH);
        centerPanel.add(calculationPanel, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * 创建附加任务2面板 - 扇形面积和弧长计算
     */
    private JPanel createBonus2Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // 任务描述
        JLabel descriptionLabel = new JLabel(
                "<html><h3>扇形面积和弧长计算</h3>" +
                "<p>在这个任务中，你将学习如何计算扇形的面积和弧长。</p>" +
                "<p>你需要使用圆周率(π)、半径(r)和中心角(θ)进行计算。</p></html>");
        panel.add(descriptionLabel, BorderLayout.NORTH);
        
        // 创建参数输入区域
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("输入参数"));
        
        inputPanel.add(new JLabel("半径 (r): "));
        inputPanel.add(new JTextField(10));
        
        inputPanel.add(new JLabel("中心角 (θ): "));
        inputPanel.add(new JTextField(10));
        
        inputPanel.add(new JLabel(""));
        JButton calculateButton = new JButton("计算");
        inputPanel.add(calculateButton);
        
        // 创建结果展示区域
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("计算结果"));
        
        JPanel formulaPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        formulaPanel.add(new JLabel("扇形面积公式: A = (θ/360) × π × r²"));
        formulaPanel.add(new JLabel("弧长公式: L = (θ/360) × 2π × r"));
        
        JPanel valuePanel = new JPanel(new GridLayout(2, 1, 5, 10));
        valuePanel.add(new JLabel("扇形面积: "));
        valuePanel.add(new JLabel("弧长: "));
        
        resultPanel.add(formulaPanel, BorderLayout.NORTH);
        resultPanel.add(valuePanel, BorderLayout.CENTER);
        
        // 绘图区域
        JPanel drawingPanel = new JPanel();
        drawingPanel.setBorder(BorderFactory.createTitledBorder("扇形图示"));
        drawingPanel.setPreferredSize(new Dimension(200, 200));
        
        // 组合面板
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        bottomPanel.add(resultPanel);
        bottomPanel.add(drawingPanel);
        
        centerPanel.add(bottomPanel, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
} 