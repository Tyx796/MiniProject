package com.geometry.resources.task3;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * 梯形面积计算示意图
 */
public class Trapezium {
    
    /**
     * 主方法，用于测试绘图功能
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Area of Trapezium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new TrapeziumPanel());
        frame.setVisible(true);
    }
    
    /**
     * 梯形面积计算面板
     */
    static class TrapeziumPanel extends JPanel {
        // 梯形参数
        private final int topLength = 3;      // 上底长(a)
        private final int bottomLength = 4;   // 下底长(b)
        private final int height = 3;         // 高度(h)
        private final double area = (topLength + bottomLength) * height / 2.0; // 面积
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // 设置字体
            Font titleFont = new Font("Arial", Font.PLAIN, 18);
            Font formulaFont = new Font("Arial", Font.BOLD, 20);
            Font labelFont = new Font("Arial", Font.BOLD, 16);
            
            // 计算绘图区域
            int padding = 50;
            int diagramWidth = 200;
            int diagramHeight = 120;
            int centerX = getWidth() / 3;
            int centerY = getHeight() / 2;
            
            // 绘制标题
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            String title = "AREA OF TRAPEZIUM:";
            g2d.drawString(title, padding, padding);
            
            // 绘制梯形
            // 缩放因子，使图形更大
            double scale = 40;
            
            // 计算梯形的四个顶点
            double topOffset = (bottomLength - topLength) * scale / 2;
            
            int x1 = (int) (centerX - bottomLength * scale / 2 + topOffset);
            int y1 = centerY - diagramHeight / 2;
            
            int x2 = (int) (centerX + bottomLength * scale / 2 - topOffset);
            int y2 = y1;
            
            int x3 = (int) (centerX + bottomLength * scale / 2);
            int y3 = centerY + diagramHeight / 2;
            
            int x4 = (int) (centerX - bottomLength * scale / 2);
            int y4 = y3;
            
            // 创建并绘制梯形路径
            Path2D path = new Path2D.Double();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x3, y3);
            path.lineTo(x4, y4);
            path.closePath();
            
            g2d.setColor(new Color(230, 230, 230)); // 浅灰色填充
            g2d.fill(path);
            g2d.setColor(new Color(180, 180, 180)); // 灰色边框
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(path);
            
            // 绘制高度线
            g2d.setColor(new Color(148, 0, 211)); // 紫色
            g2d.setStroke(new BasicStroke(2));
            Line2D heightLine = new Line2D.Double(centerX, y1, centerX, y3);
            g2d.draw(heightLine);
            
            // 上下箭头
            int arrowSize = 7;
            Polygon upArrow = new Polygon();
            upArrow.addPoint(centerX, y1);
            upArrow.addPoint(centerX - arrowSize, y1 + arrowSize * 2);
            upArrow.addPoint(centerX + arrowSize, y1 + arrowSize * 2);
            g2d.fill(upArrow);
            
            Polygon downArrow = new Polygon();
            downArrow.addPoint(centerX, y3);
            downArrow.addPoint(centerX - arrowSize, y3 - arrowSize * 2);
            downArrow.addPoint(centerX + arrowSize, y3 - arrowSize * 2);
            g2d.fill(downArrow);
            
            // 绘制上底线 (a)
            g2d.setColor(new Color(199, 21, 133)); // 洋红色
            g2d.setStroke(new BasicStroke(2));
            Line2D topLine = new Line2D.Double(x1, y1, x2, y2);
            g2d.draw(topLine);
            
            // 上底箭头
            Polygon leftArrow = new Polygon();
            leftArrow.addPoint(x1, y1);
            leftArrow.addPoint(x1 + arrowSize * 2, y1 - arrowSize);
            leftArrow.addPoint(x1 + arrowSize * 2, y1 + arrowSize);
            g2d.fill(leftArrow);
            
            Polygon rightArrow = new Polygon();
            rightArrow.addPoint(x2, y2);
            rightArrow.addPoint(x2 - arrowSize * 2, y2 - arrowSize);
            rightArrow.addPoint(x2 - arrowSize * 2, y2 + arrowSize);
            g2d.fill(rightArrow);
            
            // 绘制下底线 (b)
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.setStroke(new BasicStroke(2));
            Line2D bottomLine = new Line2D.Double(x4, y4, x3, y3);
            g2d.draw(bottomLine);
            
            // 下底箭头
            leftArrow = new Polygon();
            leftArrow.addPoint(x4, y4);
            leftArrow.addPoint(x4 + arrowSize * 2, y4 - arrowSize);
            leftArrow.addPoint(x4 + arrowSize * 2, y4 + arrowSize);
            g2d.fill(leftArrow);
            
            rightArrow = new Polygon();
            rightArrow.addPoint(x3, y3);
            rightArrow.addPoint(x3 - arrowSize * 2, y3 - arrowSize);
            rightArrow.addPoint(x3 - arrowSize * 2, y3 + arrowSize);
            g2d.fill(rightArrow);
            
            // 绘制标签
            g2d.setFont(labelFont);
            
            // 上底标签
            g2d.setColor(new Color(199, 21, 133)); // 洋红色
            g2d.drawString("LENGTH OF TOP SIDE", x1 - 10, y1 - 25);
            g2d.drawString("a", (x1 + x2) / 2 - 10, y1 - 5);
            
            // 高度标签
            g2d.setColor(new Color(148, 0, 211)); // 紫色
            g2d.drawString("HEIGHT", centerX + 10, (y1 + y3) / 2);
            
            // 下底标签
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.drawString("b", (x3 + x4) / 2 - 5, y3 + 25);
            
            // 绘制公式部分
            int formulaX = centerX + 170;
            int formulaY = centerY - 50;
            
            // AREA 箭头和标签
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("AREA", formulaX, formulaY);
            
            // 绘制向下箭头
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawLine(formulaX + 20, formulaY + 10, formulaX + 20, formulaY + 25);
            g2d.drawLine(formulaX + 20, formulaY + 25, formulaX + 15, formulaY + 20);
            g2d.drawLine(formulaX + 20, formulaY + 25, formulaX + 25, formulaY + 20);
            
            // 公式 A = (a + b)/2 × h
            formulaY += 40;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 25, formulaY);
            
            // 左括号
            g2d.drawString("(", formulaX + 45, formulaY);
            
            // a
            g2d.setColor(new Color(199, 21, 133)); // 洋红色
            g2d.drawString("a", formulaX + 55, formulaY);
            
            // +
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("+", formulaX + 70, formulaY);
            
            // b
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.drawString("b", formulaX + 85, formulaY);
            
            // 右括号
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString(")", formulaX + 95, formulaY);
            
            // 除以2
            int lineY = formulaY + 5;
            g2d.drawLine(formulaX + 45, lineY, formulaX + 95, lineY);
            g2d.drawString("2", formulaX + 70, formulaY + 20);
            
            // ×
            g2d.drawString("×", formulaX + 110, formulaY);
            
            // h
            g2d.setColor(new Color(148, 0, 211)); // 紫色高度
            g2d.drawString("h", formulaX + 130, formulaY);
            
            // 高度下划线
            g2d.drawLine(formulaX + 130, formulaY + 3, formulaX + 138, formulaY + 3);
            
            // 结果值
            formulaY += 50;
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 20, formulaY);
            
            // 左括号
            g2d.drawString("(", formulaX + 35, formulaY);
            
            // a值
            g2d.setColor(new Color(199, 21, 133)); // 洋红色
            g2d.drawString(Integer.toString(topLength), formulaX + 45, formulaY);
            
            // +
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("+", formulaX + 55, formulaY);
            
            // b值
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.drawString(Integer.toString(bottomLength), formulaX + 65, formulaY);
            
            // 右括号
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString(")", formulaX + 75, formulaY);
            
            // 除以2
            lineY = formulaY + 3;
            g2d.drawLine(formulaX + 35, lineY, formulaX + 75, lineY);
            g2d.drawString("2", formulaX + 55, formulaY + 15);
            
            // ×
            g2d.drawString("×", formulaX + 85, formulaY);
            
            // h值
            g2d.setColor(new Color(148, 0, 211)); // 紫色高度
            g2d.drawString(Integer.toString(height), formulaX + 95, formulaY);
            
            // =
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("=", formulaX + 110, formulaY);
            
            // 面积结果
            g2d.drawString(Double.toString(area), formulaX + 125, formulaY);
        }
    }
}
