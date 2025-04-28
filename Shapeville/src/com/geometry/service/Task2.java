package com.geometry.service;

import com.geometry.entity.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角度识别任务业务逻辑
 */
public class Task2 {
    // 角度类型常量
    public static final String ACUTE_ANGLE = "acute";
    public static final String RIGHT_ANGLE = "right";
    public static final String OBTUSE_ANGLE = "obtuse";
    public static final String STRAIGHT_ANGLE = "straight";
    public static final String REFLEX_ANGLE = "reflex";
    
    // 角度类型与度数范围的映射
    private final List<String> angleTypes = Arrays.asList(
        ACUTE_ANGLE, RIGHT_ANGLE, OBTUSE_ANGLE, STRAIGHT_ANGLE, REFLEX_ANGLE
    );
    
    // 已完成的角度类型
    private Set<String> completedTypes = new HashSet<>();
    
    // 当前角度
    private int currentAngle = 0;
    
    // 当前尝试次数
    private int attempts = 0;
    
    // 最大尝试次数
    private int maxAttempts = 3;
    
    // 当前角度类型
    private String currentAngleType = "";
    
    // 正确回答数
    private int correctCount = 0;
    
    // 需要完成的角度类型数
    private int requiredTypes = 4;
    
    /**
     * 设置当前角度（用户输入）
     * @param angle 用户输入的角度
     */
    public void setCurrentAngle(int angle) {
        this.currentAngle = angle;
        this.attempts = 0;
        this.currentAngleType = getAngleType(angle);
    }
    
    /**
     * 根据角度值获取角度类型
     * @param angle 角度值
     * @return 角度类型
     */
    public String getAngleType(int angle) {
        if (angle == 0) return ACUTE_ANGLE; // 特殊情况，0度当作锐角处理
        if (angle > 0 && angle < 90) return ACUTE_ANGLE;
        if (angle == 90) return RIGHT_ANGLE;
        if (angle > 90 && angle < 180) return OBTUSE_ANGLE;
        if (angle == 180) return STRAIGHT_ANGLE;
        if (angle > 180 && angle < 360) return REFLEX_ANGLE;
        if (angle == 360) return STRAIGHT_ANGLE; // 特殊情况，360度当作平角处理
        return "";
    }
    
    /**
     * 检查用户输入的角度类型是否正确
     * @param answer 用户回答的角度类型
     * @return 是否正确
     */
    public boolean checkAnswer(String answer) {
        boolean isCorrect = answer.equalsIgnoreCase(currentAngleType);
        
        if (isCorrect) {
            // 添加分数
            int attemptsUsed = maxAttempts - getRemainingAttempts() + 1;
            User.addScores("Basic", attemptsUsed);
            
            // 记录已完成的角度类型
            completedTypes.add(currentAngleType);
            correctCount++;
            
            return true;
        } else {
            attempts++;
            return false;
        }
    }
    
    /**
     * 获取当前角度
     * @return 当前角度
     */
    public int getCurrentAngle() {
        return currentAngle;
    }
    
    /**
     * 获取当前角度类型
     * @return 当前角度类型
     */
    public String getCurrentAngleType() {
        return currentAngleType;
    }
    
    /**
     * 获取已尝试次数
     * @return 已尝试次数
     */
    public int getAttempts() {
        return attempts;
    }
    
    /**
     * 获取剩余尝试次数
     * @return 剩余尝试次数
     */
    public int getRemainingAttempts() {
        return maxAttempts - attempts;
    }
    
    /**
     * 获取正确回答次数
     * @return 正确回答次数
     */
    public int getCorrectCount() {
        return correctCount;
    }
    
    /**
     * 检查任务是否完成
     * @return 是否完成
     */
    public boolean isTaskCompleted() {
        return completedTypes.size() >= requiredTypes || completedTypes.size() >= angleTypes.size();
    }
    
    /**
     * 获取当前用户得分
     * @return 当前得分
     */
    public int getCurrentScore() {
        return User.getScores();
    }
    
    /**
     * 获取所有可用的角度类型
     * @return 角度类型列表
     */
    public List<String> getAngleTypes() {
        return new ArrayList<>(angleTypes);
    }
    
    /**
     * 获取角度类型的显示名称
     * @param type 角度类型
     * @return 显示名称
     */
    public String getAngleTypeName(String type) {
        switch (type) {
            case ACUTE_ANGLE:
                return "Acute Angle";
            case RIGHT_ANGLE:
                return "Right Angle";
            case OBTUSE_ANGLE:
                return "Obtuse Angle";
            case STRAIGHT_ANGLE:
                return "Straight Angle";
            case REFLEX_ANGLE:
                return "Reflex Angle";
            default:
                return "Unknown";
        }
    }
    
    /**
     * 获取角度类型的描述
     * @param type 角度类型
     * @return 描述
     */
    public String getAngleTypeDescription(String type) {
        switch (type) {
            case ACUTE_ANGLE:
                return "less than 90° and greater than 0°";
            case RIGHT_ANGLE:
                return "equal to 90°";
            case OBTUSE_ANGLE:
                return "less than 180° and greater than 90°";
            case STRAIGHT_ANGLE:
                return "equal to 180°";
            case REFLEX_ANGLE:
                return "greater than 180° and less than 360°";
            default:
                return "";
        }
    }
    
    /**
     * 获取角度对应的图像
     * @param degrees 角度值
     * @return 角度图像
     */
    public javax.swing.ImageIcon getAngleImage(int degrees) {
        return com.geometry.entity.Angles.getAngleIcon(degrees);
    }
}