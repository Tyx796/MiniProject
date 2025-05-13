package com.geometry.resources.task4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * 圆面积计算示意图（已知直径）
 */
public class AreaByDiameter {
    /**
     * 主方法，用于测试绘图功能
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Area of Circle by Diameter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.add(new AreaByDiameterPanel());
        frame.setVisible(true);
    }

    /**
     * 圆面积计算面板（已知直径）
     */
    public static class AreaByDiameterPanel extends JPanel {
        // 圆参数
        private int diameter = 10; // 直径
        private double area;
        private static final String FONT_NAME = "Comic Sans MS";

        /**
         * 默认构造函数
         */
        public AreaByDiameterPanel() {
            this.area = 3.14 * (diameter / 2.0) * (diameter / 2.0);
        }

        /**
         * 参数化构造函数
         * @param diameter 直径
         */
        public AreaByDiameterPanel(int diameter) {
            this.diameter = diameter;
            this.area = 3.14 * (diameter / 2.0) * (diameter / 2.0);
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
            int centerX = getWidth() / 3 - 50 ;
            int centerY = getHeight() / 2;

            // 绘制标题
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            String title = "AREA OF CIRCLE:";
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
            int formulaX = centerX + 100;
            int formulaY = centerY - 40;

            // AREA 箭头和标签
            g2d.setFont(titleFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("AREA", formulaX, formulaY);

            // 绘制向下箭头
            g2d.drawLine(formulaX + 40, formulaY + 10, formulaX + 40, formulaY + 30);
            g2d.drawLine(formulaX + 40, formulaY + 30, formulaX + 35, formulaY + 25);
            g2d.drawLine(formulaX + 40, formulaY + 30, formulaX + 45, formulaY + 25);

            // 公式 A = π × (d/2)²
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("A", formulaX, formulaY);
            g2d.drawString("=", formulaX + 30, formulaY);
            g2d.drawString("π", formulaX + 60, formulaY);
            g2d.drawString("×", formulaX + 90, formulaY);
            g2d.drawString("(", formulaX + 120, formulaY);
            g2d.setColor(new Color(255, 99, 71)); // 番茄红
            g2d.drawString("d", formulaX + 130, formulaY);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("/2)", formulaX + 150, formulaY);
            g2d.drawString("²", formulaX + 185, formulaY - 10);

            // 结果值
            formulaY += 50;
            g2d.setFont(formulaFont);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("A", formulaX, formulaY);
            g2d.drawString("=", formulaX + 30, formulaY);
            g2d.drawString("π", formulaX + 60, formulaY);
            g2d.drawString("×", formulaX + 90, formulaY);
            g2d.drawString("(", formulaX + 120, formulaY);
            g2d.setColor(new Color(255, 99, 71)); // 番茄红
            g2d.drawString(Integer.toString(diameter), formulaX + 125, formulaY);
            g2d.setColor(new Color(150, 150, 150)); // 灰色
            g2d.drawString("/2)", formulaX + 150, formulaY);
            g2d.drawString("²", formulaX + 185, formulaY - 10);
            g2d.drawString("=", formulaX + 200, formulaY);
            g2d.drawString(String.format("%.2f", area), formulaX + 230, formulaY);
        }
    }
}
