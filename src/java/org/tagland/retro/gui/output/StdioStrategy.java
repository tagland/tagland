package org.tagland.retro.gui.output;

public class StdioStrategy extends Strategy {
	public StdioStrategy(){
		newline = "\r\n";
		separator = "\t";
		placeholder = " ";
	}
}