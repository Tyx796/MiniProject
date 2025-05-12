package com.geometry.ui;

import javax.swing.*;
import java.awt.*;
import com.geometry.service.Bonus1;
import com.geometry.service.Bonus2;
import com.geometry.entity.CompoundArea;
import com.geometry.entity.SectorArea;
import com.geometry.entity.User;

import java.util.Set;
import java.util.HashSet;

/**
 * 附加任务界面
 * Bonus 1: 复合图形面积计算
 * Bonus 2: 扇形面积和弧长计算
 */
public class BonusTasksPanel extends JPanel {
    private MainFrame mainFrame;
    private JButton homeButton;
    private JTabbedPane tabbedPane;  // 使用选项卡切换不同任务
    private Bonus1 bonus1Service;
    private Bonus2 bonus2Service;    // 添加Bonus2服务
    private JTextField answerField;
    private JComboBox<String> unitComboBox;
    private JLabel resultLabel;
    private JLabel questionImageLabel;
    private JButton nextButton;    // 添加下一题按钮
    private JButton submitButton;  // 添加提交按钮作为成员变量
    private JPanel questionPanel;  // 当前题目面板
    private JPanel selectionPanel; // 题目选择面板
    private Set<Integer> completedQuestions; // 已完成的题目
    
    // Bonus1相关组件
    private JTextField bonus1AnswerField;
    private JComboBox<String> bonus1UnitComboBox;
    private JLabel bonus1ResultLabel;
    private JLabel bonus1QuestionImageLabel;
    private JButton bonus1NextButton;
    private JButton bonus1SubmitButton;
    private JPanel bonus1QuestionPanel;
    private JPanel bonus1SelectionPanel;
    
    // Bonus2相关组件
    private JTextField bonus2AnswerField;
    private JComboBox<String> bonus2UnitComboBox;
    private JLabel bonus2ResultLabel;
    private JLabel bonus2QuestionImageLabel;
    private JButton bonus2NextButton;
    private JButton bonus2SubmitButton;
    private JPanel bonus2QuestionPanel;
    private JPanel bonus2SelectionPanel;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口引用
     */
    public BonusTasksPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.bonus1Service = new Bonus1();
        this.bonus2Service = new Bonus2();
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
        homeButton = new JButton("Back to Home");
        homeButton.addActionListener(e -> mainFrame.showCard(MainFrame.HOME_PANEL));
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add bonus task 1 tab - Compound shape area calculation
        JPanel bonus1Panel = createBonus1Panel();
        tabbedPane.addTab("Bonus 1: Compound Shapes", bonus1Panel);
        
        // Add bonus task 2 tab - Sector area calculation
        JPanel bonus2Panel = createBonus2Panel();
        tabbedPane.addTab("Bonus 2: Sectors", bonus2Panel);
    }
    
    /**
     * 设置布局
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top navigation area
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Bonus Challenge Tasks", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Add to main panel
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    /**
     * 创建附加任务1面板 - 复合图形面积计算
     */
    private JPanel createBonus1Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Task description
        JLabel descriptionLabel = new JLabel(
                "<html><h3>Compound Shape Area Calculation</h3>" +
                "<p>In this task, you will calculate the areas of different compound shapes.</p>" +
                "<p>Note: Answers must include the correct unit (cm² or m²).</p></html>");
        panel.add(descriptionLabel, BorderLayout.NORTH);
        
        // Create question selection panel
        bonus1SelectionPanel = createQuestionSelectionPanel();
        
        // Create answer panel (initially hidden)
        bonus1QuestionPanel = createQuestionPanel();
        bonus1QuestionPanel.setVisible(false);
        
        // Add to main panel
        JPanel centerPanel = new JPanel(new CardLayout());
        centerPanel.add(bonus1SelectionPanel, "SELECTION");
        centerPanel.add(bonus1QuestionPanel, "QUESTION");
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createQuestionSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Select a question to practice:", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Create grid layout for question selection
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        
        for (int i = 1; i <= 9; i++) {
            final int questionNum = i;
            JPanel questionCard = createQuestionCard(questionNum);
            gridPanel.add(questionCard);
        }
        
        // Add grid panel to scroll pane
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createQuestionCard(int questionNum) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Load and scale question image
        CompoundArea preview = new CompoundArea(questionNum);
        ImageIcon originalIcon = preview.getQuestionImage();
        Image scaledImage = originalIcon.getImage().getScaledInstance(120, 90, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        
        // Create question info label
        JLabel infoLabel = new JLabel("Question " + questionNum);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Create status label
        JLabel statusLabel = new JLabel(completedQuestions.contains(questionNum) ? "Completed" : "Not Started");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setForeground(completedQuestions.contains(questionNum) ? Color.GREEN : Color.RED);
        
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
                card.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        });
        
        return card;
    }
    
    private JPanel createQuestionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Create top toolbar
        JPanel toolBar = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back to Questions");
        backButton.addActionListener(e -> showSelectionPanel());
        toolBar.add(backButton, BorderLayout.WEST);
        panel.add(toolBar, BorderLayout.NORTH);
        
        // Create answer input area
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bonus1AnswerField = new JTextField(10);
        bonus1UnitComboBox = new JComboBox<>(new String[]{"cm2", "m2"});
        bonus1SubmitButton = new JButton("Submit Answer");
        bonus1NextButton = new JButton("Next Question");
        bonus1NextButton.setVisible(false);
        
        inputPanel.add(new JLabel("Area:"));
        inputPanel.add(bonus1AnswerField);
        inputPanel.add(bonus1UnitComboBox);
        inputPanel.add(bonus1SubmitButton);
        inputPanel.add(bonus1NextButton);
        
        // Create result display area
        bonus1ResultLabel = new JLabel("Please enter your answer", JLabel.CENTER);
        bonus1ResultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Create image display area
        JPanel imagePanel = new JPanel(new BorderLayout());
        bonus1QuestionImageLabel = new JLabel();
        bonus1QuestionImageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(bonus1QuestionImageLabel, BorderLayout.CENTER);
        
        // Add event listeners
        bonus1SubmitButton.addActionListener(e -> checkAnswer());
        bonus1NextButton.addActionListener(e -> showSelectionPanel());
        
        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(bonus1ResultLabel, BorderLayout.CENTER);
        centerPanel.add(imagePanel, BorderLayout.SOUTH);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private void showSelectionPanel() {
        CardLayout cl = (CardLayout) bonus1QuestionPanel.getParent().getLayout();
        cl.show(bonus1QuestionPanel.getParent(), "SELECTION");
        // 更新选择面板
        bonus1SelectionPanel.removeAll();
        bonus1SelectionPanel.add(createQuestionSelectionPanel());
        bonus1SelectionPanel.revalidate();
        bonus1SelectionPanel.repaint();
    }
    
    private void startQuestion(int questionNum) {
        // 设置当前题目
        bonus1Service.setCurrentQuestion(questionNum);
        
        // 重置输入控件
        bonus1AnswerField.setText("");
        bonus1AnswerField.setEnabled(true);
        bonus1UnitComboBox.setEnabled(true);
        bonus1SubmitButton.setEnabled(true);
        bonus1NextButton.setVisible(false);
        
        // 设置单位
        bonus1UnitComboBox.setSelectedItem(bonus1Service.getCurrentQuestionUnit());
        bonus1ResultLabel.setText("Please calculate the area of the shaded part in the figure");
        
        // 更新题目图片
        ImageIcon questionImage = bonus1Service.getCurrentQuestion().getQuestionImage();
        Image scaledImage = questionImage.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        bonus1QuestionImageLabel.setIcon(new ImageIcon(scaledImage));
        
        // 显示答题面板
        CardLayout cl = (CardLayout) bonus1QuestionPanel.getParent().getLayout();
        cl.show(bonus1QuestionPanel.getParent(), "QUESTION");
    }
    
    private void checkAnswer() {
        try {
            double answer = Double.parseDouble(bonus1AnswerField.getText());
            String selectedUnit = (String) bonus1UnitComboBox.getSelectedItem();
            Bonus1.Result result = bonus1Service.checkAnswer(answer, selectedUnit);
            
            switch (result) {
                case CORRECT:
                    // Calculate points
                    int points = User.calScores("Advanced", bonus1Service.getAttempts());
                    
                    // Create score dialog
                    JDialog scoreDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Score Alert", true);
                    scoreDialog.setLayout(new BorderLayout(10, 10));
                    scoreDialog.setSize(300, 150);
                    scoreDialog.setLocationRelativeTo(this);
                    
                    // Create score panel
                    JPanel scorePanel = new JPanel(new BorderLayout(10, 10));
                    scorePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                    
                    // Add score information
                    JLabel scoreLabel = new JLabel("Congratulations! You earned " + points + " points!", JLabel.CENTER);
                    scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    scoreLabel.setForeground(new Color(0, 150, 0));
                    
                    // Confirm button
                    JButton okButton = new JButton("Continue");
                    okButton.addActionListener(e -> scoreDialog.dispose());
                    
                    // Assemble panel
                    scorePanel.add(scoreLabel, BorderLayout.NORTH);
                    scorePanel.add(okButton, BorderLayout.SOUTH);
                    
                    scoreDialog.add(scorePanel);
                    
                    // Update status label and completion status
                    bonus1ResultLabel.setText("Correct answer!");
                    completedQuestions.add(bonus1Service.getCurrentQuestion().getQuestionNumber());
                    
                    // Show answer image
                    showAnswerImage();
                    
                    // Show score dialog
                    scoreDialog.setVisible(true);
                    
                    // Check if all questions are completed
                    if (completedQuestions.size() == 9) {
                        mainFrame.updateTaskStatus("Compound Area", true);
                        JOptionPane.showMessageDialog(this,
                            "Congratulations! You've completed all questions!",
                            "Complete",
                            JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.showCard(MainFrame.HOME_PANEL);
                    }
                    break;
                    
                case INCORRECT:
                    bonus1ResultLabel.setText("Incorrect answer. Try again! " +
                        (3 - bonus1Service.getAttempts()) + " attempts remaining");
                    break;
                    
                case MAX_ATTEMPTS_REACHED:
                    bonus1ResultLabel.setText("Maximum attempts reached. The correct answer is: " +
                        bonus1Service.getCurrentQuestionFormattedArea());
                    showAnswerImage();
                    break;
            }
        } catch (NumberFormatException ex) {
            bonus1ResultLabel.setText("Please enter a valid number!");
        }
    }

    private void showAnswerImage() {
        // Show answer image
        ImageIcon answerImage = bonus1Service.getCurrentQuestion().getAnswerImage();
        Image scaledImage = answerImage.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        bonus1QuestionImageLabel.setIcon(new ImageIcon(scaledImage));
        
        // Disable input controls
        bonus1SubmitButton.setEnabled(false);
        bonus1AnswerField.setEnabled(false);
        bonus1UnitComboBox.setEnabled(false);
        
        // Show next question button
        bonus1NextButton.setVisible(true);
        
        // Add a short delay to let user see the score
        Timer timer = new Timer(3000, e -> {
            ((Timer) e.getSource()).stop();
            bonus1ResultLabel.setText("Here's the solution. Click 'Next Question' when ready.");
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    /**
     * 创建附加任务2面板 - 扇形面积和弧长计算
     */
    private JPanel createBonus2Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Task description
        JLabel descriptionLabel = new JLabel(
                "<html><h3>Sector Area Calculation</h3>" +
                "<p>In this task, you will calculate the areas of different sectors.</p>" +
                "<p>Note: Answers must include the correct unit (cm², ft², m², in², yd², mm²).</p></html>");
        panel.add(descriptionLabel, BorderLayout.NORTH);
        
        // Create question selection panel
        bonus2SelectionPanel = createBonus2SelectionPanel();
        
        // Create answer panel (initially hidden)
        bonus2QuestionPanel = createBonus2QuestionPanel();
        bonus2QuestionPanel.setVisible(false);
        
        // Add to main panel
        JPanel centerPanel = new JPanel(new CardLayout());
        centerPanel.add(bonus2SelectionPanel, "SELECTION");
        centerPanel.add(bonus2QuestionPanel, "QUESTION");
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBonus2SelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Select a question to practice:", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Create grid layout for question selection
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        
        for (int i = 1; i <= 8; i++) {
            final int questionNum = i;
            JPanel questionCard = createBonus2QuestionCard(questionNum);
            gridPanel.add(questionCard);
        }
        
        // Add grid panel to scroll pane
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createBonus2QuestionCard(int questionNum) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Load and scale question image
        SectorArea preview = new SectorArea(questionNum);
        ImageIcon originalIcon = preview.getQuestionImage();
        Image scaledImage = originalIcon.getImage().getScaledInstance(120, 90, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        
        // Create question info label
        JLabel infoLabel = new JLabel("Question " + questionNum);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Create status label
        JLabel statusLabel = new JLabel(completedQuestions.contains(questionNum) ? "Completed" : "Not Started");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setForeground(completedQuestions.contains(questionNum) ? Color.GREEN : Color.RED);
        
        // Add components to card
        card.add(imageLabel, BorderLayout.CENTER);
        card.add(infoLabel, BorderLayout.NORTH);
        card.add(statusLabel, BorderLayout.SOUTH);
        
        // Add click events
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startBonus2Question(questionNum);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        });
        
        return card;
    }
    
    private JPanel createBonus2QuestionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Create top toolbar
        JPanel toolBar = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back to Questions");
        backButton.addActionListener(e -> showBonus2SelectionPanel());
        toolBar.add(backButton, BorderLayout.WEST);
        panel.add(toolBar, BorderLayout.NORTH);
        
        // Create answer input area
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bonus2AnswerField = new JTextField(10);
        bonus2UnitComboBox = new JComboBox<>(new String[]{"cm2", "ft2", "m2", "in2", "yd2", "mm2"});
        bonus2SubmitButton = new JButton("Submit Answer");
        bonus2NextButton = new JButton("Next Question");
        bonus2NextButton.setVisible(false);
        
        inputPanel.add(new JLabel("Area:"));
        inputPanel.add(bonus2AnswerField);
        inputPanel.add(bonus2UnitComboBox);
        inputPanel.add(bonus2SubmitButton);
        inputPanel.add(bonus2NextButton);
        
        // Create result display area
        bonus2ResultLabel = new JLabel("Please enter your answer", JLabel.CENTER);
        bonus2ResultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Create image display area
        JPanel imagePanel = new JPanel(new BorderLayout());
        bonus2QuestionImageLabel = new JLabel();
        bonus2QuestionImageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(bonus2QuestionImageLabel, BorderLayout.CENTER);
        
        // Add event listeners
        bonus2SubmitButton.addActionListener(e -> checkBonus2Answer());
        bonus2NextButton.addActionListener(e -> showBonus2SelectionPanel());
        
        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(bonus2ResultLabel, BorderLayout.CENTER);
        centerPanel.add(imagePanel, BorderLayout.SOUTH);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private void showBonus2SelectionPanel() {
        CardLayout cl = (CardLayout) bonus2QuestionPanel.getParent().getLayout();
        cl.show(bonus2QuestionPanel.getParent(), "SELECTION");
        // 更新选择面板
        bonus2SelectionPanel.removeAll();
        bonus2SelectionPanel.add(createBonus2SelectionPanel());
        bonus2SelectionPanel.revalidate();
        bonus2SelectionPanel.repaint();
    }
    
    private void startBonus2Question(int questionNum) {
        // 设置当前题目
        bonus2Service.setCurrentQuestion(questionNum);
        
        // 重置输入控件
        bonus2AnswerField.setText("");
        bonus2AnswerField.setEnabled(true);
        bonus2UnitComboBox.setEnabled(true);
        bonus2SubmitButton.setEnabled(true);
        bonus2NextButton.setVisible(false);
        
        // 设置单位
        bonus2UnitComboBox.setSelectedItem(bonus2Service.getCurrentQuestionUnit());
        bonus2ResultLabel.setText("Please calculate the area of the sector in the figure");
        
        // 更新题目图片
        ImageIcon questionImage = bonus2Service.getCurrentQuestion().getQuestionImage();
        Image scaledImage = questionImage.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        bonus2QuestionImageLabel.setIcon(new ImageIcon(scaledImage));
        
        // 显示答题面板
        CardLayout cl = (CardLayout) bonus2QuestionPanel.getParent().getLayout();
        cl.show(bonus2QuestionPanel.getParent(), "QUESTION");
    }
    
    private void checkBonus2Answer() {
        try {
            double answer = Double.parseDouble(bonus2AnswerField.getText());
            String selectedUnit = (String) bonus2UnitComboBox.getSelectedItem();
            Bonus2.Result result = bonus2Service.checkAnswer(answer, selectedUnit);
            
            switch (result) {
                case CORRECT:
                    // Calculate points
                    int points = User.calScores("Advanced", bonus2Service.getAttempts());
                    
                    // Create score dialog
                    JDialog scoreDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Score Alert", true);
                    scoreDialog.setLayout(new BorderLayout(10, 10));
                    scoreDialog.setSize(300, 150);
                    scoreDialog.setLocationRelativeTo(this);
                    
                    // Create score panel
                    JPanel scorePanel = new JPanel(new BorderLayout(10, 10));
                    scorePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                    
                    // Add score information
                    JLabel scoreLabel = new JLabel("Congratulations! You earned " + points + " points!", JLabel.CENTER);
                    scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    scoreLabel.setForeground(new Color(0, 150, 0));
                    
                    // Confirm button
                    JButton okButton = new JButton("Continue");
                    okButton.addActionListener(e -> scoreDialog.dispose());
                    
                    // Assemble panel
                    scorePanel.add(scoreLabel, BorderLayout.NORTH);
                    scorePanel.add(okButton, BorderLayout.SOUTH);
                    
                    scoreDialog.add(scorePanel);
                    
                    // Update status label and completion status
                    bonus2ResultLabel.setText("Correct answer!");
                    completedQuestions.add(bonus2Service.getCurrentQuestion().getQuestionNumber());
                    
                    // Show answer image
                    showBonus2AnswerImage();
                    
                    // Show score dialog
                    scoreDialog.setVisible(true);
                    
                    // Check if all questions are completed
                    if (completedQuestions.size() == 8) {
                        mainFrame.updateTaskStatus("Sectors", true);
                        JOptionPane.showMessageDialog(this,
                            "Congratulations! You've completed all questions!",
                            "Complete",
                            JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.showCard(MainFrame.HOME_PANEL);
                    }
                    break;
                    
                case INCORRECT:
                    bonus2ResultLabel.setText("Incorrect answer. Try again! " +
                        (3 - bonus2Service.getAttempts()) + " attempts remaining");
                    break;
                    
                case MAX_ATTEMPTS_REACHED:
                    bonus2ResultLabel.setText("Maximum attempts reached. The correct answer is: " +
                        bonus2Service.getCurrentQuestionFormattedArea());
                    showBonus2AnswerImage();
                    break;
            }
        } catch (NumberFormatException ex) {
            bonus2ResultLabel.setText("Please enter a valid number!");
        }
    }

    private void showBonus2AnswerImage() {
        // Show answer image
        ImageIcon answerImage = bonus2Service.getCurrentQuestion().getAnswerImage();
        Image scaledImage = answerImage.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        bonus2QuestionImageLabel.setIcon(new ImageIcon(scaledImage));
        
        // Disable input controls
        bonus2SubmitButton.setEnabled(false);
        bonus2AnswerField.setEnabled(false);
        bonus2UnitComboBox.setEnabled(false);
        
        // Show next question button
        bonus2NextButton.setVisible(true);
        
        // Add a short delay to let user see the score
        Timer timer = new Timer(3000, e -> {
            ((Timer) e.getSource()).stop();
            bonus2ResultLabel.setText("Here's the solution. Click 'Next Question' when ready.");
        });
        timer.setRepeats(false);
        timer.start();
    }
} 