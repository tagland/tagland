package org.tagland.retro.gui;

import javax.swing.*;

/**
 */
public class SwingUtils {

    /** For your main window, you'll want to set this: frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
     * because we don't do that here.  This method also sets the frame visible.
     */
    public static JFrame wrapPanelInFrame(JPanel panel, String caption, int width, int height){
        JFrame frame = new JFrame(caption);
        frame.setSize(width, height);
        frame.add(panel);
        //another way to do this: frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    public static JFrame wrapPanelInFrame(JFrame frame, JPanel panel, String caption, int width, int height){
        frame.setTitle(caption);
        frame.setSize(width, height);
        frame.add(panel);
        //another way to do this: frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }




}
