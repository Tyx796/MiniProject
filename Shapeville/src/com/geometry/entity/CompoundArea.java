package com.geometry.entity;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 复合图形数据实体类
 */
public class CompoundArea {
    private int questionNumber;  // 题目编号 1-9
    private Map<String, Integer> dimensions;  // 存储图形的各个尺寸
    // private static List<Double> correctArea;  // 正确答案
    private double correctArea;
    private String unit;        // 面积单位（cm2或m2）
    private String formula;     // 计算公式
    private String questionImagePath;  // 题目图片路径
    private String answerImagePath;   // 答案图片路径


    public CompoundArea(int questionNumber) {
        this.questionNumber = questionNumber;
        this.dimensions = new HashMap<>();
        this.questionImagePath = "/com/geometry/resources/bonus1/questions/" + questionNumber + ".png";
        this.answerImagePath = "/com/geometry/resources/bonus1/answers/answer" + questionNumber + ".jpg";
        initializeDimensions();
        initializeAnswerAndUnit();
    }

    private void initializeDimensions() {
        switch (questionNumber) {
            case 1:
                dimensions.put("length", 8);
                dimensions.put("width", 4);
                dimensions.put("height", 6);
                formula = "Area = (length × width) + (width × height)";
                break;
            case 2:
                dimensions.put("base", 10);
                dimensions.put("height", 6);
                dimensions.put("top", 4);
                formula = "Area = base × height - (base - top) × height/2";
                break;
            case 3:
                dimensions.put("length", 8);
                dimensions.put("width", 4);
                dimensions.put("small_width", 2);
                dimensions.put("small_height", 2);
                formula = "Area = length × width - small_width × small_height";
                break;
            case 4:
                dimensions.put("length", 10);
                dimensions.put("width", 6);
                dimensions.put("small_length", 4);
                formula = "Area = length × width - small_length × width";
                break;
            case 5:
                dimensions.put("base", 8);
                dimensions.put("height", 6);
                dimensions.put("small_base", 4);
                formula = "Area = (base × height) - (small_base × height/2)";
                break;
            case 6:
                dimensions.put("length", 8);
                dimensions.put("width", 6);
                dimensions.put("small_width", 2);
                formula = "Area = length × width + (width × small_width)";
                break;
            case 7:
                dimensions.put("length", 10);
                dimensions.put("width", 6);
                dimensions.put("small_length", 4);
                dimensions.put("small_width", 2);
                formula = "Area = (length × width) + (small_length × small_width)";
                break;
            case 8:
                dimensions.put("length", 8);
                dimensions.put("width", 6);
                dimensions.put("small_length", 4);
                formula = "Area = (length × width) + (small_length × width)";
                break;
            case 9:
                dimensions.put("outer_length", 10);
                dimensions.put("inner_length", 6);
                dimensions.put("width", 4);
                formula = "Area = (outer_length × width) - (inner_length × width)";
                break;
        }
    }

    private void initializeAnswerAndUnit() {
        switch (questionNumber) {
            case 1:
                correctArea = 213.5;
                unit = "cm2";
                break;
            case 2:
                correctArea = 331.0;
                unit = "cm2";
                break;
            case 3:
                correctArea = 216.0;
                unit = "m2";
                break;
            case 4:
                correctArea = 216.0;
                unit = "m2";
                break;
            case 5:
                correctArea = 216.0;
                unit = "m2";
                break;
            case 6:
                correctArea = 216.0;
                unit = "m2";
                break;
            case 7:
                correctArea = 3456.0;
                unit = "m2";
                break;
            case 8:
                correctArea = 174.0;
                unit = "m2";
                break;
            case 9:
                correctArea = 174.0;
                unit = "m2";
                break;
        }
    }

    private void calculateArea() {
        switch (questionNumber) {
            case 1:
                correctArea = (dimensions.get("length") * dimensions.get("width")) +
                            (dimensions.get("width") * dimensions.get("height"));
                break;
            case 2:
                double trapezoidArea = dimensions.get("base") * dimensions.get("height") -
                                     (dimensions.get("base") - dimensions.get("top")) * dimensions.get("height") / 2.0;
                correctArea = trapezoidArea;
                break;
            case 3:
                correctArea = dimensions.get("length") * dimensions.get("width") -
                            dimensions.get("small_width") * dimensions.get("small_height");
                break;
            case 4:
                correctArea = dimensions.get("length") * dimensions.get("width") -
                            dimensions.get("small_length") * dimensions.get("width");
                break;
            case 5:
                correctArea = (dimensions.get("base") * dimensions.get("height")) -
                            (dimensions.get("small_base") * dimensions.get("height") / 2.0);
                break;
            case 6:
                correctArea = dimensions.get("length") * dimensions.get("width") +
                            (dimensions.get("width") * dimensions.get("small_width"));
                break;
            case 7:
                correctArea = (dimensions.get("length") * dimensions.get("width")) +
                            (dimensions.get("small_length") * dimensions.get("small_width"));
                break;
            case 8:
                correctArea = (dimensions.get("length") * dimensions.get("width")) +
                            (dimensions.get("small_length") * dimensions.get("width"));
                break;
            case 9:
                correctArea = (dimensions.get("outer_length") * dimensions.get("width")) -
                            (dimensions.get("inner_length") * dimensions.get("width"));
                break;
        }
    }

    // Getters
    public int getQuestionNumber() { return questionNumber; }
    public Map<String, Integer> getDimensions() { return dimensions; }
    public double getCorrectArea() { return correctArea; }
    public String getUnit() { return unit; }
    public String getFormula() { return formula; }
    public String getQuestionImagePath() { return questionImagePath; }
    public String getAnswerImagePath() { return answerImagePath; }

    /**
     * 检查答案是否正确（允许0.1的误差）
     */
    public boolean checkAnswer(double userAnswer) {
        return Math.abs(userAnswer - correctArea) < 0.1;
    }

    /**
     * 获取带单位的答案字符串
     */
    public String getFormattedArea() {
        return String.format("%.1f%s", correctArea, unit);
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
