package org.tagland.retro.gui.editors;

import us.anarchia.obj.StorableComposite;

import javax.swing.*;
import java.awt.*;

/**
 */
public class EnumerationEditor extends AbstractEditor implements IEditor {

    public EnumerationEditor(Enum enumObj){
        this(enumObj.getClass().getEnumConstants());
        //StorableComposite.Behavior[] behaviorArray= StorableComposite.Behavior.values();
    }

    public EnumerationEditor(Enum[] enumArray){
        String[] fill_values = new String[enumArray.length];
        int i = 0;
        for (Enum behavior : enumArray){
            fill_values[i] = behavior.toString();
            i++;
        }
        component = new JComboBox(fill_values);
    }

    private Component component;

    public Component getComponent(){
        return component;
    }

    public String getStringValue(){
        return ((JComboBox)component).getSelectedItem().toString();
    }

    public static void main(String[] args) throws Exception {
        new EnumerationEditor(StorableComposite.Behavior.PRELOAD);
    }

}
