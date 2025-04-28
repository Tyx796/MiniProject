package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * 练习界面 - 包含KS2核心任务
 * Task 3: 图形面积计算
 * Task 4: 圆的面积和周长计算
 */
public class ExercisePanel extends JPanel {
    private MainFrame mainFrame;
    private JButton homeButton;
    private JButton bonusTasksButton; // 添加跳转到附加任务的按钮
    private JTabbedPane tabbedPane;  // 使用选项卡切换不同任务
    
    /**
     * 构造函数
     * @param mainFrame 主窗口引用
     */
    public ExercisePanel(MainFrame mainFrame) {
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
        
        // 创建前往附加任务按钮
        bonusTasksButton = new JButton("附加任务");
        bonusTasksButton.addActionListener(e -> mainFrame.showCard(MainFrame.BONUS_PANEL));
        
        // 创建选项卡面板
        tabbedPane = new JTabbedPane();
        
        // 添加任务3选项卡 - 图形面积计算
        JPanel task3Panel = createTask3Panel();
        tabbedPane.addTab("任务3: 图形面积计算", task3Panel);
        
        // 添加任务4选项卡 - 圆的面积和周长计算
        JPanel task4Panel = createTask4Panel();
        tabbedPane.addTab("任务4: 圆的面积和周长", task4Panel);
    }
    
    /**
     * 设置布局
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 顶部导航区域
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("KS2 核心任务", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(bonusTasksButton);
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // 添加到主面板
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    /**
     * 创建任务3面板 - 图形面积计算
     */
    private JPanel createTask3Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // 任务描述
        JLabel descriptionLabel = new JLabel(
                "<html><h3>图形面积计算</h3>" +
                "<p>在这个任务中，你将学习如何计算不同几何图形的面积。</p>" +
                "<p>包括：长方形、正方形、三角形、平行四边形等。</p></html>");
        panel.add(descriptionLabel, BorderLayout.NORTH);
        
        // 创建图形选择区域
        JPanel shapeSelectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        String[] shapes = {"长方形", "正方形", "三角形", "平行四边形"};
        
        for (String shape : shapes) {
            JButton shapeButton = new JButton(shape);
            shapeButton.setPreferredSize(new Dimension(120, 40));
            shapeSelectionPanel.add(shapeButton);
        }
        
        // 创建计算区域（作为占位符）
        JPanel calculationPanel = new JPanel(new BorderLayout());
        calculationPanel.setBorder(BorderFactory.createTitledBorder("面积计算"));
        JLabel placeholderLabel = new JLabel(
                "<html>选择一个图形开始计算...<br>" +
                "这里将显示图形、参数输入框和计算按钮。</html>", 
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
     * 创建任务4面板 - 圆的面积和周长计算
     */
    private JPanel createTask4Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // 任务描述
        JLabel descriptionLabel = new JLabel(
                "<html><h3>圆的面积和周长计算</h3>" +
                "<p>在这个任务中，你将学习如何计算圆的面积和周长。</p>" +
                "<p>你需要使用圆周率(π)和半径(r)进行计算。</p></html>");
        panel.add(descriptionLabel, BorderLayout.NORTH);
        
        // 创建圆形参数输入区域
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBorder(BorderFactory.createTitledBorder("输入圆的半径"));
        
        JLabel radiusLabel = new JLabel("半径 (r): ");
        JTextField radiusField = new JTextField(10);
        JButton calculateButton = new JButton("计算");
        
        inputPanel.add(radiusLabel);
        inputPanel.add(radiusField);
        inputPanel.add(calculateButton);
        
        // 创建结果展示区域
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("计算结果"));
        
        JPanel formulaPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        formulaPanel.add(new JLabel("圆的面积公式: A = π × r²"));
        formulaPanel.add(new JLabel("圆的周长公式: C = 2 × π × r"));
        
        JPanel valuePanel = new JPanel(new GridLayout(2, 1, 5, 10));
        valuePanel.add(new JLabel("面积: "));
        valuePanel.add(new JLabel("周长: "));
        
        resultPanel.add(formulaPanel, BorderLayout.NORTH);
        resultPanel.add(valuePanel, BorderLayout.CENTER);
        
        // 绘图区域
        JPanel drawingPanel = new JPanel();
        drawingPanel.setBorder(BorderFactory.createTitledBorder("圆形图示"));
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