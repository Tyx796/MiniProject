package com.geometry.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Entity class for 3D geometric shapes.
 * Manages the collection of 3D shapes and their image resources.
 */
public class Shapes3D {
    // Map to store the relationship between 3D shape names and their image paths
    private static final Map<String, String> shapes = new HashMap<>();
    // Base path for 3D shape image resources
    private static final String RESOURCES_PATH = "com/geometry/resources/task1_3D/";

    // Static initialization block to map 3D shape names to their image paths
    static {
        shapes.put("cube", RESOURCES_PATH + "cube.png");
        shapes.put("cuboid", RESOURCES_PATH + "cuboid.png");
        shapes.put("cylinder", RESOURCES_PATH + "cylinder.png");
        shapes.put("sphere", RESOURCES_PATH + "sphere.png");
        shapes.put("cone", RESOURCES_PATH + "cone.png");
        shapes.put("tetrahedron", RESOURCES_PATH + "tetrahedron.png");
        shapes.put("triangularPrism", RESOURCES_PATH + "triangularPrism.png");
        shapes.put("squareBasedPyramid", RESOURCES_PATH + "squareBasedPyramid.png");
    }

    /**
     * Get the image path for a specific 3D shape
     * @param shapeName Name of the 3D shape
     * @return Path to the shape's image resource
     */
    public static String getShapeImg(String shapeName) {
        return shapes.get(shapeName);
    }
    
    /**
     * Get all available 3D shape names
     * @return Set of all available 3D shape names
     */
    public static Set<String> getAvailableShapes() {
        return shapes.keySet();
    }
} 