package com.geometry.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Shapes2D {
    private static final Map<String, String> shapes = new HashMap<>();
    private static final String RESOURCES_PATH = "com/geometry/resources/task1_2D/";

    // map shape name to the imgs in the resources folder
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

    // get the img path of the shape
    public static String getShapeImg(String shapeName) {
        return shapes.get(shapeName);
    }
    
    /**
     * Get all available shape names
     * @return set of all available shape names
     */
    public static Set<String> getAvailableShapes() {
        return shapes.keySet();
    }
}
