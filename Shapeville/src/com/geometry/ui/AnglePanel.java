package com.geometry.ui;

import javax.swing.*;
import java.awt.*;

/**
 * 角度学习界面
 */
public class AnglePanel extends JPanel {
    private MainFrame mainFrame;
    private JButton homeButton;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口引用
     */
    public AnglePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        setupLayout();
    }
    
    /**
     * 初始化组件
     */
    private void initComponents() {
        // 创建返回主页按钮
        homeButton = new JButton("Return Home");
        homeButton.addActionListener(e -> mainFrame.showCard(MainFrame.HOME_PANEL));
    }
    
    /**
     * 设置布局
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 顶部导航区域
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Angle Learning", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // 临时显示的内容 - 角度学习说明
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // 添加角度说明内容
        JLabel infoLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<h2>Learning About Angles</h2>" +
                "<p>An angle is formed when two rays share a common endpoint.</p>" +
                "<p>Different types of angles:</p>" +
                "<ul>" +
                "<li><b>Acute angle</b>: Less than 90 degrees</li>" +
                "<li><b>Right angle</b>: Exactly 90 degrees</li>" +
                "<li><b>Obtuse angle</b>: Greater than 90 degrees but less than 180 degrees</li>" +
                "<li><b>Straight angle</b>: Exactly 180 degrees</li>" +
                "<li><b>Reflex angle</b>: Greater than 180 degrees but less than 360 degrees</li>" +
                "</ul>" +
                "<p>This module is coming soon!</p>" +
                "</div></html>");
        
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(infoLabel);
        contentPanel.add(Box.createVerticalGlue());
        
        // 添加到面板
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
} 