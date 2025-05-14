package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import com.geometry.entity.SectorArea;
import com.geometry.entity.User;
import com.geometry.ui.uiUtils.KidButton;

import java.util.Set;
import java.util.HashSet;

/**
 * 附加任务界面
 * Bonus 2: 扇形面积和弧长计算
 */
public class Bonus2 extends JPanel {
    private MainFrame mainFrame;
    private KidButton homeButton;
    private com.geometry.service.Bonus2 bonus2Service;
    private Set<Integer> completedQuestions; // 已完成的题目
    
    // Bonus2相关组件
    private JTextField answerField;
    private JComboBox<String> unitComboBox;
    private JLabel resultLabel;
    private JLabel questionImageLabel;
    private KidButton nextButton;
    private KidButton submitButton;
    private JPanel questionPanel;
    private JPanel selectionPanel;
    private static final String FONT_NAME = "Comic Sans MS";
    
    /**
     * 构造函数
     * @param mainFrame 主窗口引用
     */
    public Bonus2(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.bonus2Service = new com.geometry.service.Bonus2();
        this.completedQuestions = new HashSet<>();
        
        // 初始化组件
        initComponents();
        setupLayout();
    }
    
    /**
     * 初始化组件
     */
    private void initComponents() {
        // Create home button
        homeButton = new KidButton("Home");
        homeButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        homeButton.setPreferredSize(new Dimension(150, 40));
        homeButton.addActionListener(e -> mainFrame.showCard(MainFrame.HOME_PANEL));
        
        // Create question selection panel
        selectionPanel = createQuestionSelectionPanel();
        
        // Create answer panel (initially hidden)
        questionPanel = createQuestionPanel();
        questionPanel.setVisible(false);
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
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Top navigation area
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        JLabel titleLabel = new JLabel(
                "<html><h3 style='font-family: " + FONT_NAME + "; font-size: 24px; color: rgb(220, 224, 228);'>" +
                "Sector Area Calculation</h3>");
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Main content panel
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(selectionPanel, "SELECTION");
        contentPanel.add(questionPanel, "QUESTION");
        
        
        // Main background color: rgb(177, 208, 239)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(177, 208, 239));
        mainPanel.setOpaque(true);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add to main panel
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        
        // Default to show selection panel
        CardLayout cl = (CardLayout)contentPanel.getLayout();
        cl.show(contentPanel, "SELECTION");
    }
    
    private JPanel createQuestionSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Select a question to practice:", SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Create grid layout for question selection
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        gridPanel.setOpaque(false);
        
        for (int i = 1; i <= 8; i++) {
            final int questionNum = i;
            JPanel questionCard = createQuestionCard(questionNum);
            gridPanel.add(questionCard);
        }
        
        // Add grid panel to scroll pane
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createQuestionCard(int questionNum) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createLineBorder(new Color(52, 119, 219), 2));
        card.setBackground(new Color(200, 220, 240));
        
        // Load and scale question image
        SectorArea preview = new SectorArea(questionNum);
        ImageIcon originalIcon = preview.getQuestionImage();
        Image scaledImage = originalIcon.getImage().getScaledInstance(170, 130, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        
        // Create question info label
        JLabel infoLabel = new JLabel("Question " + questionNum);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        
        // Create status label
        JLabel statusLabel = new JLabel(completedQuestions.contains(questionNum) ? "Completed" : "Not Started");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        statusLabel.setForeground(completedQuestions.contains(questionNum) ? new Color(0, 150, 0) : Color.RED);
        
        // Add components to card
        card.add(imageLabel, BorderLayout.CENTER);
        card.add(infoLabel, BorderLayout.NORTH);
        card.add(statusLabel, BorderLayout.SOUTH);
        
        // Add click events
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startQuestion(questionNum);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0), 2));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createLineBorder(new Color(52, 119, 219), 2));
            }
        });
        
        return card;
    }
    
    private JPanel createQuestionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        
        // Create top toolbar
        JPanel toolBar = new JPanel(new BorderLayout());
        toolBar.setOpaque(false);
        KidButton backButton = new KidButton("Back to Questions");
        backButton.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        backButton.addActionListener(e -> showSelectionPanel());
        toolBar.add(backButton, BorderLayout.WEST);
        panel.add(toolBar, BorderLayout.NORTH);
        
        // Create answer input area
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setOpaque(false);
        answerField = new JTextField(10);
        answerField.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
        answerField.setPreferredSize(new Dimension(100, 40));
        // 添加焦点请求，使面板显示时自动获取焦点
        answerField.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                // 使用SwingUtilities.invokeLater确保组件完全显示后再请求焦点
                SwingUtilities.invokeLater(() -> answerField.requestFocusInWindow());
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {}
            public void ancestorMoved(javax.swing.event.AncestorEvent event) {}
        });
        
        unitComboBox = new JComboBox<>(new String[]{"cm2", "ft2", "m2", "in2", "yd2", "mm2"});
        unitComboBox.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
        
        submitButton = new KidButton("Submit Answer");
        submitButton.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        
        nextButton = new KidButton("Next Question");
        nextButton.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        nextButton.setVisible(false);
        
        JLabel areaLabel = new JLabel("Area:");
        areaLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        areaLabel.setForeground(Color.WHITE);
        
        inputPanel.add(areaLabel);
        inputPanel.add(answerField);
        inputPanel.add(unitComboBox);
        inputPanel.add(submitButton);
        inputPanel.add(nextButton);
        
        // Create result display area
        resultLabel = new JLabel("Please enter your answer", SwingConstants.CENTER);
        resultLabel.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        resultLabel.setForeground(Color.WHITE);
        
        // Create image display area
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        questionImageLabel = new JLabel();
        questionImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(questionImageLabel, BorderLayout.CENTER);
        
        // Add event listeners
        submitButton.addActionListener(e -> checkAnswer());
        nextButton.addActionListener(e -> showSelectionPanel());
        
        // Add keyboard enter listener
        answerField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER && submitButton.isEnabled()) {
                    checkAnswer();
                }
            }
        });
        
        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(resultLabel, BorderLayout.CENTER);
        centerPanel.add(imagePanel, BorderLayout.SOUTH);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private void showSelectionPanel() {
        Container parent = questionPanel.getParent();
        CardLayout cl = (CardLayout)parent.getLayout();
        cl.show(parent, "SELECTION");
        
        // Update selection panel to reflect completed questions
        parent.removeAll();
        parent.add(createQuestionSelectionPanel(), "SELECTION");
        parent.add(questionPanel, "QUESTION");
        parent.revalidate();
        parent.repaint();
    }
    
    private void startQuestion(int questionNum) {
        // 设置当前题目
        bonus2Service.setCurrentQuestion(questionNum);
        
        // 重置输入控件
        answerField.setText("");
        answerField.setEnabled(true);
        answerField.requestFocusInWindow();
        unitComboBox.setEnabled(true);
        submitButton.setEnabled(true);
        nextButton.setVisible(false);
        
        // 设置单位
        unitComboBox.setSelectedItem(bonus2Service.getCurrentQuestionUnit());
        resultLabel.setText("Please calculate the area of the sector in the figure");
        
        // 更新题目图片
        ImageIcon questionImage = bonus2Service.getCurrentQuestion().getQuestionImage();
        Image scaledImage = questionImage.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        questionImageLabel.setIcon(new ImageIcon(scaledImage));
        
        // 显示答题面板
        Container parent = questionPanel.getParent();
        CardLayout cl = (CardLayout)parent.getLayout();
        cl.show(parent, "QUESTION");
    }
    
    private void checkAnswer() {
        try {
            double answer = Double.parseDouble(answerField.getText());
            String selectedUnit = (String) unitComboBox.getSelectedItem();
            com.geometry.service.Bonus2.Result result = bonus2Service.checkAnswer(answer, selectedUnit);
            
            switch (result) {
                case CORRECT:
                    // Calculate points
                    int points = User.calScores("Advanced", bonus2Service.getAttempts());
                    
                    // Create score dialog
                    JDialog scoreDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Score", true);
                    scoreDialog.setLayout(new BorderLayout(10, 10));
                    scoreDialog.setSize(400, 220);
                    scoreDialog.setLocationRelativeTo(this);
                    
                    // Create score panel
                    JPanel scorePanel = new JPanel(new BorderLayout(10, 10));
                    scorePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                    
                    // Add score information
                    JLabel scoreLabel = new JLabel("Score + " + points + " !", SwingConstants.CENTER);
                    scoreLabel.setFont(new Font(FONT_NAME, Font.BOLD, 28));
                    scoreLabel.setForeground(new Color(0, 150, 0));
                    
                    // Confirm button
                    KidButton okButton = new KidButton("OK");
                    okButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
                    okButton.addActionListener(e -> scoreDialog.dispose());
                    
                    // Add keyboard enter listener
                    scoreDialog.getRootPane().setDefaultButton(okButton);
                    
                    // Assemble panel
                    scorePanel.add(scoreLabel, BorderLayout.CENTER);
                    scorePanel.add(okButton, BorderLayout.SOUTH);
                    
                    scoreDialog.add(scorePanel);
                    
                    // Update main window score display
                    mainFrame.updateScore();
                    
                    // Update status label and completion status
                    resultLabel.setText("Correct answer!");
                    completedQuestions.add(bonus2Service.getCurrentQuestion().getQuestionNumber());
                    
                    // Show answer image
                    showAnswerImage();
                    
                    // Show score dialog
                    scoreDialog.setVisible(true);
                    
                    // Check if all questions are completed
                    if (completedQuestions.size() == 8) {
                        mainFrame.updateTaskStatus("Bonus 2: Sector Area", true);
                        
                        // Create completion dialog
                        JDialog completionDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Completion", true);
                        completionDialog.setLayout(new BorderLayout(10, 10));
                        completionDialog.setSize(400, 220);
                        completionDialog.setLocationRelativeTo(this);
                        
                        // Create completion panel
                        JPanel completionPanel = new JPanel(new BorderLayout(10, 10));
                        completionPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                        
                        // Add completion information
                        JLabel completionLabel = new JLabel("You calculated all sectors!", SwingConstants.CENTER);
                        completionLabel.setFont(new Font(FONT_NAME, Font.BOLD, 28));
                        completionLabel.setForeground(new Color(0, 150, 0));
                        
                        // Confirm button
                        KidButton completeButton = new KidButton("OK");
                        completeButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
                        completeButton.addActionListener(e -> {
                            completionDialog.dispose();
                            mainFrame.showCard(MainFrame.HOME_PANEL);
                        });
                        
                        // Add keyboard enter listener
                        completionDialog.getRootPane().setDefaultButton(completeButton);
                        
                        // Assemble panel
                        completionPanel.add(completionLabel, BorderLayout.CENTER);
                        completionPanel.add(completeButton, BorderLayout.SOUTH);
                        
                        completionDialog.add(completionPanel);
                        completionDialog.setVisible(true);
                    }
                    break;
                    
                case INCORRECT:
                    resultLabel.setText("Incorrect answer. Try again! " +
                        (3 - bonus2Service.getAttempts()) + " attempts remaining");
                    answerField.setText("");
                    answerField.requestFocusInWindow();
                    break;
                    
                case MAX_ATTEMPTS_REACHED:
                    resultLabel.setText("Maximum attempts reached. The correct answer is: " +
                        bonus2Service.getCurrentQuestionFormattedArea());
                    showAnswerImage();
                    break;
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter a valid number!");
            answerField.selectAll();
            answerField.requestFocusInWindow();
        }
    }

    private void showAnswerImage() {
        // Show answer image
        ImageIcon answerImage = bonus2Service.getCurrentQuestion().getAnswerImage();
        Image scaledImage = answerImage.getImage().getScaledInstance(750, 300, Image.SCALE_SMOOTH);
        questionImageLabel.setIcon(new ImageIcon(scaledImage));
        
        // Disable input controls
        submitButton.setEnabled(false);
        answerField.setEnabled(false);
        unitComboBox.setEnabled(false);
        
        // Show next question button
        nextButton.setVisible(true);
        
        // Add a short delay to let user see the score
        Timer timer = new Timer(3000, e -> {
            ((Timer) e.getSource()).stop();
            resultLabel.setText("Here's the solution. Click 'Next Question' when ready.");
        });
        timer.setRepeats(false);
        timer.start();
    }
} 