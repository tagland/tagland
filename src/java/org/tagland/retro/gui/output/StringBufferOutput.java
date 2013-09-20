package org.tagland.retro.gui.output;

import java.lang.StringBuffer;

public class StringBufferOutput implements Output {
	public StringBufferOutput(StringBuffer buff) {
		m_buff = buff;
	}
	private StringBuffer m_buff;
	public Output print(String s){
		m_buff.append(s);
		return this;
	}
	public String toString(){
		return m_buff.toString();
	}
}