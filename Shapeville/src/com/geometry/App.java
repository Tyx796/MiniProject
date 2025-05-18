package com.geometry;

import com.geometry.ui.MainFrame;
import javax.swing.SwingUtilities;

/**
 * Main class for the geometry learning application
 */
public class App {
    /**
     * Program entry point
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true); // Make the main frame visible
        });
    }
} 