package com.geometry.service;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import com.geometry.resources.task3.Triangle;
import com.geometry.resources.task3.Parallelogram;
import com.geometry.resources.task3.Trapezium;
// 使用精确的Rectangle类引用避免歧义
import com.geometry.resources.task3.Rectangle;

/**
 * Task3业务逻辑类 - 图形面积计算
 */
public class Task3 {
    // 常量定义
    public static final String RECTANGLE = "Rectangle";
    public static final String TRIANGLE = "Triangle";
    public static final String PARALLELOGRAM = "Parallelogram";
    public static final String TRAPEZIUM = "Trapezium";
    
    // 随机数生成器
    private final Random random = new Random();
    
    // 当前图形类型
    private String currentShape;
    
    // 图形参数
    private int[] parameters;
    
    // 图形面积
    private double area;
    
    // 已处理过的图形
    private final Set<String> processedShapes = new HashSet<>();
    
    // 剩余尝试次数
    private int remainingAttempts = 3;
    
    /**
     * 选择图形并生成随机参数
     * @param shapeType 图形类型
     * @return 是否成功选择图形
     */
    public boolean selectShape(String shapeType) {
        // 重置尝试次数
        remainingAttempts = 3;
        
        // 设置当前图形
        currentShape = shapeType;
        
        // 生成随机参数
        generateRandomParameters();
        
        // 计算面积
        calculateArea();
        
        // 将图形添加到已处理集合
        processedShapes.add(shapeType);
        
        return true;
    }
    
    /**
     * 生成随机参数 (1-20范围内)
     */
    private void generateRandomParameters() {
        switch (currentShape) {
            case RECTANGLE:
                // 长方形需要长和宽两个参数
                parameters = new int[2];
                parameters[0] = random.nextInt(20) + 1; // 长度
                parameters[1] = random.nextInt(20) + 1; // 宽度
                break;
                
            case TRIANGLE:
                // 三角形需要底和高两个参数
                parameters = new int[2];
                parameters[0] = random.nextInt(20) + 1; // 底边长
                parameters[1] = random.nextInt(20) + 1; // 高
                break;
                
            case PARALLELOGRAM:
                // 平行四边形需要底和高两个参数
                parameters = new int[2];
                parameters[0] = random.nextInt(20) + 1; // 底边长
                parameters[1] = random.nextInt(20) + 1; // 高
                break;
                
            case TRAPEZIUM:
                // 梯形需要上底、下底和高三个参数
                parameters = new int[3];
                parameters[0] = random.nextInt(20) + 1; // 上底
                parameters[1] = random.nextInt(20) + 1; // 下底
                parameters[2] = random.nextInt(20) + 1; // 高
                break;
        }
    }
    
    /**
     * 计算面积
     */
    private void calculateArea() {
        switch (currentShape) {
            case RECTANGLE:
                // 长方形面积 = 长 × 宽
                area = parameters[0] * parameters[1];
                break;
                
            case TRIANGLE:
                // 三角形面积 = 底 × 高 / 2
                area = parameters[0] * parameters[1] / 2.0;
                break;
                
            case PARALLELOGRAM:
                // 平行四边形面积 = 底 × 高
                area = parameters[0] * parameters[1];
                break;
                
            case TRAPEZIUM:
                // 梯形面积 = (上底 + 下底) × 高 / 2
                area = (parameters[0] + parameters[1]) * parameters[2] / 2.0;
                break;
        }
    }
    
    /**
     * 验证用户答案
     * @param userAnswer 用户输入的答案
     * @return 是否正确
     */
    public boolean checkAnswer(double userAnswer) {
        // 检查答案是否正确，允许0.001的误差
        boolean isCorrect = Math.abs(userAnswer - area) < 1;
        
        if (isCorrect) {
            // 根据剩余尝试次数计算得分
            int attemptsUsed = 3 - remainingAttempts + 1;
            com.geometry.entity.User.addScores("Basic", attemptsUsed);
        } else {
            // 如果答案不正确，减少尝试次数
            remainingAttempts--;
        }
        
        return isCorrect;
    }
    
    /**
     * 获取当前图形类型
     * @return 图形类型
     */
    public String getCurrentShape() {
        return currentShape;
    }
    
    /**
     * 获取图形参数
     * @return 参数数组
     */
    public int[] getParameters() {
        return parameters;
    }
    
    /**
     * 获取面积
     * @return 面积
     */
    public double getArea() {
        return area;
    }
    
    /**
     * 获取剩余尝试次数
     * @return 剩余尝试次数
     */
    public int getRemainingAttempts() {
        return remainingAttempts;
    }
    
    /**
     * 检查用户是否已完成所有图形
     * @return 是否完成所有图形
     */
    public boolean isAllShapesCompleted() {
        // 四种图形：矩形、三角形、平行四边形、梯形
        String[] allShapes = {RECTANGLE, TRIANGLE, PARALLELOGRAM, TRAPEZIUM};
        
        // 检查是否所有图形都已处理
        return processedShapes.containsAll(Arrays.asList(allShapes));
    }
    
    /**
     * 获取面积公式描述
     * @return 面积公式描述
     */
    public String getAreaFormula() {
        switch (currentShape) {
            case RECTANGLE:
                return "Area = Length × Width";
                
            case TRIANGLE:
                return "Area = (Base × Height) ÷ 2";
                
            case PARALLELOGRAM:
                return "Area = Base × Height";
                
            case TRAPEZIUM:
                return "Area = ((a + b) ÷ 2) × Height";
                
            default:
                return "";
        }
    }
    
    /**
     * 获取参数名称
     * @return 参数名称数组
     */
    public String[] getParameterNames() {
        switch (currentShape) {
            case RECTANGLE:
                return new String[]{"Length", "Width"};
                
            case TRIANGLE:
                return new String[]{"Base", "Height"};
                
            case PARALLELOGRAM:
                return new String[]{"Base", "Height"};
                
            case TRAPEZIUM:
                return new String[]{"Top Length", "Bottom Length", "Height"};
                
            default:
                return new String[]{};
        }
    }
    
    /**
     * 重置状态
     */
    public void reset() {
        processedShapes.clear();
        remainingAttempts = 3;
        currentShape = null;
    }
    
    /**
     * 创建图形展示面板
     * @return 包含图形的面板
     */
    public JPanel createShapeDisplayPanel() {
        JPanel panel = new JPanel();
        
        switch (currentShape) {
            case RECTANGLE:
                // 创建长方形面板，传入长宽参数
                panel = new com.geometry.resources.task3.Rectangle.RectanglePanel(parameters[0], parameters[1]);
                break;
                
            case TRIANGLE:
                // 创建三角形面板，传入底和高参数
                panel = new Triangle.TrianglePanel(parameters[0], parameters[1]);
                break;
                
            case PARALLELOGRAM:
                // 创建平行四边形面板，传入底和高参数
                panel = new Parallelogram.ParallelogramPanel(parameters[0], parameters[1]);
                break;
                
            case TRAPEZIUM:
                // 创建梯形面板，传入上底、下底和高参数
                panel = new Trapezium.TrapeziumPanel(parameters[0], parameters[1], parameters[2]);
                break;
        }
        
        return panel;
    }
}
