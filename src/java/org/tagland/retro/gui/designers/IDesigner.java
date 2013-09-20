package org.tagland.retro.gui.designers;

/**
There are three styles of creating a Designer:
 <ol>
 <li> you create a PropertyPage and tell it its designedObject, as a java bean.
      This generically operates on fields, getters, and setters.</li>
 <li> you create a specialized Designer that can modify its designedObject</li>
 <li> you create a specialized Designer that can be a factory for its designedObject</li>
 </ol>
 PropertyPage.getPropertyPageFrame() is the first of these variants.

 In all cases, the caller (an IEditor) calls setDesignedObject() on input,
 and calls getDesignedObject() on output.  The IDesigner may nullify and replace
 the designed object prior to firing onDesignerDone().
*/

public interface IDesigner {
    //public void showDesigner(IEditor editor);
    public Object getDesignedObject();
    public void setDesignedObject(Object designedObject);
    public void addDesignerListener(IDesignerListener listener);
    public void removeDesignerListener(IDesignerListener listener);
}
