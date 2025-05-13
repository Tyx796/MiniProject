package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import com.geometry.service.Task13D;
import com.geometry.service.Task2;
import com.geometry.entity.Shapes3D;
import com.geometry.entity.User;
import com.geometry.ui.uiUtils.KidButton;

/**
 * 3D图形学习与识别界面
 */
public class Shape3DPanel extends JPanel {
    private MainFrame mainFrame;
    private KidButton homeButton;
    private KidButton submitButton;
    private JLabel progressLabel;
    
    // 图形识别练习组件
    private JPanel recognitionPanel;
    private JLabel shapeImageLabel;
    private JLabel attemptsLabel;
    private JLabel resultLabel;
    private JTextField answerField;
    private Task13D shapeTask;
    private static final String FONT_NAME = "Comic Sans MS";
    
    /**
     * 构造函数
     * @param mainFrame 主窗口引用
     */
    public Shape3DPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.shapeTask = new Task13D();
        setBackground(new Color(220, 240, 255));
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
        homeButton = new KidButton("Home");
        homeButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        homeButton.setPreferredSize(new Dimension(150, 40));
        homeButton.addActionListener(e -> mainFrame.showCard(MainFrame.HOME_PANEL));
        
        // 创建提交按钮
        submitButton = new KidButton("OK");
        submitButton.setNormalColor(Color.WHITE);
        submitButton.setForeground(Color.BLACK);
        submitButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        submitButton.setPreferredSize(new Dimension(120, 40));
        submitButton.addActionListener(e -> checkAnswer());
        
        // 创建进度标签
        progressLabel = new JLabel("");
        progressLabel.setFont(new Font(FONT_NAME, Font.BOLD, 22));
        updateProgressLabel();
        
        // 初始化识别练习组件
        recognitionPanel = new JPanel(new BorderLayout(15, 15));
        recognitionPanel.setOpaque(false);
        shapeImageLabel = new JLabel();
        shapeImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        attemptsLabel = new JLabel("", SwingConstants.CENTER);
        attemptsLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        attemptsLabel.setForeground(Color.WHITE);
        updateAttemptsLabel();
        
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        
        answerField = new JTextField(10);
        answerField.setFont(new Font(FONT_NAME, Font.PLAIN, 22));
        answerField.setPreferredSize(new Dimension(100, 40));
        answerField.addActionListener(e -> checkAnswer());
        // 添加焦点请求，使面板显示时自动获取焦点
        answerField.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                // 使用SwingUtilities.invokeLater确保组件完全显示后再请求焦点
                SwingUtilities.invokeLater(() -> answerField.requestFocusInWindow());
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {}
            public void ancestorMoved(javax.swing.event.AncestorEvent event) {}
        });
    }
    
    /**
     * 设置布局
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 顶部导航区域
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // 设置识别练习面板
        JPanel imagePanel = new JPanel(new BorderLayout(10, 10));
        imagePanel.setOpaque(false);
        imagePanel.add(shapeImageLabel, BorderLayout.CENTER);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        
        JPanel attemptsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        attemptsPanel.setOpaque(false);
        attemptsPanel.add(attemptsLabel);
        inputPanel.add(attemptsPanel);
        
        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        answerPanel.setOpaque(false);
        JLabel answerLabel = new JLabel("Name: ", SwingConstants.CENTER);
        answerLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        answerLabel.setForeground(Color.WHITE);
        answerPanel.add(answerLabel);
        answerPanel.add(answerField);
        answerPanel.add(submitButton);
        inputPanel.add(answerPanel);
        
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultPanel.setOpaque(false);
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
                shapeImageLabel.setText("");
            } else {
                shapeImageLabel.setText("No image");
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
            resultLabel.setText("Type answer!");
            resultLabel.setForeground(Color.RED);
            return;
        }
        
        boolean isCorrect = shapeTask.checkAnswer(answer);
        
        if (isCorrect) {
            updateAttemptsLabel();
            resultLabel.setText("Great!");
            resultLabel.setForeground(new Color(0, 128, 0));
            
            // 计算得分
            int points = User.calScores("Basic", 3 - shapeTask.getRemainingAttempts() + 1);
            
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
            scoreDialog.getRootPane().setDefaultButton(okButton);
            
            // 组装面板
            scorePanel.add(scoreLabel, BorderLayout.CENTER);
            scorePanel.add(okButton, BorderLayout.SOUTH);
            
            scoreDialog.add(scorePanel);
            
            // 更新主窗口分数显示
            mainFrame.updateScore();
            
            // 显示得分弹窗
            scoreDialog.setVisible(true);
            
            // 显示下一个图形 (延迟一秒)
            Timer timer = new Timer(500, e -> {
                if (shapeTask.isTaskCompleted()) {
                    mainFrame.updateTaskStatus("3D Shapes", true);
                    completeTask();
                } else {
                    showCurrentShape();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            resultLabel.setText("Try again!");
            resultLabel.setForeground(Color.WHITE);
            answerField.setText("");
            answerField.requestFocus();
            
            if (shapeTask.getAttempts() == 0) {
                attemptsLabel.setText("Tries: 0");
                // 展示正确答案
                String correctAnswer = shapeTask.getPreviousShape();
                resultLabel.setText("Answer: " + correctAnswer);
                
                // 显示下一个图形 (延迟两秒)
                Timer timer = new Timer(2000, e -> {
                    if (shapeTask.isTaskCompleted()) {
                        mainFrame.updateTaskStatus("3D Shapes", true);
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
        completionPanel.setOpaque(false);
        completionPanel.setLayout(new BoxLayout(completionPanel, BoxLayout.Y_AXIS));
        
        JLabel completionLabel = new JLabel("All done!", SwingConstants.CENTER);
        completionLabel.setFont(new Font(FONT_NAME, Font.BOLD, 38));
        completionLabel.setForeground(Color.WHITE);
        completionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel scoreLabel = new JLabel("You got " + correctCount + " / " + totalShapes + "!", SwingConstants.CENTER);
        scoreLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 34));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        KidButton restartButton = new KidButton("Again");
        restartButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        restartButton.setPreferredSize(new Dimension(150, 40));
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
        shapeTask = new Task13D();
        
        // 重新设置UI组件
        answerField.setEnabled(true);
        submitButton.setEnabled(true);
        
        // 移除完成面板
        Component centerComponent = ((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != shapeImageLabel.getParent()) {
            remove(centerComponent);
            
            JPanel imagePanel = new JPanel(new BorderLayout(10, 10));
            imagePanel.setOpaque(false);
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
        progressLabel.setText(current + " / " + total);
    }
    
    /**
     * 更新尝试次数标签
     */
    private void updateAttemptsLabel() {
        int remaining = shapeTask.getRemainingAttempts();
        attemptsLabel.setText("Tries: " + remaining);
    }
    
    /**
     * 创建图形图标
     */
    private ImageIcon createShapeIcon(String shapeName, int width, int height) {
        ImageIcon icon = null;
        
        try {
            // 从Shapes3D获取图片路径
            String imagePath = Shapes3D.getShapeImg(shapeName);
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
                case "cube":
                    draw3DCube(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "cuboid":
                    draw3DCuboid(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "cylinder":
                    draw3DCylinder(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "sphere":
                    draw3DSphere(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "cone":
                    draw3DCone(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "tetrahedron":
                    draw3DTetrahedron(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "triangularPrism":
                    draw3DTriangularPrism(g2d, padding, padding, shapeWidth, shapeHeight);
                    break;
                case "squareBasedPyramid":
                    draw3DSquareBasedPyramid(g2d, padding, padding, shapeWidth, shapeHeight);
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
    
    // 3D绘图方法
    
    private void draw3DCube(Graphics2D g, int x, int y, int width, int height) {
        int size = Math.min(width, height);
        int depth = size / 4;
        
        // 正面
        g.setColor(new Color(200, 200, 255));
        g.fillRect(x, y, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);
        
        // 顶面
        int[] xPoints = {x, x + depth, x + size + depth, x + size};
        int[] yPoints = {y, y - depth, y - depth, y};
        g.setColor(new Color(220, 220, 255));
        g.fillPolygon(xPoints, yPoints, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 4);
        
        // 侧面
        int[] xPoints2 = {x + size, x + size + depth, x + size + depth, x + size};
        int[] yPoints2 = {y, y - depth, y - depth + size, y + size};
        g.setColor(new Color(180, 180, 255));
        g.fillPolygon(xPoints2, yPoints2, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints2, yPoints2, 4);
    }

    private void draw3DCuboid(Graphics2D g, int x, int y, int width, int height) {
        int depth = width / 4;
        int cuboidHeight = height / 2;
        
        // 正面
        g.setColor(new Color(255, 200, 200));
        g.fillRect(x, y, width, cuboidHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, cuboidHeight);
        
        // 顶面
        int[] xPoints = {x, x + depth, x + width + depth, x + width};
        int[] yPoints = {y, y - depth, y - depth, y};
        g.setColor(new Color(255, 220, 220));
        g.fillPolygon(xPoints, yPoints, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 4);
        
        // 侧面
        int[] xPoints2 = {x + width, x + width + depth, x + width + depth, x + width};
        int[] yPoints2 = {y, y - depth, y - depth + cuboidHeight, y + cuboidHeight};
        g.setColor(new Color(255, 180, 180));
        g.fillPolygon(xPoints2, yPoints2, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints2, yPoints2, 4);
    }

    private void draw3DCylinder(Graphics2D g, int x, int y, int width, int height) {
        int cylinderWidth = width;
        int cylinderHeight = height;
        
        // 顶部椭圆
        g.setColor(new Color(200, 255, 200));
        g.fillOval(x, y, cylinderWidth, cylinderWidth / 3);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, cylinderWidth, cylinderWidth / 3);
        
        // 底部椭圆
        g.setColor(new Color(180, 255, 180));
        g.fillOval(x, y + cylinderHeight - cylinderWidth / 3, cylinderWidth, cylinderWidth / 3);
        g.setColor(Color.BLACK);
        g.drawOval(x, y + cylinderHeight - cylinderWidth / 3, cylinderWidth, cylinderWidth / 3);
        
        // 连接线
        g.drawLine(x, y + cylinderWidth / 6, x, y + cylinderHeight - cylinderWidth / 6);
        g.drawLine(x + cylinderWidth, y + cylinderWidth / 6, x + cylinderWidth, y + cylinderHeight - cylinderWidth / 6);
        
        // 矩形主体
        g.setColor(new Color(220, 255, 220));
        g.fillRect(x, y + cylinderWidth / 6, cylinderWidth, cylinderHeight - cylinderWidth / 3);
        
        // 再次绘制连接线以确保可见
        g.setColor(Color.BLACK);
        g.drawLine(x, y + cylinderWidth / 6, x, y + cylinderHeight - cylinderWidth / 6);
        g.drawLine(x + cylinderWidth, y + cylinderWidth / 6, x + cylinderWidth, y + cylinderHeight - cylinderWidth / 6);
    }

    private void draw3DSphere(Graphics2D g, int x, int y, int width, int height) {
        int size = Math.min(width, height);
        
        // 绘制圆形并添加阴影以呈现3D效果
        g.setColor(new Color(255, 200, 255));
        g.fillOval(x, y, size, size);
        
        // 添加高光
        g.setColor(new Color(255, 230, 255));
        g.fillOval(x + size/4, y + size/4, size/5, size/5);
        
        g.setColor(Color.BLACK);
        g.drawOval(x, y, size, size);
    }

    private void draw3DCone(Graphics2D g, int x, int y, int width, int height) {
        int coneDiameter = width;
        int coneHeight = height;
        
        // 底部椭圆
        g.setColor(new Color(255, 230, 200));
        g.fillOval(x, y + coneHeight - coneDiameter / 4, coneDiameter, coneDiameter / 4);
        
        // 圆锥体
        int[] xPoints = {x + coneDiameter / 2, x, x + coneDiameter};
        int[] yPoints = {y, y + coneHeight - coneDiameter / 8, y + coneHeight - coneDiameter / 8};
        g.setColor(new Color(255, 200, 150));
        g.fillPolygon(xPoints, yPoints, 3);
        
        // 边框
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 3);
        g.drawOval(x, y + coneHeight - coneDiameter / 4, coneDiameter, coneDiameter / 4);
    }

    private void draw3DTetrahedron(Graphics2D g, int x, int y, int width, int height) {
        int size = Math.min(width, height);
        
        // 前面的三角形
        int[] xPoints1 = {x + size / 2, x, x + size};
        int[] yPoints1 = {y, y + size, y + size};
        g.setColor(new Color(200, 255, 255));
        g.fillPolygon(xPoints1, yPoints1, 3);
        
        // 侧面三角形
        int[] xPoints2 = {x + size / 2, x + size, x + size + size / 4};
        int[] yPoints2 = {y, y + size, y + size / 2};
        g.setColor(new Color(180, 255, 255));
        g.fillPolygon(xPoints2, yPoints2, 3);
        
        // 边框
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints1, yPoints1, 3);
        g.drawPolygon(xPoints2, yPoints2, 3);
        g.drawLine(x + size / 2, y, x + size + size / 4, y + size / 2);
    }

    private void draw3DTriangularPrism(Graphics2D g, int x, int y, int width, int height) {
        int prismWidth = width;
        int prismHeight = height;
        int depth = prismWidth / 4;
        
        // 前面的三角形
        int[] xPoints1 = {x, x + prismWidth, x + prismWidth / 2};
        int[] yPoints1 = {y + prismHeight, y + prismHeight, y};
        g.setColor(new Color(255, 255, 200));
        g.fillPolygon(xPoints1, yPoints1, 3);
        
        // 后面的三角形
        int[] xPoints2 = {x + depth, x + prismWidth + depth, x + prismWidth / 2 + depth};
        int[] yPoints2 = {y + prismHeight - depth, y + prismHeight - depth, y - depth};
        g.setColor(new Color(255, 255, 220));
        g.fillPolygon(xPoints2, yPoints2, 3);
        
        // 上面的矩形
        int[] xPoints3 = {x + prismWidth / 2, x + prismWidth, x + prismWidth + depth, x + prismWidth / 2 + depth};
        int[] yPoints3 = {y, y + prismHeight, y + prismHeight - depth, y - depth};
        g.setColor(new Color(255, 255, 180));
        g.fillPolygon(xPoints3, yPoints3, 4);
        
        // 底面矩形
        int[] xPoints4 = {x, x + prismWidth, x + prismWidth + depth, x + depth};
        int[] yPoints4 = {y + prismHeight, y + prismHeight, y + prismHeight - depth, y + prismHeight - depth};
        g.setColor(new Color(255, 255, 150));
        g.fillPolygon(xPoints4, yPoints4, 4);
        
        // 边框
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints1, yPoints1, 3);
        g.drawPolygon(xPoints2, yPoints2, 3);
        g.drawLine(x, y + prismHeight, x + depth, y + prismHeight - depth);
        g.drawLine(x + prismWidth, y + prismHeight, x + prismWidth + depth, y + prismHeight - depth);
        g.drawLine(x + prismWidth / 2, y, x + prismWidth / 2 + depth, y - depth);
    }

    private void draw3DSquareBasedPyramid(Graphics2D g, int x, int y, int width, int height) {
        int size = Math.min(width, height);
        int baseSize = size;
        int pyramidHeight = size;
        
        // 底面正方形
        g.setColor(new Color(220, 220, 255));
        g.fillRect(x, y + pyramidHeight - baseSize/2, baseSize, baseSize/2);
        
        // 前面的三角形
        int[] xPoints1 = {x, x + baseSize, x + baseSize/2};
        int[] yPoints1 = {y + pyramidHeight - baseSize/2, y + pyramidHeight - baseSize/2, y};
        g.setColor(new Color(200, 200, 255));
        g.fillPolygon(xPoints1, yPoints1, 3);
        
        // 侧面三角形
        int[] xPoints2 = {x + baseSize, x + baseSize + baseSize/4, x + baseSize/2};
        int[] yPoints2 = {y + pyramidHeight - baseSize/2, y + pyramidHeight - baseSize/4, y};
        g.setColor(new Color(180, 180, 255));
        g.fillPolygon(xPoints2, yPoints2, 3);
        
        // 边框
        g.setColor(Color.BLACK);
        g.drawRect(x, y + pyramidHeight - baseSize/2, baseSize, baseSize/2);
        g.drawPolygon(xPoints1, yPoints1, 3);
        g.drawPolygon(xPoints2, yPoints2, 3);
    }
} 