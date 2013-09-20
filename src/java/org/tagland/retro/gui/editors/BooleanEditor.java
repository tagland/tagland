package org.tagland.retro.gui.editors;

import javax.swing.*;
import java.awt.*;

/**
 */
public class BooleanEditor extends AbstractEditor implements IEditor {
    private Component component = new JCheckBox();
    public Component getComponent(){
        return component;
    }
}
