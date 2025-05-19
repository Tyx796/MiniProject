package com.geometry.entity;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity class for angle resources.
 * Manages angle images for different degree values.
 */
public class Angles {
    // Map to store the relationship between angle degrees and their image paths
    private static final Map<Integer, String> angleImages = new HashMap<>();

    // Private constructor to prevent instantiation
    private Angles(){}
    
    // Static initialization block to map angle degrees to their image paths
    static {
        // Configure angle image resources for 0-350 degrees (increments of 10)
        for (int degree = 0; degree <= 350; degree += 10) {
            angleImages.put(degree, "com/geometry/resources/task2/angle_" + degree + ".png");
        }
    }
    
    /**
     * Get the image path for a specific angle
     * 
     * @param degrees Angle value (should be a multiple of 10, range 0-350)
     * @return Path to the angle image resource
     */
    public static String getAngleImagePath(int degrees) {
        // Normalize angle to the 0-350 range
        int normalizedDegree = degrees % 360;
        if (normalizedDegree < 0) {
            normalizedDegree += 360;
        }
        
        // Round to the nearest multiple of 10
        int roundedDegree = Math.round(normalizedDegree / 10.0f) * 10;
        if (roundedDegree == 360) {
            roundedDegree = 0;
        }
        
        return angleImages.get(roundedDegree);
    }
    
    /**
     * Get an ImageIcon for a specific angle
     * 
     * @param degrees Angle value
     * @return ImageIcon object, or null if the image cannot be found
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
                System.err.println("Could not find angle image resource: " + imagePath);
            }
        } catch (Exception e) {
            System.err.println("Failed to load angle image: " + imagePath);
            e.printStackTrace();
        }
        
        return null;
    }
} 