package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import com.geometry.service.Task12D;
import com.geometry.entity.Shapes2D;

/**
 * 图形学习与识别界面
 */
public class Shape2DPanel extends JPanel {
    private MainFrame mainFrame;
    private JButton homeButton;
    private JButton submitButton;
    private JLabel progressLabel;
    
    // 图形识别练习组件
    private JPanel recognitionPanel;
    private JLabel shapeImageLabel;
    private JLabel instructionLabel;
    private JLabel attemptsLabel;
    private JLabel resultLabel;
    private JTextField answerField;
    private Task12D shapeTask;
    private static final String FONT_NAME = "Arial";
    
    /**
     * 构造函数
     * @param mainFrame 主窗口引用
     */
    public Shape2DPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.shapeTask = new Task12D();
        initComponents();
        setupLayout();
        
        // 显示初始图形
        showCurrentShape();
    }
    
    /**
     * 初始化组件
     */
    private void initComponents() {
        // 创建返回主页按钮
        homeButton = new JButton("Return Home");
        homeButton.addActionListener(e -> mainFrame.showCard(MainFrame.HOME_PANEL));
        
        // 创建提交按钮
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> checkAnswer());
        
        // 创建进度标签
        progressLabel = new JLabel("");
        progressLabel.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        updateProgressLabel();
        
        // 初始化识别练习组件
        recognitionPanel = new JPanel(new BorderLayout(15, 15));
        shapeImageLabel = new JLabel();
        shapeImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        instructionLabel = new JLabel("What is the name of this shape?", SwingConstants.CENTER);
        instructionLabel.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        
        attemptsLabel = new JLabel("", SwingConstants.CENTER);
        updateAttemptsLabel();
        
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        
        answerField = new JTextField(20);
        answerField.addActionListener(e -> checkAnswer());
    }
    
    /**
     * 设置布局
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 顶部导航区域
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Shape Recognition", SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(progressLabel, BorderLayout.WEST);
        
        // 设置识别练习面板
        JPanel imagePanel = new JPanel(new BorderLayout(10, 10));
        imagePanel.add(shapeImageLabel, BorderLayout.CENTER);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        
        JPanel instructionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        instructionPanel.add(instructionLabel);
        inputPanel.add(instructionPanel);
        
        JPanel attemptsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        attemptsPanel.add(attemptsLabel);
        inputPanel.add(attemptsPanel);
        
        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        answerPanel.add(new JLabel("Answer: "));
        answerPanel.add(answerField);
        answerPanel.add(submitButton);
        inputPanel.add(answerPanel);
        
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultPanel.add(resultLabel);
        inputPanel.add(resultPanel);
        
        // 添加到主面板
        add(topPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }
    
    /**
     * 显示当前图形
     */
    private void showCurrentShape() {
        String shapeName = shapeTask.getCurrentShape();
        
        if (shapeName != null) {
            // 设置图形图像
            ImageIcon icon = createShapeIcon(shapeName, 300, 300);
            if (icon != null) {
                shapeImageLabel.setIcon(icon);
            } else {
                shapeImageLabel.setText("Image loading failed");
            }
            
            // 更新标签
            updateProgressLabel();
            updateAttemptsLabel();
            
            // 清空结果和输入
            resultLabel.setText("");
            answerField.setText("");
            answerField.requestFocus();
        } else {
            // 任务完成
            completeTask();
        }
    }
    
    /**
     * 检查答案
     */
    private void checkAnswer() {
        String answer = answerField.getText().trim();
        
        if (answer.isEmpty()) {
            resultLabel.setText("Please enter an answer!");
            resultLabel.setForeground(Color.RED);
            return;
        }
        
        boolean isCorrect = shapeTask.checkAnswer(answer);
        
        if (isCorrect) {
            updateAttemptsLabel();
            resultLabel.setText("Correct! Well done!");
            resultLabel.setForeground(new Color(0, 128, 0)); // 绿色
            // 不再直接调用mainFrame.updateScore，分数已在Task1中通过User类更新
            // 这里更新MainFrame中的进度条显示
            mainFrame.updateScore(shapeTask.getCurrentScore());
            
            // 显示下一个图形 (延迟一秒)
            Timer timer = new Timer(1000, e -> {
                if (shapeTask.isTaskCompleted()) {
                    completeTask();
                } else {
                    showCurrentShape();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            resultLabel.setText("Incorrect, try again!");
            resultLabel.setForeground(Color.RED);
            answerField.setText("");
            answerField.requestFocus();
            
            if (shapeTask.getAttempts() == 0) {
                attemptsLabel.setText("Attempts remaining: 0");
                // 展示正确答案
                String correctAnswer = shapeTask.getPreviousShape();
                resultLabel.setText("The correct answer is: " + correctAnswer);
                
                // 显示下一个图形 (延迟两秒)
                Timer timer = new Timer(2000, e -> {
                    if (shapeTask.isTaskCompleted()) {
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
     * 完成任务
     */
    private void completeTask() {
        // 清除组件
        shapeImageLabel.setIcon(null);
        answerField.setEnabled(false);
        submitButton.setEnabled(false);
        
        // 显示最终结果
        int correctCount = shapeTask.getCorrectCount();
        int totalShapes = shapeTask.getTotalShapes();
        
        JPanel completionPanel = new JPanel();
        completionPanel.setLayout(new BoxLayout(completionPanel, BoxLayout.Y_AXIS));
        
        JLabel completionLabel = new JLabel("Task Completed!", SwingConstants.CENTER);
        completionLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        completionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel scoreLabel = new JLabel("You correctly identified " + correctCount + 
                " out of " + totalShapes + " shapes!", SwingConstants.CENTER);
        scoreLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton restartButton = new JButton("Try Again");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> restartTask());
        
        completionPanel.add(Box.createVerticalGlue());
        completionPanel.add(completionLabel);
        completionPanel.add(Box.createVerticalStrut(20));
        completionPanel.add(scoreLabel);
        completionPanel.add(Box.createVerticalStrut(20));
        completionPanel.add(restartButton);
        completionPanel.add(Box.createVerticalGlue());
        
        // 替换中央组件
        remove(shapeImageLabel.getParent());
        add(completionPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    /**
     * 重新开始任务
     */
    private void restartTask() {
        // 重新初始化任务
        shapeTask = new Task12D();
        
        // 重新设置UI组件
        answerField.setEnabled(true);
        submitButton.setEnabled(true);
        
        // 移除完成面板
        Component centerComponent = ((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != shapeImageLabel.getParent()) {
            remove(centerComponent);
            
            JPanel imagePanel = new JPanel(new BorderLayout(10, 10));
            imagePanel.add(shapeImageLabel, BorderLayout.CENTER);
            add(imagePanel, BorderLayout.CENTER);
        }
        
        // 显示初始图形
        showCurrentShape();
        revalidate();
        repaint();
    }
    
    /**
     * 更新进度标签
     */
    private void updateProgressLabel() {
        int current = shapeTask.getCurrentShapeIndex() + 1;
        int total = shapeTask.getTotalShapes();
        progressLabel.setText("Shape " + current + " of " + total);
    }
    
    /**
     * 更新尝试次数标签
     */
    private void updateAttemptsLabel() {
        int remaining = shapeTask.getRemainingAttempts();
        attemptsLabel.setText("Attempts remaining: " + remaining);
    }
    
    /**
     * 创建图形图标
     */
    private ImageIcon createShapeIcon(String shapeName, int width, int height) {
        ImageIcon icon = null;
        
        try {
            // 从Shapes2D获取图片路径
            String imagePath = Shapes2D.getShapeImg(shapeName);
            if (imagePath == null) {
                return null;
            }
            
            // 加载图片资源
            java.net.URL imageURL = getClass().getClassLoader().getResource(imagePath);
            if (imageURL != null) {
                // 读取图片
                Image originalImage = new ImageIcon(imageURL).getImage();
                
                // 调整图片大小
                Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImage);
            } else {
                System.err.println("Cannot find image: " + imagePath);
                
                // 如果找不到图片，使用默认绘制的图形作为后备方案
                return createDefaultShapeIcon(shapeName, width, height);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        return icon;
    }
    
    /**
     * 创建默认图形图标（当无法加载图片时使用）
     */
    private ImageIcon createDefaultShapeIcon(String shapeName, int width, int height) {
        // 简单绘制基本图形作为后备方案
        ImageIcon icon = null;
        
        try {
            // 创建图像
            Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) image.getGraphics();
            
            // 设置抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 设置边框和填充颜色
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            
            // 绘制图形
            int padding = 40;
            int shapeWidth = width - 2 * padding;
            int shapeHeight = height - 2 * padding;
            
            switch (shapeName) {
                case "circle":
                    g2d.setColor(new Color(255, 200, 200)); // 浅红色填充
                    g2d.fillOval(padding, padding, shapeWidth, shapeHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(padding, padding, shapeWidth, shapeHeight);
                    break;
                    
                case "triangle":
                    int[] xPoints = {width / 2, padding, width - padding};
                    int[] yPoints = {padding, height - padding, height - padding};
                    g2d.setColor(new Color(200, 255, 200)); // 浅绿色填充
                    g2d.fillPolygon(xPoints, yPoints, 3);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(xPoints, yPoints, 3);
                    break;
                    
                case "square":
                    g2d.setColor(new Color(200, 200, 255)); // 浅蓝色填充
                    g2d.fillRect(padding, padding, shapeWidth, shapeWidth);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(padding, padding, shapeWidth, shapeWidth);
                    break;
                    
                case "rectangle":
                    g2d.setColor(new Color(255, 255, 200)); // 浅黄色填充
                    g2d.fillRect(padding, padding + shapeHeight/4, shapeWidth, shapeHeight/2);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(padding, padding + shapeHeight/4, shapeWidth, shapeHeight/2);
                    break;
                    
                case "pentagon":
                    int[] pentX = new int[5];
                    int[] pentY = new int[5];
                    int radius = shapeWidth / 2;
                    int centerX = width / 2;
                    int centerY = height / 2;
                    for (int i = 0; i < 5; i++) {
                        double angle = 2 * Math.PI * i / 5 - Math.PI / 2;
                        pentX[i] = centerX + (int)(radius * Math.cos(angle));
                        pentY[i] = centerY + (int)(radius * Math.sin(angle));
                    }
                    g2d.setColor(new Color(255, 200, 255)); // 浅紫色填充
                    g2d.fillPolygon(pentX, pentY, 5);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(pentX, pentY, 5);
                    break;
                    
                case "hexagon":
                    int[] hexX = new int[6];
                    int[] hexY = new int[6];
                    radius = shapeWidth / 2;
                    centerX = width / 2;
                    centerY = height / 2;
                    for (int i = 0; i < 6; i++) {
                        double angle = 2 * Math.PI * i / 6 - Math.PI / 2;
                        hexX[i] = centerX + (int)(radius * Math.cos(angle));
                        hexY[i] = centerY + (int)(radius * Math.sin(angle));
                    }
                    g2d.setColor(new Color(200, 255, 255)); // 浅青色填充
                    g2d.fillPolygon(hexX, hexY, 6);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(hexX, hexY, 6);
                    break;
                    
                case "heptagon":
                    int[] heptX = new int[7];
                    int[] heptY = new int[7];
                    radius = shapeWidth / 2;
                    centerX = width / 2;
                    centerY = height / 2;
                    for (int i = 0; i < 7; i++) {
                        double angle = 2 * Math.PI * i / 7 - Math.PI / 2;
                        heptX[i] = centerX + (int)(radius * Math.cos(angle));
                        heptY[i] = centerY + (int)(radius * Math.sin(angle));
                    }
                    g2d.setColor(new Color(255, 230, 200)); // 浅橙色填充
                    g2d.fillPolygon(heptX, heptY, 7);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(heptX, heptY, 7);
                    break;
                    
                case "octagon":
                    int[] octX = new int[8];
                    int[] octY = new int[8];
                    radius = shapeWidth / 2;
                    centerX = width / 2;
                    centerY = height / 2;
                    for (int i = 0; i < 8; i++) {
                        double angle = 2 * Math.PI * i / 8 - Math.PI / 2;
                        octX[i] = centerX + (int)(radius * Math.cos(angle));
                        octY[i] = centerY + (int)(radius * Math.sin(angle));
                    }
                    g2d.setColor(new Color(220, 220, 255)); // 浅蓝紫色填充
                    g2d.fillPolygon(octX, octY, 8);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(octX, octY, 8);
                    break;
                    
                case "oval":
                    g2d.setColor(new Color(255, 220, 220)); // 浅粉色填充
                    g2d.fillOval(padding, padding + shapeHeight/4, shapeWidth, shapeHeight/2);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(padding, padding + shapeHeight/4, shapeWidth, shapeHeight/2);
                    break;
                    
                case "rhombus":
                    int[] rhombX = {width/2, padding, width/2, width - padding};
                    int[] rhombY = {padding, height/2, height - padding, height/2};
                    g2d.setColor(new Color(220, 255, 220)); // 浅绿色填充
                    g2d.fillPolygon(rhombX, rhombY, 4);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(rhombX, rhombY, 4);
                    break;
                    
                case "kite":
                    int[] kiteX = {width/2, width/2 - shapeWidth/4, width/2, width/2 + shapeWidth/4};
                    int[] kiteY = {padding, height/2, height - padding, height/2};
                    g2d.setColor(new Color(255, 255, 220)); // 浅黄色填充
                    g2d.fillPolygon(kiteX, kiteY, 4);
                    g2d.setColor(Color.BLACK);
                    g2d.drawPolygon(kiteX, kiteY, 4);
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
} 