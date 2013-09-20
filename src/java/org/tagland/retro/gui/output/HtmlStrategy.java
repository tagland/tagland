package org.tagland.retro.gui.output;
    public class HtmlStrategy extends Strategy {
		public static final String DUMPHTML_START = "<table border='1' cellspacing='0' cellpadding='2'>\r\n";
		public static final String DUMPHTML_NEWLINE = "<br />\r\n";
		public static final String DUMPHTML_LINESTART = "<tr><td>";
		public static final String DUMPHTML_LINEEND = "</td></tr>";
	    public static final String DUMPHTML_ELEMSEP = "</td><td>";
	    public static final String DUMPHTML_OBJPLACEHOLDER = "&#160;";
	    public static final String DUMPHTML_END = "</td></tr></table>\r\n";
	    public HtmlStrategy() {
	    	lineend = DUMPHTML_LINEEND;
	    	linestart = DUMPHTML_LINESTART;
	    	separator = DUMPHTML_ELEMSEP;
	    	blockEnd = DUMPHTML_END;
	    	blockBegin = DUMPHTML_START;
	    	newline = DUMPHTML_NEWLINE;
	    	placeholder = DUMPHTML_OBJPLACEHOLDER;	    	
	    }
	    
	    
	    
	}