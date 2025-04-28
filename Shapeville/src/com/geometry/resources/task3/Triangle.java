package com.geometry.resources.task3;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * 三角形面积计算示意图
 */
public class Triangle {
    
    /**
     * 主方法，用于测试绘图功能
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Area of Triangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new TrianglePanel());
        frame.setVisible(true);
    }
    
    /**
     * 三角形面积计算面板
     */
    static class TrianglePanel extends JPanel {
        // 三角形参数
        private final int base = 5;     // 底边长(b)
        private final int height = 3;   // 高度(h)
        private final double area = base * height / 2.0; // 面积
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // 设置字体
            Font titleFont = new Font("Arial", Font.PLAIN, 18);
            Font formulaFont = new Font("Arial", Font.BOLD, 22);
            Font labelFont = new Font("Arial", Font.BOLD, 18);
            
            // 计算绘图区域
            int padding = 50;
            int diagramWidth = 220;
            int diagramHeight = 160;
            int centerX = getWidth() / 3;
            int centerY = getHeight() / 2;
            
            // 绘制标题
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            String title = "AREA OF TRIANGLE:";
            g2d.drawString(title, padding, padding);
            
            // 计算三角形的三个顶点
            int x1 = centerX - diagramWidth / 2;
            int y1 = centerY + diagramHeight / 2;
            
            int x2 = centerX + diagramWidth / 2;
            int y2 = y1;
            
            int x3 = centerX;
            int y3 = centerY - diagramHeight / 2;
            
            // 创建并绘制三角形路径
            Path2D path = new Path2D.Double();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x3, y3);
            path.closePath();
            
            g2d.setColor(new Color(230, 230, 230)); // 浅灰色填充
            g2d.fill(path);
            g2d.setColor(new Color(180, 180, 180)); // 灰色边框
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(path);
            
            // 绘制高度线
            g2d.setColor(new Color(148, 0, 211)); // 紫色
            g2d.setStroke(new BasicStroke(2));
            Line2D heightLine = new Line2D.Double(x3, y3, x3, y1);
            g2d.draw(heightLine);
            
            // 高度线箭头
            int arrowSize = 8;
            // 上箭头
            Polygon upArrow = new Polygon();
            upArrow.addPoint(x3, y3);
            upArrow.addPoint(x3 - arrowSize, y3 + arrowSize * 2);
            upArrow.addPoint(x3 + arrowSize, y3 + arrowSize * 2);
            g2d.fill(upArrow);
            
            // 下箭头
            Polygon downArrow = new Polygon();
            downArrow.addPoint(x3, y1);
            downArrow.addPoint(x3 - arrowSize, y1 - arrowSize * 2);
            downArrow.addPoint(x3 + arrowSize, y1 - arrowSize * 2);
            g2d.fill(downArrow);
            
            // 绘制底边线
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.setStroke(new BasicStroke(2));
            Line2D baseLine = new Line2D.Double(x1, y1 + 15, x2, y2 + 15);
            g2d.draw(baseLine);
            
            // 底边线箭头
            // 左箭头
            Polygon leftArrow = new Polygon();
            leftArrow.addPoint(x1, y1 + 15);
            leftArrow.addPoint(x1 + arrowSize * 2, y1 + 15 - arrowSize);
            leftArrow.addPoint(x1 + arrowSize * 2, y1 + 15 + arrowSize);
            g2d.fill(leftArrow);
            
            // 右箭头
            Polygon rightArrow = new Polygon();
            rightArrow.addPoint(x2, y2 + 15);
            rightArrow.addPoint(x2 - arrowSize * 2, y2 + 15 - arrowSize);
            rightArrow.addPoint(x2 - arrowSize * 2, y2 + 15 + arrowSize);
            g2d.fill(rightArrow);
            
            // 绘制标签
            g2d.setFont(labelFont);
            
            // HEIGHT 标签
            g2d.setColor(new Color(148, 0, 211)); // 紫色
            g2d.drawString("HEIGHT", x3 + 10, (y1 + y3) / 2);
            
            // BASE 标签
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.drawString("BASE", centerX - 20, y1 + 40);
            
            // 绘制公式部分
            int formulaX = centerX + 180;
            int formulaY = centerY - 50;
            
            // AREA 箭头和标签
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("AREA", formulaX, formulaY);
            
            // 绘制向下箭头
            g2d.drawLine(formulaX + 30, formulaY + 10, formulaX + 30, formulaY + 30);
            g2d.drawLine(formulaX + 30, formulaY + 30, formulaX + 25, formulaY + 25);
            g2d.drawLine(formulaX + 30, formulaY + 30, formulaX + 35, formulaY + 25);
            
            // 公式 A = 1/2 × b × h
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 20, formulaY);
            
            // 分数 1/2
            g2d.drawString("1", formulaX + 45, formulaY - 10);
            g2d.drawLine(formulaX + 40, formulaY, formulaX + 55, formulaY);
            g2d.drawString("2", formulaX + 45, formulaY + 20);
            
            // ×
            g2d.drawString("×", formulaX + 65, formulaY);
            
            // b
            g2d.setColor(new Color(255, 165, 0)); // 橙色底边
            g2d.drawString("b", formulaX + 90, formulaY);
            
            // ×
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("×", formulaX + 115, formulaY);
            
            // h
            g2d.setColor(new Color(148, 0, 211)); // 紫色高度
            g2d.drawString("h", formulaX + 140, formulaY);
            
            // 高度h的下划线
            g2d.drawLine(formulaX + 140, formulaY + 5, formulaX + 150, formulaY + 5);
            
            // 底边向上箭头
            int bArrowX = formulaX + 90;
            int bArrowY = formulaY - 25;
            Polygon bArrow = new Polygon();
            bArrow.addPoint(bArrowX, bArrowY);
            bArrow.addPoint(bArrowX - arrowSize, bArrowY + arrowSize * 2);
            bArrow.addPoint(bArrowX + arrowSize, bArrowY + arrowSize * 2);
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.fill(bArrow);
            
            // 结果值
            formulaY += 50;
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 20, formulaY);
            
            // 分数 1/2
            g2d.drawString("1", formulaX + 45, formulaY - 10);
            g2d.drawLine(formulaX + 40, formulaY, formulaX + 55, formulaY);
            g2d.drawString("2", formulaX + 45, formulaY + 20);
            
            // ×
            g2d.drawString("×", formulaX + 65, formulaY);
            
            // base值
            g2d.setColor(new Color(255, 165, 0)); // 橙色底边
            g2d.drawString(Integer.toString(base), formulaX + 85, formulaY);
            
            // ×
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("×", formulaX + 100, formulaY);
            
            // height值
            g2d.setColor(new Color(148, 0, 211)); // 紫色高度
            g2d.drawString(Integer.toString(height), formulaX + 120, formulaY);
            
            // =
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("=", formulaX + 140, formulaY);
            
            // 面积结果
            g2d.drawString(Double.toString(area), formulaX + 160, formulaY);
        }
    }
}
