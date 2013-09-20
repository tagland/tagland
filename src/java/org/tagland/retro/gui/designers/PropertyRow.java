package org.tagland.retro.gui.designers;

import org.tagland.retro.gui.RowListener;
import org.tagland.retro.gui.editors.IEditor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyRow implements IPropertyRow {
    public static class Event {
        public CALL call;
        public Object inputObject;
        public Object outputObject;
        public Object sender;
        public PropertyRow row;
    }
    public static class StringCell implements RowListener {
        public void onRowEvent(PropertyRow.Event event){
            PropertyRow row = event.row;
            if (   event.call == CALL.STRING_SET ){
                row.newValue = event.inputObject;
            } else if ( event.call == CALL.OBJECT_SET ){
                Object obj = event.inputObject;
                if (obj!=null){
                    row.newValueStr = obj.toString();
                } else {
                    row.newValue = null;
                    row.newValueStr = "";
                }
            }
        }
    }

    public String id = "";
    public String dataClassname = "";
    public Class dataClass = null;
    public String oldValueStr = "";
    public String newValueStr = "";
    public Object oldValue;
    public Object newValue;
    public Field field;  //contains the actual field object.
    public Method getMethod;
    public Method setMethod;
    public String error = "";
    public Object target; //the owner of the Field.
    public RowListener rowListener = new StringCell();

    private IEditor editor;
    public IEditor getEditor() {
        return editor;
    }
    public void setEditor(IEditor editor) {
        this.editor = editor;
        editor.setPropertyRow(this);
    }

    public boolean dirty = false;

    public boolean getDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public Object getValue() {
        if (dirty) {
            return newValue;
        } else {
            return oldValue;
        }
    }

    public String getValueStr() {
        if (dirty) {
            return newValueStr;
        } else {
            return oldValueStr;
        }
    }

    public void setValueStr(String value) {
        newValueStr = value;
        dirty = true;
        fireRowChanged(CALL.STRING_SET);
    }

    public void setValue(Object value) {
        newValue = value;
        dirty = true;
        fireRowChanged(CALL.OBJECT_SET);
    }

    public void fireRowChanged(CALL call) {
        if (rowListener != null) {
            Event ev = new Event();
            ev.sender = this;
            if (call == CALL.OBJECT_SET) {
                ev.inputObject = newValue;
            } else {
                ev.inputObject = newValueStr;
            }
            ev.row = this;
            ev.call = call;
            rowListener.onRowEvent(ev);
        }
    }

    public Object fireGetMethod() throws Exception {
        if (target != null && getMethod != null) {
            return getMethod.invoke(target);
        }
        return null;
    }

    public Object fireSetMethod(Object arg) throws Exception {
        if (target != null && setMethod != null) {
            return setMethod.invoke(target, arg);
        }
        return null;
    }

    public Object fireSetField(Object arg) throws Exception {
        if (target != null && field != null) {
            field.set(target, arg);
        }
        return null;
    }

    public void fireCommits() throws Exception {
        if (dirty){
            fireSetField(newValue);
            fireSetMethod(newValue);
        }
    }

    public String getPropertyType(){
        String type = "";
        if (getMethod != null){
            type = "getter";
        } else if (setMethod != null){
            type = "setter";
        } else if (this.field != null){
            type = "field";
        }
        return type;
    }

    public String toString() {
        String oldValueToString = "", newValueToString = "";
        try {
            oldValueToString = oldValue == null ? "" : oldValue.toString();
            newValueToString = newValue == null ? "" : newValue.toString();
        } catch (Throwable t) {
            System.err.println("ERROR converting oldValue or newValue to String" + t);
        }

        return id + ": " + dataClassname + "; old:" + oldValueStr + ':' + oldValueToString + "; new:" + newValueStr + ':' + newValueToString + "; dirty=" + dirty+";type="+getPropertyType();
    }
}

