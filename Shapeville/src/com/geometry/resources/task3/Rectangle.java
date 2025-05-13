package com.geometry.resources.task3;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * 矩形面积计算示意图
 */
public class Rectangle {
    
    /**
     * 主方法，用于测试绘图功能
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Area of Rectangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new RectanglePanel());
        frame.setVisible(true);
    }
    
    /**
     * 矩形面积计算面板
     */
    public static class RectanglePanel extends JPanel {
        // 矩形参数
        private int length = 5;    // 长度(l)
        private int width = 4;     // 宽度(w)
        private int area;          // 面积
        
        /**
         * 默认构造函数
         */
        public RectanglePanel() {
            this.area = length * width;
        }
        
        /**
         * 参数化构造函数
         * @param length 长度
         * @param width 宽度
         */
        public RectanglePanel(int length, int width) {
            this.length = length;
            this.width = width;
            this.area = length * width;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // 设置字体
            Font titleFont = new Font("Comic Sans MS", Font.PLAIN, 18);
            Font formulaFont = new Font("Comic Sans MS", Font.BOLD, 22);
            Font labelFont = new Font("Comic Sans MS", Font.BOLD, 18);
            
            // 计算绘图区域
            int padding = 50;
            int diagramWidth = 200;
            int diagramHeight = 160;
            int centerX = getWidth() / 3;
            int centerY = getHeight() / 2;
            
            // 绘制标题
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            String title = "AREA OF RECTANGLE:";
            g2d.drawString(title, padding, padding);
            
            // 绘制矩形
            int rectX = centerX - diagramWidth / 2;
            int rectY = centerY - diagramHeight / 2;
            
            // 创建并绘制矩形
            g2d.setColor(new Color(230, 230, 230)); // 浅灰色填充
            g2d.fillRect(rectX, rectY, diagramWidth, diagramHeight);
            g2d.setColor(new Color(180, 180, 180)); // 灰色边框
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(rectX, rectY, diagramWidth, diagramHeight);
            
            // 绘制宽度线
            g2d.setColor(new Color(56, 176, 170)); // 青绿色
            g2d.setStroke(new BasicStroke(2));
            Line2D widthLine = new Line2D.Double(rectX - 20, rectY, rectX - 20, rectY + diagramHeight);
            g2d.draw(widthLine);
            
            // 宽度线箭头
            int arrowSize = 8;
            // 上箭头
            Polygon upArrow = new Polygon();
            upArrow.addPoint(rectX - 20, rectY);
            upArrow.addPoint(rectX - 20 - arrowSize, rectY + arrowSize * 2);
            upArrow.addPoint(rectX - 20 + arrowSize, rectY + arrowSize * 2);
            g2d.fill(upArrow);
            
            // 下箭头
            Polygon downArrow = new Polygon();
            downArrow.addPoint(rectX - 20, rectY + diagramHeight);
            downArrow.addPoint(rectX - 20 - arrowSize, rectY + diagramHeight - arrowSize * 2);
            downArrow.addPoint(rectX - 20 + arrowSize, rectY + diagramHeight - arrowSize * 2);
            g2d.fill(downArrow);
            
            // 绘制长度线
            g2d.setColor(new Color(144, 238, 144)); // 浅绿色
            g2d.setStroke(new BasicStroke(2));
            Line2D lengthLine = new Line2D.Double(rectX, rectY + diagramHeight + 20, 
                                                 rectX + diagramWidth, rectY + diagramHeight + 20);
            g2d.draw(lengthLine);
            
            // 长度线箭头
            // 左箭头
            Polygon leftArrow = new Polygon();
            leftArrow.addPoint(rectX, rectY + diagramHeight + 20);
            leftArrow.addPoint(rectX + arrowSize * 2, rectY + diagramHeight + 20 - arrowSize);
            leftArrow.addPoint(rectX + arrowSize * 2, rectY + diagramHeight + 20 + arrowSize);
            g2d.fill(leftArrow);
            
            // 右箭头
            Polygon rightArrow = new Polygon();
            rightArrow.addPoint(rectX + diagramWidth, rectY + diagramHeight + 20);
            rightArrow.addPoint(rectX + diagramWidth - arrowSize * 2, rectY + diagramHeight + 20 - arrowSize);
            rightArrow.addPoint(rectX + diagramWidth - arrowSize * 2, rectY + diagramHeight + 20 + arrowSize);
            g2d.fill(rightArrow);
            
            // 绘制标签
            g2d.setFont(labelFont);
            
            // WIDTH 标签
            g2d.setColor(new Color(56, 176, 170)); // 青绿色
            g2d.drawString("WIDTH", rectX - 80, centerY);
            
            // LENGTH 标签
            g2d.setColor(new Color(144, 238, 144)); // 浅绿色
            g2d.drawString("LENGTH", centerX - 40, rectY + diagramHeight + 50);
            
            // 绘制公式部分
            int formulaX = centerX + 220;
            int formulaY = centerY - 50;
            
            // AREA 箭头和标签
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("AREA", formulaX - 20, formulaY);
            
            // 绘制向下箭头
            g2d.drawLine(formulaX, formulaY + 10, formulaX, formulaY + 30);
            g2d.drawLine(formulaX, formulaY + 30, formulaX - 5, formulaY + 25);
            g2d.drawLine(formulaX, formulaY + 30, formulaX + 5, formulaY + 25);
            
            // 公式 A = L × W
            formulaY += 50;
            formulaX -= 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("A", formulaX - 20, formulaY);
            
            g2d.drawString("=", formulaX, formulaY);
            
            g2d.setColor(new Color(144, 238, 144)); // 浅绿色，长度
            g2d.drawString("L", formulaX + 30, formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("×", formulaX + 50, formulaY);
            
            g2d.setColor(new Color(56, 176, 170)); // 青绿色，宽度
            g2d.drawString("W", formulaX + 75, formulaY);
            
           /*  // 长度向上箭头
            Polygon lArrow = new Polygon();
            int lArrowX = formulaX + 38;
            int lArrowY = formulaY + 13;
            lArrow.addPoint(lArrowX, lArrowY);
            lArrow.addPoint(lArrowX - arrowSize, lArrowY + arrowSize * 2);
            lArrow.addPoint(lArrowX + arrowSize, lArrowY + arrowSize * 2);
            g2d.setColor(new Color(144, 238, 144)); // 浅绿色
            g2d.fill(lArrow);
            
            // 宽度向上箭头
            Polygon wArrow = new Polygon();
            int wArrowX = formulaX + 85;
            int wArrowY = formulaY + 13;
            wArrow.addPoint(wArrowX, wArrowY);
            wArrow.addPoint(wArrowX - arrowSize, wArrowY + arrowSize * 2);
            wArrow.addPoint(wArrowX + arrowSize, wArrowY + arrowSize * 2);
            g2d.setColor(new Color(56, 176, 170)); // 青绿色
            g2d.fill(wArrow); */
            
            // 结果值
            formulaY += 50;
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("A", formulaX - 20, formulaY);
            
            g2d.drawString("=", formulaX, formulaY);
            
            g2d.setColor(new Color(144, 238, 144)); // 浅绿色，长度
            String lengthStr = Integer.toString(length);
            int lengthWidth = g2d.getFontMetrics().stringWidth(lengthStr);
            g2d.drawString(lengthStr, formulaX + 25 + (15 - lengthWidth/2), formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("×", formulaX + 50, formulaY);
            
            g2d.setColor(new Color(56, 176, 170)); // 青绿色，宽度
            g2d.drawString(Integer.toString(width), formulaX + 75, formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("=", formulaX + 100, formulaY);
            
            g2d.drawString(Integer.toString(area), formulaX + 130, formulaY);
        }
    }
}
