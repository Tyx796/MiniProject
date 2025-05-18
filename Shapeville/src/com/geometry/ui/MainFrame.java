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
    
    // Current score
    private int currentScore = 0;
    
    // Task completion status tracking
    private JPanel taskStatusPanel;
    private java.util.Map<String, Boolean> currentTaskStatus = new java.util.LinkedHashMap<>();
    
    // Interface name constants
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
    
    // Store BackgroundPanel reference in MainFrame
    private BackgroundPanel bgPanel;
    
    // Color mode toggle button
    private JButton colorModeButton;
    
    /**
     * Constructor
     */
    public MainFrame() {
        initComponents();
        initColorModeButton(); // Initialize color mode button
        setupLayout();
        setupListeners();
        
        // Initialize background image
        updateBackground(true);
        
        // Set window properties
        setTitle("Shapeville");
        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(true);
        initColorScheme();
    }
    
    /**
     * Initialize components
     */
    private void initComponents() {
        // Create main components
        levelInfoLabel = createLevelInfoLabel();
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setString("Current Score: 0");
        progressBar.setPreferredSize(new Dimension(progressBar.getPreferredSize().width, 40));
        progressBar.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        
        
        endSessionButton = new KidButton("End");
        endSessionButton.setFont(new Font(FONT_NAME, Font.BOLD, 26));
        
        // Create color mode toggle button
        colorModeButton = new KidButton("Color Mode");
        colorModeButton.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        colorModeButton.setToolTipText("Change color scheme for colorblind users");
        
        // Create card layout panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Create home panel
        JPanel homePanel = createHomePanel();
        
        // Create other functional panels
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
        
        // Add to card layout
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
     * Create level information label
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
     * Update level information label colors
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
     * Create home panel
     */
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    
        
        // Content section
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        // Create a panel containing levelInfoLabel with top margin
        JPanel levelInfoPanel = new JPanel(new BorderLayout());
        levelInfoPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        levelInfoPanel.add(levelInfoLabel, BorderLayout.CENTER);
        levelInfoPanel.setOpaque(false);
        contentPanel.add(levelInfoPanel, BorderLayout.NORTH);
        
        // Create menu buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 0)); // Use FlowLayout with horizontal gap of 100
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        JButton ks1Button = createMenuButtonHome("Key Stage 1");
        ks1Button.addActionListener(e -> showCard(KS1_SELECTION_PANEL));
        
        JButton ks2Button = createMenuButtonHome("Key Stage 2");
        ks2Button.addActionListener(e -> showCard(KS2_SELECTION_PANEL));
        
        buttonPanel.add(ks1Button);
        buttonPanel.add(ks2Button);
        buttonPanel.setOpaque(false);

        // Add color mode toggle button
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
     * Create KS1 selection panel
     */
    private JPanel createKS1SelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        
        // Content section - button panel
        JPanel centerPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for centering
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); // Reduce horizontal gap to 30
        
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
        
        // Add button panel to center panel
        centerPanel.add(buttonPanel);
        centerPanel.setOpaque(false);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // Back button
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
     * Create KS2 selection panel
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
     * Create menu button for home screen
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
        
        // Use updateButtonColor method to set button colors and event handling
        updateButtonColor(button);
        return button;
    }
    
    /**
     * Create menu button for Key Stage screens
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
        
        // Use updateButtonColor method to set button colors and event handling
        updateButtonColor(button);
        return button;
    }
    
    /**
     * Set up layout
     */
    private void setupLayout() {
        // Set up bottom status bar
        JPanel statusPanel = new JPanel(new BorderLayout(10, 0));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel progressPanel = new JPanel(new BorderLayout(5, 0));
        JLabel progressLabel = new JLabel("Progress ");
        progressLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        progressPanel.add(progressLabel, BorderLayout.WEST);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        
        // Add task status panel
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
        
        // Main layout
        setLayout(new BorderLayout());
        bgPanel = new BackgroundPanel();
        bgPanel.add(cardPanel, BorderLayout.CENTER);
        bgPanel.add(statusPanel, BorderLayout.SOUTH);
        add(bgPanel, BorderLayout.CENTER);

        
        // Show home panel by default
        cardLayout.show(cardPanel, HOME_PANEL);
    }
    
    /**
     * Set up event listeners
     */
    private void setupListeners() {
        endSessionButton.addActionListener(e -> endSession());
        
        // Set up color mode toggle button listener
        colorModeButton.addActionListener(e -> cycleColorScheme());
    }
    
    /**
     * Show specified card
     */
    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
        if (bgPanel != null) {
            if (HOME_PANEL.equals(cardName)) {
                // Choose background image based on current color scheme
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
     * Update background image to match current color scheme
     */
    private void updateBackground(boolean isHomePage) {
        if (bgPanel == null) return;
        
        if (isHomePage) {
            // Choose home background based on current color scheme
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
            // Use generic background for non-home pages
            bgPanel.setBackgroundImage("/com/geometry/ui/imgs/bg3.png");
        }
    }
    
    /**
     * Cycle through color schemes
     */
    private void cycleColorScheme() {
        int currentScheme = ColorScheme.getCurrentScheme();
        int nextScheme = (currentScheme + 1) % 3; // Cycle between 0, 1, 2
        
        ColorScheme.setColorScheme(nextScheme);
        
        // Update button text to show current scheme name
        colorModeButton.setText(ColorScheme.getSchemeName(nextScheme));
        
        // Update background image to match new color scheme
        String currentCard = getCurrentVisibleCard();
        
        // Update background image
        updateBackground(HOME_PANEL.equals(currentCard));
        
        // Update colors of all UI elements
        updateUIColors();
    }

    private void initColorScheme() {
        int initScheme = 0;
        
        ColorScheme.setColorScheme(initScheme);
        
        // Update button text to show current scheme name
        colorModeButton.setText(ColorScheme.getSchemeName(initScheme));
        
        // Update background image to match new color scheme
        String currentCard = getCurrentVisibleCard();
        
        // Update background image
        updateBackground(HOME_PANEL.equals(currentCard));
        
        // Update colors of all UI elements
        updateUIColors();
    }
    
    /**
     * Get the name of the currently visible card
     */
    private String getCurrentVisibleCard() {
        // Find visible card component
        Component visibleComp = null;
        for (Component c : cardPanel.getComponents()) {
            if (c.isVisible()) {
                visibleComp = c;
                break;
            }
        }
        
        if (visibleComp == null) {
            return HOME_PANEL; // Default to home panel
        }

        // Find name of current visible card
        for (Component comp : cardPanel.getComponents()) {
            if (comp == visibleComp) {
                // Check if it's the home panel
                if (comp == cardPanel.getComponent(0)) { // Assuming home panel is the first added component
                    return HOME_PANEL;
                } else {
                    return "OTHER";
                }
            }
        }
        
        return HOME_PANEL; // Default to home panel
    }
    
    /**
     * Update colors of UI elements
     */
    private void updateUIColors() {
        // Update main button colors
        updateButtonColors();
        
        // Update level info label colors
        updateLevelInfoLabel();
        
        // Update progress bar color
        progressBar.setForeground(ColorScheme.getColor(ColorScheme.SUCCESS));
        
        // Update task status panel
        updateTaskStatusPanel();
        
        // Recursively update all component colors
        refreshAllPanels();
        
        // Repaint entire window
        repaint();
    }
    
    /**
     * Update button colors
     */
    private void updateButtonColors() {
        // Update end session button
        if (endSessionButton instanceof KidButton) {
            KidButton kidButton = (KidButton) endSessionButton;
            kidButton.setNormalColor(ColorScheme.getColor(ColorScheme.BUTTON_NORMAL));
            kidButton.setHoverColor(ColorScheme.getColor(ColorScheme.BUTTON_HOVER));
            kidButton.setPressedColor(ColorScheme.getColor(ColorScheme.BUTTON_PRESSED));
        }
        
        // Update color mode button
        if (colorModeButton instanceof KidButton) {
            KidButton kidButton = (KidButton) colorModeButton;
            kidButton.setNormalColor(ColorScheme.getColor(ColorScheme.BUTTON_NORMAL));
            kidButton.setHoverColor(ColorScheme.getColor(ColorScheme.BUTTON_HOVER));
            kidButton.setPressedColor(ColorScheme.getColor(ColorScheme.BUTTON_PRESSED));
        }
    }
    
    /**
     * Refresh all panels to force color updates
     */
    private void refreshAllPanels() {
        // Recursively update colors of all components
        updateAllComponentsRecursively(this);
    }
    
    /**
     * Recursively update colors of all components
     * @param container Container to process
     */
    private void updateAllComponentsRecursively(Container container) {
        // Process all components in current container
        for (Component component : container.getComponents()) {
            // Update button colors
            if (component instanceof JButton) {
                updateButtonColor((JButton)component);
            }
            
            // If component is a container, recursively process its children
            if (component instanceof Container) {
                updateAllComponentsRecursively((Container)component);
            }
        }
    }
    
    /**
     * Update button color
     * @param button Button to update colors for
     */
    private void updateButtonColor(JButton button) {
        if (button instanceof KidButton) {
            // KidButton has its own color update logic
            KidButton kidButton = (KidButton) button;
            kidButton.setNormalColor(ColorScheme.getColor(ColorScheme.BUTTON_NORMAL));
            kidButton.setHoverColor(ColorScheme.getColor(ColorScheme.BUTTON_HOVER));
            kidButton.setPressedColor(ColorScheme.getColor(ColorScheme.BUTTON_PRESSED));
        } else {
            // Check if button is a main menu button
            boolean isHomeButton = button.getText().equals("Key Stage 1") || 
                                   button.getText().equals("Key Stage 2");
            
            // Check if button is a KS menu button
            boolean isKSButton = button.getText().equals("2D Shapes") ||
                                 button.getText().equals("3D Shapes") ||
                                 button.getText().equals("Angles") ||
                                 button.getText().equals("Shape Area") ||
                                 button.getText().equals("Circle") ||
                                 button.getText().contains("Bonus");
            
            // Update button appearance
            if (isHomeButton) {
                // Home button style
                button.setForeground(ColorScheme.getColor(ColorScheme.PRIMARY));
                button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.BORDER_PRIMARY), 5));
                
                // Remove old mouse listeners
                removeCustomMouseListeners(button);
                
                // Add new mouse listener
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setForeground(ColorScheme.getColor(ColorScheme.WARNING));
                        button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.WARNING), 5));
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setForeground(ColorScheme.getColor(ColorScheme.PRIMARY));
                        button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.BORDER_PRIMARY), 5));
                    }
                });
            } else if (isKSButton) {
                // KS button style
                button.setForeground(ColorScheme.getColor(ColorScheme.SECONDARY));
                button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.SECONDARY), 5));
                
                // Remove old mouse listeners
                removeCustomMouseListeners(button);
                
                // Add new mouse listener
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setForeground(ColorScheme.getColor(ColorScheme.WARNING));
                        button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.WARNING), 5));
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setForeground(ColorScheme.getColor(ColorScheme.SECONDARY));
                        button.setBorder(BorderFactory.createLineBorder(ColorScheme.getColor(ColorScheme.SECONDARY), 5));
                    }
                });
            } else {
                // Regular JButton
                button.setForeground(ColorScheme.getColor(ColorScheme.TEXT_PRIMARY));
                button.setBackground(ColorScheme.getColor(ColorScheme.BUTTON_NORMAL));
                
                // If button has a border and it's a LineBorder, update border color
                if (button.getBorder() instanceof javax.swing.border.LineBorder) {
                    Color borderColor = ColorScheme.getColor(ColorScheme.BORDER_PRIMARY);
                    int thickness = ((javax.swing.border.LineBorder)button.getBorder()).getThickness();
                    button.setBorder(BorderFactory.createLineBorder(borderColor, thickness));
                }
                
                // Remove old mouse listeners
                removeCustomMouseListeners(button);
                
                // Add new mouse listener
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setForeground(ColorScheme.getColor(ColorScheme.WARNING));
                        if (button.getBorder() instanceof javax.swing.border.LineBorder) {
                            int thickness = ((javax.swing.border.LineBorder)button.getBorder()).getThickness();
                            button.setBorder(BorderFactory.createLineBorder(
                                ColorScheme.getColor(ColorScheme.WARNING), thickness));
                        }
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setForeground(ColorScheme.getColor(ColorScheme.TEXT_PRIMARY));
                        if (button.getBorder() instanceof javax.swing.border.LineBorder) {
                            int thickness = ((javax.swing.border.LineBorder)button.getBorder()).getThickness();
                            button.setBorder(BorderFactory.createLineBorder(
                                ColorScheme.getColor(ColorScheme.BORDER_PRIMARY), thickness));
                        }
                    }
                });
            }
        }
        
        // Force button repaint
        button.revalidate();
        button.repaint();
    }
    
    /**
     * Remove all non-system mouse listeners
     * @param component Component to process
     */
    private void removeCustomMouseListeners(Component component) {
        MouseListener[] listeners = component.getMouseListeners();
        for (MouseListener listener : listeners) {
            if (listener.getClass().getEnclosingClass() == MainFrame.class ||
                (listener.getClass().getName() != null && 
                !listener.getClass().getName().startsWith("javax.swing") && 
                !listener.getClass().getName().startsWith("java.awt"))) {
                component.removeMouseListener(listener);
            }
        }
    }
    
    /**
     * End the session
     */
    private void endSession() {
        // Create dialog
        JDialog scoreDialog = new JDialog(this, "Session Ended", true);
        scoreDialog.setLayout(new BorderLayout(10, 10));
        scoreDialog.setSize(400, 220);
        scoreDialog.setLocationRelativeTo(this);
        
        // Create message panel
        JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Add score information
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>You scored " + currentScore + " points.<br>Goodbye!</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        messageLabel.setForeground(ColorScheme.getColor(ColorScheme.SUCCESS));
        
        // Confirm button
        KidButton okButton = new KidButton("OK");
        okButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        okButton.addActionListener(e -> {
            scoreDialog.dispose();
            System.exit(0);
        });
        
        // Add keyboard enter listener
        scoreDialog.getRootPane().setDefaultButton(okButton);
        
        // Assemble panel
        messagePanel.add(messageLabel, BorderLayout.CENTER);
        messagePanel.add(okButton, BorderLayout.SOUTH);
        
        scoreDialog.add(messagePanel);
        scoreDialog.setVisible(true);
        System.exit(0);
    }
    
    /**
     * Update the score
     */
    public void updateScore() {
        currentScore = User.getScores(); // Directly set the score instead of accumulating
        updateProgress();
    }
    
    /**
     * Update the progress bar
     */
    private void updateProgress() {
        // Simply assume 100 points is the maximum score
        int progress = Math.min(currentScore, 100);
        progressBar.setValue(progress);
        progressBar.setString("Current Score: " + progress);
    }
    
    /**
     * Update the task status panel
     */
    private void updateTaskStatusPanel() {
        taskStatusPanel.removeAll();
        if (!currentTaskStatus.isEmpty()) {
            for (java.util.Map.Entry<String, Boolean> entry : currentTaskStatus.entrySet()) {
                String taskName = entry.getKey();
                boolean done = entry.getValue();
                TaskStatusButton taskButton = new TaskStatusButton(taskName);
                
                // Set button color
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
    
    /**
     * Update task status with task name and completion status
     * @param taskName The name of the task
     * @param done Whether the task is completed
     */
    public void updateTaskStatus(String taskName, boolean done) {
        this.currentTaskStatus.put(taskName, done);
        updateTaskStatusPanel();
    }
    
    /**
     * Get the current color scheme
     * @return The current color scheme index
     */
    public int getCurrentColorScheme() {
        return ColorScheme.getCurrentScheme();
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
    
    /**
     * Custom panel that displays a background image
     */
    class BackgroundPanel extends JPanel {
        private Image bg;
        private String currentBgPath;
        public BackgroundPanel() {
            setLayout(new BorderLayout());
            // Don't set background image directly, let updateBackground method handle it
        }
        /**
         * Set the background image
         * @param path Path to the image resource
         */
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
     * Initialize the color mode button
     */
    private void initColorModeButton() {
        // Update button to display current color mode name
        colorModeButton.setText(ColorScheme.getSchemeName(ColorScheme.getCurrentScheme()));
        
        // Ensure button uses correct colors
        if (colorModeButton instanceof KidButton) {
            KidButton kidButton = (KidButton) colorModeButton;
            kidButton.setNormalColor(ColorScheme.getColor(ColorScheme.BUTTON_NORMAL));
            kidButton.setHoverColor(ColorScheme.getColor(ColorScheme.BUTTON_HOVER));
            kidButton.setPressedColor(ColorScheme.getColor(ColorScheme.BUTTON_PRESSED));
        }
    }
} 