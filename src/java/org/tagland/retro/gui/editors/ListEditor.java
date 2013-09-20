package org.tagland.retro.gui.editors;

import org.tagland.retro.gui.designers.PropertyRow;
import org.tagland.util.ArrayTools;

import javax.swing.JComboBox;
import java.awt.Component;
import java.util.List;

/**
 */
public class ListEditor extends AbstractEditor implements IEditor {

    private Class arrayType;
    private int numDimensions = 0;

    public ListEditor(){

        comboBox = new JComboBox();
    }

    private JComboBox comboBox;

    public Component getComponent(){
        return comboBox;
    }

    public String getStringValue(){
        Object foo =  comboBox.getSelectedItem();
        if (foo != null) {
           return foo.toString();
        }
        return "";
    }

    public void setPropertyRow(PropertyRow row){
        if (row != null) {
            Object value = row.getValue();
            if (value==null || row.dataClass == null) return;

            comboBox.removeAllItems();

            List list = (List)value;
            for (Object item: list) {
                 if (item==null) continue;
                comboBox.addItem(item);
            }
        }
    }




}
