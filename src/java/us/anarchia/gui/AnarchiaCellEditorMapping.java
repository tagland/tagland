package us.anarchia.gui;

import org.tagland.retro.gui.CellEditorMapping;
import org.tagland.retro.gui.designers.PropertyRow;
import org.tagland.retro.gui.editors.CopyrightPicker;
import org.tagland.retro.gui.editors.IEditor;

public class AnarchiaCellEditorMapping extends CellEditorMapping {
    @Override
    public IEditor lookupEditor(PropertyRow propertyRow){
        String classname = propertyRow.dataClassname;
        if (classname.equals("Copyright")){
            return CopyrightPicker.create();
        }
        return super.lookupEditor(propertyRow);
    }
}
