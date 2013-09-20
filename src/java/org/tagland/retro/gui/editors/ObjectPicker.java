package org.tagland.retro.gui.editors;

import org.tagland.retro.gui.*;
import org.tagland.retro.gui.designers.*;
import org.tagland.retro.gui.dialogs.ExceptionDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.util.List;

/** ObjectPicker is the IEditor to PropertyPage as IDesigner
 */
public class ObjectPicker extends Picker implements ActionListener, IEditor, FocusListener {
    public ObjectPicker(){
        createGUI(false);
    }

    public ObjectPicker(boolean hasButton){
        createGUI(hasButton);
    }

    protected void createGUI(boolean hasButton){
        setLayout(new BorderLayout());
        //instance.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
       //this worked, but didn't trap VK_ENTER:  edit = new NoTabTextField();// JTextField();
        edit = useKeyListener();
        add(edit, BorderLayout.CENTER);
        edit.addActionListener(this);
        edit.addFocusListener(this);
        //patch(edit);
        if (hasButton){
            JButton button = new JButton();
            button.setText("...");
            button.setPreferredSize(new Dimension(50, 50));
            add(button, BorderLayout.LINE_END);
            button.addActionListener(this);
        }
        component = this;
    }

    private JTextField edit;

    private Component component;
    public Component getComponent(){
        return component;
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
        //You don't need to do these from here any more, because we trap VK_TAB in  useKeyListener()
        //updateValueFromTextField(edit.getText()) ;
    }

    protected void updateValueFromTextField(String value){
                PropertyPage.TableCellEditorImpl tcei = (PropertyPage.TableCellEditorImpl)(this.getTableCellEditor());
                IEditor ed = tcei.getEditor();
                PropertyRow row = ed.getPropertyRow() ;
                row.setValueStr(value);
    }

    public void actionPerformed(ActionEvent event) {

        //todo: see why this won't work for  a String[]. Probably no one called setEditedObject
        try {
            if (event.getSource()==edit){
                String newValue = event.getActionCommand();
                updateValueFromTextField(edit.getText());

            } else if (getEditedObject() == null){
                PropertyPage.TableCellEditorImpl tcei = (PropertyPage.TableCellEditorImpl)(this.getTableCellEditor());
                IEditor ed = tcei.getEditor();
                PropertyRow row = ed.getPropertyRow() ;

                Class [] classParm = null;
                Object [] objectParm = null;

                String name = row.dataClassname;
                Class cl = Class.forName(name);
                Constructor co = cl.getConstructor(classParm);
                Object instance = co.newInstance(objectParm);

                setEditedObject(instance);
                getDesigner().setVisible(true);
            }

        } catch (Exception ex){
            ExceptionDialog exframe =  new ExceptionDialog("ObjectPicker.getDesigner", ex, true);
            exframe.show();
        }
    }

    private JPanel designerPanel;  //ultimately, we should be able to grab the JPanel without a top-level window,
    // so we can put the panel in other containers.
    // For now, just return the JFrame and let it show itself.

    private Designer designer;
    public Designer getDesigner() throws Exception {
        //TODO: we don't nullify this correctly, so this test is meaningless: if (designer==null){
            
            designer = createDesigner();
            designer.addDesignerListener(this);
        //}
        return designer;
    }

    public Designer createDesigner() throws Exception {
        Designer des = showPropertyPageForObject(getEditedObject());
        return des;
    }

    public Designer showPropertyPageForObject(Object obj) throws Exception{
        CellEditorMapping mapping = new CellEditorMapping();
        return showPropertyPageForObject(obj, mapping);
    }

    /** This is a big, magic method that shows a PropertyPage (IDesigner) for
     *  any Object, and currently "knows" about us.anarchia.obj.* objects. TODO: update this javadoc when anarchia-specific behavior is abstracted.
     */
    public Designer showPropertyPageForObject(Object obj, CellEditorMapping mapping ) throws Exception{
        setEditedObject(obj);
        List<PropertyRow> listPropertyRows = BeanProperties.fieldsToRows(obj, true);
        for (PropertyRow row : listPropertyRows){
            row.rowListener = new PropertyRow.StringCell();
        }
        PropertyTableModel model = new PropertyTableModel(listPropertyRows);



        String title = obj.getClass().getName()+" :: PropertyPage";
        PropertyPage propPage = new PropertyPage();
        propPage.setDesignedObject(obj);
        JFrame frame = propPage.getPropertyPageFrame(this, model, mapping, title);
        frame.setVisible(true);
        return propPage;
    }

    @Override
    public void onDesignerDone(IDesigner designer){
        edit.setText(designer.getDesignedObject().toString()); //should point at the same thing as getStringValue().
        designer = null;
    }
    //public void onDesignerDone(IDesigner designer){
    //    System.out.println("==== ObjectPicker root === onDesignerDone: "+designer);
    //}

    public String getStringValue(){
        if (getEditedObject()==null){
            return "";
        } else {
            return getEditedObject().toString();
        }
    }


    //=============== Helper to create a JTextField that does VK_ENTER and VK_TAB correctly ===========================
//If you use a JTextArea, then you don't want the scroll thumb to get focus, so use this:
    //            scrollPane.getVerticalScrollBar().setFocusable(false);

    // There is a massive class that handles some of the key handling in JTextField:
    //  http://stackoverflow.com/questions/5042429/how-can-i-modify-the-behavior-of-the-tab-key-in-a-jtextarea     //  Use a KeyListener
     //Here is a part of it:
    // Could generify this: private JComponent useKeyListener() {
    private JTextField useKeyListener() {
        final JTextField textField = new JTextField();
        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    updateValueFromTextField(textField.getText()) ;
                    e.consume();
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                }
                if (e.getKeyCode() == KeyEvent.VK_TAB && e.isShiftDown()) {
                    updateValueFromTextField(textField.getText()) ;
                    e.consume();
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    updateValueFromTextField(textField.getText()) ;
                     e.consume();
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                }
            }
        });
        return textField;
    }

    //==================== KRUFT =========================================
    //    protected static void patch(Component c) {
    //        Set<KeyStroke>
    //        strokes = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("pressed TAB")));
    //        c.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, strokes);
    //        strokes = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("shift pressed TAB")));
    //        c.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, strokes);
    //    }

    //    public class NoTabTextField extends JTextField {
    //        public boolean isManagingFocus() {
    //            return false;
    //        }
    //     }








}
