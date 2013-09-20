package org.tagland.retro.gui.designers;


import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PropertyTableModel extends AbstractTableModel {
    public PropertyTableModel(List<PropertyRow> rows) {
        this.rows = rows;
    }

    public List<PropertyRow> rows;

    public int getRowCount() {
        if (rows==null)
            return 0;
        return rows.size();
    }

    public int getColumnCount() {
        return 4;  //one for "Property", one for "Value", one for PropertyType(getter,setter,field), one for Type
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Property";
            case 1:
                return "Type";
            case 2:
                return "PropertyType";
            case 3:
                return "Value";
        }
        return "ERROR: invalid column " + column;
    }

    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return rows.get(row);
            case 1:
                return ((PropertyRow)rows.get(row)).dataClassname;
            case 2:
                return ((PropertyRow)rows.get(row)).getPropertyType();
            case 3:
                return ((PropertyRow)rows.get(row)).getValueStr();
        }
        return null;
    }

    public void setValueAt(Object value, int row, int column) {
        switch (column) {
            case 0:
                return;
            case 1:
                break;
            case 2:
                break;
            case 3:
                PropertyRow rowObject =((PropertyRow)rows.get(row));
                rowObject.setValue(value);
                rowObject.fireRowChanged(IPropertyRow.CALL.OBJECT_SET);
                return;
        }
    }

    public boolean isCellEditable(int row, int col) {
        if (col == 0||col==1)
            return false;
        return true;                    // %% todo: handle custom property editor.
    }

    //===== Retro gui framework additions: ========================================

    public PropertyRow getPropertyRow(int row){
        return rows.get(row);
    }

    /** to be fired when gui PropertyPage is done. */
    public void done() throws Exception{
        for (PropertyRow row : rows){
            row.fireCommits();
            System.out.println("PropertyTableModel.done: "+row.toString());
        }

    }

}
