/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
*/
/*
 * Created on Oct 22, 2005
 */
package org.tagland.retro.gui.browser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.net.*;
import java.util.logging.*;

//import org.lobobrowser.html.gui.*;
//import org.lobobrowser.html.*;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.gui.SelectionChangeEvent;
import org.lobobrowser.html.gui.SelectionChangeListener;
import org.lobobrowser.html.test.SimpleHtmlRendererContext;
import org.lobobrowser.html.test.SimpleUserAgentContext;

/**
 * A Swing frame that can be used to test the Lobo / Cobra HTML rendering engine.
 * There is also a org.lobobrowser.html.test.TestFrame.
 * tagland uses it as a pop-up browser window.
 */
public class BrowserFrame extends JFrame {	
	private static final Logger logger = Logger.getLogger(BrowserFrame.class.getName());
    private SimpleHtmlRendererContext rcontext;
    public SimpleHtmlRendererContext getRcontext() {
        return rcontext;
    }
    
	private final JTree tree;

	private final JTextArea textArea;
	private final JTextField addressField;
    private HtmlPanel htmlPanel;
    public HtmlPanel getHtmlPanel() {
        return htmlPanel;
    }

	public BrowserFrame() throws HeadlessException {
		this("");
	}
	
	public BrowserFrame(String title) throws HeadlessException {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,350);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		final JTextField textField = new JTextField();
		this.addressField = textField;
		JButton button = new JButton("Parse & Render");
		final JTabbedPane tabbedPane = new JTabbedPane();
		final JTree tree = new JTree();
		final JScrollPane scrollPane = new JScrollPane(tree);
		
		this.tree = tree;
		
		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(bottomPanel, BorderLayout.CENTER);
		
		topPanel.add(new JLabel("URL: "), BorderLayout.WEST);
		topPanel.add(textField, BorderLayout.CENTER);
		topPanel.add(button, BorderLayout.EAST);
		
		bottomPanel.add(tabbedPane, BorderLayout.CENTER);
		
		final HtmlPanel panel = new HtmlPanel();
		panel.addSelectionChangeListener(new SelectionChangeListener() {
			public void selectionChanged(SelectionChangeEvent event) {
				if(logger.isLoggable(Level.INFO)) {
					logger.info("selectionChanged(): selection node: " + panel.getSelectionNode());
				}
			}
		});
		this.htmlPanel = panel;	
		UserAgentContext ucontext = new SimpleUserAgentContext();
		this.rcontext = new LocalHtmlRendererContext(panel, ucontext);
		
		final JTextArea textArea = new JTextArea();
		this.textArea = textArea;
		textArea.setEditable(false);
		final JScrollPane textAreaSp = new JScrollPane(textArea);
		
		tabbedPane.addTab("HTML", panel);
		tabbedPane.addTab("Tree", scrollPane);
		tabbedPane.addTab("Source", textAreaSp);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Component component = tabbedPane.getSelectedComponent();
				if(component == scrollPane) {
					tree.setModel(new NodeTreeModel(panel.getRootNode()));
				}
				else if(component == textAreaSp) {
					textArea.setText(rcontext.getSourceCode());
				}
			}
		});
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				process(textField.getText());
			}
		});
	}
	
	public HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}
	
	public void navigate(String uri) {
		this.addressField.setText(uri);
		this.process(uri);
	}
	
	private void process(String uri) {
		try {
			URL url;
			try {
				url = new URL(uri);
			} catch(java.net.MalformedURLException mfu) {
				int idx = uri.indexOf(':');
				if(idx == -1 || idx == 1) {
					// try file
					url = new URL("file:" + uri);
				}
				else {
					throw mfu;
				}
			}
			// Call SimpleHtmlRendererContext.navigate()
			// which implements incremental rendering.
			this.rcontext.navigate(url, null);
		} catch(Exception err) {
			logger.log(Level.SEVERE, "Error trying to load URI=[" + uri + "].", err);
		}
	}

    public void setHtmlSource(String source, String uriOptional){
        HtmlRendererContext ctx = this.getHtmlRendererContext();
        //String source = "<html><body>Hello, Lobo!</body></html>";
        this.getHtmlPanel().setHtml(source, //java.lang.String htmlSource,
                          uriOptional,    //java.lang.String uri,
                          ctx);   //HtmlRendererContext rcontext)
    }

    public static BrowserFrame createTestFrame(){
        BrowserFrame testframe = new BrowserFrame();
        testframe.setVisible(true);
        return testframe;
    }


	private class LocalHtmlRendererContext extends SimpleHtmlRendererContext {
		public LocalHtmlRendererContext(HtmlPanel contextComponent, UserAgentContext ucontext) {
			super(contextComponent, ucontext);
		}

		public HtmlRendererContext open(URL url, String windowName, String windowFeatures, boolean replace) {
			BrowserFrame frame = new BrowserFrame("Cobra Test Tool");
			frame.setSize(600, 400);
			frame.setExtendedState(BrowserFrame.NORMAL);
			frame.setVisible(true);
			HtmlRendererContext ctx = frame.getHtmlRendererContext();
			ctx.setOpener(this);
			frame.navigate(url.toExternalForm());
			return ctx;
		}
	}
}