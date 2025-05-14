package com.geometry.ui.uiUtils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Color scheme manager for the application
 * Supports normal vision, red-green colorblind, and blue-yellow colorblind modes
 */
public class ColorScheme {
    // Scheme types
    public static final int NORMAL_VISION = 0;
    public static final int RED_GREEN_COLORBLIND = 1;
    public static final int BLUE_YELLOW_COLORBLIND = 2;
    
    // Named colors for use throughout the application
    public static final String PRIMARY = "primary";
    public static final String SECONDARY = "secondary";
    public static final String SUCCESS = "success";
    public static final String WARNING = "warning";
    public static final String DANGER = "danger";
    public static final String INFO = "info";
    public static final String BACKGROUND = "background";
    public static final String BUTTON_NORMAL = "buttonNormal";
    public static final String BUTTON_HOVER = "buttonHover";
    public static final String BUTTON_PRESSED = "buttonPressed";
    public static final String TEXT_PRIMARY = "textPrimary";
    public static final String TEXT_SECONDARY = "textSecondary";
    public static final String BORDER_PRIMARY = "borderPrimary";
    
    // The current active color scheme
    private static int currentScheme = NORMAL_VISION;
    
    // Color maps for each scheme
    private static final Map<String, Color> normalColors = new HashMap<>();
    private static final Map<String, Color> redGreenColorblindColors = new HashMap<>();
    private static final Map<String, Color> blueYellowColorblindColors = new HashMap<>();
    
    static {
        // Initialize normal vision colors
        normalColors.put(PRIMARY, new Color(52, 119, 219));       // Blue
        normalColors.put(SECONDARY, new Color(81, 222, 232));     // Cyan
        normalColors.put(SUCCESS, new Color(0, 150, 0));          // Green
        normalColors.put(WARNING, new Color(255, 140, 0));        // Orange
        normalColors.put(DANGER, new Color(230, 80, 100));        // Red
        normalColors.put(INFO, new Color(0, 192, 239));           // Light Blue
        normalColors.put(BACKGROUND, new Color(220, 240, 255));   // Light Blue Background
        normalColors.put(BUTTON_NORMAL, new Color(197, 197, 193));// Default button
        normalColors.put(BUTTON_HOVER, new Color(255, 140, 150)); // Hover color
        normalColors.put(BUTTON_PRESSED, new Color(230, 80, 100));// Pressed color
        normalColors.put(TEXT_PRIMARY, Color.WHITE);              // White text
        normalColors.put(TEXT_SECONDARY, Color.BLACK);            // Black text
        normalColors.put(BORDER_PRIMARY, new Color(70, 130, 180));// SteelBlue border
        
        // Initialize red-green colorblind friendly colors
        redGreenColorblindColors.put(PRIMARY, new Color(0, 105, 180));      // Blue
        redGreenColorblindColors.put(SECONDARY, new Color(0, 170, 230));    // Lighter blue
        redGreenColorblindColors.put(SUCCESS, new Color(0, 114, 178));      // Blue instead of green
        redGreenColorblindColors.put(WARNING, new Color(230, 159, 0));      // Yellow/orange
        redGreenColorblindColors.put(DANGER, new Color(213, 94, 0));        // Orange/brown instead of red
        redGreenColorblindColors.put(INFO, new Color(86, 180, 233));        // Sky blue
        redGreenColorblindColors.put(BACKGROUND, new Color(225, 238, 255)); // Light blue background
        redGreenColorblindColors.put(BUTTON_NORMAL, new Color(180, 180, 180)); // Gray
        redGreenColorblindColors.put(BUTTON_HOVER, new Color(230, 159, 0));    // Yellow/orange
        redGreenColorblindColors.put(BUTTON_PRESSED, new Color(213, 94, 0));   // Orange/brown
        redGreenColorblindColors.put(TEXT_PRIMARY, Color.WHITE);               // White text
        redGreenColorblindColors.put(TEXT_SECONDARY, Color.BLACK);             // Black text
        redGreenColorblindColors.put(BORDER_PRIMARY, new Color(0, 114, 178));  // Blue border
        
        // Initialize blue-yellow colorblind friendly colors
        blueYellowColorblindColors.put(PRIMARY, new Color(148, 103, 189));     // Purple
        blueYellowColorblindColors.put(SECONDARY, new Color(188, 128, 189));   // Lighter purple
        blueYellowColorblindColors.put(SUCCESS, new Color(23, 190, 187));      // Teal instead of green
        blueYellowColorblindColors.put(WARNING, new Color(214, 39, 40));       // Red instead of orange/yellow
        blueYellowColorblindColors.put(DANGER, new Color(227, 73, 101));       // Pink/red
        blueYellowColorblindColors.put(INFO, new Color(140, 86, 178));         // Purple
        blueYellowColorblindColors.put(BACKGROUND, new Color(240, 228, 245));  // Light purple background
        blueYellowColorblindColors.put(BUTTON_NORMAL, new Color(180, 180, 180)); // Gray
        blueYellowColorblindColors.put(BUTTON_HOVER, new Color(227, 73, 101));   // Pink/red
        blueYellowColorblindColors.put(BUTTON_PRESSED, new Color(214, 39, 40));  // Red
        blueYellowColorblindColors.put(TEXT_PRIMARY, Color.WHITE);               // White text
        blueYellowColorblindColors.put(TEXT_SECONDARY, Color.BLACK);             // Black text
        blueYellowColorblindColors.put(BORDER_PRIMARY, new Color(148, 103, 189)); // Purple border
    }
    
    /**
     * Get color from current scheme
     * @param colorName The name of the color to retrieve
     * @return The Color object
     */
    public static Color getColor(String colorName) {
        Map<String, Color> currentColorMap = getCurrentColorMap();
        Color color = currentColorMap.get(colorName);
        return color != null ? color : Color.BLACK; // Default to black if color not found
    }
    
    /**
     * Get the current color map based on active scheme
     * @return Map of color names to Color objects
     */
    private static Map<String, Color> getCurrentColorMap() {
        switch (currentScheme) {
            case RED_GREEN_COLORBLIND:
                return redGreenColorblindColors;
            case BLUE_YELLOW_COLORBLIND:
                return blueYellowColorblindColors;
            case NORMAL_VISION:
            default:
                return normalColors;
        }
    }
    
    /**
     * Set the active color scheme
     * @param schemeType The scheme type to activate
     */
    public static void setColorScheme(int schemeType) {
        if (schemeType >= NORMAL_VISION && schemeType <= BLUE_YELLOW_COLORBLIND) {
            currentScheme = schemeType;
        }
    }
    
    /**
     * Get the current scheme type
     * @return The active scheme type
     */
    public static int getCurrentScheme() {
        return currentScheme;
    }
    
    /**
     * Get scheme name from type
     * @param schemeType The scheme type
     * @return The scheme name
     */
    public static String getSchemeName(int schemeType) {
        switch (schemeType) {
            case RED_GREEN_COLORBLIND:
                return "Red-Green Colorblind";
            case BLUE_YELLOW_COLORBLIND:
                return "Blue-Yellow Colorblind";
            case NORMAL_VISION:
            default:
                return "Normal Vision";
        }
    }
} 