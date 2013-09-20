package org.tagland.retro.gui.output;
/** Base class of an output template method, that also accepts an object that 
 *  defines a strategy for layout in the output medium.  Concrete subclasses
 *  include HtmlStrategy, which defines a table layout using HTML, and a
 *  StdioStrategy which defines newlines and tabs.  
 * 
 * 	The sequence for outputing a table of objects by a template would be: 
 *    
    blockBegin
	   linestart
	   	object, separator, [..., object], 
	   lineend
	   newline
    blockEnd
 */  
   	

public class Strategy{
    public String newline="\r\n";
    public String linestart="";
    public String lineend="";
    public String separator="";
    public String blockBegin="";
    public String blockEnd="";
    public String placeholder=" ";
    public String indentString=" ";
    public int indentLevel = 0;
    public void incIndent(){indentLevel++;}
    public void decIndent(){indentLevel--;}
    public String getIndent(){
    	StringBuffer sb = new StringBuffer();
    	for (int i=0; i<indentLevel; i++){
    		sb.append(indentString);
    	}
    	return sb.toString();
    }
	public String lineStartWithIndent() {
		return getIndent()+linestart;
	}
}
