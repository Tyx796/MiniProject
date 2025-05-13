package com.geometry.resources.task4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * 圆周长计算示意图（已知半径）
 */
public class CByRadius {
    /**
     * 主方法，用于测试绘图功能
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Circumference by Radius");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new CByRadiusPanel());
        frame.setVisible(true);
    }

    /**
     * 圆周长计算面板（已知半径）
     */
    public static class CByRadiusPanel extends JPanel {
        // 圆参数
        private int radius = 5; // 半径
        private double circumference;
        private static final String FONT_NAME = "Comic Sans MS";


        /**
         * 默认构造函数
         */
        public CByRadiusPanel() {
            this.circumference = 2 * 3.14 * radius;
        }

        /**
         * 参数化构造函数
         * @param radius 半径
         */
        public CByRadiusPanel(int radius) {
            this.radius = radius;
            this.circumference = 2 * 3.14 * radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // 设置字体
            Font titleFont = new Font(FONT_NAME, Font.PLAIN, 18);
            Font formulaFont = new Font(FONT_NAME, Font.BOLD, 22);
            Font labelFont = new Font(FONT_NAME, Font.BOLD, 18);

            // 计算绘图区域
            int padding = 50;
            int diagramRadius = 80;
            int centerX = getWidth() / 3 - 50;
            int centerY = getHeight() / 2;

            // 绘制标题
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            String title = "CIRCUMFERENCE OF CIRCLE:";
            g2d.drawString(title, padding, padding);

            // 绘制圆
            int circleX = centerX - diagramRadius;
            int circleY = centerY - diagramRadius;
            int diameter = diagramRadius * 2;
            g2d.setColor(new Color(230, 230, 230)); // 浅灰色填充
            g2d.fill(new Ellipse2D.Double(circleX, circleY, diameter, diameter));
            g2d.setColor(new Color(180, 180, 180)); // 灰色边框
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new Ellipse2D.Double(circleX, circleY, diameter, diameter));

            // 绘制半径线
            g2d.setColor(new Color(70, 130, 180)); // 钢青色
            g2d.setStroke(new BasicStroke(2));
            Line2D radiusLine = new Line2D.Double(centerX, centerY, centerX + diagramRadius, centerY);
            g2d.draw(radiusLine);

            // 半径箭头
            int arrowSize = 8;
            Polygon arrow = new Polygon();
            arrow.addPoint(centerX + diagramRadius, centerY);
            arrow.addPoint(centerX + diagramRadius - arrowSize, centerY - arrowSize);
            arrow.addPoint(centerX + diagramRadius - arrowSize, centerY + arrowSize);
            g2d.fill(arrow);

            // 绘制半径标签
            g2d.setFont(labelFont);
            g2d.setColor(new Color(70, 130, 180)); // 钢青色
            g2d.drawString("RADIUS", centerX + diagramRadius / 2 - 20, centerY - 10);
            g2d.drawString("r", centerX + diagramRadius / 2 + 10, centerY + 25);

            // 绘制公式部分
            int formulaX = centerX + 100;
            int formulaY = centerY - 40;

            // C 箭头和标签
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("CIRCUMFERENCE", formulaX, formulaY);

            // 绘制向下箭头
            g2d.drawLine(formulaX + 100, formulaY + 10, formulaX + 100, formulaY + 30);
            g2d.drawLine(formulaX + 100, formulaY + 30, formulaX + 95, formulaY + 25);
            g2d.drawLine(formulaX + 100, formulaY + 30, formulaX + 105, formulaY + 25);

            // 公式 C = 2 × π × r
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("C", formulaX, formulaY);
            g2d.drawString("=", formulaX + 30, formulaY);
            g2d.drawString("2", formulaX + 60, formulaY);
            g2d.drawString("×", formulaX + 80, formulaY);
            g2d.drawString("π", formulaX + 110, formulaY);
            g2d.drawString("×", formulaX + 140, formulaY);
            g2d.setColor(new Color(70, 130, 180)); // 钢青色
            g2d.drawString("r", formulaX + 170, formulaY);

            // 结果值
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("C", formulaX, formulaY);
            g2d.drawString("=", formulaX + 30, formulaY);
            g2d.drawString("2", formulaX + 60, formulaY);
            g2d.drawString("×", formulaX + 80, formulaY);
            g2d.drawString("π", formulaX + 110, formulaY);
            g2d.drawString("×", formulaX + 140, formulaY);
            g2d.setColor(new Color(70, 130, 180)); // 钢青色
            g2d.drawString(Integer.toString(radius), formulaX + 170, formulaY);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("=", formulaX + 205, formulaY);
            g2d.drawString(String.format("%.2f", circumference), formulaX + 220, formulaY);
        }
    }
}
