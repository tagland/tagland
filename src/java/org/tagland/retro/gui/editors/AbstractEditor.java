package org.tagland.retro.gui.editors;

import com.dynamide.util.Tools;
import org.tagland.retro.gui.designers.IDesigner;
import org.tagland.retro.gui.designers.PropertyRow;
import org.tagland.retro.gui.dialogs.ExceptionDialog;

import javax.swing.*;
import javax.swing.table.TableCellEditor;

/**
 */
public abstract class AbstractEditor extends JPanel implements IEditor {

    private TableCellEditor tableCellEditor;
    public TableCellEditor getTableCellEditor(){
        return tableCellEditor;
    }
    public void setTableCellEditor(TableCellEditor ed){
        tableCellEditor = ed;
    }
    /** Empty impl, sub-classes should override.*/
    public void onDesignerDone(IDesigner designer){
    }

    public String getStringValue(){
        return "AbstractEditor";
    }
    private Object editedObject;
    public Object getEditedObject() {
        return editedObject;
    }
    public void setEditedObject(Object editedObject) {
        this.editedObject = editedObject;
    }
    /** The caller of this function is responsible for calling row.setEditor(editor) after this call to complete the two-way link. */
    public void setPropertyRow(PropertyRow row){
        propertyRow = row;
    }
    private PropertyRow propertyRow;
    public PropertyRow getPropertyRow(){
        return propertyRow;
    }
    


}
