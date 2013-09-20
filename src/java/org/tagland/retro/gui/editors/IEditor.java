package org.tagland.retro.gui.editors;

import org.tagland.retro.gui.designers.IDesigner;
import org.tagland.retro.gui.designers.IDesignerListener;
import org.tagland.retro.gui.designers.PropertyRow;

import javax.swing.table.TableCellEditor;
import java.awt.Component;

/**
 */
public interface IEditor extends IDesignerListener {
    public Component getComponent();
    public String getStringValue();
    public TableCellEditor getTableCellEditor();
    public void setTableCellEditor(TableCellEditor tce);
    public Object getEditedObject();
    public void setEditedObject(Object editedObject);
    /** This is the method you override in an impl class to get notified when you have a live object to show;
     *   look in row.getValue() or row.getValueStr(). */
    public void setPropertyRow(PropertyRow row);
    public PropertyRow getPropertyRow();
}
