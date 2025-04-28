package com.geometry.entity;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;

/**
 * 角度图片资源管理类
 */
public class Angles {
    // 存储角度图像的Map
    private static final Map<Integer, String> angleImages = new HashMap<>();
    
    // 初始化角度图像路径映射
    static {
        // 配置角度图像资源路径 (0-350度，每10度一个)
        for (int degree = 0; degree <= 350; degree += 10) {
            angleImages.put(degree, "com/geometry/resources/task2/angle_" + degree + ".png");
        }
    }
    
    /**
     * 根据角度值获取对应的图像路径
     * 
     * @param degrees 角度值 (必须是10的倍数，范围0-350)
     * @return 图像资源路径
     */
    public static String getAngleImagePath(int degrees) {
        // 标准化角度到0-350范围
        int normalizedDegree = degrees % 360;
        if (normalizedDegree < 0) {
            normalizedDegree += 360;
        }
        
        // 四舍五入到最接近的10的倍数
        int roundedDegree = Math.round(normalizedDegree / 10.0f) * 10;
        if (roundedDegree == 360) {
            roundedDegree = 0;
        }
        
        return angleImages.get(roundedDegree);
    }
    
    /**
     * 根据角度获取图像图标
     * 
     * @param degrees 角度值
     * @return 图像图标，如果不存在则返回null
     */
    public static ImageIcon getAngleIcon(int degrees) {
        String imagePath = getAngleImagePath(degrees);
        if (imagePath == null) {
            return null;
        }
        
        try {
            java.net.URL imageURL = Angles.class.getClassLoader().getResource(imagePath);
            if (imageURL != null) {
                return new ImageIcon(imageURL);
            } else {
                System.err.println("找不到角度图像资源: " + imagePath);
            }
        } catch (Exception e) {
            System.err.println("无法加载角度图像: " + imagePath);
            e.printStackTrace();
        }
        
        return null;
    }
} 