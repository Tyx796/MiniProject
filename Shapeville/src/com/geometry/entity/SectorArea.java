package com.geometry.entity;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 扇形面积数据实体类
 */
public class SectorArea {
    private int questionNumber;      // 题目编号 1-8
    private double correctArea;      // 正确答案
    private String unit;            // 面积单位（cm2, ft2, m2等）
    private String questionImagePath;  // 题目图片路径
    private String answerImagePath;    // 答案图片路径

    public SectorArea(int questionNumber) {
        this.questionNumber = questionNumber;
        this.questionImagePath = "/com/geometry/resources/bonus2/questions/" + questionNumber + ".png";
        this.answerImagePath = "/com/geometry/resources/bonus2/answers/" + questionNumber + ".jpg";
        initializeAnswerAndUnit();
    }

    private void initializeAnswerAndUnit() {
        switch (questionNumber) {
            case 1:
                correctArea = 50.24;
                unit = "cm2";
                break;
            case 2:
                correctArea = 367.38;
                unit = "ft2";
                break;
            case 3:
                correctArea = 755.69;
                unit = "cm2";
                break;
            case 4:
                correctArea = 464.37;
                unit = "ft2";
                break;
            case 5:
                correctArea = 10.68;
                unit = "m2";
                break;
            case 6:
                correctArea = 150.72;
                unit = "in2";
                break;
            case 7:
                correctArea = 351.68;
                unit = "yd2";
                break;
            case 8:
                correctArea = 490.63;
                unit = "mm2";
                break;
        }
    }

    // Getters
    public int getQuestionNumber() { return questionNumber; }
    public double getCorrectArea() { return correctArea; }
    public String getUnit() { return unit; }
    public String getQuestionImagePath() { return questionImagePath; }
    public String getAnswerImagePath() { return answerImagePath; }

    /**
     * 检查答案是否正确（允许0.01的误差）
     */
    public boolean checkAnswer(double userAnswer) {
        return Math.abs(userAnswer - correctArea) < 0.01;
    }

    /**
     * 获取带单位的答案字符串
     */
    public String getFormattedArea() {
        return String.format("%.2f%s", correctArea, unit);
    }

    /**
     * 获取题目图片
     */
    public ImageIcon getQuestionImage() {
        return new ImageIcon(getClass().getResource(questionImagePath));
    }

    /**
     * 获取答案图片
     */
    public ImageIcon getAnswerImage() {
        return new ImageIcon(getClass().getResource(answerImagePath));
    }
}
