package org.tagland.retro.gui.designers;

/**
 */
public interface IPropertyRow {
    public enum CALL {
        ROW_CHANGED,
        STRING_SET,
        OBJECT_SET,
        ERROR
    }
    public boolean getDirty();
    public void setDirty(boolean dirty);
    public Object getValue();
    public String getValueStr();
    public void setValue(Object value);
    public void setValueStr(String value);
    public void fireRowChanged(CALL call);
}
