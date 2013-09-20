package org.tagland.retro.gui.editors;

import org.tagland.retro.gui.designers.PropertyRow;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 */
public class StringEditor extends AbstractEditor implements IEditor {
    private JTextField textField = new JTextField();
    public Component getComponent(){
        return textField;
    }
    public String getStringValue(){
        return textField.getText();
    }
    public void setPropertyRow(PropertyRow row){
        if (row != null) {
            textField.setText(row.getValueStr());
        }
    }
}
