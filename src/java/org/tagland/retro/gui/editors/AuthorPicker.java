package org.tagland.retro.gui.editors;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 */
public class AuthorPicker extends ObjectPicker {
    public static JComponent create() {
        JPanel instance = new JPanel();
        instance.setLayout(new BorderLayout());
        instance.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        JButton button = new JButton();
        button.setText("...");
        instance.add(button, BorderLayout.CENTER);
        return instance;
    }

}
