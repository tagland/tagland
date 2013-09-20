package org.tagland.retro.gui.designers;

import javax.swing.*;

public abstract class Designer extends JPanel implements IDesigner {

    private Object designedObject;
    public Object getDesignedObject() {
        return designedObject;
    }
    public void setDesignedObject(Object designedObject) {
        this.designedObject = designedObject;
    }

    private IDesignerListener listener;
    public IDesignerListener getDesignerListener(){
        return listener;
    }

    public void addDesignerListener(IDesignerListener listener){
        this.listener = listener;
    }
    public void removeDesignerListener(IDesignerListener listener){
        if (this.listener == listener){
            this.listener = null;
        }
    }


}
