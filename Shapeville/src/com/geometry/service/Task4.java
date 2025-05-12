package com.geometry.service;

import javax.swing.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import com.geometry.resources.task4.AreaByRadius;
import com.geometry.resources.task4.AreaByDiameter;
import com.geometry.resources.task4.CByRadius;
import com.geometry.resources.task4.CByDiameter;

/**
 * Task4业务逻辑类 - 圆的面积和周长计算
 */
public class Task4 {
    public static final String AREA_RADIUS = "Area by Radius";
    public static final String AREA_DIAMETER = "Area by Diameter";
    public static final String CIRC_RADIUS = "Circumference by Radius";
    public static final String CIRC_DIAMETER = "Circumference by Diameter";

    private static final double PI = 3.14;
    private final Random random = new Random();
    private String currentType;
    private int value; // 半径或直径
    private double answer;
    private int remainingAttempts = 3;
    private final Set<String> completedTypes = new HashSet<>();

    public boolean selectType(String type) {
        currentType = type;
        remainingAttempts = 3;
        // 随机生成参数
        value = random.nextInt(20) + 1;
        // 计算答案
        switch (type) {
            case AREA_RADIUS:
                answer = PI * value * value;
                break;
            case AREA_DIAMETER:
                answer = PI * (value / 2.0) * (value / 2.0);
                break;
            case CIRC_RADIUS:
                answer = 2 * PI * value;
                break;
            case CIRC_DIAMETER:
                answer = PI * value;
                break;
        }
        completedTypes.add(type);
        return true;
    }

    public boolean checkAnswer(double userAnswer) {
        boolean correct = Math.abs(userAnswer - answer) < 0.01;
        if (correct) {
            // 根据剩余尝试次数计算得分
            int attemptsUsed = 3 - remainingAttempts + 1;
            com.geometry.entity.User.addScores("Basic", attemptsUsed);
        } else {
            remainingAttempts--;
        }
        return correct;
    }

    public String getCurrentType() { return currentType; }
    public int getValue() { return value; }
    public double getAnswer() { return answer; }
    public int getRemainingAttempts() { return remainingAttempts; }
    public boolean isAllCompleted() {
        return completedTypes.contains(AREA_RADIUS) && completedTypes.contains(AREA_DIAMETER)
            && completedTypes.contains(CIRC_RADIUS) && completedTypes.contains(CIRC_DIAMETER);
    }
    public void reset() {
        completedTypes.clear();
        remainingAttempts = 3;
        currentType = null;
    }
    public JPanel createDisplayPanel() {
        switch (currentType) {
            case AREA_RADIUS:
                return new AreaByRadius.AreaByRadiusPanel(value);
            case AREA_DIAMETER:
                return new AreaByDiameter.AreaByDiameterPanel(value);
            case CIRC_RADIUS:
                return new CByRadius.CByRadiusPanel(value);
            case CIRC_DIAMETER:
                return new CByDiameter.CByDiameterPanel(value);
            default:
                return new JPanel();
        }
    }
    public String getFormula() {
        switch (currentType) {
            case AREA_RADIUS:
                return "A = π × r²";
            case AREA_DIAMETER:
                return "A = π × (d/2)²";
            case CIRC_RADIUS:
                return "C = 2 × π × r";
            case CIRC_DIAMETER:
                return "C = π × d";
            default:
                return "";
        }
    }
    public String getParamLabel() {
        switch (currentType) {
            case AREA_RADIUS:
            case CIRC_RADIUS:
                return "Radius: ";
            case AREA_DIAMETER:
            case CIRC_DIAMETER:
                return "Diameter: ";
            default:
                return "";
        }
    }
}
