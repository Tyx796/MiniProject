package com.geometry.resources.task4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * 圆周长计算示意图（已知直径）
 */
public class CByDiameter {
    /**
     * 主方法，用于测试绘图功能
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Circumference by Diameter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new CByDiameterPanel());
        frame.setVisible(true);
    }

    /**
     * 圆周长计算面板（已知直径）
     */
    public static class CByDiameterPanel extends JPanel {
        // 圆参数
        private int diameter = 10; // 直径
        private double circumference;

        /**
         * 默认构造函数
         */
        public CByDiameterPanel() {
            this.circumference = 3.14 * diameter;
        }

        /**
         * 参数化构造函数
         * @param diameter 直径
         */
        public CByDiameterPanel(int diameter) {
            this.diameter = diameter;
            this.circumference = 3.14 * diameter;
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
            int diagramRadius = 80;
            int centerX = getWidth() / 3;
            int centerY = getHeight() / 2;

            // 绘制标题
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            String title = "CIRCUMFERENCE OF CIRCLE:";
            g2d.drawString(title, padding, padding);

            // 绘制圆
            int circleX = centerX - diagramRadius;
            int circleY = centerY - diagramRadius;
            int d = diagramRadius * 2;
            g2d.setColor(new Color(230, 230, 230)); // 浅灰色填充
            g2d.fill(new Ellipse2D.Double(circleX, circleY, d, d));
            g2d.setColor(new Color(180, 180, 180)); // 灰色边框
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new Ellipse2D.Double(circleX, circleY, d, d));

            // 绘制直径线
            g2d.setColor(new Color(255, 99, 71)); // 番茄红
            g2d.setStroke(new BasicStroke(2));
            Line2D diameterLine = new Line2D.Double(centerX - diagramRadius, centerY, centerX + diagramRadius, centerY);
            g2d.draw(diameterLine);

            // 直径两端箭头
            int arrowSize = 8;
            Polygon leftArrow = new Polygon();
            leftArrow.addPoint(centerX - diagramRadius, centerY);
            leftArrow.addPoint(centerX - diagramRadius + arrowSize, centerY - arrowSize);
            leftArrow.addPoint(centerX - diagramRadius + arrowSize, centerY + arrowSize);
            g2d.fill(leftArrow);
            Polygon rightArrow = new Polygon();
            rightArrow.addPoint(centerX + diagramRadius, centerY);
            rightArrow.addPoint(centerX + diagramRadius - arrowSize, centerY - arrowSize);
            rightArrow.addPoint(centerX + diagramRadius - arrowSize, centerY + arrowSize);
            g2d.fill(rightArrow);

            // 绘制直径标签
            g2d.setFont(labelFont);
            g2d.setColor(new Color(255, 99, 71)); // 番茄红
            g2d.drawString("DIAMETER", centerX - 40, centerY - 15);
            g2d.drawString("d", centerX - 10, centerY + 30);

            // 绘制公式部分
            int formulaX = centerX + 180;
            int formulaY = centerY - 40;

            // C 箭头和标签
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("CIRCUMFERENCE", formulaX, formulaY);

            // 绘制向下箭头
            g2d.drawLine(formulaX + 100, formulaY + 10, formulaX + 100, formulaY + 30);
            g2d.drawLine(formulaX + 100, formulaY + 30, formulaX + 95, formulaY + 25);
            g2d.drawLine(formulaX + 100, formulaY + 30, formulaX + 105, formulaY + 25);

            // 公式 C = π × d
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("C", formulaX, formulaY);
            g2d.drawString("=", formulaX + 30, formulaY);
            g2d.drawString("π", formulaX + 60, formulaY);
            g2d.drawString("×", formulaX + 90, formulaY);
            g2d.setColor(new Color(255, 99, 71)); // 番茄红
            g2d.drawString("d", formulaX + 120, formulaY);

            // 结果值
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("C", formulaX, formulaY);
            g2d.drawString("=", formulaX + 30, formulaY);
            g2d.drawString("π", formulaX + 60, formulaY);
            g2d.drawString("×", formulaX + 90, formulaY);
            g2d.setColor(new Color(255, 99, 71)); // 番茄红
            g2d.drawString(Integer.toString(diameter), formulaX + 120, formulaY);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("=", formulaX + 150, formulaY);
            g2d.drawString(String.format("%.2f", circumference), formulaX + 170, formulaY);
        }
    }
}
