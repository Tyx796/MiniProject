package com.geometry.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 3D shapes data entity
 */
public class Shapes3D {
    private static final Map<String, String> shapes = new HashMap<>();
    private static final String RESOURCES_PATH = "com/geometry/resources/task1_3D/";

    // map shape name to the imgs in the resources folder
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
     * Get the image path of the shape
     * @param shapeName name of the 3D shape
     * @return path to the shape image
     */
    public static String getShapeImg(String shapeName) {
        return shapes.get(shapeName);
    }
    
    /**
     * Get all available 3D shape names
     * @return set of all available 3D shape names
     */
    public static Set<String> getAvailableShapes() {
        return shapes.keySet();
    }
} 