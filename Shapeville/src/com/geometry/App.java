package com.geometry;

import com.geometry.ui.MainFrame;
import javax.swing.SwingUtilities;

/**
 * 几何学习应用程序主类
 */
public class App {
    
    /**
     * 程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
} 