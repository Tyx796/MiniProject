package com.geometry.ui.uiUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer; // Using Swing Timer instead of java.util.Timer

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A custom child-friendly button component with rounded corners and animation effects.
 * Designed specifically for children aged 5-10 to provide an engaging and colorful
 * user interface element that can dynamically adapt to different color schemes.
 */
public class KidButton extends JButton{
    // Default colors from ColorScheme - these will update when color scheme changes
    private Color normal = ColorScheme.getColor(ColorScheme.BUTTON_NORMAL);
    private Color hover = ColorScheme.getColor(ColorScheme.BUTTON_HOVER);
    private Color pressed = ColorScheme.getColor(ColorScheme.BUTTON_PRESSED);
    
    /**
     * Set the normal state color for this button.
     * @param normalColor The color to display when button is in normal state
     */
    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
        repaint();
    }

    /**
     * Set the hover state color for this button.
     * @param hoverColor The color to display when button is being hovered over
     */
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        repaint();
    }

    /**
     * Set the pressed state color for this button.
     * @param pressedColor The color to display when button is being pressed
     */
    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
        repaint();
    }

    // Current colors for different button states - initialized with ColorScheme defaults
    private Color normalColor = normal;
    private Color hoverColor = hover;
    private Color pressedColor = pressed;
    
    // Icon-related fields
    private ImageIcon icon;
    private int iconTextGap = 10; // Space between icon and text
    
    // Animation-related fields
    private float scale = 1.0f;   // Current scale factor for animation
    private boolean enableAnimation = true;
    private Timer animationTimer;  // Timer for controlling animation transitions
    
    // Visual properties
    private int cornerRadius = 20; // Radius for rounded corners
    
    /**
     * Creates a KidButton with the specified text.
     * @param text The text to display on the button
     */
    public KidButton(String text) {
        super(text);
        initButton();
    }
    
    /**
     * Creates a KidButton with the specified text and icon.
     * @param text The text to display on the button
     * @param icon The icon to display on the button
     */
    public KidButton(String text, ImageIcon icon) {
        super(text);
        this.icon = icon;
        initButton();
    }
    
    /**
     * Initialize button properties and appearance.
     * Sets up basic visual properties and event listeners.
     */
    private void initButton() {
        // Basic settings for appearance
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(ColorScheme.getColor(ColorScheme.TEXT_PRIMARY));
        setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        
        // Set up mouse listeners for hover effects
        setupMouseListeners();
    }
    
    /**
     * Set up mouse event listeners for animation effects.
     * Handles mouse enter/exit events to trigger animations.
     */
    private void setupMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (enableAnimation) {
                    startAnimation(1.0f);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (enableAnimation) {
                    startAnimation(1.0f);
                }
            }
        });
    }
    
    /**
     * Start the scaling animation effect.
     * Gradually changes the button's scale factor to create smooth transitions.
     * 
     * @param targetScale The target scale to animate towards
     */
    private void startAnimation(float targetScale) {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        animationTimer = new Timer(20, e -> {
            float step = 0.02f;
            if (Math.abs(scale - targetScale) < step) {
                scale = targetScale;
                repaint();
                animationTimer.stop();
            } else {
                scale += (targetScale > scale) ? step : -step;
                repaint();
            }
        });
        animationTimer.start();
    }
    
    /**
     * Custom painting for the button component.
     * Handles drawing the background, icon, and text with proper styling.
     * 
     * @param g The Graphics context to use for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Update colors to adapt to possible color scheme changes
        updateColors();
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Apply scaling transformation for animation effect
        if (enableAnimation) {
            int width = getWidth();
            int height = getHeight();
            int newWidth = (int)(width * scale);
            int newHeight = (int)(height * scale);
            int x = (width - newWidth) / 2;
            int y = (height - newHeight) / 2;
            g2.translate(x, y);
            g2.scale(scale, scale);
        }
        
        // Determine current background color based on button state
        Color bgColor;
        if (getModel().isPressed()) {
            bgColor = pressedColor;
        } else if (getModel().isRollover()) {
            bgColor = hoverColor;
        } else {
            bgColor = normalColor;
        }
        
        // Draw rounded rectangle background
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        // Draw inner shadow effect
        g2.setColor(new Color(0, 0, 0, 20));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);
        
        // Draw icon if present
        if (icon != null) {
            int iconX = (getWidth() - (icon.getIconWidth() + getTextWidth() + iconTextGap)) / 2;
            int iconY = (getHeight() - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g2, iconX, iconY);
        }
        
        // Draw text using parent implementation (with icon offset consideration)
        super.paintComponent(g2);
        g2.dispose();
    }
    
    /**
     * Update colors to match current ColorScheme.
     * This ensures the button adapts to colorblind modes when they're activated.
     */
    private void updateColors() {
        // If custom colors haven't been set, use ColorScheme defaults
        if (normalColor.equals(normal)) {
            normalColor = ColorScheme.getColor(ColorScheme.BUTTON_NORMAL);
        }
        if (hoverColor.equals(hover)) {
            hoverColor = ColorScheme.getColor(ColorScheme.BUTTON_HOVER);
        }
        if (pressedColor.equals(pressed)) {
            pressedColor = ColorScheme.getColor(ColorScheme.BUTTON_PRESSED);
        }
        
        // Update text color from ColorScheme
        setForeground(ColorScheme.getColor(ColorScheme.TEXT_PRIMARY));
    }
    
    /**
     * Calculate the width of the button text.
     * @return The width of the text in pixels
     */
    private int getTextWidth() {
        return getFontMetrics(getFont()).stringWidth(getText());
    }
    
    /**
     * Custom border painting (disabled).
     * This button uses custom rounded corners instead of standard borders.
     * 
     * @param g The Graphics context
     */
    @Override
    protected void paintBorder(Graphics g) {
        // No border - using custom rounded rectangle instead
    }
}
