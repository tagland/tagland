package org.tagland.retro.gui.editors;

import org.tagland.retro.gui.designers.Designer;
import us.anarchia.obj.Copyright;

import java.awt.event.ActionListener;

/**
 */
public class CopyrightPicker extends ObjectPicker implements ActionListener, IEditor {
    public CopyrightPicker(){
        super(true);  //calls createGUI(hasButton)
    }

    private Copyright copyright;

    public String getStringValue(){
        if (copyright!=null){
            return copyright.toString();
        }
        return "";
    }

    public static IEditor create() {
        CopyrightPicker instance = new CopyrightPicker();
        return instance;
    }

    @Override
    public Designer createDesigner() throws Exception {
        if (copyright==null){
            copyright = new Copyright();
            setEditedObject(copyright);
        }
        Designer des = showPropertyPageForObject(copyright);
        return des;
    }


}
