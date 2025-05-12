package com.geometry.ui.uiUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class TaskStatusButton extends JButton{
    // color: rgb(197, 197, 193)
    private Color normal = new Color(197, 197, 193);
    private Color hover = new Color(255, 140, 150);
    private Color pressed = new Color(230, 80, 100);
    
    
    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    // 当前颜色状态
    private Color normalColor = normal;
    private Color hoverColor = hover;
    private Color pressedColor = pressed;
    
    // 图标相关
    private ImageIcon icon;
    private int iconTextGap = 10;
    
    // 动画相关
    private float scale = 1.0f;
    private boolean enableAnimation = true;
    
    // 圆角半径
    private int cornerRadius = 20;
    
    public TaskStatusButton(String text) {
        super(text);
        initButton();
    }
    
    public TaskStatusButton(String text, ImageIcon icon) {
        super(text);
        this.icon = icon;
        initButton();
    }
    
    private void initButton() {
        // 基础设置
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        
    }
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 应用缩放变换
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
        
        
        // 绘制圆角背景
        g2.setColor(normalColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        // 绘制内阴影
        g2.setColor(new Color(0, 0, 0, 20));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);
        
        // 绘制图标
        if (icon != null) {
            int iconX = (getWidth() - (icon.getIconWidth() + getTextWidth() + iconTextGap)) / 2;
            int iconY = (getHeight() - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g2, iconX, iconY);
        }
        
        // 绘制文本（考虑图标偏移）
        super.paintComponent(g2);
        g2.dispose();
    }
    
    private int getTextWidth() {
        return getFontMetrics(getFont()).stringWidth(getText());
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        // 无边框
    }
}
