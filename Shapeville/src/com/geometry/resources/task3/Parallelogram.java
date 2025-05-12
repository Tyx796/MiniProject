package com.geometry.resources.task3;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * 平行四边形面积计算示意图
 */
public class Parallelogram {
    
    /**
     * 主方法，用于测试绘图功能
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Area of Parallelogram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(new ParallelogramPanel());
        frame.setVisible(true);
    }
    
    /**
     * 平行四边形面积计算面板
     */
    public static class ParallelogramPanel extends JPanel {
        // 平行四边形参数
        private int base = 4; // 底边长
        private int height = 3; // 高度
        private int area; // 面积
        
        /**
         * 默认构造函数
         */
        public ParallelogramPanel() {
            this.area = base * height;
        }
        
        /**
         * 参数化构造函数
         * @param base 底边长
         * @param height 高度
         */
        public ParallelogramPanel(int base, int height) {
            this.base = base;
            this.height = height;
            this.area = base * height;
        }
        
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
            int diagramWidth = 200;
            int diagramHeight = 150;
            int centerX = getWidth() / 2 - 50;
            int centerY = getHeight() / 2;
            
            // 绘制标题
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            String title = "AREA OF PARALLELOGRAM:";
            g2d.drawString(title, padding, padding);
            
            // 绘制平行四边形
            int shearFactor = 40; // 平行四边形的斜切程度
            
            // 计算平行四边形的四个顶点
            int scale = 40; // 缩放因子，使图形更大
            
            int x1 = centerX - diagramWidth/2 + shearFactor;
            int y1 = centerY - diagramHeight/2;
            
            int x2 = centerX + diagramWidth/2 + shearFactor;
            int y2 = y1;
            
            int x3 = centerX + diagramWidth/2;
            int y3 = centerY + diagramHeight/2;
            
            int x4 = centerX - diagramWidth/2;
            int y4 = y3;
            
            // 创建并绘制平行四边形路径
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
            Line2D heightLine = new Line2D.Double(x4 + 20, y4, x4 + 20, y1);
            g2d.draw(heightLine);
            
            // 高度线上下的小T型线
            int tLength = 10;
            g2d.drawLine(x4 + 20 - tLength, y1, x4 + 20 + tLength, y1);
            g2d.drawLine(x4 + 20 - tLength, y4, x4 + 20 + tLength, y4);
            
            // 绘制底边线
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.setStroke(new BasicStroke(2));
            Line2D baseLine = new Line2D.Double(x4, y4 + 15, x3, y3 + 15);
            g2d.draw(baseLine);
            
            // 底边线左右的小T型线
            g2d.drawLine(x4, y4 + 15 - tLength, x4, y4 + 15 + tLength);
            g2d.drawLine(x3, y3 + 15 - tLength, x3, y3 + 15 + tLength);
            
            // 绘制标签
            g2d.setFont(labelFont);
            
            // HEIGHT 标签
            g2d.setColor(new Color(148, 0, 211)); // 紫色
            g2d.drawString("HEIGHT", x4 - 80, (y1 + y4) / 2);
            
            // BASE 标签
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.drawString("BASE", (x3 + x4) / 2 - 30, y4 + 40);
            
            // 绘制公式部分
            int formulaX = x3 + 80;
            int formulaY = centerY - 20;
            
            // AREA 箭头和标签
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("AREA", formulaX, formulaY);
            
            // 绘制向下箭头
            g2d.drawLine(formulaX + 30, formulaY + 10, formulaX + 30, formulaY + 30);
            g2d.drawLine(formulaX + 30, formulaY + 30, formulaX + 25, formulaY + 25);
            g2d.drawLine(formulaX + 30, formulaY + 30, formulaX + 35, formulaY + 25);
            
            // 公式 A = b × h
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 25, formulaY);
            
            g2d.setColor(new Color(255, 165, 0)); // 橙色底边
            g2d.drawString("b", formulaX + 50, formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("×", formulaX + 75, formulaY);
            
            g2d.setColor(new Color(148, 0, 211)); // 紫色高度
            g2d.drawString("h", formulaX + 100, formulaY);
            
            // 结果值
            formulaY += 40;
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("A", formulaX, formulaY);
            
            g2d.drawString("=", formulaX + 25, formulaY);
            
            g2d.setColor(new Color(255, 165, 0)); // 橙色底边
            g2d.drawString(Integer.toString(base), formulaX + 50, formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("×", formulaX + 75, formulaY);
            
            g2d.setColor(new Color(148, 0, 211)); // 紫色高度
            g2d.drawString(Integer.toString(height), formulaX + 100, formulaY);
            
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("=", formulaX + 125, formulaY);
            
            g2d.drawString(Integer.toString(area), formulaX + 150, formulaY);
            
            // 底边下划线
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.drawLine(formulaX + 50, formulaY + 5, formulaX + 60, formulaY + 5);
            
            // 高度下划线
            g2d.setColor(new Color(148, 0, 211)); // 紫色
            g2d.drawLine(formulaX + 100, formulaY + 5, formulaX + 110, formulaY + 5);
        }
    }
}
