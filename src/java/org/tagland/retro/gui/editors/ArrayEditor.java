package org.tagland.retro.gui.editors;

import org.tagland.retro.gui.designers.PropertyRow;
import org.tagland.util.ArrayTools;
import us.anarchia.obj.Copyright;
import us.anarchia.obj.StorableComposite;

import javax.swing.*;
import java.awt.*;

/**
 */
public class ArrayEditor extends AbstractEditor implements IEditor {

    private Class arrayType;
    private int numDimensions = 0;

    public ArrayEditor(Class arrayType, int dimensions){
        this.arrayType = arrayType;
        this.numDimensions = dimensions;
        //only deal with 1-d arrays now...
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
            if ( ! row.dataClass.isArray()) return;             //belt AND suspenders.
            if ( ! (value instanceof Object[]))  return;

            comboBox.removeAllItems();
            Object[] arr  = (Object[])value;
            for (Object cell: arr) {
                 if (cell==null) continue;
                comboBox.addItem(cell);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        String[] foo = {"First", "Second", "Third"};
        int dim = ArrayTools.getDimension(foo);
        Class fooClass = foo.getClass();
        Class ct = fooClass.getComponentType();
        new ArrayEditor(ct, dim);
    }

}
