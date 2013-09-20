package org.tagland.retro.gui;

import org.tagland.retro.gui.designers.PropertyRow;
import org.tagland.retro.gui.editors.*;
import org.tagland.util.ArrayTools;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;



/**   This class maps from a java type, such as "java.lang.String" to a Component class
 *    that can handle showing and editor or designer in a PropertyPage.
 */
public class CellEditorMapping {
    private Map<String, Component> cellEditorMap = new HashMap<String, Component>();

    
    public IEditor getDefaultComponent(PropertyRow propertyRow){
        return new ObjectPicker(true);  // false means don't create picker button.
    }

    public IEditor lookupEditor(PropertyRow propertyRow){
        String classname = propertyRow.dataClassname;
        Object oldValue = propertyRow.oldValue;
        Class dataClass = propertyRow.dataClass;
        if (dataClass != null)  {
                 Object[] enumArray = dataClass.getEnumConstants();
                 if (enumArray != null && enumArray.length > 0 && (enumArray  instanceof  Enum[])) {
                     return new EnumerationEditor((Enum[])enumArray);
                 } else if ( dataClass.isArray() ) {
                     //TODO: this is new, and probably wrong.  Notably, you depend on oldValue, which is only set for enums now.
                     int dim = ArrayTools.getDimension(dataClass);
                     return new ArrayEditor( dataClass.getComponentType(), dim);
                 }
        }
        if ((oldValue != null) && (oldValue instanceof Enum)) {
                 Enum[] enumArray = ((Enum)oldValue).getClass().getEnumConstants();
                 if (enumArray != null && enumArray.length > 0) {
                     return new EnumerationEditor(enumArray);
                 }
        }


        if (classname.equals("java.lang.String")) return new StringEditor();
        if (classname.equals("java.lang.Boolean")) return new BooleanEditor();
        if (classname.equals("java.util.Date")) return new StringEditor();
       //todo:  if (classname.equals("java.util.List")) return new EnumerationEditor();
        if (classname.equals("java.util.List")) return new org.tagland.retro.gui.editors.ListEditor();
        if (classname.equals("java.util.Map")) return new org.tagland.retro.gui.editors.MapEditor();
        return getDefaultComponent(propertyRow);
    }

}
