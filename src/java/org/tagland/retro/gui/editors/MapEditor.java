package org.tagland.retro.gui.editors;

import org.tagland.retro.gui.designers.PropertyRow;
import org.tagland.util.ArrayTools;

import javax.swing.JComboBox;
import java.awt.Component;
import java.util.Iterator;
import java.util.Map;

/**
 */
public class MapEditor extends AbstractEditor implements IEditor {

    private Class arrayType;
    private int numDimensions = 0;

    public MapEditor(){
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

            Map map = (Map)value;
            Iterator it = map.entrySet().iterator();
           while (it.hasNext()){
               Map.Entry entry = (Map.Entry)it.next();
               if (entry==null) continue;
               comboBox.addItem(entry.getKey()+":"+entry.getValue());
            }
        }
    }

}
