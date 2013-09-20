//Some of the code here was inspired from an article in Javaworld:
// http://www.javaworld.com/javaworld/javatips/jw-javatip102.html

package org.tagland.retro.gui.designers;


import org.tagland.retro.gui.*;
import org.tagland.retro.gui.editors.IEditor;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** A PropertyPage is a JPanel that may be wrapped into a JFrame (window);
 *  this class IS_A IDesigner, and works with an IEditor, such as ObjectPicker,
 *  and this class supports rows that have IEditors that can create yet more
 *  IDesigners, including this class, PropertyPage since this is an IDesigner.
 */
public class PropertyPage extends Designer implements IDesigner {

	private static final long serialVersionUID = 1L;

    private JFrame frame;

	public PropertyPage(){
    }
    public void createGUI(PropertyTableModel tableModel, CellEditorMapping cellEditorMapping) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        JTable jtable = new PropertyEditorTable(tableModel, cellEditorMapping);
        jtable.setRowHeight(50);
        JScrollPane scrollpane = new JScrollPane(jtable);
        add(scrollpane, BorderLayout.CENTER);
    }

    public void done(){
        if (getDesignerListener()!=null){
            getDesignerListener().onDesignerDone(this);
        }
        frame = null;
    }

    /** This is a helper method to wrap this instance in a JFrame,
     *  and also to set the window closing event to call this.done() and PropertyTableModel.done(). */
    public JFrame getPropertyPageFrame(final IEditor editor,
                                       final PropertyTableModel tableModel,
                                       CellEditorMapping cellEditorMapping,
                                       String title){
        if (frame != null){
            frame.setVisible(true);
            return frame;
        }
        createGUI(tableModel, cellEditorMapping);
        this.addDesignerListener(editor);
        frame = SwingUtils.wrapPanelInFrame(this, title, 600, 350);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                try {
                    //TODO: this should be made generic, or used as a pattern for other IDesigners.
                    tableModel.done();
                    done();
                } catch (Exception ex){
                    System.err.println("ERROR calling tableModel.done()."+ex);
                }
            }
        });
        return frame;
    }

    //============= Helper Classes ======================================================

    public static class TableCellEditorImpl extends AbstractCellEditor implements TableCellEditor {
        private IEditor editor;
        public IEditor getEditor(){
            return editor;
        }
        public TableCellEditorImpl(IEditor editor){
            this.editor = editor;
        }
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
            return editor.getComponent();
        }
        public String getCellEditorValue() {  //could be Object or String ???
            return editor.getStringValue();
        }
    }

    /**public static class TableCellEditorImpl extends AbstractCellEditor implements TableCellEditor {
        private Component component;
        public TableCellEditorImpl(Component component){
            this.component = component;
        }
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
            return component;
        }
        public  Object 	getCellEditorValue() {
            return component.toString();
        }
    } */


    public static class PropertyEditorTable extends JTable {
        private CellEditorMapping cellEditorMapping;
        @Override
        public TableCellEditor getCellEditor(int row, int col) {
            PropertyRow propertyRow = ((PropertyTableModel)getModel()).getPropertyRow(row);
            if (propertyRow !=null){
                IEditor ed = propertyRow.getEditor();
                if (ed == null){
                    ed = cellEditorMapping.lookupEditor(propertyRow);
                    //ed = new TableCellEditorImpl(editor);
                    TableCellEditorImpl tce = new TableCellEditorImpl(ed);
                    ed.setTableCellEditor(tce);
                    propertyRow.setEditor(ed);
                }
                return ed.getTableCellEditor();
            }
            return null;  //Swing will disallow editing that cell, then.  But you should have an PropertyRow for every row.
        }

        public PropertyEditorTable (PropertyTableModel model, CellEditorMapping cellEditorMapping){
            super(model);  //model IS_A PropertyTableModel IS_A TableModel
            this.cellEditorMapping = cellEditorMapping;
        }
    }


}

