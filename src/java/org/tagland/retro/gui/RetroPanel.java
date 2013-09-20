package org.tagland.retro.gui;


import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.tagland.retro.gui.browser.BrowserFrame;
import org.tagland.retro.gui.browser.LoboFrame;
import org.tagland.retro.gui.editors.ObjectPicker;
import us.anarchia.obj.Author;
import us.anarchia.obj.Link;
import us.anarchia.AnarchiaDBMain;

import com.dynamide.util.WebMacroTools;
import org.webmacro.Context;
import com.dynamide.util.FileTools;


public class RetroPanel extends JPanel implements ActionListener, CaretListener {
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    private RetroMain retro;

    public RetroPanel(RetroMain retro) {
        super(new GridBagLayout());

        this.retro = retro;

        textField = new JTextField(20);
        textField.addActionListener(this);

        textField.addCaretListener(this);
        textField.setText("AUTHOR");

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }

    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        textArea.append(text + newline);
        try {
            String res = fireOnText(text);
            textArea.append(res + newline);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public String newMenu(String arg) throws Exception {
        String result = "";
        MenuItem mi = new MenuItem();

        
        System.out.println("RESULT: -->" + result + "<--");
        return result;
    }

    public String newAuthor(String arg) throws Exception {
        Author au1 = new Author();
        au1.setID("Laramie");
        au1.setFamiliarName("Laramie");
        au1.setFullName("Laramie Crocker");
        Link l = new Link("http://LaramieCrocker.com", "Laramie's Website");
        au1.addWebsite(l);
        l = new Link("http://b-rant.com", "Web Rant Website");
        au1.addWebsite(l);
        l = new Link("http://LaramieCrocker.com/crazy.html", "Laramie's CDs");
        au1.addWebsite(l);
        return authorToString(au1);

    }

    public String authorToString(Author au1) throws Exception {
        String viewfile = "C:\\tagland\\src\\java\\org\\tagland\\retro\\views\\Author.wm";
        return expandTemplate("author", au1, viewfile);
    }

    public static String expandTemplate(String targetName, Object target, String template)
    throws Exception {
        String result = "";
        WebMacroTools wmt = new WebMacroTools("", "", false);
        Context c = wmt.getContext();
        try {
            c.put(targetName, target);
            String templateText = FileTools.readFile(template);
            System.out.println("template: " + templateText);
            result = wmt.expand(c, templateText);
        } finally {
            wmt.releaseContext(c);
            wmt.destroy();
        }
        //System.out.println("RESULT: -->" + result + "<--");
        return result;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI(RetroMain retro) {
        JFrame frame = SwingUtils.wrapPanelInFrame(new RetroPanel(retro), "Retro", 500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void factory(final RetroMain retro) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(retro);
            }
        });

    }


    public static void main(String[] args) {
        factory(null);
    }


    //===========================================================================
    //interface IKeyListener {
    //	public void onKey();
    //}
    private int dot = 0;
    private int mark = 0;
    private String currentLine = "";

    public void caretUpdate(CaretEvent ev) {
        dot = ev.getDot();
        mark = ev.getMark();

        String text = textField.getText();
        fireMenu(text, dot, mark);
    }

    private LoboFrame loboframe;

    public String fireOnText(String line) throws Exception {
        String t = line.toUpperCase();
        if (t.startsWith("NEW ")) {
            printMenu("NEW");
        }
        if (t.startsWith("LOBO ")) {  //and return key hit.
            try {
                loboframe = new LoboFrame();
                String destination = line.substring("LOBO ".length(), line.length());
                loboframe.navigate(destination);
            } catch (Exception e){
                System.out.println("error: "+e);
                return "Browser Failed";
            }
            return "Browser Started";
        }
        if (t.startsWith("AUTHOR")){
            try {
                Author author =  (new AnarchiaDBMain()).getAuthor();
                if (author == null){
                    return "Author was null from AnarchiaDBMain";
                }
                String authorStr = authorToString(author);
                //This worked:
                //showBrowserFrame(authorStr);
                ObjectPicker picker = new ObjectPicker();
                picker.showPropertyPageForObject(author);
            } catch (Exception e){
                System.out.println("error: "+e);
                return "Browser Failed";
            }
            return "Browser Started";
        }
        if (true){
            AnarchiaDBMain main = new AnarchiaDBMain();
            Object result = main.testMenu("CALL");
            return "Command intercepted: "+t+" result: "+result;
        }
        return "DONE";
    }

    public void showBrowserFrame(String authorStr){
        BrowserFrame frame = BrowserFrame.createTestFrame();
        frame.setHtmlSource(authorStr, "");

    }

    public void fireMenu(String text, int dot, int mark) {
        //this function is for generating hints, popups, etc.
        // It gets fired every time the caret moves or selection is changed.
    }

    public void printMenu(String key) {

        System.out.println("MENU:: " + key);
        //look-up key and find menu
        //load sub-menus from menu.
        //present menu

    }


}

