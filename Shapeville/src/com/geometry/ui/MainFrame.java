package com.geometry.ui;

import javax.swing.*;

import com.geometry.entity.User;
import com.geometry.ui.uiUtils.ColorScheme;
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
    public static final String SHAPE_AREA_PANEL = "SHAPE_AREA";
    public static final String CIRCLE_PANEL = "CIRCLE";
    public static final String BONUS_PANEL = "BONUS";
    public static final String BONUS1_PANEL = "BONUS1";
    public static final String BONUS2_PANEL = "BONUS2";
    public static final String KS1_SELECTION_PANEL = "KS1_SELECTION";
    public static final String KS2_SELECTION_PANEL = "KS2_SELECTION";
    public static final String FONT_NAME = "Comic Sans MS";
    
    // 在MainFrame中保存BackgroundPanel引用
    private BackgroundPanel bgPanel;
    
    // 颜色切换按钮
    private JButton colorModeButton;
    
    /**
     * 构造函数
     */
    public MainFrame() {
        initComponents();
        initColorModeButton(); // 初始化颜色模式按钮
        setupLayout();
        setupListeners();
        
        // 初始化背景图
        updateBackground(true);
        
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
        levelInfoLabel = createLevelInfoLabel();
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setString("Current Score: 0");
        progressBar.setPreferredSize(new Dimension(progressBar.getPreferredSize().width, 40));
        progressBar.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        
        endSessionButton = new KidButton("End");
        endSessionButton.setFont(new Font(FONT_NAME, Font.BOLD, 26));
        
        // 创建颜色切换按钮
        colorModeButton = new KidButton("Color Mode");
        colorModeButton.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        colorModeButton.setToolTipText("Change color scheme for colorblind users");
        
        // 创建卡片布局面板
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // 创建主页面
        JPanel homePanel = createHomePanel();
        
        // 创建其他功能面板
        JPanel shapePanel = new Shape2DPanel(this);
        JPanel shape3DPanel = new Shape3DPanel(this);
        JPanel anglePanel = new AnglePanel(this);
        JPanel shapeAreaPanel = new ShapeArea(this);
        JPanel circlePanel = new Circle(this);
        JPanel bonus1Panel = new Bonus1(this);
        JPanel bonus2Panel = new Bonus2(this);
        JPanel ks1SelectionPanel = createKS1SelectionPanel();
        JPanel ks2SelectionPanel = createKS2SelectionPanel();

        cardPanel.setOpaque(false);
        homePanel.setOpaque(false);
        shapePanel.setOpaque(false);
        shape3DPanel.setOpaque(false);
        anglePanel.setOpaque(false);
        shapeAreaPanel.setOpaque(false);
        circlePanel.setOpaque(false);
        bonus1Panel.setOpaque(false);
        bonus2Panel.setOpaque(false);
        ks1SelectionPanel.setOpaque(false);
        ks2SelectionPanel.setOpaque(false);
        
        // 添加到卡片布局
        cardPanel.add(homePanel, HOME_PANEL);
        cardPanel.add(shapePanel, SHAPE_2D_PANEL);
        cardPanel.add(shape3DPanel, SHAPE_3D_PANEL);
        cardPanel.add(anglePanel, ANGLE_PANEL);
        cardPanel.add(shapeAreaPanel, SHAPE_AREA_PANEL);
        cardPanel.add(circlePanel, CIRCLE_PANEL);
        cardPanel.add(bonus1Panel, BONUS1_PANEL);
        cardPanel.add(bonus2Panel, BONUS2_PANEL);
        cardPanel.add(ks1SelectionPanel, KS1_SELECTION_PANEL);
        cardPanel.add(ks2SelectionPanel, KS2_SELECTION_PANEL);
    }
    
    /**
     * 创建关卡信息标签
     */
    private JLabel createLevelInfoLabel() {
        String primaryColorHex = String.format("#%02x%02x%02x", 
            ColorScheme.getColor(ColorScheme.PRIMARY).getRed(),
            ColorScheme.getColor(ColorScheme.PRIMARY).getGreen(),
            ColorScheme.getColor(ColorScheme.PRIMARY).getBlue());
        
        JLabel label = new JLabel("<html>" +
                "<p style='font-size: 24px; font-family: \"" + FONT_NAME + "\"; color:" + primaryColorHex + ";'>Key Stage 1 (KS1): Recognize basic shapes and angles </p>" +
                "<p style='font-size: 24px; font-family: \"" + FONT_NAME + "\"; color:" + primaryColorHex + ";'>Key Stage 2 (KS2): Area and Perimeter</p>"
                );
        return label;
    }
    
    /**
     * 更新关卡信息标签的颜色
     */
    private void updateLevelInfoLabel() {
        String primaryColorHex = String.format("#%02x%02x%02x", 
            ColorScheme.getColor(ColorScheme.PRIMARY).getRed(),
            ColorScheme.getColor(ColorScheme.PRIMARY).getGreen(),
            ColorScheme.getColor(ColorScheme.PRIMARY).getBlue());
        
        levelInfoLabel.setText("<html>" +
                "<p style='font-size: 24px; font-family: \"" + FONT_NAME + "\"; color:" + primaryColorHex + ";'>Key Stage 1 (KS1): Recognize basic shapes and angles </p>" +
                "<p style='font-size: 24px; font-family: \"" + FONT_NAME + "\"; color:" + primaryColorHex + ";'>Key Stage 2 (KS2): Area and Perimeter</p>"
                );
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

        // 添加颜色模式切换按钮
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(colorModeButton);
        topPanel.setOpaque(false);
        panel.add(topPanel, BorderLayout.NORTH);

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

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(120, 120, 120, 120));

        JButton task3Button = createMenuButtonKS("Shape Area");
        task3Button.setFont(new Font(FONT_NAME, Font.BOLD, 35));
        task3Button.setPreferredSize(new Dimension(80, 80));
        task3Button.addActionListener(e -> showCard(SHAPE_AREA_PANEL));

        JButton task4Button = createMenuButtonKS("Circle");
        task4Button.setFont(new Font(FONT_NAME, Font.BOLD, 35));
        task4Button.setPreferredSize(new Dimension(180, 80));
        task4Button.addActionListener(e -> showCard(CIRCLE_PANEL));

        JButton bonus1Button = createMenuButtonKS("<html><center>Bonus 1:<br>Compound Area</center></html>");
        bonus1Button.setFont(new Font(FONT_NAME, Font.BOLD, 35));
        bonus1Button.setPreferredSize(new Dimension(180, 80));
        bonus1Button.addActionListener(e -> showCard(BONUS1_PANEL));

        JButton bonus2Button = createMenuButtonKS("<html><center>Bonus 2:<br>Sector Area</center></html>");
        bonus2Button.setFont(new Font(FONT_NAME, Font.BOLD, 35));
        bonus2Button.setPreferredSize(new Dimension(180, 80));
        bonus2Button.addActionListener(e -> showCard(BONUS2_PANEL));

        buttonPanel.setOpaque(false);
        buttonPanel.add(task3Button);
        buttonPanel.add(task4Button);
        buttonPanel.add(bonus1Button);
        buttonPanel.add(bonus2Button);

        panel.add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new KidButton("Home");
        backButton.setFont(new Font(FONT_NAME, Font.BOLD, 26));
        backButton.addActionListener(e -> showCard(HOME_PANEL));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backButton);
        bottomPanel.setOpaque(false);
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
        button.setFont(new Font(FONT_NAME, Font.BOLD, 30));
        button.setForeground(ColorScheme.getColor(ColorScheme.PRIMARY));
        button.setPreferredSize(new Dimension(250, 100));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.BORDER_PRIMARY), 5));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.WARNING), 5));
                button.setForeground(ColorScheme.getColor(ColorScheme.WARNING)); // 鼠标悬停时改变字体颜色
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.BORDER_PRIMARY), 5));
                button.setForeground(ColorScheme.getColor(ColorScheme.PRIMARY)); // 鼠标离开时恢复原来的字体颜色
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
        button.setBackground(new Color(255, 255, 255, 100)); 
        button.setFont(new Font(FONT_NAME, Font.BOLD, 30));
        button.setForeground(ColorScheme.getColor(ColorScheme.SECONDARY));
        button.setPreferredSize(new Dimension(250, 100));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.SECONDARY), 5));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.WARNING), 5));
                button.setForeground(ColorScheme.getColor(ColorScheme.WARNING)); // 鼠标悬停时改变字体颜色为橙色
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.SECONDARY), 5));
                button.setForeground(ColorScheme.getColor(ColorScheme.SECONDARY)); // 鼠标离开时恢复原来的字体颜色
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
        
        // 设置颜色模式切换按钮的监听器
        colorModeButton.addActionListener(e -> cycleColorScheme());
    }
    
    /**
     * 显示指定卡片
     */
    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
        if (bgPanel != null) {
            if (HOME_PANEL.equals(cardName)) {
                // 根据当前颜色方案选择对应的背景图
                updateBackground(true);
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
            updateTaskStatus("Bonus 1: Compound Area", false);
            updateTaskStatus("Bonus 2: Sector Area", false);
            updateTaskStatusPanel();
        }
    }
    
    /**
     * 更新背景图以匹配当前颜色方案
     */
    private void updateBackground(boolean isHomePage) {
        if (bgPanel == null) return;
        
        if (isHomePage) {
            // 根据当前颜色方案选择主页背景
            int scheme = ColorScheme.getCurrentScheme();
            switch (scheme) {
                case ColorScheme.RED_GREEN_COLORBLIND:
                    bgPanel.setBackgroundImage("/com/geometry/ui/imgs/bg1RedGreenBlind.png");
                    break;
                case ColorScheme.BLUE_YELLOW_COLORBLIND:
                    bgPanel.setBackgroundImage("/com/geometry/ui/imgs/bg1BlueYellowBlind.png");
                    break;
                case ColorScheme.NORMAL_VISION:
                default:
                    bgPanel.setBackgroundImage("/com/geometry/ui/imgs/bg1Default.png");
                    break;
            }
        } else {
            // 非主页使用通用背景
            bgPanel.setBackgroundImage("/com/geometry/ui/imgs/bg3.png");
        }
    }
    
    /**
     * 循环切换颜色方案
     */
    private void cycleColorScheme() {
        int currentScheme = ColorScheme.getCurrentScheme();
        int nextScheme = (currentScheme + 1) % 3; // 循环在0, 1, 2之间切换
        
        ColorScheme.setColorScheme(nextScheme);
        
        // 更新按钮显示的文本，显示当前方案名称
        colorModeButton.setText(ColorScheme.getSchemeName(nextScheme));
        
        // 更新背景图以匹配新的颜色方案
        String currentCard = getCurrentVisibleCard();
        
        // 更新背景图
        updateBackground(HOME_PANEL.equals(currentCard));
        
        // 更新所有UI元素的颜色
        updateUIColors();
    }
    
    /**
     * 获取当前可见的卡片名称
     */
    private String getCurrentVisibleCard() {
        // 查找可见的卡片组件
        Component visibleComp = null;
        for (Component c : cardPanel.getComponents()) {
            if (c.isVisible()) {
                visibleComp = c;
                break;
            }
        }
        
        if (visibleComp == null) {
            return HOME_PANEL; // 默认返回主页
        }

        // 查找当前可见卡片对应的名称
        for (Component comp : cardPanel.getComponents()) {
            if (comp == visibleComp) {
                // 检查是否是主页面
                if (comp == cardPanel.getComponent(0)) { // 假设主页是第一个添加的组件
                    return HOME_PANEL;
                } else {
                    return "OTHER";
                }
            }
        }
        
        return HOME_PANEL; // 默认返回主页
    }
    
    /**
     * 更新UI元素的颜色
     */
    private void updateUIColors() {
        // 更新主要按钮颜色
        updateButtonColors();
        
        // 更新关卡信息标签颜色
        updateLevelInfoLabel();
        
        // 更新进度条颜色
        progressBar.setForeground(ColorScheme.getColor(ColorScheme.SUCCESS));
        
        // 更新任务状态面板
        updateTaskStatusPanel();
        
        // 要求所有子面板更新颜色
        refreshAllPanels();
        
        // 重绘整个窗口
        repaint();
    }
    
    /**
     * 更新按钮颜色
     */
    private void updateButtonColors() {
        // 更新结束会话按钮
        if (endSessionButton instanceof KidButton) {
            KidButton kidButton = (KidButton) endSessionButton;
            kidButton.setNormalColor(ColorScheme.getColor(ColorScheme.BUTTON_NORMAL));
            kidButton.setHoverColor(ColorScheme.getColor(ColorScheme.BUTTON_HOVER));
            kidButton.setPressedColor(ColorScheme.getColor(ColorScheme.BUTTON_PRESSED));
        }
        
        // 更新颜色模式按钮
        if (colorModeButton instanceof KidButton) {
            KidButton kidButton = (KidButton) colorModeButton;
            kidButton.setNormalColor(ColorScheme.getColor(ColorScheme.BUTTON_NORMAL));
            kidButton.setHoverColor(ColorScheme.getColor(ColorScheme.BUTTON_HOVER));
            kidButton.setPressedColor(ColorScheme.getColor(ColorScheme.BUTTON_PRESSED));
        }
    }
    
    /**
     * 刷新所有面板，强制它们更新颜色
     */
    private void refreshAllPanels() {
        // 这里可以添加通知各个面板更新颜色的逻辑
        Component[] components = cardPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                component.repaint();
            }
        }
    }
    
    /**
     * 结束会话
     */
    private void endSession() {
        // 创建对话框
        JDialog scoreDialog = new JDialog(this, "Session Ended", true);
        scoreDialog.setLayout(new BorderLayout(10, 10));
        scoreDialog.setSize(400, 220);
        scoreDialog.setLocationRelativeTo(this);
        
        // 创建消息面板
        JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // 添加得分信息
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>You scored " + currentScore + " points.<br>Goodbye!</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        messageLabel.setForeground(ColorScheme.getColor(ColorScheme.SUCCESS));
        
        // 确认按钮
        KidButton okButton = new KidButton("OK");
        okButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        okButton.addActionListener(e -> {
            scoreDialog.dispose();
            System.exit(0);
        });
        
        // 添加键盘回车监听
        scoreDialog.getRootPane().setDefaultButton(okButton);
        
        // 组装面板
        messagePanel.add(messageLabel, BorderLayout.CENTER);
        messagePanel.add(okButton, BorderLayout.SOUTH);
        
        scoreDialog.add(messagePanel);
        scoreDialog.setVisible(true);
        System.exit(0);
    }
    
    /**
     * 更新分数
     */
    public void updateScore() {
        currentScore = User.getScores(); // 直接设置分数而不是累加
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
                    taskButton.setNormalColor(ColorScheme.getColor(ColorScheme.SUCCESS));
                } else {
                    taskButton.setNormalColor(ColorScheme.getColor(ColorScheme.BUTTON_NORMAL));
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
    
    // 获取当前颜色方案
    public int getCurrentColorScheme() {
        return ColorScheme.getCurrentScheme();
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
            // 不直接设置背景图，由updateBackground方法统一处理
        }
        public void setBackgroundImage(String path) {
            if (path != null && path.equals(currentBgPath)) return;
            try {
                bg = new ImageIcon(getClass().getResource(path)).getImage();
                currentBgPath = path;
            } catch (Exception e) {
                System.err.println("Failed to load background image: " + path);
                e.printStackTrace();
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
    
    /**
     * 初始化颜色模式按钮
     */
    private void initColorModeButton() {
        // 更新按钮显示当前颜色模式名称
        colorModeButton.setText(ColorScheme.getSchemeName(ColorScheme.getCurrentScheme()));
        
        // 确保按钮使用正确的颜色
        if (colorModeButton instanceof KidButton) {
            KidButton kidButton = (KidButton) colorModeButton;
            kidButton.setNormalColor(ColorScheme.getColor(ColorScheme.BUTTON_NORMAL));
            kidButton.setHoverColor(ColorScheme.getColor(ColorScheme.BUTTON_HOVER));
            kidButton.setPressedColor(ColorScheme.getColor(ColorScheme.BUTTON_PRESSED));
        }
    }
} 