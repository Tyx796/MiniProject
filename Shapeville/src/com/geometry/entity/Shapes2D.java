package com.geometry.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Entity class for 2D geometric shapes.
 * Manages the collection of 2D shapes and their image resources.
 */
public class Shapes2D {
    // Map to store the relationship between shape names and their image paths
    private static final Map<String, String> shapes = new HashMap<>();
    // Base path for 2D shape image resources
    private static final String RESOURCES_PATH = "com/geometry/resources/task1_2D/";

    // Static initialization block to map shape names to their image paths
    static {
        shapes.put("circle", RESOURCES_PATH + "circle.png");
        shapes.put("heptagon", RESOURCES_PATH + "heptagon.png");
        shapes.put("hexagon", RESOURCES_PATH + "hexagon.png");
        shapes.put("kite", RESOURCES_PATH + "kite.png");
        shapes.put("octagon", RESOURCES_PATH + "octagon.png");
        shapes.put("oval", RESOURCES_PATH + "oval.png");
        shapes.put("pentagon", RESOURCES_PATH + "pentagon.png");
        shapes.put("rectangle", RESOURCES_PATH + "rectangle.png");
        shapes.put("rhombus", RESOURCES_PATH + "rhombus.png");
        shapes.put("square", RESOURCES_PATH + "square.png");
        shapes.put("triangle", RESOURCES_PATH + "triangle.png");
    }

    /**
     * Get the image path for a specific 2D shape
     * @param shapeName Name of the 2D shape
     * @return Path to the shape's image resource
     */
    public static String getShapeImg(String shapeName) {
        return shapes.get(shapeName);
    }
    
    /**
     * Get all available 2D shape names
     * @return Set of all available 2D shape names
     */
    public static Set<String> getAvailableShapes() {
        return shapes.keySet();
    }
}
