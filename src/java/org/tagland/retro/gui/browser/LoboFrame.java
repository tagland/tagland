package org.tagland.retro.gui.browser;


import org.lobobrowser.gui.*;
import org.lobobrowser.main.*;
import javax.swing.*;

public class LoboFrame extends JFrame {
	private FramePanel framePanel;

    public LoboFrame() throws Exception {
		// Create frame with a specific size.
		// "this" is a JFrame as well as a LoboBrowserFrame.
        JFrame frame = this;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setVisible(true);
		framePanel = new FramePanel();
		frame.getContentPane().add(framePanel);
	}

    public static void init(){
        try {
            // This optional step initializes logging so only warnings are printed out.
            PlatformInit.getInstance().initLogging(false);
            // This step is necessary for extensions to work:
            PlatformInit.getInstance().init(false, false);
        } catch (Exception lbe){
            System.out.println("ERROR starting browser: "+lbe);
        }

	}

    static {
        init();
    }

    public void navigate(String url) throws Exception {
        framePanel.navigate(url);
        
    }

    public static void main(String[] args) throws Exception {
        LoboFrame frame = new LoboFrame();
        frame.navigate("http://LaramieCrocker.com");
        

    }

}
