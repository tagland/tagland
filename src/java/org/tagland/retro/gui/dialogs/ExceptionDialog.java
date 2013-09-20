package org.tagland.retro.gui.dialogs;

import com.dynamide.util.Tools;

import javax.swing.*;
import java.awt.*;

/**
 */
public class ExceptionDialog {
    public ExceptionDialog(String caption, Throwable t, boolean printStackTrace){
        String trace = "";
        if (t!=null){
            if (printStackTrace){
                trace = Tools.errorToString(t, true, true);
            }   else {
                trace = t.toString();
            }
        }
        frame = new JFrame();
        frame.setSize(400,350);
        frame.setTitle("ERROR");
        JTextArea area = new JTextArea();
        area.setPreferredSize(new Dimension(500, 500));
        frame.add(area);
        area.setText(caption+"\r\n"+trace);
    }
    JFrame frame;
    public JFrame show(){
        frame.setVisible(true);
        return frame;
    }
    public static void test(){
        try {
            throw new Exception("test exception");
        } catch (Exception e){
            ExceptionDialog exframe =  new ExceptionDialog("CopyrightPicker.createDesigner", e, true);
            exframe.show();
        }
    }
}
